package com.example.pipayshopapi.util;
import com.example.pipayshopapi.exception.BusinessException;
import org.springframework.util.FileCopyUtils;
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
    private static String PRE="images\\";
    private static String UPLOAD_PRE = "pipayshop-api\\src\\main\\resources\\static\\";
    //用户头像存储位置
    public static String AVATAR="avatar";
    //首页轮播图存储位置
    public static String BG_IMG="bg_img";
    //网店商品图存储位置
    public static String ITEM_COMMODITY_IMG="item_commodity_img";
    //网店背景广告图存储位置
    public static String ITEM_IMG="item_img";
    //实体店商品图存储位置
    public static String SHOP_COMMODITY_IMG="shop_commodity_img";
    //实体店背景广告图存储位置
    public static String SHOP_IMG="shop_img";
    //酒店头像图片储存位置
    public static String ROOM_TOP_IMG="room_top_img";

    //实体店分类图片储存位置
    public static String CATEGORY_IMG="shop_category_img";
    //酒店轮播图储存位置
    public static String ROOM_IMAGE_LIST="room_image_list";

    //网店头像图片储存位置
    public static String  ITEM_TOP_IMAGS="item_top_imags";
    //商品图片的地址集合储存位置
    public static String ITEM_IMAGS_LIST="item_imags_list";
    //商品详情图片的地址集合
    public static String ITEM_DETAIL_IMAGS="item_detail_imags";


    /**
     * 文件上传
     * @param multipartFile
     * @return 文件路径+文件名
     */
    public static String uploadFile(MultipartFile multipartFile,String path) {
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
        File readPath = new File(UPLOAD_PRE+PRE+path);
        if (!readPath.isDirectory()) {
            readPath.mkdirs();
        }
        //将文件复制到指定路径
        File destFile = new File(readPath.getAbsolutePath()+File.separator + fileName);
//        System.out.println("保存路径: " + readPath);

        try {
            FileCopyUtils.copy(multipartFile.getBytes(), destFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return File.separator+PRE+path+File.separator+fileName;
    }
    /**
     *删除文件
     * @param fileName 这是图片的路径
     * @return
     */
    public static boolean deleteFile(String fileName){
        File file = new File(UPLOAD_PRE+fileName);
        //判断文件存不存在
        if(!file.exists()){
            System.out.println("删除文件失败："+fileName+"不存在！");
            return false;
        }else{;
            //判断这是不是一个文件，ps：有可能是文件夹
            if(file.isFile()){
                return file.delete();
            }
        }
        return false;
    }
}
