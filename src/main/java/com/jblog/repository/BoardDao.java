package com.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.CategoryVo;
import com.jblog.vo.PostVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession session;

	//id에 맞는 카테고리 가져오기
	public List<CategoryVo> getCategory(int userNo) {
		return session.selectList("board.getCategory", userNo);
	}

	public List<PostVo> getPost(int userNo) {
		return session.selectList("board.getPost", userNo);
	}

}
