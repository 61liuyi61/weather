package org.weather.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.weather.bean.User;

@Repository
public interface UserDao {
	
	//通过用户名查
	@Select("select * from user where name = #{name}")
	User getUserInfo(String name);

	//修改用户信息
	@Insert("insert into user(name, password) values (#{name}, #{password})")
	void updateUserInfo(String name, String password);

}
