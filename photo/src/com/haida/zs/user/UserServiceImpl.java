package com.haida.zs.user;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haida.zs.dao.UserMapper;
import com.haida.zs.pojo.Course;
import com.haida.zs.pojo.Photo;
import com.haida.zs.pojo.User;

@Service
public class UserServiceImpl implements UserServiceI {

	@Resource
	private UserMapper userMapper;
	
	@Override
	@Transactional
	public void saveBySelect(User record) {

		userMapper.insertSelective(record);
	}

	@Override
	@Transactional
	public void updateBySelect(User record) {

		userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional
	public void deleteById(int id) {

		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public User queryUserById(int id) {
		
		return userMapper.selectByPrimaryKey(id);
	}

	

}
