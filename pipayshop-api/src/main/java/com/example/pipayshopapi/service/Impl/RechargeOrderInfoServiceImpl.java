package com.example.pipayshopapi.service.Impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.config.CommonConfig;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.RechargeInfo;
import com.example.pipayshopapi.entity.RechargeOrderInfo;
import com.example.pipayshopapi.entity.dto.CompleteDTO;
import com.example.pipayshopapi.entity.dto.IncompleteDTO;
import com.example.pipayshopapi.entity.dto.PaymentDTO;
import com.example.pipayshopapi.entity.dto.TransactionDTO;
import com.example.pipayshopapi.entity.enums.PaymentEnum;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.RechargeInfoMapper;
import com.example.pipayshopapi.mapper.RechargeOrderInfoMapper;
import com.example.pipayshopapi.service.RechargeOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.HttpClientUtil;
import okhttp3.*;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 商户订单数据表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
@Service
public class RechargeOrderInfoServiceImpl extends ServiceImpl<RechargeOrderInfoMapper, RechargeOrderInfo> implements RechargeOrderInfoService {

    @Resource
    CommonConfig commonConfig;

    @Resource
    RechargeOrderInfoMapper rechargeOrderInfoMapper;

    @Resource
    RechargeInfoMapper rechargeInfoMapper;

    @Resource
    AccountInfoMapper accountInfoMapper;

    @Resource
    RedissonClient redissonClient;


