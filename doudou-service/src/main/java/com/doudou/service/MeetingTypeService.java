package com.doudou.service;



import com.doudou.po.Meetingtype;

import java.util.List;

/**
 * @author caiminjie
 * @date 2019/11/28
 **/
public interface MeetingTypeService {
    /**
     * 查询课题类别数据信息 有效的排序数据
     */
    List<Meetingtype> selectByStatusAndSortNumAsc();
}
