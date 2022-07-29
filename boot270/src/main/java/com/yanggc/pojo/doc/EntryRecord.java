package com.yanggc.pojo.doc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author: YangGC
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntryRecord implements Serializable {

    /**
     * 索引字段应以业务字段为依据使用驼峰命名约定
     */
    private Long id;

//    @TableId
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long member_be_present_id;

    private Long member_id;
    private Long club_id;
    private Long store_id;

    private String store_name;

    private String name;
    private String mobile;

    //进店方式，1 saas进店，2 人脸进店,3 手环进店（设备进店）
    private Integer arrival_mode;

    private String face_img;

    private Integer stu;
    /**
     * 进店时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime time;

    /**
     * 离店时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime end_time;

    private Long card_id;
    private Long card_detail_id;
    private String card_name;

//    private String chipCode;
    private String remark;

    //入场会员类型  1 会员 2 员工
    private Integer member_type;
}
