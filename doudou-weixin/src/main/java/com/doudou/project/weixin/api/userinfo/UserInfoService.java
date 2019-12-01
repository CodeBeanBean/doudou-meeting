package com.doudou.project.weixin.api.userinfo;

import com.doudou.po.WeiUser;
import com.doudou.project.weixin.api.accesstoken.AccessTokenRedis;
import com.doudou.project.weixin.util.WeixinUtil;
import com.doudou.service.WeiUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/27
 * @Time:0:18
 */
@Service
public class UserInfoService {
    @Autowired
    private AccessTokenRedis accessTokenRedis;
    @Autowired
    private  WeiUserService weiUserService;
   private static final String USER_INFO_URL ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 手机用户的个人信息
     *  先通过openid 判断,如果不存在 执行添加功能
     *                     如果存在  更新/不执行
     *
     */
    public void userInfo(String openid){
        WeiUser weiuserObj =weiUserService.selectByOpenid(openid);
            if(weiuserObj==null){
                // 1得到用户的openid //2根据openid向微信服务器发送get请求得到用户信息
                JSONObject jsonObject = getJsonObjectByOpenid(openid);
                // 3得到用户是jsonobject对象-》转成weiuser对象
                WeiUser weiuser = convertJSONWeiUser(jsonObject);
                //4将weiuser对象进行数据库的添加操作
                 addWeiUser(weiuser);

            }else {
                //什么都不做/或者更新
            }

    }

    /**
     * 1根据得到用户的openid 向微信服务器发送get请求得到用户信息
     * @param openid
     * @return
     */
    public JSONObject getJsonObjectByOpenid(String openid){
        String url = USER_INFO_URL.replace("OPENID",openid).replace("ACCESS_TOKEN",accessTokenRedis.getAccessTokenVal());
       JSONObject jsonObject = WeixinUtil.httpRequest(url,"GET",null);

        return jsonObject;
    }

    /**
     * 2得到用户是jsonobject对象-》转成weiuser对象
     * @param jsonObject
     * @return
     */
    public WeiUser convertJSONWeiUser(JSONObject jsonObject ){
                /**
                 * simpleway
                 *  WeiUser weiuser = new Weiuser();
                 *         weiUser.setOpenid(jsonObject.getString("openid"));
                 */
        WeiUser weiuser = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            weiuser = objectMapper.readValue(jsonObject.toString(), WeiUser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return weiuser;
    }

    /**
     * 3将weiuser对象进行数据库的添加操作
     * @param weiuser
     * @return
     */
    public int addWeiUser(WeiUser weiuser){
        return  weiUserService.insertSelective(weiuser);
    }
}
