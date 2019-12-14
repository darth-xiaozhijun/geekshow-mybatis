package com.geekshow.mybatis.binding;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

import com.geekshow.mybatis.session.SqlSession;

public class MappedProxy implements InvocationHandler {
	
	private SqlSession sqlSession;
	
	public MappedProxy(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Class<?> returnType = method.getReturnType();
		//返回参数是不是Collection类及其子类
		if (Collection.class.isAssignableFrom(returnType)) {
			return sqlSession.selectList(method.getDeclaringClass().getName() + "." + method.getName(), 
					args == null ? null : args[0]);
		}
		return sqlSession.selectOne(method.getDeclaringClass().getName() + "." + method.getName(), 
				args == null ? null : args[0]);
	}

}
