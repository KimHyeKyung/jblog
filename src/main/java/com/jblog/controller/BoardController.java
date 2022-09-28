package com.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jblog.service.BoardService;
import com.jblog.service.UserService;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.PostVo;
import com.jblog.vo.UserVo;

@Controller
public class BoardController {

	@Autowired
	private UserService userService;
	@Autowired
	private BoardService boardService;
	
	//내블로그 페이지
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String blogMain(@PathVariable("id") String id, Model model) {
		int userNo;
		//1.db에서 주소창에 쓴 id가 있는지 확인
		int count = userService.checkId(id);
		//2.있으면 그 id에 맞는 userNo, userName값 가져오기
		if(count == 1) {
			UserVo userVo = userService.getUserNo(id);
			model.addAttribute("userVo", userVo);
			userNo = userVo.getUserNo();
			//3.userNo에 맞는 category,post 가져오기
			//category
			List<CategoryVo> categoryVo = boardService.getCategory(userNo);
			model.addAttribute("categoryVo", categoryVo);
			//post
			List<PostVo> postVo = boardService.getPost(userNo);
			model.addAttribute("postVo", postVo);
			
			return "blog/blog-main";
			
		}else {
			return "blog/blog-error";
		}
		
	}
	
	

}
