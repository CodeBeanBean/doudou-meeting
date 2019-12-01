package com.doudou.oauth;

import com.doudou.project.weixin.main.MenuManager;
import com.doudou.project.weixin.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/26
 * @Time:19:40
 */
@RequestMapping("oauth")
@Controller("weixinOauth1")
public class WeixinOauth {
    @RequestMapping("weixin")
    public void oauth(HttpServletResponse response) throws IOException {
        String redirect_url=MenuManager.REAL_URL+"oauth/invoke";
        try {
            redirect_url = URLEncoder.encode(redirect_url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //1.用户同意授权。获取code
       String url= "https://open.weixin.qq.com/connect/oauth2/authorize?" +
               "appid=" + MenuManager.appId+
               "&redirect_uri=" +redirect_url+
               "&response_type=code" +
               "&scope=snsapi_userinfo" +
               "&state=doudou" +
               "#wechat_redirect";
        response.sendRedirect(url);
    }
    @RequestMapping("invoke")
    public String invoke(HttpServletRequest request){
        //第二步 得到code
        String code= request.getParameter("code");
        System.out.println("得到code"+code);
        request.getParameter("state");
        //获取code后，请求以下链接获取access_token：
        String url ="https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" +MenuManager.appId+
                "&secret=" +MenuManager.appSecret+
                "&code=" +code+
                "&grant_type=authorization_code";

        //发送请求
     JSONObject jsonObject = WeixinUtil.httpRequest(url,"GET",null);
        System.out.println("json"+jsonObject.toString());
        //得到accesstoken
        String access_token =  jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");

        //3 拉取用户基本信息（scope位 snsapi_userinfo）
        //hhttp:GET 需要https协议
       String userInfoUrl ="https://api.weixin.qq.com/sns/userinfo?" +
               "access_token=" +access_token+
               "&openid=" +openid+
               "&lang=zh_CN";

       JSONObject userInfoJson = WeixinUtil.httpRequest(userInfoUrl,"GET",null);

       request.setAttribute("userInfoJson",userInfoJson);

        return "oauth";
    }
}
