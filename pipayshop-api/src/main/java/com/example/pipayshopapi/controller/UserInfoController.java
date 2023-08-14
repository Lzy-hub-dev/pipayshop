package com.example.pipayshopapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.dto.LoginDTO;
import com.example.pipayshopapi.entity.vo.ItemMinInfoVo;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户数据表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "用户数据接口",tags = "用户数据接口")
@RestController
@RequestMapping("/pipayshopapi/user-info")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("test")
    @ApiOperation("test")
    public String test(HttpServletRequest  request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        System.out.println(ip);
        String URL = "https://api.beijinxuetang.com/api/common/ip";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip",ip);
        JSONObject json = restTemplate.postForObject(URL,jsonObject, JSONObject.class);
        if (json.getInteger("code") == 0) {
            json = json.getJSONObject("data");
            // 国家
            String nation = json.getString("nation");
            // 省份
            String province = json.getString("province");
            // 市
            String city = json.getString("city");
        }
        System.out.println(json);
        return "ok";
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public ResponseVO<UserInfo> login(@RequestBody LoginDTO loginDTO) {
        try {
            UserInfo userInfo = userInfoService.login(loginDTO);
            if (userInfo == null) {
                throw new Exception();
            }

            return ResponseVO.getSuccessResponseVo(userInfo);
        } catch (Exception e) {

            throw new BusinessException("登录注册失败，请联系后台人员 + "+e.getLocalizedMessage()+e+e.getCause().toString());
        }
    }

    @GetMapping("selectUserInfoByUid/{uid}")
    @ApiOperation("根据用户Id查找用户数据表的基本信息")
    public ResponseVO selectUserInfoByUid(@PathVariable String uid){
        try {
            UserInfoVO userInfoVO = userInfoService.selectUserInfoByUid(uid);
            return ResponseVO.getSuccessResponseVo(userInfoVO);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id查找用户数据表的基本信息失败，请联系后台人员");
        }
    }

    @PostMapping("updateUserInfoByUid")
    @ApiOperation("根据用户Id更改用户数据表的基本信息")
    public ResponseVO updateUserInfoByUid(@RequestBody UserInfoVO userInfoVO){
        try {
            boolean result = userInfoService.updateUserInfoByUid(userInfoVO);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据用户Id更改用户数据表的基本信息成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id更改用户数据表的基本信息失败，请联系后台人员");
        }
    }

    @PostMapping("updateLanguageByUid/{uid}/{language}")
    @ApiOperation("根据用户Id更改用户语言标识")
    public ResponseVO updateLanguageByUid(@PathVariable String uid,@PathVariable String language){
        try {
            boolean result = userInfoService.updateLanguageByUid(uid, language);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据用户Id更改用户语言标识成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id更改用户语言标识失败，请联系后台人员");
        }
    }

    @PostMapping("updateCountryByUid/{uid}/{country}")
    @ApiOperation("根据用户Id更改用户国家标识")
    public ResponseVO updateCountryByUid(@PathVariable String uid,@PathVariable String country){
        try {
            boolean result = userInfoService.updateCountryByUid(uid, country);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("根据用户Id更改用户国家标识成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id更改用户国家标识失败，请联系后台人员");
        }
    }
    @PostMapping("uploadUserImage")
    @ApiOperation("根据用户Id上传头像")
    public ResponseVO uploadUserImage(String userId, MultipartFile file){
        try {
            boolean result = userInfoService.uploadUserImage(userId, file);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("上传成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("上传失败，请联系后台人员");
        }
    }

    @GetMapping("getItemInfoByUid")
    @ApiOperation("根据用户ID获取网店ID和商品数量")
    public ResponseVO<ItemMinInfoVo> getItemInfoByUid(String userId){
        try {
            ItemMinInfoVo result = userInfoService.getItemInfoByUid(userId);
            return ResponseVO.getSuccessResponseVo(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户ID获取网店ID和商品数量失败，请联系后台人员");
        }
    }

    /**
     * 根据用户id查询它的网店id
     */
    @GetMapping("getItemIdByUserId/{userId}")
    @ApiOperation("根据用户id查询它的网店id")
    public ResponseVO<String> getItemIdByUserId(@PathVariable  String userId){
        try {
            String itemId = userInfoService.getItemIdByUserId(userId);
            return ResponseVO.getSuccessResponseVo(itemId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户id查询它的网店id失败，请联系后台人员");
        }
    }

    @GetMapping("shopBalance/{uid}")
    @ApiOperation("根据用户Id判断用户能绑定实体店的数量余额")
    public ResponseVO<Integer> shopBalance(@PathVariable String uid){
        try {
            Integer count = userInfoService.releaseShopIsNotById(uid);
            return ResponseVO.getSuccessResponseVo(count);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("失败");

        }
    }
}
