package com.doudou.service.impl;

import com.doudou.mapper.MeetinggrabMapper;
import com.doudou.po.Meetinggrab;
import com.doudou.service.MeetingGrabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author caiminjie
 * @date 2019/11/30
 **/
@Service
public class MeetingGrabServiceImpl implements MeetingGrabService {
    @Autowired
    private MeetinggrabMapper meetinggrabMapper;
    /**
     * 微信端 会议抢单--》进行抢单功能
     */
    @Override
    public int insertSelectiveWeixin(Meetinggrab meetinggrab) {
        meetinggrab.setId(UUID.randomUUID().toString());
        meetinggrab.setCreatedate(new Date());
        meetinggrab.setGrabstatus(0); //默认值为0 未匹配状态
        meetinggrab.setStatus((short) 1);
        return meetinggrabMapper.insertSelective(meetinggrab);
    }
}
