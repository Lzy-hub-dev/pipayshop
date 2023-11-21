package com.example.pipayshopapi.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.config.CommonConfig;
import com.example.pipayshopapi.entity.Image;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.ImageMapper;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * @author:
 * @ClassName FileUploadUtil
 * @Description 文件上传工具类
 */
@Slf4j
@Component
public class FileUploadUtil {
    private static final String SEPARATOR = "/";
    private static final String PRE="images/";
    private static  final String UPLOAD_PRE = "pipayshop-api/src/main/resources/static/";
    //用户头像存储位置
    public static final String AVATAR="avatar";
    //首页轮播图存储位置
    public static final String BG_IMG="bg_img";
    //网店商品图存储位置
    public static final String ITEM_COMMODITY_IMG="item_commodity_img";
    //网店背景广告图存储位置
    public static final String ITEM_IMG="item_img";
    //实体店商品图存储位置
    public static final String SHOP_COMMODITY_IMG="shop_commodity_img";
    //实体店背景广告图存储位置
    public static final String SHOP_IMG="shop_img";
    //酒店头像图片储存位置
    public static final String ROOM_TOP_IMG="room_top_img";

    // 网店首页的分类栏的图片
    public static final String ITEM_TOP_CATEGORY_IMG="item_category_top";


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
    //实体店商品展示图上传
    public static String SHOP_COMMODITY_TOP_IMAGE_UP =  "shop_commodity_top_imageUp";
    //实体店商品轮播图上传
    public static String SHOP_COMMODITY_IMAGE_UP  = "shop_commodity_image_Up";
    //实体店展示图上传
    public static String SHOP_TOP_IMAGE_UP =  "shop_top_imageUp";
    //实体店轮播图上传
    public static String SHOP_IMAGE_UP  = "shop_image_Up";
    //PI ID 图上传
    public static String PI_ID_IMAGE = "pi_id_image";

    @Resource
    private  CommonConfig commonConfig;
    private static FileUploadUtil fileUploadUtil;
    @PostConstruct
    public void init(){
        fileUploadUtil = this;
        fileUploadUtil.commonConfig= commonConfig;
    }

    /**
     * 文件上传
     */
    public static String uploadFile(MultipartFile multipartFile, String path) {
        if (multipartFile.isEmpty()) {
            throw new BusinessException("文件不能为空") ;
        }

        String fileName = multipartFile.getOriginalFilename();

        if ("".equals(fileName)) {
            return "文件名不能为空";
        }

        String postStr = fileName.substring(fileName.lastIndexOf("."));
        String preStr = StringUtil.generateShortId();
        fileName = preStr +  postStr;

        String imagePath = fileUploadUtil.commonConfig.getImagePath();
        File readPath = new File(imagePath + File.separator + path);
        if (!readPath.isDirectory()) {
            readPath.mkdirs();
        }
        //将文件复制到指定路径
        String separator = File.separator;
        File destFile = new File(readPath.getAbsolutePath()+ separator + fileName);
        try {
            FileCopyUtils.copy(multipartFile.getBytes(), destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SEPARATOR + PRE + path + SEPARATOR  + fileName;
    }

    /**
     *删除文件
     */
    public static boolean deleteFile(String fileName){
        File file = new File(UPLOAD_PRE+fileName);
        //判断文件存不存在
        if(!file.exists()){
            return false;
        }else{;
            //判断这是不是一个文件，ps：有可能是文件夹
            if(file.isFile()){
                return file.delete();
            }
        }
        return false;
    }

    /**
     * 将一张图片异步裁剪压缩为多张小规格图片
     */
    public static void  asyCropping(String path, List<String> sizeList){
        // 对图片进行批量压缩存储
        sizeList.stream()
                .parallel()
                .peek(size -> {
                    String[] sizeArray = size.split(",");
                    int length = Integer.parseInt(sizeArray[0]);
                    int wide = Integer.parseInt(sizeArray[1]);
                try {
                    File file = new File("pipayshop-api/src/main/resources/static/" + path);
                    String fileName = file.getName();
                    String fileExtension = "";
                    int dotIndex = fileName.lastIndexOf(".");
                    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                        fileExtension = fileName.substring(dotIndex + 1);
                    }
                    String newFileName = file.getAbsolutePath()
                            .replaceFirst("\\.([a-z]+)", "_" + length +"_" + wide + "."+fileExtension);
                    Thumbnails.of(file)
                            .size(length, wide)
                            .outputFormat(fileExtension)
                            .toFile(newFileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).count();
    }

    /**
     * 文件MD5值获取
     */
    public static String calculateMD5(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        byte[] fileBytes = file.getBytes();
        byte[] digest = md5Digest.digest(fileBytes);
        return DigestUtils.md5DigestAsHex(digest);
    }

    /**
     * 完整上传图片操作
     */
    public static String allUploadImageData(MultipartFile file, ImageMapper imageMapper, String smallPath,List<String> sizeList){
        String path = null;
        try {
            // 校验MD
            String md5 = FileUploadUtil.calculateMD5(file);
            Image image = imageMapper.selectOne(new QueryWrapper<Image>().eq("md5", md5).select("image_id","origin_path"));
            if (image != null) {
                // 获取缩略图
                if (sizeList != null){
                    asyCropping(image.getOriginPath(),sizeList);
                }
                // 存在就直接返回目标图片id,不需要保存
                return image.getImageId();
            }
            // 相同图片不存在
            // 1.存储图片
            path = uploadFile(file, smallPath);
            // 2.记录图片数据
            String imageId = StringUtil.generateShortId();
            Image img = new Image(null, imageId, md5, path, null, null, null, null);
            int insert = imageMapper.insert(img);
            if (insert < 1) {
                log.error("记录图片数据失败");
                throw new BusinessException("文件上传失败");
            }
            // 3.压缩图片
            if (sizeList != null){
                asyCropping(path,sizeList);
            }
            return imageId;
        } catch (IOException | NoSuchAlgorithmException e) {
            if (path != null) {
                // 上传失败,释放内存
                FileUploadUtil.deleteFile(path);
            }
            throw new RuntimeException(e);
        }
    }
}
