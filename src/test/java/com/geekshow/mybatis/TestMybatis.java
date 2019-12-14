package com.geekshow.mybatis;

import java.util.List;

import com.geekshow.mybatis.entity.User;
import com.geekshow.mybatis.mapper.IUserMapper;
import com.geekshow.mybatis.session.SqlSession;
import com.geekshow.mybatis.session.SqlSessionFactory;

public class TestMybatis {
	
	public static void main(String[] args) {
		
		SqlSessionFactory factory = new SqlSessionFactory();
		
		SqlSession sqlSession = factory.openSession();
		System.out.println(sqlSession);
		
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		User user = userMapper.selectByPrimaryKey(1);
		System.out.println(user);
		
		System.out.println("------------------------");
		
		List<User> users = userMapper.selectAll();
		for (User u : users) {
			System.out.println(u);
		}
	}

}
