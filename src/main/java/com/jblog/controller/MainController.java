package com.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	//메인화면 출력
	@RequestMapping({"/","main"})
	public String main() {
	  System.out.println("main....");
		return "main/index";
	}
	
	//로그인폼화면 출력
	@RequestMapping({"/user/loginForm"})
	public String loginForm() {
	  System.out.println("go loginForm");
		return "user/loginForm";
	}
	
	//회원가입폼화면 출력
	@RequestMapping({"/user/joinForm"})
	public String joinForm() {
	  System.out.println("go joinForm");
		return "user/joinForm";
	}
	
	
}
