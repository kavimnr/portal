<%@page import="com.portal.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit employee</title>
</head>
<body>
		<%Employee emp=(Employee)session.getAttribute("employee") ;
		%>
		<h3>Edit Employee</h3>
		<%out.print("<h4>Employee ID is : "+emp.getEmp_id()+"</h4>"); %>
		<br></br>
		
		<form action="updateEmployee" method="post">
		<table>
		<tr><td>Enter name:</td><td> <input type="text" name="name" value=<%=emp.getName() %>></td></tr>
		<tr><td>Enter department:</td><td> <input type="text" name="department" value=<%=emp.getDepartment() %>></td></tr>
		<tr><td>Enter reporting manager:</td><td> <input type="text" name="reporting_to" value=<%=emp.getReporting_to()%>></td></tr>
		<tr><td>Enter mobile:</td><td> <input type="text" name="mobile" value=<%=emp.getMobile() %>></td></tr>
		<tr><td>Enter Email:</td><td> <input type="text" name="email" value=<%=emp.getEmail() %>></td></tr>
		<tr><td>Enter location:</td><td> <input type="text" name="location" value=<%=emp.getLocation()%>></td></tr>
		<tr><td><input type="submit" name="signup" value="Save Employee"></td></tr>
		</table>
		</form>
		
</body>
</html>