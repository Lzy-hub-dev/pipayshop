package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.config.CommonConfig;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.service.AccountInfoService;
import com.example.pipayshopapi.util.Constants;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * 用户账户表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Resource
    private AccountInfoMapper accountInfoMapper;


    @Resource
    private CommonConfig commonConfig;

    /**
     * 根据用户Id查找用户账户表的积分余额和pi币余额
     * */
    @Override
    public AccountInfoVO selectAccountById(String uid) {
        return accountInfoMapper.selectAccountInfo(uid);
    }

    private String getAuth(String client_id, String app_secret) {
        String auth = client_id + ":" + app_secret;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    public String generateAccessToken() {
        String auth = this.getAuth(
                commonConfig.getPAYPAL_CLIENT_ID(),
                commonConfig.getPAYPAL_APP_SECRET()
        );
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + auth);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        HttpEntity<?> request = new HttpEntity<>(requestBody, headers);
        requestBody.add("grant_type", "client_credentials");

        ResponseEntity<String> response = restTemplate.postForEntity(
                Constants.PAYPAL_BASE_PATH +"/v1/oauth2/token",
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.log(Level.INFO, "GET TOKEN: SUCCESSFUL!");
            return new JSONObject(response.getBody()).getString("access_token");
        } else {
            LOGGER.log(Level.SEVERE, "GET TOKEN: FAILED!");
            return "Unavailable to get ACCESS TOKEN, STATUS CODE " + response.getStatusCode();
        }
    }

    @Override
    public Object capturePayment(String orderId) {
        log.error("capturePayment===========================================start");
        String accessToken = generateAccessToken();
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();

        headers.set("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                Constants.PAYPAL_BASE_PATH + "/v2/checkout/orders/" + orderId + "/capture",
                HttpMethod.POST,
                entity,
                Object.class
        );
        log.error("capturePayment===========================================end");
        log.error("response==========================================="+response.getBody());
        if (response.getStatusCode() == HttpStatus.CREATED) {
            LOGGER.log(Level.INFO, "ORDER CREATED");
            return response.getBody();
        } else {
            LOGGER.log(Level.INFO, "FAILED CREATING ORDER");
            return "Unavailable to get CREATE AN ORDER, STATUS CODE " + response.getStatusCode();
        }
    }

    @Override
    public Object createOrder() {
        log.error("createOrder===========================================start");
        String accessToken = generateAccessToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);

        //JSON String
        String requestJson = "{\"intent\":\"CAPTURE\",\"purchase_units\":[{\"amount\":{\"currency_code\":\"USD\",\"value\":\"1.00\"}}]}";
        log.error("requestJson===========================================start"+requestJson);
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        log.error("entity===========================================start"+entity);
        ResponseEntity<Object> response = restTemplate.exchange(
                Constants.PAYPAL_BASE_PATH + "/v2/checkout/orders",
                HttpMethod.POST,
                entity,
                Object.class
        );
        log.error("response===========================================start"+response);
        log.error("createOrder===========================================end");
        log.error("response==============================================="+response.getBody());
        if (response.getStatusCode() == HttpStatus.CREATED) {
            LOGGER.log(Level.INFO, "ORDER CAPTURE");
            return response.getBody();
        } else {
            LOGGER.log(Level.INFO, "FAILED CAPTURING ORDER");
            return "Unavailable to get CAPTURE ORDER, STATUS CODE " + response.getStatusCode();
        }
    }

}
