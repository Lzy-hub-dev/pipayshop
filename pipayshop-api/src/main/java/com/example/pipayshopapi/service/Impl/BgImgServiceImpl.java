package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.BgImg;
import com.example.pipayshopapi.entity.dto.BgImgDTO;
import com.example.pipayshopapi.entity.vo.BgImgVO;
import com.example.pipayshopapi.mapper.BgImgMapper;
import com.example.pipayshopapi.mapper.ImageMapper;
import com.example.pipayshopapi.service.BgImgService;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * item/shop的首页背景轮播图数据 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class BgImgServiceImpl extends ServiceImpl<BgImgMapper, BgImg> implements BgImgService {

    @Resource
    private BgImgMapper bgImgMapper;

    @Resource
    private ImageMapper imageMapper;

    /**
     * 新增首页背景轮播图
     */
    @Override
    public Boolean addBgImg(BgImgDTO bgImgDTO) {

        return bgImgMapper.insert(new BgImg(StringUtil.generateShortId(),bgImgDTO.getImgUrl(),bgImgDTO.getCategory(),bgImgDTO.getContentId())) > 0;
    }

    /**
     * 查询首页轮播背景图列表
     */
    @Override
    public List<BgImgVO> selectBgImgList(int category) {
        return bgImgMapper.selectBgImgList(category);
    }

    @Override
    public String bgCategoryImage(MultipartFile multipartFile) {
        List<String> imageSize = new ArrayList<>();
        imageSize.add("400,300");
        return FileUploadUtil.allUploadImageData(multipartFile, imageMapper, FileUploadUtil.BG_IMG,imageSize);

    }

    /**
     * 逻辑删除店的首页背景图片
     */
    @Override
    public boolean logicDeleteBgImg(String bgId) {
        int result = bgImgMapper.update(null, new UpdateWrapper<BgImg>()
                .eq("bg_id", bgId)
                .set("del_flag", 1));
        return result > 0;
    }

}
