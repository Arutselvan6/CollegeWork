<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Users</title>
</head>
<body>
<a href='<c:url value="/" />'>Home</a><br/><br/><br/>
<table>
	<thead>
		<tr>
			<td>Id</td>
			<td>Name</td>
			<td>Password</td>
			<td>Address</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="x" items="${data}" >
			<tr>
				<td>${x.uid}</td>
				<td>${x.uname}</td>
				<td>${x.upass}</td>
				<td>${x.uaddr}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</body>
</html>