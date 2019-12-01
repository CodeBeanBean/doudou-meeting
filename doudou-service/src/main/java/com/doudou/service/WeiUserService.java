package com.doudou.service;

import com.doudou.po.WeiUser;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/26
 * @Time:23:35
 */

public interface WeiUserService {

    /**
     *根据openid 判断weiuser表中是否存在该对象信息
     */
    WeiUser selectByOpenid(String openid);

    /**
     * 添加功能
     *
     */
    int insertSelective(WeiUser record);

}
