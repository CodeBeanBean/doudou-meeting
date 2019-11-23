package com.doudou.project.weixin.api.accesstoken;

import com.doudou.project.weixin.main.MenuManager;
import com.doudou.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/23
 * @Time:10:32
 */
@Service
public class AccessTokenRedis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    private static final String REDIS_ACCESS_TOKEN="access_token";
    /**
     * @Author 窦刘柱
     * @Description //TODO key如果存在 到redis中查询如果不存在
     * 在微信服务器中获取请求结果，存入redis中
     * @Date 10:34 2019/11/23
     * @Param []
     * @return java.lang.String
     **/
    public String getAccessTokenVal(){
        if(redisTemplate.hasKey(REDIS_ACCESS_TOKEN)){
            System.out.println("redis中的access-token");

           return (String) redisTemplate.opsForValue().get(REDIS_ACCESS_TOKEN);

        }else {
           String accessTokenVal = getAccessTokenValFromWeixin();
            System.out.println("从微信拿的access-token");

            redisTemplate.opsForValue().set(REDIS_ACCESS_TOKEN,accessTokenVal,10, TimeUnit.SECONDS);
            return accessTokenVal;
        }


    }

    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private  String getAccessTokenValFromWeixin(){
        String requestUrl=ACCESS_TOKEN_URL.replace("APPID", MenuManager.appId).replace("APPSECRET",MenuManager.appSecret);
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,"GET",null);
        String result = (String) jsonObject.get("access_token");
        return result;
    }
}
