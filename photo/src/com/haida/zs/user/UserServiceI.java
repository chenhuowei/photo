package com.haida.zs.user;

import java.util.List;

import com.haida.zs.pojo.Course;
import com.haida.zs.pojo.Photo;
import com.haida.zs.pojo.User;

public interface UserServiceI {

	public void saveBySelect(User record);
	public void updateBySelect(User record);
	public void deleteById(int id);
	public User queryUserById(int id);
}
