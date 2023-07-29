package com.example.pipayshopapi.util;

import com.example.pipayshopapi.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
 
 
/**
 * @author: 
 * @ClassName FileUploadUtil
 * @Description 文件上传工具类
 */
public class FileUploadUtil {


    private static String UPLOAD_DIR="pipayshop-api/src/main/resources/localImg";
    /**
     * 文件上传
     * @param multipartFile
     * @return 文件路径+文件名
     */
    public static String uploadFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new BusinessException("文件不能为空") ;
        }
 
        String fileName = multipartFile.getOriginalFilename();
 
        if ("".equals(fileName)) {
            return "文件名不能为空";
        }
//        System.out.println("文件名: " + fileName);
        String postStr = fileName.substring(fileName.lastIndexOf("."));
        String preStr = StringUtil.generateShortId();
        fileName = preStr +  postStr;
        File readPath = new File(UPLOAD_DIR);
        if (!readPath.isDirectory()) {
            readPath.mkdirs();
        }
//        System.out.println("保存路径: " + readPath);
 
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = multipartFile.getInputStream();
            os = new FileOutputStream(new File(readPath, fileName));
 
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        return readPath+File.separator+fileName;
    }
}