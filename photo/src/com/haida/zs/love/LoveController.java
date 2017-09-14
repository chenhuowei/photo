package com.haida.zs.love;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haida.zs.dao.LoveMapper;
import com.haida.zs.pojo.Love;

@Controller
public class LoveController {

	@Resource
	private LoveMapper loveMapper;
//	保存或更新点赞方法
	@RequestMapping("love/save.html")
	@ResponseBody
	public Object updateLoveStatus(Love lo){
		if (null != lo.getId() && lo.getId() != 0){
			
			loveMapper.updateByPrimaryKeySelective(lo);
		}else {
			
			loveMapper.insertSelective(lo);
		}
		return true;
	}
	
}
