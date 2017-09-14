package com.haida.zs.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.haida.zs.dao.CourseMapper;
import com.haida.zs.dao.PhotoMapper;

@Controller
public class SearchController {

	@Resource
	private CourseMapper courseMapper;
	@Resource
	private PhotoMapper photoMapper;
	
	@RequestMapping(value="search.html",method=RequestMethod.POST)
//	查询方法
	public ModelAndView search(@RequestParam(required=false)String keyword){
		
		ModelAndView mv = new ModelAndView("home/search_page");
//		%+字符+% 表示在数据中的模糊查询
		mv.addObject("photos",photoMapper.search("%"+keyword+"%"));
		mv.addObject("courses",courseMapper.search("%"+keyword+"%"));
		return mv;
		
	}
	
	
}
