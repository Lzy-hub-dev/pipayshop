package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.Complaint;
import com.example.pipayshopapi.entity.dto.ComplaintDTO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.service.ComplaintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "举报接口",tags = "举报接口")
@RequestMapping("/pipayshopapi/complaint")
public class ComplaintController {
    @Autowired
    ComplaintService complaintService;

    @PostMapping("addComplaint")
    @ApiOperation("添加举报信息")
    public ResponseVO<String> addComplaint(@RequestBody ComplaintDTO complaintDTO) {

        Complaint complaint = new Complaint();
        BeanUtils.copyProperties(complaintDTO, complaint);
        complaintService.save(complaint);
        return ResponseVO.getSuccessResponseMsg(null, "添加举报信息成功");
    }

    @PostMapping("getComplaint")
    @ApiOperation("查询举报信息")
    public ResponseVO getComplaint() {

        return ResponseVO.getSuccessResponseMsg(null, "查询举报信息成功");
    }
}
