package com.haida.zs.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.haida.zs.dao.UserMapper;
import com.haida.zs.pojo.User;
import com.haida.zs.utils.StringUtil;

@Service
public class LoginRegisterServiceImpl implements LoginRegisterServiceI{

	@Resource
	private UserMapper userMapper;
	
	public void userSave(User user) {
		userMapper.insertSelective(user);
	}

	public void findByAccount(String account,String password) {
		
	}

	@Override
	public User findByAccountAndPassword(String account, String password) {
		if (StringUtil.isNotBlank(account) && StringUtil.isNotBlank(password)){
			List<User> users = userMapper.findByAccountAndPassword(account, password);
			if (null !=users && users.size() > 0){
				return users.get(0);
			}
		}
		
		return null;
	}

	@Override
	public void userUpdate(User user) {

		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void deleteById(int id) {

		userMapper.deleteByPrimaryKey(id);
	}

}
