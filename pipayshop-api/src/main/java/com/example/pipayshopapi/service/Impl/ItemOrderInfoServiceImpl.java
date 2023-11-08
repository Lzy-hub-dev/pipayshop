package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.config.QueueConfig;
import com.example.pipayshopapi.entity.*;
import com.example.pipayshopapi.entity.dto.ItemOrderDetailDTO;
import com.example.pipayshopapi.entity.vo.ItemOrderDetailVO;
import com.example.pipayshopapi.entity.vo.MyItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.StringUtil;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 商户订单数据表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@Service
public class ItemOrderInfoServiceImpl extends ServiceImpl<ItemOrderInfoMapper, ItemOrderInfo> implements ItemOrderInfoService {

    @Resource
    AccountInfoMapper accountInfoMapper;

    @Resource
    ItemCommodityInfoMapper itemCommodityInfoMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ItemOrderMapper itemOrderMapper;

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private ItemOrderDetailMapper itemOrderDetailMapper;

    private static final String PAY_ERROR = "该订单已经支付，请勿重复下单！";

    private static final String ERROR_MAG = "生成未支付订单失败";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delOrderByOrderId(String orderId) {
        if (orderId == null || "".equals(orderId)){
            return 0;
        }
        // 删除订单表数据
        int update = itemOrderMapper.update(null, new UpdateWrapper<ItemOrder>()
                .eq("order_id", orderId)
                .set("del_flag", 1)
                .set("update_time", new Date()));
        if (update < 1) {
            throw new BusinessException("删除订单失败");
        }
        // 删除订单详情表数据
        // 获取订单关联的商品数据
        return itemOrderDetailMapper.delete(new UpdateWrapper<ItemOrderDetail>().eq("order_id", orderId).set("del_flag", 1));
    }


    @Override
    public ItemOrderDetailVO getOrderDetail(String orderId) {
        return itemOrderMapper.getOrderDetail(orderId);
    }

