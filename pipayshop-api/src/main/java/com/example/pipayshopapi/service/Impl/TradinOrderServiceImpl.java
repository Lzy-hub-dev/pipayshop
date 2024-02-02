package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.TradinJournal;
import com.example.pipayshopapi.entity.TradinOrder;
import com.example.pipayshopapi.entity.TradinPost;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.entity.vo.TradinOrderDetailVO;
import com.example.pipayshopapi.entity.vo.TradinOrderListVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.TradinOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.StringUtil;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wzx
 * @since 2023-09-19
 */
@Service
public class TradinOrderServiceImpl extends ServiceImpl<TradinOrderMapper, TradinOrder> implements TradinOrderService {

    @Resource
    private TradinOrderMapper tradinOrderMapper;
    @Resource
    private TradinPostMapper tradinPostMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private TradinJournalMapper tradinJournalMapper;

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateTradeOrder(String tradinId, String buyerId) {

        // 获取发布详情
        TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>()
                                                            .eq("tradin_id", tradinId));
        // 判断是否有人已经下单了
        if (tradinPost.getStatus() != 0){
            throw new BusinessException("已经下单了");
        }

        // 乐观锁基于版本号更新
        // 更新版本号
        int update = tradinPostMapper.update(null, new UpdateWrapper<TradinPost>()
                .setSql("status=status+1")
                .eq("tradin_id", tradinId)
                .eq("status", tradinPost.getStatus()));
        if (update < 1){
            // 更新版本号失败,已经给人抢先下单了，慢了一步返回null
            throw new BusinessException("已经下单了");
        }
        // 生成订单
        String orderId= StringUtil.generateShortId();
        TradinOrder tradinOrder = null;
        if (tradinPost.getTypeId() == 0){
            // 订单状态为0:开始交易
            tradinOrder=new TradinOrder(orderId,
                    tradinPost.getPublisherUid(),
                    buyerId,
                    tradinPost.getTypeId(),
                    tradinPost.getPiBalance(),
                    tradinPost.getPointBalance());
        }else if (tradinPost.getTypeId() == 1){
            // 订单状态为1:提交积分/地址
            tradinOrder = new TradinOrder(orderId,
                    tradinPost.getPublisherUid(),
                    buyerId,
                    tradinPost.getTypeId(),
                    tradinPost.getPiBalance(),
                    tradinPost.getPointBalance(),
                    tradinPost.getPiAddress(),
                    1);
        }
        int insert =tradinOrderMapper.insert(tradinOrder);
        // 改变交易状态为交易中，
        tradinPost.setStatus(2);
        tradinPost.setOrderId(orderId);
        insert += tradinPostMapper.updateById(tradinPost);
        if (insert < 2){
            throw new BusinessException("更新/插入失败");
        }
        return orderId;

   }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upLoadPointBalance(String token) {
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        Integer typeId = dataFromToken.get("typeId", Integer.class);
        String orderId = dataFromToken.get("orderId", String.class);

        // 此交易发布者需要积分
        if (typeId == 0){
            String buyerId = dataFromToken.get("buyerId", String.class);
            String piAddress = dataFromToken.get("piAddress", String.class);
            // 查询订单信息
            TradinOrder tradinOrder = tradinOrderMapper.selectOne(new QueryWrapper<TradinOrder>()
                    .eq("order_id", orderId));
               if (typeId != tradinOrder.getTypeId()){
                throw new BusinessException("类型不一样") ;
            }

            // 交易需要的积分
            BigDecimal pointBalance = tradinOrder.getPointBalance();
            // 获取交易者账户信息
            AccountInfoVO traderAccountInfoVO = accountInfoMapper.selectAccountInfo(buyerId);
            BigDecimal availableBalance = traderAccountInfoVO.getAvailableBalance();
            if (availableBalance.compareTo(pointBalance) < 0){
                throw new BusinessException("余额不足") ;
            }
            // 计算积分 冻结积分
            BigDecimal subtractPointBalance = availableBalance.subtract(pointBalance);
            BigDecimal frozenBalance = traderAccountInfoVO.getFrozenBalance().add(pointBalance);
            // 改变订单状态为提交积分和写入钱包地址
            tradinOrder.setPiAddress(piAddress);
            tradinOrder.setStatus(1);
            //  交易者账户 订单状态和钱包地址
           int update=accountInfoMapper.update(null,new UpdateWrapper<AccountInfo>()
                    .eq("uid",buyerId)
                    .set("available_balance",subtractPointBalance)
                   .set("frozen_balance",frozenBalance));
            update += tradinOrderMapper.updateById(tradinOrder);
            if (update < 2){
                throw new BusinessException("更新失败") ;
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
        public boolean upLoadImg(MultipartFile file, String token) {
        if (file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        if ("".equals(fileName)) {
            throw new BusinessException("文件名不能为空");
        }
        String separator = File.separator;
        String postStr = fileName.substring(fileName.lastIndexOf(".")+1);
        String preStr = StringUtil.generateShortId();
        fileName = "temp-"+preStr + "."+ postStr;
        String ThumbnailPath=preStr + "."+ postStr;
        File readPath = new File(Constants.CERTIFICATE_IMAG_PATH + separator );
        if (!readPath.isDirectory()) {
            readPath.mkdirs();
        }
        // 将文件复制到指定路径
        File destFile = new File(readPath.getAbsolutePath()+ separator + fileName);

        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        Integer typeId = dataFromToken.get("typeId", Integer.class);
        String orderId = dataFromToken.get("orderId", String.class);
        String userId = dataFromToken.get("userId", String.class);

        String confirmUserId= null;

        // 查询订单，判断交易类型是否一致
        TradinOrder tradinOrder = tradinOrderMapper.selectOne(new QueryWrapper<TradinOrder>()
                .eq("order_id", orderId));
        if (typeId != tradinOrder.getTypeId()){
            throw new BusinessException("类型不一样") ;
        }
        // 判断是否是对应的人提交凭证
        if ( tradinOrder.getTypeId() == 0){

            if (!userId.equals(tradinOrder.getSellerId()) ){
                throw new BusinessException("提交凭证对象不对") ;
            }
            confirmUserId=tradinOrder.getBuyerId();
        }
        else if(tradinOrder.getTypeId() == 1) {

            if (!userId.equals(tradinOrder.getBuyerId())){
                throw new BusinessException("提交凭证对象不对") ;
            }
            confirmUserId=tradinOrder.getSellerId();
        }

        // 上传图片和改订单状态为2:提交凭证图片
        tradinOrder.setImageUrl("/images/tradin_post_certificate/"+ThumbnailPath);
        tradinOrder.setStatus(2);
        // 更改订单状态和提交图片
        int update = tradinOrderMapper.updateById(tradinOrder);

        if (update < 1){
            throw new BusinessException("提交失败") ;
        }


        try {
            // 移动temp图片到目标文件夹
            FileCopyUtils.copy(file.getBytes(), destFile);
            String absolutePath = new File(Constants.CERTIFICATE_IMAG_PATH+separator + ThumbnailPath).getAbsolutePath();
            // 压缩图片
            Thumbnails.of(Constants.CERTIFICATE_IMAG_PATH+separator+fileName)
                    .size(300, 300)
                    .outputFormat(postStr)
                    .toFile(absolutePath);
            // 删除temp图片
            if (destFile.exists()){
                destFile.delete();
            }

        } catch (Exception e) {
            if (destFile.exists()){
                destFile.delete();
                throw new BusinessException("提交失败") ;
            }
            e.printStackTrace();
        }
        Map<String, String> confirmOrder = new HashMap<>();
        confirmOrder.put("userId",confirmUserId);
        confirmOrder.put("orderId",orderId);
        String orderMap = JSON.toJSONString(confirmOrder);
        rabbitTemplate.convertAndSend(Constants.DELAYED_EXCHANGE_NAME, Constants.DELAYED_ROUTING_KEY, orderMap,
                correlationData ->{
                    // 自动确认订单设为7天
                    correlationData.getMessageProperties().setDelay(604800000);
                    return correlationData;
        });
        return true;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmTransaction(String token) {

        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String userId = dataFromToken.get("userId", String.class);
        String orderId = dataFromToken.get("orderId", String.class);
        // 查询订单
        TradinOrder tradinOrder = tradinOrderMapper.selectOne(new QueryWrapper<TradinOrder>().eq("order_id",orderId));
        AccountInfo buyerAccountInfo=null;
        AccountInfo sellerAccountInfo = null;
        TradinJournal tradinJournal=null;
        String journalId = StringUtil.generateShortId();
        // 交易类型为发布者需要pi币
        if (tradinOrder.getTypeId() == 1){
            // 查看userId是否是这次交易的发布者uid
            if (! tradinOrder.getSellerId().equals(userId)) {
                throw new BusinessException("卖家uid不匹配") ;
            }
            // 获取卖家账户 并扣除总积分和冻结积分
            sellerAccountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>().eq("uid", tradinOrder.getSellerId()));
            BigDecimal sellerSubtractFrozenBalance = sellerAccountInfo.getFrozenBalance().subtract(tradinOrder.getPointBalance());
            BigDecimal sellerOldPointBalance = sellerAccountInfo.getPointBalance();
            BigDecimal sellerNewPointBalance = sellerOldPointBalance.subtract(tradinOrder.getPointBalance());
            sellerAccountInfo.setPointBalance(sellerNewPointBalance);
            sellerAccountInfo.setFrozenBalance(sellerSubtractFrozenBalance);
            // 获取买家账户 加积分和可用积分
            buyerAccountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>().eq("uid", tradinOrder.getBuyerId()));
            BigDecimal buyerOldPointBalance = buyerAccountInfo.getPointBalance();
            BigDecimal buyerNewPointBalance = buyerOldPointBalance.add(tradinOrder.getPointBalance());
            BigDecimal newAvailableBalance = buyerAccountInfo.getAvailableBalance().add(tradinOrder.getPointBalance());
            buyerAccountInfo.setPointBalance(buyerNewPointBalance);
            buyerAccountInfo.setAvailableBalance(newAvailableBalance);
            // 改变订单状态为交易完成
            tradinOrder.setStatus(3);
            // 添加日志
            tradinJournal = new TradinJournal(journalId,
                    tradinOrder.getSellerId(),
                    tradinOrder.getBuyerId(),
                    tradinOrder.getTypeId(),
                    tradinOrder.getPointBalance(),
                    tradinOrder.getPiBalance(),
                    sellerOldPointBalance,
                    sellerNewPointBalance,
                    buyerOldPointBalance,
                    buyerNewPointBalance);
            tradinOrder.setJournalId(journalId);

            // 交易类型为发布者需要积分
        }else if (tradinOrder.getTypeId() == 0){
            // 查看userId是否是这次交易的买家uid
            if (! tradinOrder.getBuyerId().equals(userId)) {
                throw new BusinessException("买家uid不匹配") ;
            }
            // 获取买家账户 并扣除总积分和冻结积分
            buyerAccountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>().eq("uid", tradinOrder.getBuyerId()));
            BigDecimal buyerSubtractFrozenBalance = buyerAccountInfo.getFrozenBalance().subtract(tradinOrder.getPointBalance());
            BigDecimal buyerOldPointBalance = buyerAccountInfo.getPointBalance();
            BigDecimal buyerNewPointBalance = buyerOldPointBalance.subtract(tradinOrder.getPointBalance());
            buyerAccountInfo.setPointBalance(buyerNewPointBalance);
            buyerAccountInfo.setFrozenBalance(buyerSubtractFrozenBalance);
            // 获取卖家账户 并加积分和可用积分
            sellerAccountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>().eq("uid", tradinOrder.getSellerId()));
            BigDecimal sellerOldPointBalance = sellerAccountInfo.getPointBalance();
            BigDecimal sellerNewPointBalance = sellerOldPointBalance.add(tradinOrder.getPointBalance());
            BigDecimal newAvailableBalance = sellerAccountInfo.getAvailableBalance().add(tradinOrder.getPointBalance());
            sellerAccountInfo.setPointBalance(sellerNewPointBalance);
            sellerAccountInfo.setAvailableBalance(newAvailableBalance);
            // 改变订单状态为交易完成
            tradinOrder.setStatus(3);
            // 添加日志
            tradinJournal = new TradinJournal(journalId,
                    tradinOrder.getSellerId(),
                    tradinOrder.getBuyerId(),
                    tradinOrder.getTypeId(),
                    tradinOrder.getPointBalance(),
                    tradinOrder.getPiBalance(),
                    sellerOldPointBalance,
                    sellerNewPointBalance,
                    buyerOldPointBalance,
                    buyerNewPointBalance);
            tradinOrder.setJournalId(journalId);

        }
        // 更新交易账户
        int update = accountInfoMapper.updateById(sellerAccountInfo);
        update += accountInfoMapper.updateById(buyerAccountInfo);
        // 添加日志
        update += tradinJournalMapper.insert(tradinJournal);
        // 更新订单为3:交易完成 和日志外键
        update += tradinOrderMapper.updateById(tradinOrder);
        if (update < 4) {
            throw new BusinessException("更新失败") ;
        }
        return true;
    }

    @Override
    public List<TradinOrderListVO> selectTradinyOrderByUid(String token) {
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String userId = dataFromToken.get("userId", String.class);
        List<TradinOrderListVO>  tradinyOrderListVOS=tradinOrderMapper.selectTradinyOrderByUid(userId);
        for (TradinOrderListVO traditionVO : tradinyOrderListVOS) {
            traditionVO.setUserImage(imageMapper.selectPath(traditionVO.getUserImage()));
        }
        return tradinyOrderListVOS;
    }

    @Override
    public TradinOrderDetailVO selectTradinOrderDetail(String orderId) {
        TradinOrderDetailVO tradinOrderDetailVO = tradinOrderMapper.selectTradinOrderDetail(orderId);
        tradinOrderDetailVO.setSellerImageUrl(imageMapper.selectPath(tradinOrderDetailVO.getSellerImageUrl()));
        tradinOrderDetailVO.setBuyerImageUrl(imageMapper.selectPath(tradinOrderDetailVO.getBuyerImageUrl()));
        return tradinOrderDetailVO;

    }
}
