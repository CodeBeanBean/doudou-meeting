package com.doudou.service;

import com.doudou.po.User;
import com.doudou.po.WeiUser;
import org.apache.ibatis.annotations.Select;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/27
 * @Time:20:39
 */
public interface UserService {
//###################TODO 后台端代码
    /**
     * 后台增删改常用方法
     */
    //##################TODO 微信端代码
    /**微信端O
     * 根据wid判断user表中是否存在该对象
     */
    @Select("select * from user where wid=#{wid}")
    User selectByWid(Integer wid);

    /**
     * 根据邮箱查询user对象信息
     */
    User selectByEmail(String email);

    /**
     * 进行登录绑定功能
     */
    int updateByEmail(Integer wid,String email);

    int updateByPrimaryKeySelective(User record);

    /**
     * 会议发单 角色判断
     */
    User selectBtOpenid(String openid);
}

