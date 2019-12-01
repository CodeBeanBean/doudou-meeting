package com.doudou.service;


import com.doudou.po.Meetinggrab;

import java.util.List;

/**
 * @author caiminjie
 * @date 2019/11/30
 **/
public interface MeetingGrabService {
    /**
     * 微信端 会议抢单--》进行抢单功能
     */
    int insertSelectiveWeixin(Meetinggrab meetinggrab);

    /**
     * 根据会议发单id获取抢单人列表信息
     */
    List<Meetinggrab> selectGrabListByPid(String pid);

    /**
     * 就选你功能
     * 就选你功能
     * 1.首先将所有的抢单根据pid改为匹配失败，grabStatus=2
     * 2.将指定的用户（作为讲者）改状态为1 匹配成功
     */
    int chooseMeetingGrab(String pid,String uid) throws RuntimeException;
}
