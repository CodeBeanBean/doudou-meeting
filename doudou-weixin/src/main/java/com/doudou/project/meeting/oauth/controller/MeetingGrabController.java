package com.doudou.project.meeting.oauth.controller;


import com.doudou.po.MeetingPub;
import com.doudou.po.Meetinggrab;
import com.doudou.service.MeetingGrabService;
import com.doudou.service.MeetingPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author caiminjie
 * @date 2019/11/30
 **/

@Controller
public class MeetingGrabController {
    @Autowired
    private MeetingGrabService meetingGrabService;
    @Autowired
    private MeetingPubService meetingPubService;
    /**
     * 会议抢单--》可抢单--》目标页面
     */
    @RequestMapping("meetingGrab/addPage")
    public String meetingGrabAddPage(@RequestParam("uid") String uid, @RequestParam("pid") String pid, Map<String,Object> map){

        map.put("uid",uid);
        map.put("pid",pid);


        return "weixin/meetingGrab/meetingGrabAdd";
    }

    /**
     * 进行会议的添加功能
     * 并进入会议抢单列表页面
     */
    @RequestMapping("meetingGrab/add")//meetingGrab/add
    public ModelAndView meetingGrabAdd(Meetinggrab meetinggrab){
        ModelAndView modelAndView = new ModelAndView();
        meetingGrabService.insertSelectiveWeixin(meetinggrab);
        modelAndView.addObject("uid",meetinggrab.getUid());
        modelAndView.setViewName("weixin/meetingGrab/meetingGrab");

        return modelAndView;
    }

    /**
     * 我的抢单列表
     * 会议 --》会议抢单 --》 我的抢单
     * @Param uid 是抢单人的UID
     */
    @RequestMapping("meetingGrab/uid")//meetingGrab/uid?uid=..?
    @ResponseBody
    public List<MeetingPub> selectMyGrabList(@RequestParam("uid") String uid){
        return meetingPubService.selectMyGrabList(uid);
    }
}
