package com.doudou.service.impl;

import com.doudou.mapper.MeetingPubMapper;
import com.doudou.po.MeetingPub;
import com.doudou.service.MeetingPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/28
 * @Time:21:03
 */
@Service
public class MeetingPubServiceImpl implements MeetingPubService {


    @Autowired
    private MeetingPubMapper meetingPubMapper;
    /**
     * 微信端发单功能
     * @param meetingpub
     * @return
     */
    @Override
    public int insertWeixinSelective(MeetingPub meetingpub) {

        meetingpub.setId(UUID.randomUUID().toString());
        meetingpub.setCreatedate(new Date());
        meetingpub.setStatus((short) 1);
        meetingpub.setPcode(pcodeGen(meetingpub.getPtime()));//按规则生成

        return meetingPubMapper.insertSelective(meetingpub);
    }

    @Override
    public List<MeetingPub> selectMeetingPubByUid(String uid) {
        return meetingPubMapper.selectMeetingPubByUid(uid);
    }

    /**
     * 我的抢单者列表
     * （显示都是发单数据的详情列表）
     *
     * @param uid
     * @param tname
     */
    @Override
    public List<MeetingPub> selectGrabList(String uid, String tname) {
        return meetingPubMapper.selectGrabList(uid,tname);
    }

    /**
     * 会议编号生成规则
     * 1.生成时间进行字符串截取20191128
     * 2.根据时间字符串去数据库进行查找
     * selecct max(pcode) from meetingpub where LEFT(pcode,8)="20191128";
     * 如果查询到null 则20191128+001
     * 如果不为null，最大的那个单数取出，对其进行加1操作 就是当前编号
     */
    private  String pcodeGen(String ptime){
        //对时间做截取
        String str = ptime.substring(0,10);
        str =  str.replaceAll("-","");//例如2019-11-28 12：30--》2019-11-28--》20191128

        String result = meetingPubMapper.selectMaxPcodeByTime(str);

        if (StringUtils.isEmpty(result)){
            return str+"001";
        }else {
            Long l = Long.parseLong(result)+1;
            return l.toString();
        }
    }
}
