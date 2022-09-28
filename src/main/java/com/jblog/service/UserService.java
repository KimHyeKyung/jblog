package com.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jblog.repository.UserDao;
import com.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	//회원가입 실행
	public int doJoin(UserVo userVo) {
		return userDao.insert(userVo);
	}

	//이메일체크(중복이면1, 아니면0)
	public int checkId(String id) {
		System.out.println(userDao.checkId(id));
		return userDao.checkId(id);
	}

	//로그인 개수
	public int doLoginCnt(UserVo userVo) {
		return userDao.doLoginCnt(userVo);
	}

	//로그인 실행
	public UserVo doLogin(UserVo userVo) {
		return userDao.doLogin(userVo);
	}

	//회원가입시 blog테이블에도 정보 넣어야한다.
	public void createBlog(UserVo userVo) {
		userDao.createBlog(userVo);
	}

	//id에 맞는 userNo, userName값 가져오기
	public UserVo getUserNo(String id) {
		return userDao.getUserNo(id);
	}

}
