package com.doudou.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MeetingPub implements Serializable {
   /** 主键Id UUID */
    private String id;
    /** 会议编号（按规则生成）*/
    private String pcode;
        /** 会议召开日期*/
    private String ptime;
        /**课题类别 */
    private String tname;
        /**会议主题*/
    private String ptitle;
        /** 讲者区域*/
    private String pzone;
        /** 发单人*/
    private String uid;
        /** 描述**/
    private String remark;
    /** 发单时间*/
    private Date createdate;
        /**发单状态 0无效1有效*/
    private Short status;


}