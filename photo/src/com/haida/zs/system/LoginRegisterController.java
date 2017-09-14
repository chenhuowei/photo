package com.haida.zs.system;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haida.zs.dao.CourseMapper;
import com.haida.zs.dao.PhotoMapper;
import com.haida.zs.pojo.User;
import com.haida.zs.utils.DateUtil;

@Controller
public class LoginRegisterController {

	@Resource
	private LoginRegisterServiceI loginRegisterServiceI;
	@Resource
	private PhotoMapper photoMapper;
	@Resource
	private CourseMapper courseMapper;
	
	
	@RequestMapping("index.html")
//	跳转到主页的方法
	public ModelAndView indexPage(){
		
		ModelAndView mv = new ModelAndView("home/index");
		
		mv.addObject("photos", photoMapper.findByLast());
		mv.addObject("courses", courseMapper.findByLast());
		
		return mv;
	}
	
	@RequestMapping("login.html")//跳转登录页面
	public String loginPage(){
		
		return "system/login";
	}
	
	@RequestMapping("logout.html")//跳转登录页面
	public ModelAndView logout(HttpSession session){
		ModelAndView mv = new ModelAndView("system/login");
		if (null != session && null != session.getAttribute("user")){
//			用户退出，注销用户信息
			session.invalidate();
			
		}
		
		return mv;
	}
	
	@RequestMapping(value="user/login.html",method=RequestMethod.POST)//提交登录表单
	public ModelAndView login(String account,String password,
			HttpSession session,RedirectAttributes redirectAttributes){
		ModelAndView mv = new ModelAndView();
		//从数据库中查找账号密码是否匹配，若匹配则返回用户对象
		User user = loginRegisterServiceI.findByAccountAndPassword(account, password);
		if(null == user){//账号或密码不正确，跳转登录页面，让用户重新登录
			mv.setViewName("redirect:/login.html");
			redirectAttributes.addFlashAttribute("message", "用户账号或密码不正确，请重新输入");
		}else{//用户账号密码正确，进入首页
			session.setAttribute("user", user);
			user.setLastLoginTime(DateUtil.dateTimeFormat(new Date(), null));
			mv.setViewName("redirect:/index.html");
			try{
				loginRegisterServiceI.userUpdate(user);//更新数据库当前用户的信息
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return mv;
	}
	//用来处理请求地址映射的注解      method 请求的类型
	@RequestMapping(value="user/register.html",method=RequestMethod.POST)
	//post模式下使用responsebody绑定请求对象 Spring会帮你进行协议转换，将Json、Xml协议转换成你需要的对象。
	@ResponseBody
	public boolean register(User user){//用户注册
		try {
			if (null != user){
				user.setCreateTime(DateUtil.dateTimeFormat(new Date(), null));//记录注册时间
				user.setStatus(true);
				loginRegisterServiceI.userSave(user);//用户注册，通过调用用户服务层保存到数据库
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	@RequestMapping("register.html")
	public String registerPage(){
		
		return "system/register";
	}
}
