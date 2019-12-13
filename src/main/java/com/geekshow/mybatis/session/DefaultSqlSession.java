package com.geekshow.mybatis.session;

import java.util.List;

import com.geekshow.mybatis.config.Configuration;

public class DefaultSqlSession implements SqlSession {
	
	private Configuration configuration;
	
	public DefaultSqlSession(Configuration configuration) {
		this.configuration = configuration;
	}

	public <T> T selectOne(String statement, Object parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	public <E> List<E> selectList(String statement, Object parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getMapper(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
