<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>
		<%String message=(String)session.getAttribute("message");
		if(message!=null)
		{
			out.print("<h3>"+message+"</h3><br></br>");
			session.removeAttribute("message");
			response.setIntHeader("Refresh", 3);
		}
		%>
		Welcome to home page<br></br>
		<a href="add-employee.jsp">Add Employee</a><br></br>
		<a href="employeesView?page_no=1">View Employees</a><br></br>
		<a href="logout">Log out</a><br></br>
</body>
</html>