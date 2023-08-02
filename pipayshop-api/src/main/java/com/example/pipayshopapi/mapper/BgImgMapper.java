package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.BgImg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.BgImgVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * item/shop的首页背景轮播图数据 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface BgImgMapper extends BaseMapper<BgImg> {
    List<BgImgVO> selectBgImgList();
}
