package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件的上传和删除接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Api(value = "文件操作接口",tags = "文件操作接口")
@RestController
@RequestMapping("/pipayshopapi/follow-focus")
public class FileUtilController {

    @PostMapping("UpFile")
    @ApiOperation("文件的上传")
    public ResponseVO UpFile(@RequestBody MultipartFile multipartFile){
        try {
            String fileUrl = FileUtil.uploadFile(multipartFile);
            if (fileUrl.equals("")){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(fileUrl);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("文件的上传失败");
        }
    }
}
