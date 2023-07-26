package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.BuyerData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.BuyerDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 买家的基本数据（多选） Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface BuyerDataMapper extends BaseMapper<BuyerData> {

    /**
     * 根据id查找买家的基本信息
     * @param id
     * @return
     */
    BuyerDataVO selectBuyerDataById(@Param("id") long id);

}
