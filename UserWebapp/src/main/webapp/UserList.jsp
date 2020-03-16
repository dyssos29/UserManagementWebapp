<%@page import="com.eurodynamics.web.User"%>
<%@page import="java.util.List"%>
<%@ page isELIgnored = "false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User Web Application</title>
		<link href="static/css/ViewList.css" rel="stylesheet" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	</head>
	<body>
		<h1>User Web Application</h1>
		<a href="index.jsp"><h4>Home</h4></a>
		<a href="UserForm.jsp"><h4>Register New User</h4></a>
		<h3>List of All Users</h3>

		<table>
			<thead>
				<tr>
					<th>User name</th>
					<th>Gender</th>
					<th>Birthday</th>
					<th>Home Address</th>
					<th>Work Address</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userList}" var="user">
				    <tbody class="labels">
						<tr>
							<td colspan="5">
								<label for="${user.getId()}"><c:out value="${user.getfName()} ${user.getlName()}"/></label>
								<input type="checkbox" name="name" id="${user.getId()}" data-toggle="toggle">
							</td>
						</tr>
					</tbody>
					<tbody class="hide">
						<tr>
							<td><form action="delete?id=<c:out value="${user.getId()}"/>" method="post"><input type="submit" value="Delete"></form></td>
							<td><c:out value="${user.getGender()}"/></td>
							<td><c:out value="${user.getBirthdate()}"/></td>
							<td><c:out value="${user.getHomeAddress() == null ? 'no address' : user.getHomeAddress().getAddress()}"/></td>
							<td><c:out value="${user.getWorkAddress() == null ? 'no address' : user.getWorkAddress().getAddress()}"/></td>
						</tr>
					</tbody>
				</c:forEach>
			</tbody>
		</table>
	</body>
	<script>
		$(document).ready(function() {
			$('[data-toggle="toggle"]').change(function(){
				$(this).parents().next('.hide').toggle();
			});
		});

		$('.hide').toggle();
	</script>
</html>