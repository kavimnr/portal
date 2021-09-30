package com.portal.rest;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.portal.dao.impl.DBConnection;
import com.portal.dao.impl.EmployeeDao;
import com.portal.model.Employee;

@Path("employees")
public class EmployeeResource
{
	DBConnection con=new DBConnection();
	EmployeeDao dao=EmployeeDao.getInstance(con.getConnection());
	
	@PermitAll
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public List<Employee> getEmployees()
	{
		List<Employee> list=null;
		try {
			list = dao.getEmployees();
		} catch (SQLException e) 
		{
			System.out.println("Exception Catched");
			e.printStackTrace();
		}
		return list; 
	}
	@GET
	@Path("{emp_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getEmployee(@PathParam("emp_id") int id) throws SQLException
	{
		
		return dao.getEmployeeById(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean saveEmployee(Employee e) throws SQLException
	{
		return dao.save(e);
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public int postEmployee(Employee e) throws SQLException
	{
		return dao.updateEmployee(e);
	}
	
	@DELETE
	@Path("/{emp_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public int deleteEmployee(@PathParam("emp_id") int id) throws SQLException
	{
		return dao.deleteEmployee(id);
	}
	
}
