package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: jiangjiafeng
 * @ClassName FansVO
 * @Description TODO
 * @date 2023/8/2 10:08
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FansVO {
    private String itemId;
    private String followId;
    private Date createTime;
    private String userName;
    private String userImage;
}
