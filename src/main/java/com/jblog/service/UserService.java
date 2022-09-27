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

}
