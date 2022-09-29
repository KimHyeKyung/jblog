package com.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.repository.BlogAdminDao;
import com.jblog.vo.BlogVo;

@Service
public class BlogAdminService {

	@Autowired
	private BlogAdminDao blogAdminDao;

	//기존 정보들 블로그 관리 화면에 표출
	public BlogVo getData(int userNo) {
		return blogAdminDao.getData(userNo);
	}

	//내블로그 관리페이지 > 기본설정변경버튼
	public int blogSetting(BlogVo blogVo) {
		return  blogAdminDao.blogSetting(blogVo);
	}
	
	
	
}
