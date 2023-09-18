package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.TradinRate;
import com.example.pipayshopapi.entity.vo.TradinRateDTO;
import com.example.pipayshopapi.mapper.TradinRateMapper;
import com.example.pipayshopapi.service.TradinRateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wzx
 * @since 2023-09-14
 */
@Service
public class TradinRateServiceImpl extends ServiceImpl<TradinRateMapper, TradinRate> implements TradinRateService {

    @Resource
    private TradinRateMapper tradinRateMapper;

    @Override
    public List<TradinRateDTO> selectAllTradinRate() {
        return tradinRateMapper.selectAllTradinRate();
    }
}
