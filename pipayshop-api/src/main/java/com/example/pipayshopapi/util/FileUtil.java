package com.example.pipayshopapi.util;

import com.example.pipayshopapi.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: jiangjiafeng
 * @ClassName FileUploadUtil
 * @Description 文件上传工具类
 * @date 2023/7/19 17:59
 * @Version 1.0
 */
public class FileUtil {

    /**
     * 文件上传路径
     */
    public static final String UPLOAD_DIR = "pipayshop-api/src/main/resources/headPic";
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
        System.out.println("文件名: " + fileName);
        String postStr = fileName.substring(fileName.lastIndexOf("."));
        String preStr = StringUtil.generateShortId();
        fileName = preStr +  postStr;
        File readPath = new File(UPLOAD_DIR);
        if (!readPath.isDirectory()) {
            readPath.mkdirs();
        }
        System.out.println("保存路径: " + readPath);

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

    /**
     *删除文件
     * @param fileName 这是图片的路径
     * @return
     */
    public static boolean deleteFile(String fileName){
        File file = new File(fileName);
        //判断文件存不存在
        if(!file.exists()){
            System.out.println("删除文件失败："+fileName+"不存在！");
            return false;
        }else{
            //判断这是不是一个文件，ps：有可能是文件夹
            if(file.isFile()){
                return file.delete();
            }
        }
        return false;
    }
}
