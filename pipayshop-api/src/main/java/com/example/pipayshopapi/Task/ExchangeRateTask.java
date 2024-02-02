package com.example.pipayshopapi.Task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.ExchangeRate;
import com.example.pipayshopapi.entity.TradinRate;
import com.example.pipayshopapi.service.TradinRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@Component
public class ExchangeRateTask {

    @Autowired
    TradinRateService tradinRateService;

    @Scheduled(cron = "0 0 */6 * * ?") // 每6个小时执行一次
    public void start() {
        String from = "CNY";
        // 更新美元汇率
        UpdateExchangeRate(from, "USD");
        // 更新欧元汇率
        UpdateExchangeRate(from, "EUR");
        // 更新英镑汇率
        UpdateExchangeRate(from, "GBP");
        // 更新日元汇率
        UpdateExchangeRate(from, "JPY");
        // 更新港币汇率
        UpdateExchangeRate(from, "HKD");
    }

    public void UpdateExchangeRate(String from, String to) {
        try {
            String key = "d06ebe37735a2a5b01a2758ebfc90b04";
            String money = "1";

            // 拼接请求 URL
            String baseUrl = "http://api.tanshuapi.com/api/exchange/v1/index";
            String query = String.format("key=%s&from=%s&to=%s&money=%s",
                    URLEncoder.encode(key, "UTF-8"),
                    URLEncoder.encode(from, "UTF-8"),
                    URLEncoder.encode(to, "UTF-8"),
                    URLEncoder.encode(money, "UTF-8"));
            String urlString = baseUrl + "?" + query;

            // 创建 URL 对象
            URL url = new URL(urlString);

            // 创建 HttpURLConnection 对象，并设置请求方法
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 发送请求并获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 使用 Fastjson 解析 JSON 响应
                JSONObject jsonResponse = JSON.parseObject(response.toString());
                String data = jsonResponse.getString("data");
                ExchangeRate exchangeRate = JSON.parseObject(data, ExchangeRate.class);
                TradinRate tradinRate = new TradinRate();
                tradinRate.setConversionRate(new BigDecimal(exchangeRate.getExchange()));
                tradinRate.setFormat(exchangeRate.getTo());
                tradinRate.setRateUpdateTime(exchangeRate.getUpdatetime());
                tradinRateService.saveOrUpdate(tradinRate, new QueryWrapper<TradinRate>()
                        .eq("format", exchangeRate.getTo()));
                System.out.println("更新"+ exchangeRate.getTo_name() +"汇率成功， 更新时间：" + exchangeRate.getUpdatetime());

            } else {
                System.out.println("获取汇率接口出错，code:" + responseCode);
            }

            // 断开连接
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
