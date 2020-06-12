package com.xr.run.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author: hxy
 * @description: 登录相关dao
 * @date: 2017/10/24 11:02
 */
@Repository
public interface LoginDao {
	/**
	 * 根据用户名和密码查询对应的用户
	 */
	@Select("SELECT u.sid userId,u.username username, u.password password, u.name nickName FROM " +
			" sys_staff u WHERE u.username = #{username} AND u.password = #{password} AND u.staus = '1'")
	JSONObject getUser(@Param("username") String username, @Param("password") String password);
}
