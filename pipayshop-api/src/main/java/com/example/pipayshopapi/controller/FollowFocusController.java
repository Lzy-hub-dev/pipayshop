package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.FollowFocusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 粉丝关注表 前端控制器
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "粉丝关注接口",tags = "粉丝关注接口")
@RestController
@RequestMapping("/pipayshopapi/follow-focus")
public class FollowFocusController {

    @Resource
    private FollowFocusService followFocusService;

    @GetMapping("selectUpdateTimeByFollowId/{followId}")
    @ApiOperation("根据粉丝Id查找粉丝关注时间")
    public ResponseVO selectUpdateTimeByUidOrFollowId(@PathVariable String followId){
        try {
            Date date = followFocusService.selectUpdateTimeByFollowId(followId);
            return ResponseVO.getSuccessResponseVo(date);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据用户Id或者粉丝Id查找粉丝关注时间失败，请联系后台人员");
        }
    }

    @GetMapping("attentionFollowId/{followId}")
    @ApiOperation("关注操作")
    public ResponseVO attentionFollowId(@PathVariable String followId){
        try {
            boolean result = followFocusService.attentionFollowId(followId);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("关注成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("关注失败，请联系后台人员");
        }
    }

    @PostMapping("cancelAttentionFollowId/{followId}")
    @ApiOperation("取关操作")
    public ResponseVO cancelAttentionFollowId(@PathVariable String followId){
        try {
            boolean result = followFocusService.cancelAttentionFollowId(followId);
            if (!result) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("取关成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("取关失败，请联系后台人员");
        }
    }
}