    @Override
    public int completedOrder(String orderId) {
        return itemOrderMapper.update(null, new UpdateWrapper<ItemOrder>()
                .eq("order_id", orderId)
                .set("order_status", 2)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void failOrder(String orderId) {
        // 死信队列到点了就调用改失效方法，通过orderId对应的订单的状态来确定是否使其失效
        boolean flag = itemOrderMapper.exists(new LambdaUpdateWrapper<ItemOrder>()
                .eq(ItemOrder::getOrderId, orderId)
                .eq(ItemOrder::getDelFlag, 0)
                .eq(ItemOrder::getOrderStatus, 0));
        if (!flag){
            // 不存在说明并没有
            return;
        }
        // 获取订单内的商品数据
        List<ItemOrderDetailDTO> itemOrderDetailDTOS = itemOrderDetailMapper.selectCommodityList(orderId);
        // 释放锁住的商品库存
        itemOrderDetailDTOS.forEach(itemOrderDetailDTO -> {
                                int i = itemCommodityInfoMapper.addStock(itemOrderDetailDTO.getNumber(), itemOrderDetailDTO.getCommodityId());
                                if (i < 1){throw new RuntimeException();}
                            });
        // 修改订单状态为失效状态
        int i1 = itemOrderMapper.update(null, new UpdateWrapper<ItemOrder>()
                .eq("order_id", orderId)
                .set("order_status", 3)
                .set("update_time", new Date()));
        if (i1 < 1) {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteFailOrders() {
        itemOrderMapper.update(null, new UpdateWrapper<ItemOrder>()
                .eq("order_status", 3)
                .set("del_flag", 1)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> generateUnpaidOrder(String token) {
        log.error(token);
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String uid = dataFromToken.get("uid", String.class);
        String buyerDataId = dataFromToken.get("buyerDataId", String.class);
        // 订单中的商品集合
        List<Object> itemOrderDetailDTOList = dataFromToken.get("itemOrderDetailDTOList", List.class);
        // 生成的订单id集合
        List<String> orderIdList = new ArrayList<>();
         /*由于Map中的参数get出来，并强转成其他类型时，它会先转换成LinkedHashMap，再尝试强转成其他的类。但是基本不会强转成功，
         所以会报 java.util.LinkedHashMap cannot be cast to*******这个错误。
         解决思路：从list中取出来的数据需要进行转化成json格式字符串，然后再将该json格式字符串转换成对象，这样就不会再出现报错情况，能成功遍历该list列表。
         */
        List<ItemOrderDetailDTO> detailList = itemOrderDetailDTOList.stream().map(itemOrderDetailDTO -> {
            // 将list中的数据转成json字符串
            String jsonObject = JSON.toJSONString(itemOrderDetailDTO);
            // 将json转成需要的对象
            return JSONObject.parseObject(jsonObject, ItemOrderDetailDTO.class);
        }).collect(Collectors.toList());
        // 这里将传递过来的商品集合根据itemId来划分订单范围，一个订单存储的数据要是同一个item才行，这样子才能保证物流数据的正确性
        // detailMap用于存储itemId和对应的子集合
        Map<String, List<ItemOrderDetailDTO>> detailMap = new HashMap<>(detailList.size());
        detailList.stream().parallel().forEach(detail -> {
            String itemId = detail.getItemId();
            List<ItemOrderDetailDTO> sublist = detailMap.computeIfAbsent(itemId, k -> new ArrayList<>());
            sublist.add(detail);
        });
        // Map中的每一位元素都对应一个订单
        detailMap.forEach((key, commodityList) -> {
            // 获取一个订单的商品数据列表
            // 计算原总价
            BigDecimal originSum = commodityList.stream().map(ItemOrderDetailDTO::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            // 计算打折后的总价
            BigDecimal discountSum = commodityList.stream().map(ItemOrderDetailDTO::getDiscount).reduce(BigDecimal.ZERO, BigDecimal::add);
            // 生成订单id
            String orderId = StringUtil.generateShortId();
            // 记录订单id
            orderIdList.add(orderId);
            // 插入订单主题表数据
            ItemOrder itemOrder = new ItemOrder(null, orderId, originSum, discountSum, key, uid, buyerDataId
                    , 0, null, null, null, null);
            int insert = itemOrderMapper.insert(itemOrder);
            if (insert < 1) {
                throw new BusinessException(ERROR_MAG);
            }
            // 插入订单详情表数据
            commodityList
                    .forEach(itemOrderDetailDTO -> {
                        ItemOrderDetail itemOrderDetail = new ItemOrderDetail();
                        // 属性转移
                        BeanUtils.copyProperties(itemOrderDetailDTO, itemOrderDetail);
                        // 补全属性
                        itemOrderDetail.setOrderId(orderId);
                        // 修改商品展示图数据指向image表的id
                        String imageId = imageMapper.selectImageIdByPath(itemOrderDetailDTO.getAvatarImag());
                        itemOrderDetail.setAvatarImag(imageId);
                        // 插入数据库
                        int insertItemOrderDetail = itemOrderDetailMapper.insert(itemOrderDetail);
                        if (insertItemOrderDetail < 1) {
                            throw new BusinessException(ERROR_MAG);
                        }
                        // 扣减商品的剩余库存余额
                        int update = itemCommodityInfoMapper.reduceStock(itemOrderDetailDTO.getNumber(), itemOrderDetailDTO.getCommodityId());
                        if (update < 1) {
                            throw new BusinessException(ERROR_MAG);
                        }
                    });
            // 订单十分钟未支付的失效处理
            rabbitTemplate.convertAndSend(QueueConfig.QUEUE_MESSAGE_DELAY, "item_" + orderId, message1 -> {
                message1.getMessageProperties().setExpiration("1000 * 60 * 10");
                return message1;
            });
        });
        return orderIdList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateUnpaidOrderByPi(String token) {
        log.error(token);
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String uid = dataFromToken.get("uid", String.class);
        Integer paymentId = dataFromToken.get("paymentId", Integer.class);
        String buyerDataId = dataFromToken.get("buyerDataId", String.class);
        // 订单中的商品
        Object object = dataFromToken.get("itemOrderDetailDTO", Object.class);
        String itemOrderDetailDTOByString=JSON.toJSONString(object);
        // 将json转成需要的对象
        ItemOrderDetailDTO itemOrderDetailDTO = JSONObject.parseObject(itemOrderDetailDTOByString, ItemOrderDetailDTO.class);

        // 获取一个订单的商品数据列表
        // 计算原总价
        BigDecimal piPrice = itemOrderDetailDTO.getPiPrice();
        // 生成订单id
        String orderId = StringUtil.generateShortId();
        // 网店id
        String itemId = itemOrderDetailDTO.getItemId();
        // 插入订单主题表数据
        ItemOrder itemOrder = new ItemOrder(null, orderId, null, null, itemId, uid, buyerDataId
                , 0, null, null, null, null,paymentId,null,piPrice);
        int insert = itemOrderMapper.insert(itemOrder);
        if (insert < 1) {
            throw new BusinessException(ERROR_MAG);
        }

        ItemOrderDetail itemOrderDetail = new ItemOrderDetail();
        // 属性转移
        BeanUtils.copyProperties(itemOrderDetailDTO, itemOrderDetail);
        // 补全属性
        itemOrderDetail.setOrderId(orderId);
        // 修改商品展示图数据指向image表的id
        String imageId = imageMapper.selectImageIdByPath(itemOrderDetailDTO.getAvatarImag());
        itemOrderDetail.setAvatarImag(imageId);
        // 插入数据库
        int insertItemOrderDetail = itemOrderDetailMapper.insert(itemOrderDetail);
        if (insertItemOrderDetail < 1) {
            throw new BusinessException(ERROR_MAG);
        }
        // 扣减商品的剩余库存余额
        int update = itemCommodityInfoMapper.reduceStock(itemOrderDetailDTO.getNumber(), itemOrderDetailDTO.getCommodityId());
        if (update < 1) {
            throw new BusinessException(ERROR_MAG);
        }

        // 订单十分钟未支付的失效处理
        rabbitTemplate.convertAndSend(QueueConfig.QUEUE_MESSAGE_DELAY, "item_" + orderId, message1 -> {
            message1.getMessageProperties().setExpiration("1000 * 60 * 10");
            return message1;
        });
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrderCertificate(MultipartFile file, String token) {
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
        File readPath = new File(Constants.PAYMENT_IMAG_PATH + separator );
        if (!readPath.isDirectory()) {
            readPath.mkdirs();
        }
        // 将文件复制到指定路径
        File destFile = new File(readPath.getAbsolutePath()+ separator + fileName);
        // 解析token
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String orderId = dataFromToken.get("orderId", String.class);
        String userId = dataFromToken.get("userId", String.class);


        // 获取订单详情
        ItemOrder itemOrder = itemOrderMapper.selectOne(new QueryWrapper<ItemOrder>()
                                                    .eq("order_id", orderId));


        // 上传图片和改订单状态为2:提交凭证图片
        itemOrder.setCertificateImag("/images/tradin_post_certificate/"+ThumbnailPath);
        itemOrder.setOrderStatus(1);
        // 更改订单状态和提交图片
        int update = itemOrderMapper.updateById(itemOrder);

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
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(String token) {

        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        // 这里获取的是orderId的数组，因为多个不同店铺的商品一起下单的话会产生多个orderId，用“，”分隔
        List<String> orderIdArray = dataFromToken.get("orderIdArray", ArrayList.class);
        String uid1 = dataFromToken.get("uid", String.class);
        // 所有商品的总积分价格
        BigDecimal payPointSum = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("payPointSum", String.class)));
//        BigDecimal payPointSum = BigDecimal.valueOf(dataFromToken.get("payPointSum", Integer.class));
        Date date = new Date();

        orderIdArray.stream().forEach(orderId -> {
            // 校验订单id是否已经存在，保证接口的幂等性，避免重复下单
            ItemOrder itemOrder = itemOrderMapper.selectOne(new QueryWrapper<ItemOrder>()
                    .eq("order_id", orderId)
                    .eq("order_status", 0));
            if (itemOrder == null){throw new BusinessException(PAY_ERROR);}
            // 订单状态、修改时间更新
            int update1 = itemOrderMapper.update(null, new UpdateWrapper<ItemOrder>()
                    .eq("order_id", orderId)
                    .set("order_status", 1)
                    .set("order_time", date)
                    .set("update_time", date));
            if (update1 < 1){throw new BusinessException(PAY_ERROR);}
        });
        // 用户余额更新
        int uid = accountInfoMapper.updatePointBalanceByUid(payPointSum,uid1);
        if (uid < 1){throw new BusinessException(PAY_ERROR);}
        return true;
    }


    /**
     * 根据用户id查询网店的所有订单
     */
    @Override
    public PageDataVO getMyOrderByUid(Integer page,Integer limit,String uid,Integer status) {
        Integer allMyOrderByUid = itemOrderMapper.getAllMyOrderByUid(uid,status);
        // 获取非商品数据
        List<MyItemOrderInfoVO> list = itemOrderMapper.getMyOrderByUid((page - 1) * limit, limit, uid,status);
        // 获取订单内商品数据
        getOrderCommodityList(list);
        return new PageDataVO(allMyOrderByUid,list);
    }

    private void getOrderCommodityList(List<MyItemOrderInfoVO> list) {
        // 获取订单内的商品数据
        list.stream()
                .parallel()
                .forEach(myItemOrderInfoVO -> {
                    List<ItemOrderDetailDTO> commodityList = itemOrderDetailMapper.selectCommodityList(myItemOrderInfoVO.getOrderId());
                    myItemOrderInfoVO.setCommodityList(commodityList);
                });
    }

    @Override
    public List<MyItemOrderInfoVO> getOrderList(String userId) {
        // 获取非商品数据
        List<MyItemOrderInfoVO> list = itemOrderMapper.getBuyerOrderList(userId);
        // 获取订单内商品数据
        getOrderCommodityList(list);
        return list;
    }

    /**
     * 未支付订单改价接口
     *
     */
    @Override
    public int changePrice(String token) {
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String orderId = dataFromToken.get("orderId", String.class);
        BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("discount", String.class)));
        if (discount.doubleValue() < 0) {
            throw new BusinessException("输入的金额不合法");
        }
        return itemOrderMapper.update(null, new LambdaUpdateWrapper<ItemOrder>()
                .eq(ItemOrder::getOrderId, orderId)
                .eq(ItemOrder::getOrderStatus, 0)
                .eq(ItemOrder::getDelFlag, 0)
                .set(ItemOrder::getDiscount, discount));
    }


}
