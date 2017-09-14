package com.haida.zs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haida.zs.pojo.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
    
    List<Comment> findByCourseId(@Param("courseId")int courseId);
    List<Comment> findByPhotoId(@Param("photoId")int phototId);
    
}