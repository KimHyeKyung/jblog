package com.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.BlogVo;

@Repository
public class BlogAdminDao {

	@Autowired
	private SqlSession session;

	//기존 정보들 블로그 관리 화면에 표출
	public BlogVo getData(int userNo) {
		return session.selectOne("blogAdmin.getData", userNo);
	}

	//내블로그 관리페이지 > 기본설정변경버튼
	public int blogSetting(BlogVo blogVo) {
		return session.update("blogAdmin.blogSetting", blogVo);
	}

}
