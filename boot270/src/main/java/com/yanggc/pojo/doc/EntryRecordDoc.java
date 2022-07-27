package com.yanggc.pojo.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 * Description:
 *
 * @author: YangGC
 */
//Setting是创建分片和副本，新版默认都是1，可以不写。
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntryRecordDoc {

//    //必须有 id,这里的 id 是全局唯一的标识，等同于 es 中的"_id"
    private Long id;


    private Long memberBePresentId;

    private Long memberId;
    private Long clubId;
    private Long storeId;

    private String storeName;

    private String name;
    private String mobile;

    //进店方式，1 saas进店，2 人脸进店,3 手环进店（设备进店）
    private Integer arrivalMode;

    private String faceImg;

    private Integer stu;
    /**
     * 进店时间
     */
    private LocalDateTime time;

    /**
     * 离店时间
     */
    private LocalDateTime endTime;

    private Long cardId;
    private Long cardDetailId;
    private String cardName;

    private String chipCode;
    private String remark;

    private Integer memberType;
}
