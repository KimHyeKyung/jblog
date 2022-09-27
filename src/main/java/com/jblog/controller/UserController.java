package com.jblog.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	//id체크(중복이면1, 아니면0)
	//@RequestParam: 쿼리스트링으로  넘길때, @RequestBody: POST
	@ResponseBody
	@RequestMapping(value = "/checkId", method = RequestMethod.POST)
	public int checkId(@RequestBody UserVo userVo) {
		String id = userVo.getId();
		int result = userService.checkId(id);
		return result;
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
