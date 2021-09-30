<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Employee</title>
</head>
<body>
		<h3>Add new Employee</h3><br></br>
		<form action="addEmployee" method="post">
		<table>
		<tr><td>Enter name:</td><td> <input type="text" name="name"></td></tr>
		<tr><td>Enter department:</td><td> <input type="text" name="department"></td></tr>
		<tr><td>Enter reporting manager:</td><td> <input type="text" name="reporting_to"></td></tr>
		<tr><td>Enter mobile:</td><td> <input type="text" name="mobile"></td></tr>
		<tr><td>Enter Email:</td><td> <input type="text" name="email"></td></tr>
		<tr><td>Enter location:</td><td> <input type="text" name="location"></td></tr>
		<tr><td><input type="submit" name="signup" value="Save Employee"></td></tr>
		</table>
		</form>
</body>
</html>