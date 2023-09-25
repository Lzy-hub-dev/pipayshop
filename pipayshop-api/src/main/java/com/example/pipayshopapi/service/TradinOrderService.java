package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.TradinOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.TradinOrderDetailVO;
import com.example.pipayshopapi.entity.vo.TradinOrderListVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wzx
 * @since 2023-09-19
 */
public interface TradinOrderService extends IService<TradinOrder> {

    String generateTradeOrder(String tradinId, String buyerId) throws InterruptedException;

    boolean upLoadPointBalance(String token);

    boolean upLoadImg(MultipartFile file, String token);

    boolean confirmTransaction(String token);

    List<TradinOrderListVO> selectTradinyOrderByUid(String token);

    TradinOrderDetailVO selectTradinOrderDetail(String orderId);
}
