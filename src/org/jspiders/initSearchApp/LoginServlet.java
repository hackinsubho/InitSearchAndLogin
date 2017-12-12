package org.jspiders.initSearchApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LoginServlet extends GenericServlet{
	Connection con;
	PreparedStatement ps;
	PrintWriter pw;
	String user;
	String password;
	String driver;
	String url;
	@Override
	public void init(ServletConfig config) throws ServletException {
	 	
		super.init(config);
		System.out.println("i am init");
		user=config.getInitParameter("un");
		 password = config.getInitParameter("pass");
		 driver= config.getInitParameter("driver");
		url = config.getInitParameter("url");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			String qry ="select * from jdbc.logininfo where uname=? and password=?";
			ps= con.prepareStatement(qry);
			
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		 
	}
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		pw= res.getWriter();
	    String userId= req.getParameter("id");
		String psd=req.getParameter("psd");
	
		try {
			ps.setString(1, userId);
			ps.setString(2, psd);
			ResultSet rs = 	ps.executeQuery();
			if(rs.next()) {
				pw.println("Welcome "+ rs.getString(3));
			}
			else {
				pw.println("Error");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
