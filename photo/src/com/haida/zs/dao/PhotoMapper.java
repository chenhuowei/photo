package com.haida.zs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haida.zs.pojo.Photo;

public interface PhotoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Photo record);

    int insertSelective(Photo record);

    Photo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Photo record);

    int updateByPrimaryKey(Photo record);
    
    List<Photo> findByPage();
    List<Photo> findByLast();
    List<Photo> findByUser(@Param("userId") int userId);
    List<Photo> findByTheme(@Param("theme") String theme,@Param("type") String type);
    List<Photo> search(@Param("keyword") String keyword);
}