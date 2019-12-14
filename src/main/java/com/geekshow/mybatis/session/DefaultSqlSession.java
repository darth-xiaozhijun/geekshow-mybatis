package com.geekshow.mybatis.session;

import java.lang.reflect.Proxy;
import java.util.List;

import com.geekshow.mybatis.binding.MappedProxy;
import com.geekshow.mybatis.config.Configuration;
import com.geekshow.mybatis.config.MappedStatement;
import com.geekshow.mybatis.executor.DefaultExecutor;
import com.geekshow.mybatis.executor.Executor;

/**
 * 对外提供服务，把请求转发给executor
 * 给mapper接口生产实现类
 * @author Administrator
 *
 */
public class DefaultSqlSession implements SqlSession {
	
	private Configuration configuration;
	
	private Executor executor;
	
	public DefaultSqlSession(Configuration configuration) {
		super();
		this.configuration = configuration;
		executor = new DefaultExecutor(configuration);
	}

	public <T> T selectOne(String statement, Object parameter) {
		
		List<T> selectList = this.selectList(statement, parameter);
		if (selectList == null || selectList.size() == 0) {
			return null;
		}
		if (selectList.size() == 1) {
			return selectList.get(0);
		}
		throw new RuntimeException("Too many result!");
	}

	public <E> List<E> selectList(String statement, Object parameter) {

		MappedStatement mappedStatement = configuration.getMappedStatements().get(statement);
		return executor.query(mappedStatement, parameter);
	}

	public <T> T getMapper(Class<T> type) {
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new MappedProxy(this));
	}

}
