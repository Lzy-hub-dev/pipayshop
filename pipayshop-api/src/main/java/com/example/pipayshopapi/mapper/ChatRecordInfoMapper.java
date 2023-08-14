package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ChatRecordInfo;
import com.example.pipayshopapi.entity.vo.ChatDataVO;
import com.example.pipayshopapi.entity.vo.ChatVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzx
 * @since 2023-07-18
 */
@Mapper
public interface ChatRecordInfoMapper extends BaseMapper<ChatRecordInfo> {

    /**
     * 删除过期的聊天记录
     */
    @Delete("DELETE FROM chat_record_info WHERE send_time < DATE_SUB(NOW(), INTERVAL 3 DAY)")
    void deleteOutTime();


    List<ChatVO> getChatRecord(@Param("chatDataVO") ChatDataVO chatDataVO);

    Integer getChatRecordSum(@Param("chatDataVO") ChatDataVO chatDataVO);
}
