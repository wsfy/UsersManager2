package com.hsp.service;

import com.hsp.domain.User;
import com.hsp.util.SqlHelper;

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
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		// 根据rs来判断该用户是否存在
		try {
			if (rs.next()) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {// 关闭资源
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());// 典型：反向引用
		}
		return b;
	}
}
