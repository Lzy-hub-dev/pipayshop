package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.BgImg;
import com.example.pipayshopapi.entity.vo.BgImgVO;
import com.example.pipayshopapi.mapper.BgImgMapper;
import com.example.pipayshopapi.service.BgImgService;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    /**
     * 新增首页背景轮播图
     *
     * @param file
     * @param bgImg
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addBgImg(MultipartFile file, BgImg bgImg) {
        if (file == null) {
            log.error("未选择图片");
            return false;
        }
        if (bgImg == null) {
            log.error("参数列表为空！");
            return false;
        }
        String picPath = FileUploadUtil.uploadFile(file, FileUploadUtil.BG_IMG);
        bgImg.setImgUrl(picPath);
        bgImg.setBgId(StringUtil.generateShortId());
        return bgImgMapper.insert(bgImg) > 0;
    }

    /**
     * 查询首页轮播背景图列表
     */
    @Override
    public List<BgImgVO> selectBgImgList(int category) {
        return bgImgMapper.selectBgImgList(category);
    }

    /**
     * 逻辑删除店的首页背景图片
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDeleteBgImg(String bgId) {
        int result = bgImgMapper.update(null, new UpdateWrapper<BgImg>()
                .eq("bg_id", bgId)
                .set("del_flag", 1));
        return result > 0;
    }

}
