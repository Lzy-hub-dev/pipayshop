package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.UserRegister;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.dto.UserRegisterDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzx
 * @since 2023-09-18
 */
public interface UserRegisterMapper extends BaseMapper<UserRegister> {

    @Insert("insert into user_register(pi_name, password, uid) values (#{userRegisterDTO.piName}, #{userRegisterDTO.password}, #{userRegisterDTO.uid})")
    int insertRegisterData(@Param("userRegisterDTO") UserRegisterDTO userRegisterDTO);
}
