package com.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jblog.service.UserService;
import com.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	//회원가입 성공페이지
	@RequestMapping(value = "/joinSuccess", method = RequestMethod.GET)
	public String joinSuccess() {
		return "/user/joinSuccess";
	}
	
	//회원가입 실행
	@RequestMapping(value = "/doJoin", method = RequestMethod.POST)
	public String doJoin(@ModelAttribute UserVo userVo) {
		int result = userService.doJoin(userVo);
		if(result == 1) {
			return "redirect:/user/joinSuccess";
		}else {
			return "redirect:/user/doJoin";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
