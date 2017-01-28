<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Add User</title>
	</head>
	<body>
		<a href='<c:url value="/" />'>Home</a><br/><br/><br/>
		<f:form action="saveUser" commandName="obj" method="post" >
			<table>
				<tr>
					<td>UserName</td>
					<td> <f:input path="uname"/> </td>
				</tr>
				<tr>
					<td>Password</td>
					<td> <f:password path="upass"/> </td>
				</tr>
				<tr>
					<td>Address</td>
					<td> <f:input path="uaddr"/> </td>
				</tr>
				<tr>
					<td> <input type="submit" value="Add Data"/> </td>
					<td> <input type="reset" value="Clear Data"/> </td>
				</tr>
			</table>
		</f:form>
	</body>
</html>