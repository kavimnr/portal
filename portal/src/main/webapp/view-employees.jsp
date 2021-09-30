<%@page import="com.portal.dao.impl.DBConnection"%>
<%@page import="com.portal.dao.impl.EmployeeDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.portal.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee List</title>
</head>
<body>
		<% String count=(String)application.getAttribute("total");
			int total=Integer.parseInt(count);
			DBConnection con=new DBConnection();
			
			EmployeeDao dao=EmployeeDao.getInstance(con.getConnection());
		%>
		<% ArrayList<Employee> list=(ArrayList)request.getAttribute("list");
		%>
		<h4>Employees List</h4><br></br>
		<form action="searchEmployee?page_no=1">
		<table width='100'>
		<tr>
		<td><input type="hidden" name="page_no" value="1"></td>
		<td><input type="text" name="emp_id" size="2" placeholder="ID"></td>
		<td><input type="text" name="name" size=22 placeholder="Name"></td>
		<td><input type="text" name="department" size=27 placeholder="Department"></td>
		<td><input type="text" name="reporting_to" size=24 placeholder="Reporting to"></td>
		<td><input type="text" name="email" size=43 placeholder="Email"></td>
		<td><input type="text" name="mobile" placeholder="Mobile"></td>
		<td><input type="text" name="location" placeholder="Location"></td>
		</tr>
		</table>
		
		<input type="submit" name="mode" value="Search">
		</form>
		
		<form action="sortEmployees">
		<select name="sort_by">
			  <option value="emp_id">Employee ID</option>
			  <option value="name">Name</option>
			  <option value="department">Department</option>
			  <option value="reporting_to">Reporting Manager</option>
			  <option value="email">Email</option>
			  <option value="mobile">Mobile</option>
			  <option value="location">Location</option>
		</select>
		<select name="mode">
			  <option value="asc">Ascending Order</option>
			  <option value="desc">Descending order</option>
		</select>
		<input type="submit" value="Sort">
		</form>
		
		<table border='1' width='100%'>
		<tr><th>Id</th><th>Name</th><th>Department</th><th>Manager</th><th>Email</th><th>Mobile</th><th>Location</th><th>Edit</th><th>Delete</th><th>Benefits</th><th>Head of</th></tr>
		<% 
		for(Employee e:list)
		{  
         out.print("<tr><td>"+e.getEmp_id()+"</td><td>"+e.getName()+"</td><td>"+e.getDepartment()+"</td><td>"+e.getReporting_to()+"</td><td>"+e.getEmail()+"</td><td>"+e.getMobile()+"</td><td>"+e.getLocation()+"</td><td><a href='editEmployee?emp_id="+e.getEmp_id()+"'>edit</a></td>  <td><a href='deleteEmployee?emp_id="+e.getEmp_id()+"'>delete</a></td><td><a href='viewBenefits?emp_id="+e.getEmp_id()+"'>view</a></td><td><a href='viewMembers?emp_id="+e.getEmp_id()+"'>"+dao.getTotalMembers(e.getName())+"</a></td></tr>");  
        } %>
        </table>
        <br></br>
        <% for(int i=1;i<(list.size()/total)+1;i++)
		{  
         out.print("<a href='employeesView?page_no="+i+"'>"+i+"</a>");
         out.print("   ");
        } %>
</body>
</html>