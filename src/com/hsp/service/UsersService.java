package com.hsp.service;

import com.hsp.domain.User;
import java.sql.*;
public class UsersService {

	
	
	// 添加用户
	// 修改用户
	// 删除用户
	
	// 写一个验证用户是否合法的函数
	public boolean checkUser(User user) {
		
		boolean b = false;
		
		// 使用SqlHelper来完成数据查询任务
		String sql = "select * from users where id=? and passwd=?";
		String parameters[] = {user.getId() + "", user.getPwd()};// 该参数是一个字符数组
		ResultSet rs = SqlHelper.executeQuery("select ",parameters);
	}
}
