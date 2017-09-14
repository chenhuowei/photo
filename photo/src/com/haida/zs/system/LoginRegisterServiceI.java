package com.haida.zs.system;

import com.haida.zs.pojo.User;

public interface LoginRegisterServiceI {

	public void userSave(User user);
	public void userUpdate(User user);
	public void deleteById(int id);
	public void findByAccount(String account,String password);
	public User findByAccountAndPassword(String account,String password);
	
}
