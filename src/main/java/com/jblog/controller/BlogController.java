package com.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jblog.service.BlogAdminService;
import com.jblog.service.BlogService;
import com.jblog.service.UserService;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.PostVo;
import com.jblog.vo.UserVo;

@Controller
public class BlogController {

	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private BlogAdminService blogAdminService;
	
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
			List<CategoryVo> categoryVo = blogService.getCategory(userNo);
			model.addAttribute("categoryVo", categoryVo);
			//post
			//getPost: postNo, postTitle, postContent
			List<PostVo> postVo = blogService.getPost(userNo);
			model.addAttribute("postVo", postVo);
			model.addAttribute("lastPostVo", postVo.get(0));
			
			//4.블로그 타이틀 이름 가져오기
			//기존 정보들 블로그 관리 화면에 표출
			BlogVo basic = new BlogVo();
			basic = blogAdminService.getData(userNo);
			model.addAttribute("basic",basic);
			
			return "blog/blog-main";
		}else {
			return "blog/blog-error";
		}
	}
	
	//게시글 제목 클릭 후 postNo에 맞는 게시물 화면에 표출
	@ResponseBody
	@RequestMapping(value = "/showPost", method = RequestMethod.POST)
	public PostVo showPost(@RequestBody PostVo postVo) {
		int postNo = postVo.getPostNo();
		System.out.println("postNo : "+postNo);
		
		PostVo showPost = blogService.showPost(postNo);
		System.out.println(showPost);
		
		PostVo pv = new PostVo();
		pv.setPostTitle(showPost.getPostTitle());
		pv.setPostContent(showPost.getPostContent());
		return pv;
	}
	
	
	
	
	
	
	
	
	
	
	
}
