package com.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jblog.service.BlogAdminService;
import com.jblog.service.BlogService;
import com.jblog.service.UserService;
import com.jblog.vo.BlogVo;
import com.jblog.vo.UserVo;

//내블로그 관리
@Controller
@RequestMapping("/{id}/admin")
public class BlogAdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogAdminService blogAdminService;
	
	//내블로그 관리페이지 이동
	@RequestMapping(value = "/basic", method = RequestMethod.GET)
	public String manageBlog(@PathVariable("id") String id, Model model) {
		//주소의id에 맞는 userNo가져오기
		UserVo userVo = userService.getUserNo(id);
		int userNo = userVo.getUserNo();
		
		
		//기존 정보들 블로그 관리 화면에 표출
		BlogVo basic = new BlogVo();
		basic = blogAdminService.getData(userNo);
		model.addAttribute("basic",basic);
		return "blog/admin/blog-admin-basic";
	}
	
	
	//내블로그 관리페이지 > 기본설정변경버튼
	@RequestMapping(value = "/blogSetting", method = RequestMethod.POST)
	public String blogSetting(@ModelAttribute BlogVo blogVo, @ModelAttribute UserVo uservo) {
		//주소의id에 맞는 userNo가져오기
		UserVo userVo = userService.getUserNo(uservo.getId());
		int userNo = userVo.getUserNo();
		blogVo.setUserNo(userNo);
		//입력사항 update로
//		System.out.println("getBlogTitle:  "+blogVo.getBlogTitle());
//		System.out.println("userNo:  "+blogVo.getUserNo());
//		System.out.println("userID:  "+uservo.getId());
		int bvo = blogAdminService.blogSetting(blogVo);
//		System.out.println("bvo가 1이면성공:  "+bvo);
		return "redirect:/"+ uservo.getId() +"/admin/basic";
	}
	
	
	
	
	
	
	
	
	
	
	
}
