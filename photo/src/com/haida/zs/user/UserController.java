
package com.haida.zs.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.haida.zs.dao.CourseMapper;
import com.haida.zs.dao.PhotoMapper;
import com.haida.zs.pojo.User;

@Controller
public class UserController {

	@Resource
	private UserServiceI userServiceI;
	@Resource
	private CourseMapper courseMapper;
	@Resource
	private PhotoMapper photoMapper;
	
//	跳转到个人空间的方法
	@RequestMapping("user/zone.html")
	public ModelAndView userPage(HttpSession session){
//		获取当前登录用户信息
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView("user/zone_page");
		if (null != user){
			mv.addObject("courses",courseMapper.findByUser(user.getId()) );
			mv.addObject("photos",photoMapper.findByUser(user.getId()) );
			
		}
		return mv;
	}
//	更新用户信息
	@RequestMapping(value="user/update.html")
	@ResponseBody
	public Object updateUser(User user,HttpSession session){
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			userServiceI.updateBySelect(user);
//			更新成功后，重新记录用户信息
			session.setAttribute("user", userServiceI.queryUserById(user.getId()));
			json.put("success", true);
		} catch (Exception e) {
			json.put("success", false);

		}
		return json;
	}
	
	
}
