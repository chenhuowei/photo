package com.haida.zs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haida.zs.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> findByAccountAndPassword(@Param("account")String account,@Param("password")String password);
}