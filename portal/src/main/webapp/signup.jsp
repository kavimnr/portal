<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign Up</title>
</head>
<body>
		<form action="signupController" method="post">
		<table>
		<tr><td>Enter name:</td><td><input type="text" name="name"></td></tr>
		<tr><td>Enter city: </td><td><input type="text" name="city"></td></tr>
		<tr><td>Enter email: </td><td><input type="text" name="email"></td></tr>
		<tr><td>Enter mobile: </td><td><input type="text" name="mobile"></td></tr>
		<tr><td>Enter username: </td><td><input type="text" name="username"></td></tr>
		<tr><td>Enter password: </td><td><input type="password" name="password"></td></tr>
		<tr><td><input type="submit" name="signup" value="Sign Up"></td></tr>
		</table>
		</form>
</body>
</html>