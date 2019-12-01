package com.doudou.service.impl;

import com.doudou.mapper.WeiUserMapper;
import com.doudou.po.WeiUser;
import com.doudou.service.WeiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/26
 * @Time:23:35
 */
@Service
public class WeiUserServiceImpl implements WeiUserService {
    @Autowired
    public WeiUserMapper weiUserMapper;
    /**
     * 根据openid 判断weiuser表中是否存在该对象信息
     * @param openid
     */
    @Override
    public WeiUser selectByOpenid(String openid) {
        return weiUserMapper.selectByOpenid(openid);
    }

    /**
     * 添加功能
     *
     * @param record
     */
    @Override
    public int insertSelective(WeiUser record) {
        return weiUserMapper.insertSelective(record);
    }
}
