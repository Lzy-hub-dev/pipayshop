package com.example.pipayshopapi.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

/**
 * @author ThinkPad
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityLiveInfoListDTO {


   private String shopId;

   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date startTime;

   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date endTime;
}
