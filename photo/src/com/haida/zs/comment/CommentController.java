package com.haida.zs.comment;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haida.zs.dao.CommentMapper;
import com.haida.zs.dao.CourseMapper;
import com.haida.zs.dao.PhotoMapper;
import com.haida.zs.pojo.Comment;
import com.haida.zs.utils.DateUtil;

@Controller
public class CommentController {

	@Resource
	private CommentMapper commentMapper;
	@Resource
	private CourseMapper courseMapper;
	@Resource
	private PhotoMapper photoMapper;
	
	@RequestMapping(value="comment/save.html",method=RequestMethod.POST)//保存评论
	public ModelAndView save(Comment comment,final RedirectAttributes redirectAttributes){
		try {
			comment.setCreateTime(DateUtil.dateTimeFormat(new Date()));
			commentMapper.insertSelective(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView();
		if (null != comment.getCourseId() && comment.getCourseId() !=0){
//			跳转回到原来的页面
			mv.setViewName("redirect:/course/single.html");
			redirectAttributes.addAttribute("courseId", comment.getCourseId());
		}
		if (null != comment.getPhotoId() && comment.getPhotoId() !=0){
			mv.setViewName("redirect:/photo/single.html");
			redirectAttributes.addAttribute("photoId", comment.getPhotoId());
		}
		return mv;
		
	}
	
	
}
