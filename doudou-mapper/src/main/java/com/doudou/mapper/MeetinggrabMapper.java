package com.doudou.mapper;


import com.doudou.po.Meetinggrab;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetinggrabMapper {

    int deleteByPrimaryKey(String id);

    int insert(Meetinggrab record);

    int insertSelective(Meetinggrab record);

    Meetinggrab selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Meetinggrab record);

    int updateByPrimaryKey(Meetinggrab record);
    /**
     * 根据会议发单id获取抢单人列表信息
     */
    List<Meetinggrab> selectGrabListByPid(String pid);

    /**
     * 就选你功能
     * 1.首先将所有的抢单根据pid改为匹配失败，grabStatus=2
     * 2.将指定的用户（作为讲者）改状态为1 匹配成功
     */
    @Update("update meetinggrab set grabStatus=2,grabDate=NOW() where pid = #{pid}")
    int updateMeetingGrabChooseFail(String pid);
    @Update("update meetinggrab set grabStatus=1,grabDate=NOW() where pid=#{pid} and uid=#{uid}")
    int updateMeetingGrabChooseSucc(String pid,String uid);

}