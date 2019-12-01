package com.doudou.project.meeting.oauth.controller;



import com.doudou.po.Meetingtype;
import com.doudou.service.MeetingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author caiminjie
 * @date 2019/11/28
 **/
@Controller
@RequestMapping("meetingType")
public class MeetingTypeController {

    @Autowired
    private MeetingTypeService meetingTypeService;

    /**
     * 微信会议发布页面中 加载下拉框的数据
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "list")//meetingType/list
    public List<Meetingtype> selectMeetingTypeList(){
        return meetingTypeService.selectByStatusAndSortNumAsc();
    }
}

