package com.doudou.project.weixin.api.accesstoken;

import com.doudou.project.weixin.main.MenuManager;
import com.doudou.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import sun.java2d.loops.GeneralRenderer;

/**
 * @Description:线程定时向服务器获得access_token
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/22
 * @Time:19:10
 */
public class AccessTokenThread extends Thread{
    /**
     * 接收access_token的值
     */
    public static String access_token_val;


    @Override
    public void run() {
      while(true){

          //微信的access_token失效时间是7200s ==> 7200*1000
          try {
              access_token_val = getAccessTokenVal();
              System.out.println(access_token_val);
              Thread.sleep(7000000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
    }

    /**
     * @Author 窦刘柱
     * @Description //TODO 向微信服务器发送get请求,得到access_token
     * @Date 19:26 2019/11/22
     * @Param
     * @return
     **/
    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private  String getAccessTokenVal(){
        String requestUrl=ACCESS_TOKEN_URL.replace("APPID", MenuManager.appId).replace("APPSECRET",MenuManager.appSecret);
        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl,"GET",null);
        String result = (String) jsonObject.get("access_token");
        return result;
    }
}
