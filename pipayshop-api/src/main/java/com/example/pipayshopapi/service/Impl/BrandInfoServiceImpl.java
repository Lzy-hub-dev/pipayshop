package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.BrandInfo;
import com.example.pipayshopapi.entity.vo.BrandInfoVO;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;
import com.example.pipayshopapi.mapper.BrandInfoMapper;
import com.example.pipayshopapi.mapper.ShopCategoryMapper;
import com.example.pipayshopapi.mapper.ShopCategoryTopMapper;
import com.example.pipayshopapi.service.BrandInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Resource
    private ShopCategoryTopMapper shopCategoryTopMapper;

    @Resource
    private ShopCategoryMapper shopCategoryMapper;

    /**
     * 查找品牌分类列表
     * @return
     */
    @Override
    public List<ShopCategoryVO> selectAllBrandList() {
        //查找出一级分类列表
        List<ShopCategoryVO> shopCategoryVOS = shopCategoryTopMapper.selectAllTopContentList();
        List<ShopCategoryVO> temList = shopCategoryVOS;

        for (ShopCategoryVO shopCategoryVO : shopCategoryVOS) {
            String categoryId = shopCategoryVO.getCategoryId();
            //查找出二级分类列表
            List<ShopCategoryVO> list = shopCategoryMapper.selectAllContentList(categoryId);
            shopCategoryVO.setCategoryVOList(list);

            for (ShopCategoryVO listSon : list) {
                String sonCategoryId = listSon.getCategoryId();
                List<BrandInfoVO> brandInfoVOS = brandInfoMapper.selectAllContentList(sonCategoryId);
                listSon.setCategoryVOList(brandInfoVOS);
            }
        }
        return temList;
    }
}
