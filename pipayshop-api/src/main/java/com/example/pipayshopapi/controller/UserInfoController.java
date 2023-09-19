package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.Country;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.dto.LoginDTO;
import com.example.pipayshopapi.entity.dto.RegisterDTO;
import com.example.pipayshopapi.entity.dto.UserRegisterDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.CountryService;
import com.example.pipayshopapi.service.UserInfoService;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.CreateImageCode;
import com.example.pipayshopapi.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
    private CountryService countryService;

    @Resource
    private RedisUtil<String> redisUtil;

    @GetMapping("test")
    @ApiOperation("test")
    public String test(){

        return "ok";
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public ResponseVO<ResponseResultVO> login(@RequestBody LoginDTO loginDTO) {
        try {
            ResponseResultVO  responseResultVO  = userInfoService.login(loginDTO);
            if (responseResultVO  == null) {
                throw new Exception();
            }

            return ResponseVO.getSuccessResponseVo(responseResultVO );
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
    public ResponseVO<String> uploadUserImage(String userId, MultipartFile file){
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

    /**
     * 当一个用户绑定的店铺数 >= 10 的时候，可以升级为商家用户
     */
    @PostMapping("upToVipUser/{userId}")
    @ApiOperation("升级为商家用户")
    public ResponseVO upToVipUser(@PathVariable String userId){
        try {
            boolean result = userInfoService.upToVipUser(userId);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("升级为商家用户成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("升级为商家用户失败，请联系后台人员");
        }
    }

    /**
     * 查看一个用户是否是VIP用户
     */
    @GetMapping("isVipUser/{uid}")
    @ApiOperation("查看一个用户是否是VIP用户")
    public ResponseVO<Boolean> isVipUser(@PathVariable String uid){
        try {
            boolean flag = userInfoService.isVipUser(uid);
            return ResponseVO.getSuccessResponseVo(flag);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("失败");

        }
    }

    /**
     * 获取国家列表数据
     */
    @GetMapping("getCountryList")
    @ApiOperation("获取国家列表数据")
    public ResponseVO<List<CountryVO>> getCountryList(){
        try {
            List<CountryVO> list = countryService.getCountryList();
            return ResponseVO.getSuccessResponseVo(list);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("获取国家列表数据失败");

        }
    }


    /**
     * 首次注册pi账号登录后，强制要求其设置一个账号密码实现普通浏览器登录效果
     */
    @PostMapping("insertRegisterData")
    @ApiOperation("首次注册pi账号登录后，强制要求其设置一个账号密码实现普通浏览器登录效果")
    public ResponseVO<String> insertRegisterData(UserRegisterDTO userRegisterDTO){
        try {
            boolean result = userInfoService.insertRegisterData(userRegisterDTO);
            if (!result){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("注册普通浏览器账号成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("注册普通浏览器账号失败，请联系后台人员");
        }
    }

    /**
     * 普通浏览器登录
     */
    @GetMapping("userRegister")
    @ApiOperation("普通浏览器登录")
    public ResponseVO<ResponseResultVO> userRegister(HttpServletRequest request, RegisterDTO registerDTO){
        try {
            ResponseResultVO  responseResultVO  = userInfoService.userRegister(request.getSession().getId(),registerDTO);
            if (responseResultVO  == null) {
                throw new BusinessException();
            }
            return ResponseVO.getSuccessResponseVo(responseResultVO );
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 生成验证码，并保存值到redis中用于校验
     */
    @GetMapping("/checkCode")
    @ApiOperation("生成验证码，并保存值到redis中用于校验")
    public void checkCode(HttpServletRequest request, HttpServletResponse response) {
        OutputStream out = null;
        try{
            // 创建验证码对象并生成验证码
            CreateImageCode code = new CreateImageCode();
            String verifyCode = code.getCode();
            // 把随机生成的验证码字符串保存到 redis 中(以sessionId来标识)
            String sessionId = request.getSession().getId();
            redisUtil.set(Constants.CHECK_CODE_PRE + sessionId, verifyCode);
            // 把验证码图片设置为响应正文并输出到客户端
            response.setContentType("image/jpeg");
            out = response.getOutputStream();
            ImageIO.write(code.getBufferedImage(), "jpeg", out);
        } catch (IOException e) {
            throw new BusinessException("生成验证码失败");
        }finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                throw new BusinessException("生成验证码失败");
            }
            try {
                out.close();
            } catch (IOException e) {
                throw new BusinessException("生成验证码失败");
            }
        }
    }

}
