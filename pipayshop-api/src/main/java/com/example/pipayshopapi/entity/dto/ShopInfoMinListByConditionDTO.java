package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopInfoMinListByConditionDTO {

   private Integer limit;
   private Integer pages;
   private String categoryId;
   private String regionId;
   private String shopName;
}
