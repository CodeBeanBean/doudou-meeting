package com.doudou.mapper;

import com.doudou.po.User;
import com.doudou.po.WeiUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据wid判断user表中是否存在该对象
     */
    @Select("select * from user where wid=#{wid}")
    User selectByWid(Integer wid);
    /**
     * 根据邮箱查询user对象信息
     */
    @Select("select * from user where email=#{email}")
    User selectByEmail(String email);

    /**
     * 进行登录绑定功能
     */
    @Update("update user set wid=#{wid} where email=#{email}")
    int updateByEmail(Integer wid,String email);

    /**
     * 根据weiuser中的openid 查询得到user对象
     */

    @Select("select * from user where wid =(select id from weiuser where openid=#{openid}) ")
    User selectBtOpenid(String openid);
    /**
     * 注解 能解决百分之百问题
     *   优点   方便 直观
     *    缺点  （动态标签script） 复杂的sql拼接 动态标签 用注解的话很难阅读
     * xml配置
     *
     *
     */
}