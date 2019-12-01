package com.doudou.service.impl;

import com.doudou.mapper.UserMapper;
import com.doudou.po.User;
import com.doudou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.util.PendingException;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/27
 * @Time:20:45
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 微信端O
     * 根据wid判断user表中是否存在该对象
     *
     * @param wid
     *
     */
    @Override
    public User selectByWid(Integer wid) {
        return userMapper.selectByWid(wid);
    }


    @Override
    public User selectByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    /**
     * 进行登录绑定功能
     */
    @Override
    public int updateByEmail(Integer wid, String email) {
        return userMapper.updateByEmail(wid,email);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 会议发单 角色判断
     *
     * @param openid
     */
    @Override
    public User selectBtOpenid(String openid) {
        return  userMapper.selectBtOpenid(openid);
    }
}
