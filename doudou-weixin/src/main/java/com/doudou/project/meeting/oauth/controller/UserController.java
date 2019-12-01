package com.doudou.project.meeting.oauth.controller;

import com.doudou.po.User;
import com.doudou.po.WeiUser;
import com.doudou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/27
 * @Time:20:08
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 登录绑定流程
     * 用户输入的邮箱在数据库user表中 是否存在
     *    如果在
     *        该邮箱已被其他用户绑定
     *            提示：该邮箱已被其他用户绑定，如有疑问请联系管理员
     *         该邮箱未被其他用户绑定
     *             才能进行绑定功能：修改user表中的wid值
     *     如果不在
     *          提示：该邮箱不存在，无法进行登录功能
     */
    @ResponseBody
    @RequestMapping("login")
    public String login(@RequestParam("email")String email,
                        @RequestParam("wid")Integer wid){
        //用户输入的邮箱在数据库user表中是否存在
        User user=userService.selectByEmail(email);
        if (user!=null){
            //判断此user中是否存在wid，存在：已被其他人注册，不存在：将用户的wid存入
            if (user.getWid()!=null){
                //该邮箱已被其他用户绑定，如有疑问请联系管理员
                return "1";
            }else {
                //进行 绑定功能的业务逻辑 修改user表中的wid值
                userService.updateByEmail(wid, email);
                //登陆成功
                return "2";
            }
        }else {
            //邮箱不存在
            return "3";
        }
    }

    /**
     * 更新用户信息
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "updateUser")
    public String updateUserInfo(User user){
        int num =  userService.updateByPrimaryKeySelective(user);
        return  num+"";
    }

    /**
     * 页面跳转 Controlller操作
     * 登录页面 微信/login.html
     */
    @RequestMapping("to/login") //user/to/login
    public  String login(HttpServletRequest request){
/*        String  wid = request.getParameter("wid"); //在WeixinOauth中已经验证       request.setAttribute("wid", wid);*/
        return "weixin/login";
    }
    //无权限页面 weixin/unauth.html
    @RequestMapping("to/unauth")
    public  String unauth(){ //user/to/unauth

        return "weixin/unauth";
    }
    /**
     * 进入抢单页面
     */
    @RequestMapping("to/meetingGrab") //user/to/meetingGrab
    public String meetingGrab(HttpServletRequest request){
        String uid = request.getParameter("uid");
        request.setAttribute("uid",uid);
        return "weixin/meetingGrab/meetingGrab";
    }
}