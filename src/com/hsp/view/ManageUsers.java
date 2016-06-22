package com.hsp.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageUsers extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script type='text/javascript' language='javascript'>");
		out.println("function gotoPageNow() { " +
				"var pageNow=document.getElementById('pageNow'); " +
				"window.open('/UsersManager2/ManageUsers?pageNow=' + pageNow.value,'_self');}");
		out.println("</script>"); 
		out.println("<img src='imgs/1.gif'/>欢迎**登录 <a href='/UsersManager2/LoginServlet'>返回主界面</a> <a href='/UsersManager2/LoginServlet'>安全退出</a><hr/>");
		out.println("<h1>管理用户</h1>");
		// 从数据库中取出用户数据，并显示
		// 到数据库中去验证
		Connection ct = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// 定义分页需要的变量
		
		int pageNow = 1;// 当前页
		
		// 接受用户的pageNow
		String sPageNow = request.getParameter("pageNow");
		
		if (sPageNow != null) {
			pageNow = Integer.parseInt(sPageNow);
		}
		
		
		int pageSize = 3;// 指定每页显示3条记录
		int pageCount = 1;// 总共有多少页。该值是计算出来的。
		int rowCount = 1;// 表示共有多少记录，该值是查询数据库得到
		
		
		try {
			
			// 1、加载驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2、得到连接
			ct = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott", "tiger");
			
			// 算出共有多少页
			// 1、查询rowCount
			ps = ct.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			rs.next();// 把游标下移
			rowCount = rs.getInt(1);
			
			pageCount = rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize + 1;
			
			// 3、创建PreparedStatement对象（创建预处理语句对象）
			// 请大家思考，如果给出的条件 pageNow=3 pageSize=2
			ps = ct.prepareStatement("select * from (select t.*, rownum rn from " +
					"(select * from users order by id) t where rownum<=" + pageSize * pageNow + ") where rn>=" + (pageSize * (pageNow - 1) + 1));
			String sql = "select * from (select t.*, rownum rn from " +
					"(select * from users order by id) t where rownum<=" + pageSize * pageNow + ") where rn>=" + (pageSize * (pageNow - 1) + 1);
			System.out.println("sql=" + sql);
			// 4、执行操作
			rs = ps.executeQuery();
			out.println("<table border=1px bordercolor=green cellspacing=0 width=500px>");
			out.println("<tr><th>id</th><th>用户名</th><th>email</th><th>级别</th></tr>");
			// 循环显示所有用户信息
			while (rs.next()) {
				
				out.println("<tr><td>" + rs.getInt(1) + 
						"</td><td>" + rs.getString(2) + 
						"</td><td>" + rs.getString(3) + 
						"</td><td>" + rs.getInt(4) + 
						"</td></tr>");
			}
			out.println("</table><br/>");
			
			// 显示上一页
			if (pageNow != 1) {
				out.println("<a href='/UsersManager2/ManageUsers?pageNow="+ (pageNow - 1) +"'>上一页</a>");
			}
			
			
			// 显示分页
			for (int i = 1; i <= pageCount; i++) {
				out.println("<a href='/UsersManager2/ManageUsers?pageNow=" + i + "'><" + i + "></a>");
			}
			
			// 显示下一页
			if (pageNow != pageCount) {
				out.println("<a href='/UsersManager2/ManageUsers?pageNow="+ (pageNow + 1) +"'>下一页</a>");
			}
			
			// 显示分页信息
			out.println("&nbsp;&nbsp;&nbsp;当前页" + pageNow + "/总页数" + pageCount + "<br/>");
			out.println("跳转到<input type='text' id='pageNow' name='pageNow'/> <input type='button' onClick='gotoPageNow()' value='跳转'/>");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs = null;
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ps = null;
			}	
			if (ct != null) {
				try {
					ct.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ct = null;
			}
			
		}
		out.println("<hr/><img src='imgs/mylogo.gif'/>");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
