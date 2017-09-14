package com.haida.zs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haida.zs.pojo.Course;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
    
    List<Course> findByPage();
    List<Course> findByLast();
    
    List<Course> findByUser(@Param("userId")int userId);
    List<Course> findByTheme(@Param("theme")String theme);
    List<Course> search(@Param("keyword")String keyword);
}