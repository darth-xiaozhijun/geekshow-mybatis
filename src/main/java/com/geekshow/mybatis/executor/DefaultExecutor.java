package com.geekshow.mybatis.executor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.geekshow.mybatis.config.Configuration;
import com.geekshow.mybatis.config.MappedStatement;
import com.geekshow.mybatis.reflection.ReflecitonUtils;

public class DefaultExecutor implements Executor {
	
	private Configuration configuration;
	
	public DefaultExecutor(Configuration configuration) {
		this.configuration = configuration;
	}

	public <E> List<E> query(MappedStatement mappedStatement, Object parameter) {
		
//		System.out.println(mappedStatement.getSql());
//		System.out.println(mappedStatement.getResultType());
		
		ArrayList<E> ret = new ArrayList<E>();//返回结果集
		try {
			Class.forName(configuration.getJdbcDriver());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection(configuration.getJdbcUrl(), 
					configuration.getJdbcUsername(), configuration.getJdbcPassword());
			
			preparedStatement = connection.prepareStatement(mappedStatement.getSql());
			
			parameterize(preparedStatement, parameter);
			
			resultSet = preparedStatement.executeQuery();
			
			handlerResultSet(resultSet, ret, mappedStatement.getResultType());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				
				resultSet.close();
				preparedStatement.close();
				connection.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	private void parameterize(PreparedStatement preparedStatement,Object parameter) throws SQLException{
		
		if (parameter instanceof Integer) {
			preparedStatement.setInt(1, (Integer) parameter);
		} else if (parameter instanceof Long) {
			preparedStatement.setLong(1, (Long) parameter);
		}else if (parameter instanceof String) {
			preparedStatement.setString(1, (String) parameter);
		}
	}
	
	private <E> void handlerResultSet(ResultSet resultSet,List<E> ret,String className){
		Class<E> clazz  = null;
		try {
			clazz = (Class<E>) Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			while (resultSet.next()) {
				
				Object entity = clazz.newInstance();
				ReflecitonUtils.setPropToBeanFromResultSet(entity, resultSet);
				ret.add((E) entity);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
