package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.dto.RebateDTO;
import com.example.pipayshopapi.entity.vo.ResponseVO;

public interface RebateService {
    /**
     * 一级团返佣
     * @return
     */
    ResponseVO<String> firstRebate(RebateDTO rebateDTO);

    /**
     * 二级团返佣
     * @return
     */
    ResponseVO<String> twoRebate(RebateDTO rebateDTO);
}
