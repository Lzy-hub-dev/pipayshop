package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.TradinPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.dto.TradinPostDTO;
import com.example.pipayshopapi.entity.dto.TransactionDTO;
import com.example.pipayshopapi.entity.vo.TraditionDetailVO;
import com.example.pipayshopapi.entity.vo.TraditionListVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wzx
 * @since 2023-09-09
 */
public interface TradinPostService extends IService<TradinPost> {

    int publishTradition(String token);

    List<TraditionListVO> selectTraditionList(Integer typeId);

    TraditionDetailVO selectTraditionDetail(String tradinId) throws InterruptedException;

    void updateStatusByTradinId(String tradinId);

    boolean upLoadImg(MultipartFile file, TradinPostDTO tradinPostDTO);
}
