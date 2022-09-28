package com.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.repository.BoardDao;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.PostVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	//userNo에 맞는 카테고리 가져오기
	public List<CategoryVo> getCategory(int userNo) {
		return boardDao.getCategory(userNo);
	}

	//userNo에 맞는 게시글 가져오기
	public List<PostVo> getPost(int userNo) {
		return boardDao.getPost(userNo);
	}


}
