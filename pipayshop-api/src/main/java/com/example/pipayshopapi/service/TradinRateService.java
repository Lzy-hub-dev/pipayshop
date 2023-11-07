package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.TradinRate;
import com.baomidou.mybatisplus.extension.service.IService;

import com.example.pipayshopapi.entity.vo.TradinRateVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wzx
 * @since 2023-09-14
 */
public interface TradinRateService extends IService<TradinRate> {

    List<TradinRateVO> selectAllTradinRate();
}
