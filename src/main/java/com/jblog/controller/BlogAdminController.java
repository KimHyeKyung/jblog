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
import com.jblog.service.UserService;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
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
		model.addAttribute("userNo",userNo);
		//기존 정보들 블로그 관리 화면에 표출
		BlogVo basic = new BlogVo();
		basic = blogAdminService.getData(userNo);
		model.addAttribute("basic",basic);
		return "blog/admin/blog-admin-basic";
	}
	
	//내블로그 관리페이지 > 기본설정변경버튼
	@RequestMapping(value = "/blogSetting", method = RequestMethod.POST)
	public String blogSetting(@ModelAttribute BlogVo blogVo, @ModelAttribute UserVo uservo, Model model) {
		//주소의id에 맞는 userNo가져오기
		UserVo userVo = userService.getUserNo(uservo.getId());
		int userNo = userVo.getUserNo();
		blogVo.setUserNo(userNo);
		
		//기존 정보들 블로그 관리 화면에 표출
		BlogVo basic = new BlogVo();
		basic = blogAdminService.getData(userNo);
		model.addAttribute("basic",basic);
		
		//입력사항 update로
		blogAdminService.blogSetting(blogVo);
		return "redirect:/"+ uservo.getId() +"/admin/basic";
	}
	
	//내블로그 관리 > 카테고리이동
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category(@PathVariable("id") String id, Model model) {
		//주소의id에 맞는 userNo가져오기
		UserVo uservo = userService.getUserNo(id);
		int userNo = uservo.getUserNo();
		//System.out.println("userNo는?!?: "+userNo);
		model.addAttribute("userNo",userNo);
		
		//기존 정보들 블로그 관리 화면에 표출
		BlogVo basic = new BlogVo();
		basic = blogAdminService.getData(userNo);
		model.addAttribute("basic",basic);
				
		//카테고리 데이터 가져오기(카테고리번호, 카테고리명, 포스트 수, 설명)
		List<CategoryVo> cateVo = blogAdminService.getCateData(userNo);
		model.addAttribute("cateVo",cateVo);
		
		return "blog/admin/blog-admin-cate";
	}
	
	
	//카테고리 리스트 추가
	@ResponseBody
	@RequestMapping(value = "/addCate", method = RequestMethod.POST)
	public CategoryVo addCate(@RequestBody CategoryVo catevo) {
		//insert -> userNo, cateName, description
		System.out.println("catevo.getUserNo(): "+catevo.getUserNo());
		System.out.println("catevo.getCateName(): "+catevo.getCateName());
		System.out.println("catevo.getDescription(): "+catevo.getDescription());
		blogAdminService.addCate(catevo);
		
		return catevo;
	}
	
	//카테고리 리스트 삭제 전 post개수확인
	@ResponseBody
	@RequestMapping(value = "/deleteCate", method = RequestMethod.POST)
	public CategoryVo deleteCate(@RequestBody CategoryVo catevo, Model model){
		//선택한 카테고리의 포스트 수를 가져오기 parameter = userNo, cateNo
		//System.out.println(catevo.toString());
		CategoryVo cv = blogAdminService.getCountPost(catevo);
		//int countPost = cv.getCountPost();
		//System.out.println("countPost: "+countPost);
		
		return cv;
	}
	
	//카테고리 리스트 삭제
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public CategoryVo delete(@RequestBody CategoryVo catevo, Model model){
		int cateNo = catevo.getCateNo();
		blogAdminService.delete(cateNo);
		return catevo;
	}

	//내블로그 관리 > 글작성
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@PathVariable("id") String id, Model model) {
		//주소의id에 맞는 userNo가져오기
		UserVo userVo = userService.getUserNo(id);
		int userNo = userVo.getUserNo();
		
		//기존 정보들 블로그 관리 화면에 표출
		BlogVo basic = new BlogVo();
		basic = blogAdminService.getData(userNo);
		model.addAttribute("basic",basic);
		
		return "blog/admin/blog-admin-write";
	}

	
	
	
	
	
}
