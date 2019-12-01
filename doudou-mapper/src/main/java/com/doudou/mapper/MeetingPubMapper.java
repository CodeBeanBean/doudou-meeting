package com.doudou.mapper;

import com.doudou.po.MeetingPub;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MeetingPubMapper {
    int deleteByPrimaryKey(String id);

    int insert(MeetingPub record);

    int insertSelective(MeetingPub record);

    MeetingPub selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MeetingPub record);

    int updateByPrimaryKey(MeetingPub record);

    /**
     * 会议编号的生成规则
     */
    @Select("select max(pcode) from meetingpub where left(pcode,8)=#{time}")
    String selectMaxPcodeByTime(String time);

    /**
     * 我的会议 根据发单人
     */
    @Select("select * from meetingpub where uid=#{uid} and status = 1 order by pcode desc")
    List<MeetingPub> selectMeetingPubByUid(String uid);

    /**
     * 我的抢单者列表
     * （显示都是发单数据的详情列表）
     * pojo javabean
     * Map方式
     * arg0 arg1
     * param1 param2
     *  tname = -1 代表用户要查看是全部类别
     *
     *  tname = java / ui / 数据库
     */
    List<MeetingPub> selectGrabList(String uid,String tname);

    /**
     * 我的抢单列表g
     * 会议 --》会议抢单 --》 我的抢单
     * @Param uid 是抢单人的UID
     */
    List<MeetingPub> selectMyGrabList(String uid);
}