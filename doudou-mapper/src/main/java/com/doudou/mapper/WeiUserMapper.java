package com.doudou.mapper;

import com.doudou.po.WeiUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface WeiUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeiUser record);

    int insertSelective(WeiUser record);

    WeiUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeiUser record);

    int updateByPrimaryKey(WeiUser record);
    /**
     * 根据openid判断weiuser表中是否存在该对象
     */
    @Select("select * from weiuser where openid=#{openid}")
    WeiUser selectByOpenid(String openid);
}