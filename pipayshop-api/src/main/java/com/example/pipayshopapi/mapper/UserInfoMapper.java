package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户数据表 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
