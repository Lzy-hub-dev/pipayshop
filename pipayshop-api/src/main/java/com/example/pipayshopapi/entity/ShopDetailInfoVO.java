package com.example.pipayshopapi.entity;

import com.example.pipayshopapi.entity.vo.EvaluateVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDetailInfoVO {

    /**
     * 商品的id
     */
    private String commodityId;
    /**
     * 商品的名字
     */
    private String commodityName;
    /**
     * 商品的图片路径集合(前端接收)
     */
    private List<String> commodityImgList1;

    //路经集合(数据库接收)
    private String commodityImgList;

    /**
     * 商品详情
     */
    private String commodityDetail;
    /**
     * 商品的价格
     */
    private BigDecimal price;
    /**
     * 月售量
     */
    private Integer monthlySales;
    /**
     * 该商品的店铺id
     */
    private String shopId;
    /**
     * 商品有效期
     */
    private Integer validityTime;
    /**
     * 商品剩余余额
     */
    private Integer residue;
    /**
     * 注意事項
     */
    private String reservationInformation;
    /**
     * 顾客评价列表10条
     */
    private List<EvaluateVO> evaluateVOList;
}
