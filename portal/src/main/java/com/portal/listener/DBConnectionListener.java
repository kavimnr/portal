package com.portal.listener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class DBConnectionListener
 *
 */
public class DBConnectionListener implements ServletContextListener {

  
    public DBConnectionListener() {
        // TODO Auto-generated constructor stub
    }

	
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	
    public void contextInitialized(ServletContextEvent context)  
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
		String total=properties.getProperty("data.total.records");
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con=DriverManager.getConnection(url,user,password);
			System.out.println("Connection created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ServletContext application=context.getServletContext();
		application.setAttribute("connection", con);
		application.setAttribute("total", total);
		
    }
	
}
