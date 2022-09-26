package com.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jblog.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession session;
	
	public int insert(UserVo userVo) {
		int count = session.insert("user.join",userVo);
		if(count == 1) {
			return 1;
		}else {
			return 0; 
		}
	}

}
