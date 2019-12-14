package com.geekshow.mybatis.mapper;

import java.util.List;

import com.geekshow.mybatis.entity.User;

public interface IUserMapper {
	
	User selectByPrimaryKey(Integer id);
	
	List<User> selectAll();

}
