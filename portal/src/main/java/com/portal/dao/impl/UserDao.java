package com.portal.dao.impl;


import java.sql.Connection;
import java.util.Iterator;


import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;
import com.portal.dao.AbstractUserDao;
import com.portal.model.User;

public class UserDao implements AbstractUserDao 
{	
	private UserDao()
	{}
	
	private static UserDao dao=new UserDao();
	Connection con=null;
	public boolean addUser(User user) throws Exception
	{
		Row r=new Row("Users");
		r.set("USERNAME", user.getUsername());
		r.set("PASSWORD", user.getPassword());
		r.set("ROLE", user.getRole());
		
		DataObject dataObject=new WritableDataObject();
		dataObject.addRow(r);
		if(DataAccess.add(dataObject)!=null)
			return true;
		else
			return false;
	}
	
	public User getUser(String username) throws Exception 
	{
		
		User u=new User();
		Table table=new Table("Users");
		SelectQuery squery=new SelectQueryImpl(table);
		
		squery.addSelectColumn(new Column("Users","*"));
		Criteria c=new Criteria(new Column("Users","USERNAME"), username, QueryConstants.EQUAL);
		squery.setCriteria(c);
		DataObject dataObject = null;
		try 
		{
			dataObject = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		Iterator result = null;
		try 
		{
			result = dataObject.getRows("Users");
			if(result.hasNext())
			{
			Row r=(Row)result.next();
			u.setUsername((String)r.get("USERNAME"));
			u.setPassword((String)r.get("PASSWORD"));
			u.setRole((String)r.get("ROLE"));
			}
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return u;
	}
	
	public static UserDao getConnection(Connection connection)
	{
		dao.con=connection;
		return dao;
	}
}
