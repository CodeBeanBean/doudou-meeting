package com.doudou.project.weixin.api.tuling.bean;

import lombok.Data;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/21
 * @Time:17:03
 */
@Data
public class UserInfo {
    /**
     * 机器人标识
     */
    private String apiKey;
    /**
     *用户标识
     */
    private String UserId;
}
