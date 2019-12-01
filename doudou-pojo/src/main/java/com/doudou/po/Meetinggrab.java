package com.doudou.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Meetinggrab implements Serializable {
    /**主键id */
    private String id;
    /**发单id */
    private String pid;
    /**抢单描述信息 */
    private String remark;
    /**抢单人 */
    private String uid;
    /**抢单时间 */
    private Date createdate;
    /**匹配状态  0未匹配，1匹配成功，2匹配失败 */
    private Integer grabstatus;
    /**匹配时间*/
    private Date grabdate;
    /**状态*/
    private Short status;

}