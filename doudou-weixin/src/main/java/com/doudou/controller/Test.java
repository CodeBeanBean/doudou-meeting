package com.doudou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/23
 * @Time:9:50
 */
@RestController
public class Test {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @RequestMapping("info")
    public String info(){
        System.out.println("redis对象"+redisTemplate);
        return "info";
    }
}
