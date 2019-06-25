package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// set printwriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		
       // Get a connectoin to the database
		
		Connection myconn = null;
		Statement stat = null;
		ResultSet rs = null;
		
		try{
	
			myconn = dataSource.getConnection();
			
			String sql = "select * from student";
			stat = myconn.createStatement();
			
			rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				String myEmail = rs.getString("email");
				out.println(myEmail);
			}
		
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
	     //response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
