package com.portal.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection 
{
	public Connection getConnection()
	{
		Connection con=null;
    	FileReader file = null;
		try {
			file = new FileReader("D:\\webapps\\portal\\src\\main\\resources\\input.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties properties=new Properties();
		try {
			properties.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String url=properties.getProperty("data.url")+properties.getProperty("data.dbport")+"/"+properties.getProperty("data.dbname");
		
		String user=properties.getProperty("data.username");
		String password=properties.getProperty("data.password");
		
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con=DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
