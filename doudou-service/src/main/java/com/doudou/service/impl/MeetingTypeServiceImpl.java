package com.doudou.service.impl;


import com.doudou.mapper.MeetingtypeMapper;
import com.doudou.po.Meetingtype;
import com.doudou.service.MeetingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author caiminjie
 * @date 2019/11/28
 **/
@Service
public class MeetingTypeServiceImpl implements MeetingTypeService {
    @Autowired
    private MeetingtypeMapper meetingtypeMapper;

    @Override
    public List<Meetingtype> selectByStatusAndSortNumAsc() {
        return meetingtypeMapper.selectByStatusAndSortNumAsc();
    }
}
