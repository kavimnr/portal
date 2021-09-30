package com.portal.dao.impl;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.System.Logger;
import java.sql.Connection;
import java.sql.SQLException;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.DeleteQuery;
import com.adventnet.ds.query.DeleteQueryImpl;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.QueryConstructionException;
import com.adventnet.ds.query.Range;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.SortColumn;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;
import com.portal.dao.AbstractEmployeeDao;
import com.portal.model.Employee;

public class EmployeeDao implements AbstractEmployeeDao
{
	private EmployeeDao()
	{}
	private static EmployeeDao emp=new EmployeeDao(); 
	Connection con=null;
	public boolean save(Employee emp) throws SQLException 
	{
		List<Employee> list=getEmployees();
		Iterator<Employee> i=list.iterator();
		while(i.hasNext())
		{
			if(i.next().getEmail().equals(emp.getEmail()))
				throw new SQLException();
		}
		
		Row r=new Row("Employee");
		r.set("NAME", emp.getName());
		r.set("DEPARTMENT", emp.getDepartment());
		r.set("REPORTING_TO", emp.getReporting_to());
		r.set("EMAIL", emp.getEmail());
		r.set("MOBILE", emp.getMobile());
		r.set("LOCATION", emp.getLocation());
		
		
		
		
		DataObject dataObject=new WritableDataObject();
		try {
			dataObject.addRow(r);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		try {
			if(DataAccess.add(dataObject)!=null)
				return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Employee> viewEmployees(int page_no,int total) throws SQLException 
	{
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		Range range=new Range((page_no-1)*total, page_no*total);
		squery.addSortColumn(new SortColumn(new Column("Employee","EMP_ID"), true));
		squery.setRange(range);
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
			result = dataObject.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		List list=new ArrayList<Employee>();
		while(result.hasNext())
		{
			Employee emp=new Employee();
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			list.add(emp);
		}
		return list;
	}
	
	public int updateEmployee(Employee emp) throws SQLException 
	{
		UpdateQuery query=new UpdateQueryImpl("Employee");
		Criteria c=new Criteria(new Column("Employee","EMP_ID"), emp.getEmp_id(), QueryConstants.EQUAL);
		query.setUpdateColumn("NAME",emp.getName());
		query.setUpdateColumn("DEPARTMENT",emp.getDepartment());
		query.setUpdateColumn("REPORTING_TO",emp.getReporting_to());
		query.setUpdateColumn("EMAIL",emp.getEmail());
		query.setUpdateColumn("MOBILE",emp.getMobile());
		query.setUpdateColumn("LOCATION",emp.getLocation());
		query.setCriteria(c);
		try {
			return DataAccess.update(query);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return 0;
	}

	public int deleteEmployee(int emp_id) throws SQLException 
	{
		DeleteQuery query=new DeleteQueryImpl("Employee");
		Criteria c=new Criteria(new Column("Employee","EMP_ID"),emp_id,QueryConstants.EQUAL);
		query.setCriteria(c);
		try {
			return DataAccess.delete(query);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		return 0;
	}
	public static EmployeeDao getInstance(Connection con)
	{
		emp.con=con;
		return emp;
	}

	public Employee getEmployeeById(int id) throws SQLException 
	{
		Employee emp=new Employee();
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		Criteria c=new Criteria(new Column("Employee","EMP_ID"), id,QueryConstants.EQUAL);
		squery.setCriteria(c);
		DataObject data = null;
		try {
			data = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		try {
			Iterator result=data.getRows("Employee");
			
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));	
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		
		return emp;
	}

	public List<Employee> getEmployeesByName(String name,int total) throws SQLException 
	{
		List<Employee> list=new ArrayList<Employee>();
		
		Iterator result=null;
		
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		Criteria c=new Criteria(new Column("Employee","NAME"), "*"+name+"*",QueryConstants.LIKE,false);
		squery.setCriteria(c);
		DataObject data = null;
		try {
			data = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		
		try {
			result=data.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		
		while(result.hasNext())
		{
			Employee emp=new Employee();
			
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			 
			list.add(emp);
		}
		return list;
	}

	public List<Employee> getEmployeesByDeparment(String department,int total) throws SQLException 
	{
		List<Employee> list=new ArrayList<Employee>();
		
		Iterator result=null;
		
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		Criteria c=new Criteria(new Column("Employee","DEPARTMENT"), "*"+department+"*",QueryConstants.LIKE,false);
		squery.setCriteria(c);
		DataObject data = null;
		try {
			data = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		
		try {
			result=data.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		
		while(result.hasNext())
		{
			Employee emp=new Employee();
			
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			 
			list.add(emp);
		}
		return list;
	}

	public List<Employee> getEmployeesByReportingManager(String manager,int total) throws SQLException 
	{
		List<Employee> list=new ArrayList<Employee>();
		
		Iterator result=null;
		
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		Criteria c=new Criteria(new Column("Employee","REPORTING_TO"), "*"+manager+"*",QueryConstants.LIKE,false);
		squery.setCriteria(c);
		DataObject data = null;
		try {
			data = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		try {
			result=data.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		while(result.hasNext())
		{
			Employee emp=new Employee();
			
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			 
			list.add(emp);
		}
		return list;
	}

	public List<Employee> getEmployeesByEmail(String email,int total) throws SQLException 
	{
		List<Employee> list=new ArrayList<Employee>();
		
		Iterator result=null;
		
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		Criteria c=new Criteria(new Column("Employee","EMAIL"), "*"+email+"*",QueryConstants.LIKE,false);
		squery.setCriteria(c);
		DataObject data = null;
		try {
			data = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		try {
			result=data.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		while(result.hasNext())
		{
			Employee emp=new Employee();
			
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			 
			list.add(emp);
		}
		return list;
	}

	public List<Employee> getEmployeesBymobile(String mobile,int total) throws SQLException 
	{
		List<Employee> list=new ArrayList<Employee>();
		
		Iterator result=null;
		
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		Criteria c=new Criteria(new Column("Employee","MOBILE"), "*"+mobile+"*",QueryConstants.LIKE,false);
		squery.setCriteria(c);
		DataObject data = null;
		try {
			data = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		try {
			result=data.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		while(result.hasNext())
		{
			Employee emp=new Employee();
			
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			 
			list.add(emp);
		}
		return list;
	}

	public List<Employee> getEmployeesByLocation(String location,int total) throws SQLException 
	{
		List<Employee> list=new ArrayList<Employee>();
		
		Iterator result=null;
		
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		Criteria c=new Criteria(new Column("Employee","LOCATION"), "*"+location+"*",QueryConstants.LIKE,false);
		squery.setCriteria(c);
		DataObject data = null;
		try {
			data = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		try {
			result=data.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		while(result.hasNext())
		{
			Employee emp=new Employee();
			
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			 
			list.add(emp);
		}
		return list;
	}

	public List<Employee> getEmployeesById(int id,int total) throws SQLException 
	{
		List<Employee> list=new ArrayList<Employee>();
		
		Iterator result=null;
		
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		Criteria c=new Criteria(new Column("Employee","EMP_ID"), "*"+id+"*",QueryConstants.LIKE,false);
		squery.setCriteria(c);
		DataObject data = null;
		try {
			data = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		try {
			result=data.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		while(result.hasNext())
		{
			Employee emp=new Employee();
			
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			 
			list.add(emp);
		}
		return list;
	}

	public List<Employee> viewEmployeesSorted(String sort_by,String mode,int total) throws SQLException 
	{
		
		List<Employee> list=new ArrayList<Employee>();
		
		Iterator result=null;
		
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));
		boolean sort_mode=true;
		
		if(mode.equals("asc"))
			sort_mode=true;
		else
			sort_mode=false;
		
		SortColumn s=new SortColumn(new Column("Employee",sort_by.toUpperCase()),sort_mode);
		squery.addSortColumn(s);
		DataObject data = null;
		try {
			data = DataAccess.get(squery);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		try {
			result=data.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		while(result.hasNext())
		{
			Employee emp=new Employee();
			
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			 
			list.add(emp);
		}
		return list;
		
	}

	public List<Employee> getEmployees() throws SQLException
	{
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		squery.addSelectColumn(new Column("Employee","*"));

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
			result = dataObject.getRows("Employee");
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		}
		List<Employee> list=new ArrayList<Employee>();
		while(result.hasNext())
		{
			Employee emp=new Employee();
			Row row=(Row)result.next();
			emp.setEmp_id((Integer)row.get("EMP_ID"));
			emp.setName((String)row.get("NAME"));
			emp.setDepartment((String)row.get("DEPARTMENT"));
			emp.setReporting_to((String)row.get("REPORTING_TO"));
			emp.setEmail((String)row.get("EMAIL"));
			emp.setMobile((String)row.get("MOBILE"));
			emp.setLocation((String)row.get("LOCATION"));
			list.add(emp);
		}
		return list;
	}

	public List<String> getEmployeeDetails(int emp_id) throws DataAccessException 
	{
		
		
		Persistence per = null;
		try {
			per = (Persistence)BeanUtil.lookup("Persistence");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Criteria c=new Criteria(new Column("Employee","EMP_ID"),emp_id,QueryConstants.EQUAL);
		DataObject d =per.getForPersonality("employeeDetails", c);
		
		
		List<String> list=new ArrayList<String>();
		
		Row r1=d.getRow("Employee");
		Row r2=d.getRow("EmployeePerks");
		Row r3=d.getRow("EmployeeLeave");
		
		int id=(Integer)r1.get("EMP_ID");
		String empID=String.valueOf(id);
		list.add(empID);
		list.add((String)r1.get("NAME"));
		int salary_int=(Integer)r2.get("SALARY");
		int salary_leaves=(Integer)r3.get("TOTAL_LEAVES");
		list.add((String)String.valueOf(salary_int));
		list.add((String)String.valueOf(salary_leaves));
		
		return list;
	}
	public int getTotalMembers(String reporting_to) throws DataAccessException
	{
		Connection connection=null;
		
		int count=0;
		DataSet s=null;
		Table table=new Table("Employee");
		SelectQuery squery=new SelectQueryImpl(table);
		
		Criteria criteria=new Criteria(new Column("Employee","REPORTING_TO"),reporting_to,QueryConstants.EQUAL);
		
		//Logging
		java.util.logging.Logger logger= java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		
		
		Column c1=new Column("Employee","*").count();
		c1.setColumnAlias("TOTAL_COUNT");
		
		squery.setCriteria(criteria);
		squery.addSelectColumn(c1);
		try {
			connection=RelationalAPI.getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			s=RelationalAPI.getInstance().executeQuery(squery, connection);
			
		} catch (QueryConstructionException e) 
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		String c = null;
		try {
			if(s.next())
			{
			
			count = (Integer)s.getValue("TOTAL_COUNT");
			logger.info("Here reached......"+reporting_to+" : "+count);
			}
		} catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		return count;
	}
}
