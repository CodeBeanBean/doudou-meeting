package com.doudou.project.meeting.oauth;

import com.doudou.po.User;
import com.doudou.po.WeiUser;
import com.doudou.project.weixin.main.MenuManager;
import com.doudou.project.weixin.util.WeixinUtil;
import com.doudou.service.UserService;
import com.doudou.service.WeiUserService;
import net.sf.json.JSONObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Date:2019/11/27
 * @Time:20:09
 */

@RequestMapping("oauth")
@Controller
@MapperScan(basePackages = {"com.doudou.mapper"})
public class WeixinOauth {
    @Autowired
    private WeiUserService weiUserService;
    @Autowired
    private UserService userService;


    /**
     * 微信个人中心菜单按钮
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("weixin/user")
    public void oauth(HttpServletResponse response) throws IOException {
        //授权后重定向的回调连接地址，使用uirEncode对链接进行处理
        String redirect_url = MenuManager.REAL_URL + "oauth/weixin/user/invoke";
        try {
            redirect_url = URLEncoder.encode(redirect_url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //1.用户同意授权。获取code
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + MenuManager.appId +
                "&redirect_uri=" + redirect_url +
                "&response_type=code" +
                "&scope=snsapi_userinfo" + //静默授权
                "&state=doudou" +
                "#wechat_redirect";//只能在微信中打开
        //如果提示无法访问则 检查参数错误 scope 对应授权权限
        response.sendRedirect(url);
    }

    @RequestMapping("weixin/user/invoke")
    public String invoke(HttpServletRequest request) {
        //第二步 得到code
        String code = request.getParameter("code");
        System.out.println("得到code" + code);
        request.getParameter("state");
        //获取code后，请求以下链接获取access_token：
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + MenuManager.appId +
                "&secret=" + MenuManager.appSecret +
                "&code=" + code +
                "&grant_type=authorization_code";

        //发送请求
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
        System.out.println("json" + jsonObject.toString());
        //得到accesstoken
        String access_token = jsonObject.getString("access_token");
        //得到openid
        String openid = jsonObject.getString("openid");
        /**
         * 用户时候进行绑定
         * 1根据openid 查询weiuser类 得到逐渐ID
         * 2根据wid去查询weiuser表
         *
         */
        WeiUser weiUser = weiUserService.selectByOpenid(openid);
        if (weiUser == null) {
            //微信的个人信息不存在我的库中
            //1 提示 重新关注
            //2 收集用户信息 存入数据库中
            System.out.println("该用户微信个人信息不存在" + openid);

        } else {
            //判断当前的微信用户是否进行登录（绑定）功能
            User user = userService.selectByWid(weiUser.getId());
            if (user == null) { //未绑定 跳到登陆页面
                request.setAttribute("wid", weiUser.getId());
                return "weixin/login";//templates/***.html
            } else {
                request.setAttribute("user", user);
                return "weixin/user/userInfo";
            }

        }
        return "oauth";
    }

    /**
     * 微信 会议 ---》会议发布
     */
    @RequestMapping("weixin/meetingPub") //oauth/weixin/meetingPub
    public void oauthMeetingPub(HttpServletResponse response) throws IOException {
        //授权后重定向的回调连接地址，使用uirEncode对链接进行处理
        String redirect_url = MenuManager.REAL_URL + "oauth/weixin/meetingPub/invoke";
        try {
            redirect_url = URLEncoder.encode(redirect_url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //1.用户同意授权。获取code
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + MenuManager.appId +
                "&redirect_uri=" + redirect_url +
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=doudou" +
                "#wechat_redirect";//只能在微信中打开
        //如果提示无法访问则 检查参数错误 scope 对应授权权限
        response.sendRedirect(url);
    }

    @RequestMapping("weixin/meetingPub/invoke")  // oauth/weixin/meetingPub/invoke
    public String meetingPubinvoke(HttpServletRequest request) {
        //第二步 得到code
        String code = request.getParameter("code");
        System.out.println("得到code" + code);
        request.getParameter("state");
        //获取code后，请求以下链接获取access_token：
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + MenuManager.appId +
                "&secret=" + MenuManager.appSecret +
                "&code=" + code +
                "&grant_type=authorization_code";

        //发送请求
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
        //得到openid
        String openid = jsonObject.getString("openid");

        /**
         * 1、通过openid 得到主键id weiuser
         * 2、通过wid查询user对象user.getRid
         */

        WeiUser weiUser = weiUserService.selectByOpenid(openid); //1、通过openid 得到主键id weiuser
        if (weiUser == null) {
            //1.数据库weiuser表中没有你的微信个人信息--》重新收集个人数据提示/提示：网络异常 重新关注公众号
            return "";
        } else {
            // * 2、通过wid查询user对象user.getRid
            User user = userService.selectByWid(weiUser.getId());
            if (user == null) { //2、未进行绑定的过程 跳到登陆页面
                request.setAttribute("wid", weiUser.getId());
                return "weixin/login";//templates/***.html
            } else {//已绑定判断角色是发单组还是抢单组
                if (1 == user.getRid()) {//发单组 跳到发单组页面
                    request.setAttribute("uid",user.getId());
                    return "weixin/meetingPub/meetingPub";
                } else if (2 == user.getRid()) {//抢单组--》无权限页面
                    return "weixin/unauth";
                } else {//查看组
                    return "";//其他情况
                }
            }
        }

    }
}