    @Override
    public ResponseVO incomplete(IncompleteDTO incompleteDTO) {
        log.error("incomplete--------------------------------");
        try {
            // 先处理未完成的订单
            String oldPaymentId = incompleteDTO.getIdentifier();
            TransactionDTO transaction = incompleteDTO.getTransaction();
            log.error("?transation--------------------"+transaction);
            log.error("?oldpaymentId------------------"+oldPaymentId);
            if (null != transaction) {
                log.error("transation--------------------"+transaction);
                log.error("oldpaymentId------------------"+oldPaymentId);
                String txid = transaction.getTxid();
                String txURL = transaction.get_link();
//                RechargeOrderInfo  rechargeOrderInfo = rechargeOrderInfoMapper.selectOne(new QueryWrapper<RechargeOrderInfo>()
//                        .eq("order_id", oldPaymentId));
//                if (null == rechargeOrderInfo) {
//                    log.error("order-----------------null");
//                    return new ResponseVO(2, PaymentEnum.getStatusByCode(2),PaymentEnum.getMsgByCode(2));
//                }
//
//                if (rechargeOrderInfo.getOrderStatus()==1) {
//                    return new ResponseVO(2,PaymentEnum.getStatusByCode(2),PaymentEnum.getMsgByCode(2));
//                }
                // 发请求获取未处理订单的信息
                String get = HttpClientUtil.sendGet(txURL);
                JSONObject jsonObject1 = JSON.parseObject(get);
                // 获取订单ID
                String piOrderId = jsonObject1.getString("memo");

                log.error("memo---------------------"+piOrderId);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("txid", txid);

                Map<String, String> heads = new HashMap<>();
                heads.put("Content-Type", "application/json;charset=UTF-8");
                heads.put("Authorization", "Key " + commonConfig.getServerAccessKey());

                try {
                    HttpResponse response = HttpRequest.post("https://api.minepi.com/v2/payments/" + piOrderId + "/cancel")
                            .headerMap(heads, false)
                            .body(String.valueOf(jsonObject))
                            .timeout(5 * 60 * 1000)
                            .execute();
                    String body = response.body();
                    JSONObject jsonObject2 = JSON.parseObject(body);
                    String error = jsonObject2.getString("error");
                    if (!StringUtils.isEmpty(error)) {
                        log.error("!response------------------"+error);
                        throw new RuntimeException("订单完成异常!");
                    }

                    return new ResponseVO(7,PaymentEnum.getStatusByCode(7),PaymentEnum.getMsgByCode(7));
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e) {
            return new ResponseVO(8,PaymentEnum.getStatusByCode(8),PaymentEnum.getMsgByCode(8));
        }
        return new ResponseVO(8,PaymentEnum.getStatusByCode(8),PaymentEnum.getMsgByCode(8));

    }

    @Override
    public ResponseVO approve(PaymentDTO paymentDTO) {
        log.error("approve-------------------------------------------------------------");
        String rechargeOrderId = paymentDTO.getRechargeOrderId();
        RechargeOrderInfo rechargeOrderInfo = rechargeOrderInfoMapper.selectOne(new QueryWrapper<RechargeOrderInfo>()
                .eq("order_id", rechargeOrderId));
        if (null == rechargeOrderInfo) {
            return new ResponseVO(1,PaymentEnum.getStatusByCode(1),PaymentEnum.getMsgByCode(1));
        }
        if (!"0".equals(rechargeOrderInfo.getOrderStatus())) {
            return new ResponseVO(2,PaymentEnum.getStatusByCode(2),PaymentEnum.getMsgByCode(2));

        }
        //获取付款信息
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.minepi.com/v2/payments/" + paymentDTO.getPaymentId())
                .addHeader("Authorization", "Key " + commonConfig.getServerAccessKey())
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String string = response.body().string();
                JSONObject jsonObject1 = JSON.parseObject(string);
                log.error("!response-------------------------------------------------------------"+commonConfig.getServerAccessKey());
                throw new RuntimeException("payments error " + jsonObject1.getString("error_message"));
            }
            String string = response.body().string();
            log.error("response-------------------------------------------------------------"+string);

            JSONObject jsonObject1 = JSON.parseObject(string);
            //校验实际支付金额
            BigDecimal userFinalPrice = rechargeOrderInfo.getPiSum();


            if (userFinalPrice.compareTo(jsonObject1.getBigDecimal("amount")) < 0) {
                log.error(userFinalPrice+"response-------------------------------------------------------------"+jsonObject1.getBigDecimal("amount"));
                throw new RuntimeException("支付金额少于订单金额");
            }
            OkHttpClient client1 = new OkHttpClient();
            //信息真实，通知PI我准备好了，可以付款了
            Request request1 = new Request.Builder()
                    .url("https://api.minepi.com/v2/payments/" + paymentDTO.getPaymentId() + "/approve")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Access-Control-Allow-Origin", "*")
                    .addHeader("Authorization", "Key " + commonConfig.getServerAccessKey())
                    .post(RequestBody.create("", MediaType.parse("application/json")))
                    .build();
            try (Response response1 = client1.newCall(request1).execute()) {
                if (!response1.isSuccessful()) {
                    throw new RuntimeException("approve error: ");
                }
                log.error("return-------------------------------------------------------------");
            } catch (RuntimeException | IOException e) {
                log.error("error-------------------------------------------------------------");
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw  new BusinessException("支付授权失败，请联系后台人员"+e.getLocalizedMessage()+e.toString()+e.getCause().toString());
        }
        return new ResponseVO(7,PaymentEnum.getStatusByCode(7),PaymentEnum.getMsgByCode(7));

    }

    @Override
    @Transactional
    public ResponseVO complete(CompleteDTO completeDTO) throws InterruptedException {

        log.error("complete------------------------------------------------------------"+completeDTO);
        //PI钱包支付
        String paymentId = completeDTO.getPaymentId();//PI订单号
        String lockName = "access:lock:complete:" + paymentId;
        log.error(paymentId+"-----------------"+lockName+"---------------------");
        // 获取锁（可重入），指定锁的名称
        RLock lock = redissonClient.getLock(lockName);

        // 尝试获取锁，参数分别是：获取锁的最大等待时间（期间会重试），锁自动释放时间，时间单位
        boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);

        // 判断释放获取成功
        if(isLock){

            try {

                RechargeOrderInfo rechargeOrderInfo = rechargeOrderInfoMapper.selectOne(new QueryWrapper<RechargeOrderInfo>()
                        .eq("order_id", completeDTO.getRechargeOrderId()));
                log.error("orderId--------------------------------------------------------------");
                if (null == rechargeOrderInfo) {
                    // 订单不存在
                    log.error("!orderinfo--------------------------------------------------------不存在");
                    return new ResponseVO(1,PaymentEnum.getStatusByCode(1),PaymentEnum.getMsgByCode(1));
                }
                log.error("orderinfo------------------------------------------------------------------");
                if (rechargeOrderInfo.getOrderStatus() != 0) {
                    // 订单不是待支付状态
                    log.error("!order---------------------------------------------------------pay");
                    return  new ResponseVO(2,PaymentEnum.getStatusByCode(2),PaymentEnum.getMsgByCode(2));
                }


                //通知PI完成交易
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("txid", completeDTO.getTxId());

                Map<String, String> heads = new HashMap<>();
                heads.put("Content-Type", "application/json;charset=UTF-8");
                heads.put("Authorization", "Key " + commonConfig.getServerAccessKey());
                log.error("pi-----------------------------------------"+jsonObject);
                try {
                    HttpResponse response = HttpRequest.post("https://api.minepi.com/v2/payments/" + paymentId + "/complete")
                            .headerMap(heads, false)
                            .body(String.valueOf(jsonObject))
                            .timeout(5 * 60 * 1000)
                            .execute();
                    String body = response.body();
                    JSONObject jsonObject1 = JSON.parseObject(body);
                    String error = jsonObject1.getString("error");
                    if (!StringUtils.isEmpty(error)) {
                        log.error("!strinutils-----------------------------"+body);
                        throw new RuntimeException("订单完成异常!");
                    }
                    rechargeOrderInfo.setOrderStatus(1);
                    // 更新订单
                    rechargeOrderInfoMapper.updateById(rechargeOrderInfo);
                    // 获取账号
                    AccountInfo accountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>().eq("uid", rechargeOrderInfo.getUid()));
                    BigDecimal pointBalance = accountInfo.getPointBalance();
                    BigDecimal add = pointBalance.add(rechargeOrderInfo.getPointAmount());

                    // 加积分
                    accountInfo.setPointBalance(add);
                    int insert = accountInfoMapper.updateById(accountInfo);


                    // 记录充值记录
                    RechargeInfo rechargeInfo = new RechargeInfo();
                    rechargeInfo.setUserId(rechargeOrderInfo.getUid());
                    rechargeInfo.setPiNum(rechargeOrderInfo.getPiSum());
                    rechargeInfo.setPrePi(accountInfo.getPiBalance());
                    rechargeInfo.setLastPi(accountInfo.getPiBalance());
                    rechargeInfo.setPointSum(rechargeOrderInfo.getPointAmount());
                    rechargeInfo.setPrePoint(accountInfo.getPointBalance());
                    rechargeInfo.setLastPoint(add);
                    insert += rechargeInfoMapper.insert(rechargeInfo);


                    if (insert < 2){ throw new RuntimeException(); }

                    log.error("支付成功------------------------------------------------------");
                    // 支付成功
                    return  new ResponseVO(6,PaymentEnum.getStatusByCode(6),PaymentEnum.getMsgByCode(6));
                } catch (Exception e) {
                    throw e;
                }
            }finally {
                // 释放锁
                lock.unlock();
            }

        }else {
            // 调用太快
            log.error("!RedisLockUtil---------------------------------------------------------调用太快");
            return new ResponseVO(4,PaymentEnum.getStatusByCode(4),PaymentEnum.getMsgByCode(4));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelled(String rechargeOrderId) {
        int update = rechargeOrderInfoMapper.update(null, new UpdateWrapper<RechargeOrderInfo>()
                .eq("order_id", rechargeOrderId)
                .set("order_status", 3));
        return update > 0;
    }
}
