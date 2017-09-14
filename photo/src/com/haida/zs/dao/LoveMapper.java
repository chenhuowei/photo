package com.haida.zs.dao;

import org.apache.ibatis.annotations.Param;

import com.haida.zs.pojo.Love;

public interface LoveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Love record);

    int insertSelective(Love record);

    Love selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Love record);

    int updateByPrimaryKey(Love record);
    
    Love findByUserCourse(@Param("userId")int userId,@Param("courseId") int courseId);
    Love findByUserPhoto(@Param("userId")int userId,@Param("photoId") int photoId);
}