package com.geekshow.mybatis;

import com.geekshow.mybatis.mapper.IUserMapper;
import com.geekshow.mybatis.session.SqlSession;
import com.geekshow.mybatis.session.SqlSessionFactory;

public class TestMybatis {
	
	public static void main(String[] args) {
		
		SqlSessionFactory factory = new SqlSessionFactory();
		
		SqlSession sqlSession = factory.openSession();
		System.out.println(sqlSession);
		
//		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		
	}

}
