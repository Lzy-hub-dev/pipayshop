package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopTags;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ShopTagsMapper;
import com.example.pipayshopapi.service.ShopTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 实体店的标签 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ShopTagsServiceImpl extends ServiceImpl<ShopTagsMapper, ShopTags> implements ShopTagsService {

    @Autowired
    private ShopTagsMapper shopTagsMapper;

    @Override
    public PageDataVO getShopTagsList(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<ShopTags> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopTags::getDelFlag, 0);

        Page<ShopTags> page = new Page<>(pageNum,pageSize);
        Page<ShopTags> tags = shopTagsMapper.selectPage(page, wrapper);

        return new PageDataVO(Integer.valueOf(tags.getTotal()+""),tags.getRecords());
    }

    /**
     * 根据标签id查询标签信息
     */
    @Override
    public ShopTags getShopTagsById(String tagsId) {
        return shopTagsMapper.selectOne(new LambdaQueryWrapper<ShopTags>()
                .eq(ShopTags::getDelFlag,0)
                .eq(ShopTags::getTagId,tagsId));
    }

    /**
     * 根据标签id删除标签
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteShopTagsById(String tagsId) {
        return shopTagsMapper.update(null, new LambdaUpdateWrapper<ShopTags>()
                .eq(ShopTags::getTagId, tagsId)
                .eq(ShopTags::getDelFlag,0)
                .set(ShopTags::getTagId,1))>0;
    }

    /**
     * 根据标签id修改标签
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateShopTagsById(ShopTags shopTags) {
        return shopTagsMapper.update(shopTags,new LambdaQueryWrapper<ShopTags>()
                .eq(ShopTags::getDelFlag,0)
                .eq(ShopTags::getTagId,shopTags.getTagId())) > 0;
    }

    /**
     * 新增标签
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addShopTags(ShopTags shopTags) {
        return shopTagsMapper.insert(shopTags) > 0;
    }
}
