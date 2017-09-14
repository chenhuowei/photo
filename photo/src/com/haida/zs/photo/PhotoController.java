package com.haida.zs.photo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haida.zs.dao.CommentMapper;
import com.haida.zs.dao.LoveMapper;
import com.haida.zs.dao.PhotoMapper;
import com.haida.zs.pojo.Photo;
import com.haida.zs.pojo.User;
import com.haida.zs.utils.DateUtil;

@Controller
public class PhotoController {

	@Resource
	private PhotoMapper photoMapper;
	@Resource
	private CommentMapper commentMapper;
	@Resource
	private LoveMapper loveMapper;
	
	
	@RequestMapping("photo/list.html")//跳转到作品展示列表
	public ModelAndView photoPage(@RequestParam(required=false) String theme,@RequestParam(required=false) String type){
		ModelAndView mv = new ModelAndView("home/photo_page");
		String key = "全部";
		String word = "全部";
		try {
			if(null != theme){
				
				if (null != type){
				
				}
			
				key = theme;
				if (null != type){
					word = type;
				}
				
				System.out.println(key);
				System.out.println(word);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != key && !"".equals(key)&&!"全部".equals(key)){
			mv.addObject("theme", key);
			if (null != word && !"".equals(word)&&!"全部".equals(word)){
				mv.addObject("photos", photoMapper.findByTheme(key, word));
				System.out.println( photoMapper.findByTheme(key, word).size());
				mv.addObject("type", word);
			}else{
				mv.addObject("type", "全部");
				System.out.println( photoMapper.findByTheme(key, null).size());
				mv.addObject("photos", photoMapper.findByTheme(key, null));
			}
			
		}else{
			mv.addObject("type", "全部");
			mv.addObject("theme", "全部");
			mv.addObject("photos", photoMapper.findByPage());
		}
		return mv;
		
	}
	
	@RequestMapping("photo/single.html")//跳转到单个作品展示
	public ModelAndView singlePage(HttpSession session,int photoId,final RedirectAttributes redirectAttributes){
		User user = (User) session.getAttribute("user");
		ModelAndView mv = new ModelAndView("home/photo_single_page");
		mv.addObject("photo", photoMapper.selectByPrimaryKey(photoId));
		mv.addObject("comments", commentMapper.findByPhotoId(photoId));
			if (null != user){
				mv.addObject("love", loveMapper.findByUserPhoto(user.getId(), photoId));
		}
		return mv;
		
	}
	@RequestMapping("photo/write.html")//跳转作品展示
	public ModelAndView writePage(@RequestParam(required=false)Integer photoId){
		ModelAndView mv = new ModelAndView("home/photo_write");
		if (null != photoId && photoId != 0) {
			
			mv.addObject("photo", photoMapper.selectByPrimaryKey(photoId));
		}
		return mv;
		
	}
//	作品提交后的的处理方法
	@RequestMapping(value="photo/save.html",method=RequestMethod.POST)//
	public ModelAndView save(HttpServletRequest request,Photo photo,final RedirectAttributes redirectAttributes,
			@RequestParam(value = "image", required = false) MultipartFile image){
		int number = 0;
		photo.setCreateTime(DateUtil.dateTimeFormat(new Date()));
		
		//文件保存目录路径
		String savePath = request.getSession().getServletContext().getRealPath("/") + "photo/";
		//文件保存目录URL
		String saveUrl  = request.getContextPath() + "/photo/";
		if (!image.isEmpty()){
			 String fileName = DateUtil.dateTimeFormat(new Date(), "yyyyMMddHHmmss")+"-"+image.getOriginalFilename();  
		        File targetFile = new File(savePath,fileName);
		        if(!targetFile.exists()){  
		            targetFile.mkdirs();  
		        }  
		        //保存  
		        try {  
		            image.transferTo(targetFile); 
		            photo.setUrl(saveUrl+fileName);
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }  
		}
	       
	        
		try{
			if (null != photo.getId() && photo.getId() != 0){
				photoMapper.updateByPrimaryKeySelective(photo);
				number = photo.getId();
			}else{
				photoMapper.insertSelective(photo);
				number = photo.getId();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView("redirect:/photo/single.html");
		redirectAttributes.addAttribute("photoId", number);
		return mv;
		
	}
	
	
	
	
	
	
}
