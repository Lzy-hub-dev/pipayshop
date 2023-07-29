package com.example.pipayshopapi.controller;


import com.example.pipayshopapi.entity.FollowFocus;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemCollectionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店商品收藏表	 前端控制器
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Controller
@RequestMapping("/pipayshopapi/item-collection")
public class ItemCollectionController {

}
