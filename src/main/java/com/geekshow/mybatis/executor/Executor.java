package com.geekshow.mybatis.executor;

import java.util.List;

import com.geekshow.mybatis.config.MappedStatement;

/**
 * Mybatis核心接口之一，
 * 定义了数据库操作最基本的方法
 * SqlSession的功能是基于它实现的
 * @author Administrator
 *
 */
public interface Executor {

	/**
	 * 查询接口
	 * @param mappedStatement 封装SQL语句mappedStatement对象
	 * @param parameter	传入的SQL参数
	 * @return	将数据转换成指定的对象结果集返回
	 */
	<E> List<E> query(MappedStatement mappedStatement, Object parameter);
}
