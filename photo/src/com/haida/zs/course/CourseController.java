package com.haida.zs.course;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.haida.zs.dao.CommentMapper;
import com.haida.zs.dao.CourseMapper;
import com.haida.zs.dao.LoveMapper;
import com.haida.zs.pojo.Course;
import com.haida.zs.pojo.User;
import com.haida.zs.utils.DateUtil;

@Controller
public class CourseController {

	@Resource
	private CourseMapper courseMapper;
	@Resource
	private CommentMapper commentMapper;
	@Resource
	private LoveMapper loveMapper;
	
	@RequestMapping("course/list.html")
//	获取教程的列表
	public ModelAndView cousePage(@RequestParam(required=false)String theme){
//		此方法为跳转到要到达的页面
		ModelAndView mv = new ModelAndView("home/course_page");
		String key = "全部";
		 try {
			 if (null != theme){
//				 字符编码转换为UTF-8
				
				 key = theme;
					System.out.println(key);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!"".equals(key)&&!"全部".equals(key)){
//			此方法是转发对象到要到达页面（其它的也是如次作用）
			mv.addObject("courses", courseMapper.findByTheme(key));
			mv.addObject("theme", key);
		}else{
			
			mv.addObject("theme", "全部");
			mv.addObject("courses", courseMapper.findByPage());
		}
		return mv;
	}
	
	@RequestMapping("course/single.html")
	public ModelAndView couseSinglePage(HttpSession session,Integer courseId,final RedirectAttributes redirectAttributes){
//	从session中获取当前登录用户的信息
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView("home/course_single_page");
		mv.addObject("course", courseMapper.selectByPrimaryKey(courseId));
		mv.addObject("comments", commentMapper.findByCourseId(courseId));
		if (null != user){
			mv.addObject("love", loveMapper.findByUserCourse(user.getId(), courseId));
		}
		return mv;
	}
	
	@RequestMapping("course/write.html")
	public ModelAndView couseWritePage(@RequestParam(required=false)Integer courseId){
		
		ModelAndView mv = new ModelAndView("home/course_write");
		if (null != courseId && courseId != 0){
			mv.addObject("course", courseMapper.selectByPrimaryKey(courseId));
		}
		
		return mv;
	}
	
	private static HashMap<String, String> extMap = new HashMap<String, String>();
	static{
		//定义允许上传的文件扩展名
				extMap.put("image", "gif,jpg,jpeg,png,bmp");
	}

//教程中的图片上传方法	
	@RequestMapping(value="course/upload.html",method=RequestMethod.POST)
	public void upload(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		//文件保存目录路径
		String savePath = request.getSession().getServletContext().getRealPath("/") + "course/";

		//文件保存目录URL
		String saveUrl  = request.getContextPath() + "/course/";

		

		//最大文件大小
		long maxSize = 1000000;

		response.setContentType("text/html; charset=UTF-8");
		try {
			out = response.getWriter();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		if(!ServletFileUpload.isMultipartContent(request)){
			out.println(getError("请选择文件。"));
			return;
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			uploadDir.mkdir();
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			out.println(getError("上传目录没有写权限。"));
			return;
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			out.println(getError("目录名不正确。"));
			return;
		}
		//创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		String ymd = DateUtil.dateTimeFormat(new Date(), "yyyyMMddHHmmss");
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		MultipartHttpServletRequest mrequest= (MultipartHttpServletRequest)request;  
        Map map=mrequest.getFileMap();  
        Collection<MultipartFile> c = map.values();  
		try {
			//items = upload.parseRequest(request);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Iterator itr = c.iterator();
		while (itr.hasNext()) {
			 CommonsMultipartFile file=(CommonsMultipartFile) itr.next();  
	            FileItem item=file.getFileItem();
			//FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			item.getSize();
			if (!item.isFormField()) {
				//检查文件大小
				if(item.getSize() > maxSize){
					out.println(getError("上传文件大小超过限制。"));
					return;
				}
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
					out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
					return;
				}

				String newFileName = DateUtil.dateTimeFormat(new Date(), "yyyyMMddHHmmss") + "_" + new Random().nextInt(1000) + "." + fileExt;
				try{
					File uploadedFile = new File(savePath, newFileName);
					item.write(uploadedFile);
				}catch(Exception e){
					e.printStackTrace();
					out.println(getError("上传文件失败。"));
					return;
				}

				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				out.println(obj.toJSONString());
			}
		}
	}
	
	@RequestMapping(value="course/save.html",method=RequestMethod.POST)
//	保存教程的方法
	public ModelAndView save(Course course,final RedirectAttributes redirectAttributes){
		int number = 0;
		try {
			
			course.setCreateTime(DateUtil.dateTimeFormat(new Date()));
			course.setStatus(true);
			if (null != course.getId() && course.getId() != 0){
				courseMapper.updateByPrimaryKeySelective(course);
				number = course.getId();
			}else{
				courseMapper.insertSelective(course);
				number = course.getId();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModelAndView mv = new ModelAndView("redirect:/course/single.html");
		redirectAttributes.addAttribute("courseId", number);
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
//	封装错误信息的格式
	
		private String getError(String message) {
			JSONObject obj = new JSONObject();
			obj.put("error", 1);
			obj.put("message", message);
			return obj.toJSONString();
		}	
	
	
}
