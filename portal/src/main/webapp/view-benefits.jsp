<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Benefits</title>
</head>
<body>
		<% ArrayList<String> list=(ArrayList)request.getAttribute("list");%>
		
		<h3>Employee Benefits</h3><br></br>
		<table border='1' width='40%'>
		<tr><th>Id</th><%out.print("<td>"+list.get(0)+"</td><tr>"); %>
		<tr><th>Name</th><%out.print("<td>"+list.get(1)+"</td><tr>"); %>
		<tr><th>Salary</th><%out.print("<td>"+list.get(2)+"</td><tr>"); %>
		<tr><th>Leaves</th><%out.print("<td>"+list.get(3)+"</td><tr>"); %>
        </table>
</body>
</html>