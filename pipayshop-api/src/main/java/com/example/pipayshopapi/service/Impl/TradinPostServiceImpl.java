package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.TradinJournal;
import com.example.pipayshopapi.entity.TradinPost;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ImageMapper;
import com.example.pipayshopapi.mapper.TradinJournalMapper;
import com.example.pipayshopapi.mapper.TradinPostMapper;
import com.example.pipayshopapi.service.TradinPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.StringUtil;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import net.coobird.thumbnailator.Thumbnails;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wzx
 * @since 2023-09-09
 */
@Service
public class TradinPostServiceImpl extends ServiceImpl<TradinPostMapper, TradinPost> implements TradinPostService {

    @Resource
    private TradinPostMapper tradinPostMapper;
    @Resource
    private ImageMapper imageMapper;
    @Resource
    private AccountInfoMapper accountInfoMapper;
    @Resource
    private TradinJournalMapper tradinJournalMapper;

    @Resource
    private RedissonClient redissonClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int publishTradition(String token) {
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String publisherId = dataFromToken.get("publisherId", String.class);
        Integer typeId = dataFromToken.get("typeId", Integer.class);
        String content = dataFromToken.get("content", String.class);
        BigDecimal pointBalance = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("pointBalance", String.class)));
        BigDecimal piBalance = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("piBalance", String.class)));
        String tradinId = StringUtil.generateShortId();

        int insert = 0;
        // 发布者换积分
        if (typeId == 0){
            String journalId=StringUtil.generateShortId();
            // 日志属性填充
            TradinJournal tradinJournal = new TradinJournal(journalId,publisherId,typeId,pointBalance,piBalance);
            // 插入数据
            insert += tradinJournalMapper.insert(tradinJournal);
            insert += tradinPostMapper.insert(new TradinPost(tradinId, publisherId, typeId, content, pointBalance, piBalance,journalId));
          if (insert < 2){
              throw new BusinessException("插入失败失败");
          }
          // 发布者换pi币
        } else if (typeId == 1) {
          // 获取发布者账户
          AccountInfoVO accountInfoVO = accountInfoMapper.selectAccountInfo(publisherId);
          if (accountInfoVO.getPointBalance().compareTo(pointBalance) < 0) {
              throw new BusinessException("账户余额不足");
          }

          // 计算积分
          BigDecimal oldPointBalance = accountInfoVO.getPointBalance();
          BigDecimal newPointBalance = oldPointBalance.subtract(pointBalance);
          String journalId=StringUtil.generateShortId();
          String piAddress = dataFromToken.get("piAddress", String.class);
          // 日志属性填充
          TradinJournal tradinJournal = new TradinJournal(journalId,publisherId,typeId,pointBalance,piBalance,oldPointBalance,newPointBalance);
          insert +=tradinJournalMapper.insert(tradinJournal);
          insert += accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>().eq("uid", publisherId).set("point_balance", newPointBalance));
          insert += tradinPostMapper.insert(new TradinPost(tradinId,publisherId,typeId,content,pointBalance,piBalance,piAddress,journalId));
          if (insert < 3){
              throw new BusinessException("插入失败");
          }
        }
        return insert;
    }

    @Override
    public PageDataVO selectTraditionList(Integer typeId, Integer page, Integer limit) {
        Integer count=tradinPostMapper.selectTraditionListCount(typeId);
        List<TraditionListVO> traditionVOS = tradinPostMapper.selectTraditionList(typeId,(page-1)*limit,limit);
        for (TraditionListVO traditionVO : traditionVOS) {
            traditionVO.setUserImage(imageMapper.selectPath(traditionVO.getUserImage()));
        }
        PageDataVO pageDataVO = new PageDataVO(count,traditionVOS);
        return pageDataVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TraditionDetailVO selectTraditionDetail(String tradinId) throws InterruptedException {
        // 获取锁（可重入），指定锁的名称
        RLock lock = redissonClient.getLock(tradinId);
        // 尝试获取锁，参数分别是：获取锁的最大等待时间（期间会重试），锁自动释放时间，时间单位
        boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);
        TraditionDetailVO traditionDetailVO = null;
        // 判断释放获取成功
        if(isLock){
            try {
                //执行业务需求
                TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>()
                                                                    .eq("tradin_id", tradinId)
                                                                    .select("status","tradin_id","id"));
                // 判断是否有人查看
                if (tradinPost.getStatus() != 0){
                    // 有人在查看
                    return null;
                }
                // 上锁让其他人不能访问 更新状态，
                tradinPost.setStatus(1);
                int updateById = tradinPostMapper.updateById(tradinPost);
                if (updateById < 1){
                    throw new BusinessException("更新状态失败");
                }
                // 返回数据
                traditionDetailVO = tradinPostMapper.selectTraditionDetail(tradinId);
                traditionDetailVO.setUserImage(imageMapper.selectPath(traditionDetailVO.getUserImage()));
            }finally {
                // 释放锁
                lock.unlock();
            }
        }
        return  traditionDetailVO;
    }

    @Override
    public void updateStatusByTradinId(String tradinId) {
        TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>()
                .eq("tradin_id", tradinId)
                .select("status","tradin_id","id"));
        if (tradinPost.getStatus() == 1){
            tradinPost.setStatus(0);
            tradinPostMapper.updateById(tradinPost);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upLoadImg(MultipartFile file, String token) {
        if (file.isEmpty()) {
            throw new BusinessException("文件不能为空") ;
        }
        String fileName = file.getOriginalFilename();
        if ("".equals(fileName)) {
            throw new BusinessException("文件名不能为空") ;
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
        String tradinId = dataFromToken.get("tradinId", String.class);
        // 此交易发布者需要换pi币
        if (typeId == 1){
            String traderUid = dataFromToken.get("traderUid", String.class);
            // 查询交易
            TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>().eq("tradin_id", tradinId));
            if (typeId != tradinPost.getTypeId()){
                throw new BusinessException("类型不一样") ;
            }
            // 获取交易日志
            TradinJournal tradinJournal = tradinJournalMapper.selectOne(new QueryWrapper<TradinJournal>()
                                                    .eq("journal_id", tradinPost.getJournalId()));

            // 更改交易状态为交易中
            tradinPost.setStatus(2);
            tradinPost.setImageUrl("/images/tradin_post_certificate/"+ThumbnailPath);
            tradinPost.setTraderUid(traderUid);
            int update = tradinPostMapper.updateById(tradinPost);
            // 更新日志
            tradinJournal.setTraderUid(traderUid);
            update+=tradinJournalMapper.updateById(tradinJournal);
            if (update < 2){
                throw new BusinessException("提交失败") ;
            }

            // 发布者需要上传交易凭证给交易者检查
        } else if (typeId== 0) {
            // 查询交易
            TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>().eq("tradin_id", tradinId));
            if (typeId != tradinPost.getTypeId()){
                throw new BusinessException("类型不一样") ;
            }
            // 上传交易凭证图片
            tradinPost.setImageUrl("/images/tradin_post_certificate/"+ThumbnailPath);
            int update = tradinPostMapper.updateById(tradinPost);
            if (update < 1){
                throw new BusinessException("提交失败") ;
            }

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
            return true;
        } catch (Exception e) {
            if (destFile.exists()){
                destFile.delete();
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upLoadPointBalance(String token) {
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        Integer typeId = dataFromToken.get("typeId", Integer.class);
        String tradinId = dataFromToken.get("tradinId", String.class);
        String traderUid = dataFromToken.get("traderUid", String.class);
        String piAddress = dataFromToken.get("piAddress", String.class);
        // 此交易发布者需要积分
        if (typeId == 0){
            // 查询交易信息
            TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>().eq("tradin_id", tradinId));

            if (typeId != tradinPost.getTypeId()){
                throw new BusinessException("类型不一样") ;
            }
            // 交易需要的积分
            BigDecimal pointBalance = tradinPost.getPointBalance();
            // 获取交易者账户信息
            AccountInfoVO traderAccountInfoVO = accountInfoMapper.selectAccountInfo(traderUid);
            BigDecimal oldPointBalance = traderAccountInfoVO.getPointBalance();
            if (oldPointBalance.compareTo(pointBalance) < 0){
                throw new BusinessException("余额不足") ;
            }
            // 计算积分
            BigDecimal newPointBalance = oldPointBalance.subtract(pointBalance);
            // 日志属性补全
            TradinJournal tradinJournal = tradinJournalMapper.selectOne(new QueryWrapper<TradinJournal>().eq("journal_id", tradinPost.getJournalId()));
            // 记录交易者的积分变化
            tradinJournal.setTraderPointBalanceBefore(oldPointBalance);
            tradinJournal.setTraderPointBalanceAfter(newPointBalance);
            tradinJournal.setTraderUid(traderUid);
            // 改变交易状态
            tradinPost.setStatus(2);
            tradinPost.setTraderUid(traderUid);
            tradinPost.setPiAddress(piAddress);
            // 更新日志信息 交易者账户 交易状态
            int update = tradinJournalMapper.updateById(tradinJournal);
            update+=accountInfoMapper.update(null,new UpdateWrapper<AccountInfo>()
                                                    .eq("uid",traderUid)
                                                    .set("point_balance",newPointBalance));
            update += tradinPostMapper.updateById(tradinPost);
            if (update < 3){
                throw new BusinessException("更新失败") ;
            }
            return true;
        }
        return false;
    }

    @Override
    public List<TraditionListVO> selectTradinPostByUid(String token) {
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String userId = dataFromToken.get("userId", String.class);
        List<TraditionListVO>  traditionListVOS=tradinPostMapper.selectTradinPostByUid(userId);
        for (TraditionListVO traditionVO : traditionListVOS) {
            traditionVO.setUserImage(imageMapper.selectPath(traditionVO.getUserImage()));
        }
        return traditionListVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmTransaction(String token) {
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String userId = dataFromToken.get("userId", String.class);
        String tradinId = dataFromToken.get("tradinId", String.class);
        // 查询交易
        TradinPost tradinPost = tradinPostMapper.selectOne(new QueryWrapper<TradinPost>().eq("tradin_id", tradinId));

        // 交易类型为发布者需要pi币
        if (tradinPost.getTypeId() == 1){
            // 查看userId是否是这次交易的发布者uid
            if (! tradinPost.getPublisherUid().equals(userId)) {
                throw new BusinessException("发布者uid不匹配") ;
            }
            // 获取交易者账户
            AccountInfo accountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>().eq("uid", tradinPost.getTraderUid()));
            BigDecimal oldPointBalance = accountInfo.getPointBalance();
            BigDecimal newPointBalance = oldPointBalance.add(tradinPost.getPointBalance());
            accountInfo.setPointBalance(newPointBalance);
            // 更新发布者账户
            int update = accountInfoMapper.updateById(accountInfo);
            // 改变交易状态
            tradinPost.setStatus(3);
            update += tradinPostMapper.updateById(tradinPost);
            // 更新日志
            TradinJournal tradinJournal = tradinJournalMapper.selectOne(new QueryWrapper<TradinJournal>().eq("journal_id", tradinPost.getJournalId()));
            tradinJournal.setTraderPointBalanceBefore(oldPointBalance);
            tradinJournal.setTraderPointBalanceAfter(newPointBalance);
             update += tradinJournalMapper.updateById(tradinJournal);
            if (update < 3) {
                throw new BusinessException("更新失败") ;
            }
            return true;
         // 交易类型为发布者需要积分
        }else if (tradinPost.getTypeId() == 0){
            // 查看userId是否是这次交易的交易者uid
            if (! tradinPost.getTraderUid().equals(userId)) {
                throw new BusinessException("交易者uid不匹配") ;
            }
            // 获取发布者账户
            AccountInfo accountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>().eq("uid", tradinPost.getPublisherUid()));
            BigDecimal oldPointBalance = accountInfo.getPointBalance();
            BigDecimal newPointBalance = oldPointBalance.add(tradinPost.getPointBalance());
            accountInfo.setPointBalance(newPointBalance);
            // 更新发布者账户
            int update = accountInfoMapper.updateById(accountInfo);
            // 改变交易状态
            tradinPost.setStatus(3);
            update +=tradinPostMapper.updateById(tradinPost);
            // 更新日志
            TradinJournal tradinJournal = tradinJournalMapper.selectOne(new QueryWrapper<TradinJournal>().eq("journal_id", tradinPost.getJournalId()));
            tradinJournal.setPublisherPointBalanceBefore(oldPointBalance);
            tradinJournal.setPublisherPointBalanceAfter(newPointBalance);
            update += tradinJournalMapper.updateById(tradinJournal);
            if (update < 3) {
                throw new BusinessException("更新失败") ;
            }
            return true;
        }
        return false;
    }

    @Override
    public DealDetailVO selectDealDetail(String tradinId) {
        DealDetailVO  dealDetailVO = tradinPostMapper.selectDealDetail(tradinId);
        dealDetailVO.setPublisherImage(imageMapper.selectPath(dealDetailVO.getPublisherImage()));
        if (dealDetailVO.getTraderUid() != null){
            dealDetailVO.setTraderImage(imageMapper.selectPath(dealDetailVO.getTraderImage()));
        }
        return dealDetailVO;
    }
}
