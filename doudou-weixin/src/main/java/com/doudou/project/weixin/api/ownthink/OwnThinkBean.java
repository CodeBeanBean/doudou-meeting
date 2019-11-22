package com.doudou.project.weixin.api.ownthink;

import lombok.Data;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/22
 * @Time:10:25
 */
@Data
public class OwnThinkBean {
    /**
     * {
     *     "spoken": "姚明多高啊？",
     *     "appid": "xiaosi",
     *     "userid": "user"
     * }
     */
    private String spoken;
    private String appid;
    private String user;
}
