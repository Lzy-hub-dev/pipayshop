package com.example.pipayshopapi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfoListByConditionDTO {

   private  Integer limit;
   private  Integer pages;
   private  String categoryId;
   private  Boolean score;
   private  String regionId;
   private  String shopName;

}
