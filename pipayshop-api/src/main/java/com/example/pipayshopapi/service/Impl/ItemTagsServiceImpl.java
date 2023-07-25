package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.ItemTags;
import com.example.pipayshopapi.mapper.ItemTagsMapper;
import com.example.pipayshopapi.service.IItemTagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网店的标签表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ItemTagsServiceImpl extends ServiceImpl<ItemTagsMapper, ItemTags> implements IItemTagsService {

}
