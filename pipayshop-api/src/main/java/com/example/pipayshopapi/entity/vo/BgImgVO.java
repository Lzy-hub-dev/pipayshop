package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: jiangjiafeng
 * @ClassName BgImgVO
 * @Description
 * @date 2023/8/2 14:25
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BgImgVO {
    private String imgUrl;
    private String contentId;
    private String category;
}
