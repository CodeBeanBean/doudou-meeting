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

import java.util.List;

/**
 * @author caiminjie
 * @date 2019/11/28
 **/
@Controller
@RequestMapping("meetingPub")
public class MeetingPubController {
    @Autowired
    private MeetingPubService meetingPubService;
    @Autowired
    private MeetingGrabService meetingGrabService;

    @ResponseBody
    @RequestMapping("add")//meetingPub/add
    public  int meetingPubAdd(MeetingPub meetingpub){
        int num = meetingPubService.insertWeixinSelective(meetingpub);
        return num;

    }

    /**
     * 我的会议
     */
    @ResponseBody
    @RequestMapping("byUid")//meetingPub/byUid
    public List<MeetingPub> selectMyMeetingPub(@RequestParam("uid") String uid){


        return meetingPubService.selectMeetingPubByUid(uid);
    }
    /**会议--》会议抢单
     * 可抢单列表
     */
    @ResponseBody
    @RequestMapping("grabList") //meetingPub/grabList
    public List<MeetingPub> selectGrabList(@RequestParam("uid") String uid,
                                           @RequestParam("tname") String tname){
     return meetingPubService.selectGrabList(uid,tname);

    }

    /**
     * 我的会议--》选则讲者 加载讲者列表
     */
    @ResponseBody
    @RequestMapping("grabListByPid")  //meetingPub/grabListByPid
    public List<Meetinggrab> selectGrabListByPid(@RequestParam("pid") String pid){
        return meetingGrabService.selectGrabListByPid(pid);
    }

    /**
     * 就选你功能
     */
    @ResponseBody
    @RequestMapping("chooseGrabList") //meetingPub/chooseGrabList
    public int chooseGrabList(@RequestParam("pid") String pid,@RequestParam("uid") String uid){
        int num = 0;
        try{
            num = meetingGrabService.chooseMeetingGrab(pid,uid);
        }catch (RuntimeException e){
            e.getMessage();
            return num;
        }
        return num;
    }
}
