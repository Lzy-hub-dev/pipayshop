package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.BrandInfo;
import com.example.pipayshopapi.entity.vo.BrandInfoVO;
import com.example.pipayshopapi.mapper.BrandInfoMapper;
import com.example.pipayshopapi.service.BrandInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-26
 */
@Service
public class BrandInfoServiceImpl extends ServiceImpl<BrandInfoMapper, BrandInfo> implements BrandInfoService {

    @Resource
    private BrandInfoMapper brandInfoMapper;

    /**
     * 查找二级分类的id查找的品牌集合
     */
    @Override
    public List<BrandInfoVO> selectAllBrandList() {
        return brandInfoMapper.selectAllContentList();
    }

    /**
     * 网店获取所有品牌接口
     */
    @Override
    public List<BrandInfoVO> itemSelectAllBrandList() {
        return brandInfoMapper.itemSelectAllContentList();
    }
}
