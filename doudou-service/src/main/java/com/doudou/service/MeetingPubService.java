package com.doudou.service;

/**
 * @Description:
 * @Company:
 * @Author: 窦刘柱
 * @Date:2019/11/28
 * @Time:21:02
 */

import com.doudou.po.MeetingPub;

import java.util.List;

/**
 * 会议发单表
 */
public interface MeetingPubService {

    /**
     * 微信端 会议发单功能
     * @param record
     * @return
     */
    int insertWeixinSelective(MeetingPub record);

    /**
     * 我的会议 根据发单人
     */
    List<MeetingPub> selectMeetingPubByUid(String uid);

    /**
     * 我的抢单者列表
     * （显示都是发单数据的详情列表）
     */
    List<MeetingPub> selectGrabList(String uid,String tname);
}

