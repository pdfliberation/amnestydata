<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Amnesty International Alert Database</title>
<style>
textarea { width: 100% }
</style>
</head>
<body>
	<h2><a href="/home">Amnesty International Alerts Database</a></h2>
	<form accept-charset="UTF-8" action="/alerts" method="post">
		<table border="1">
			<thead>
				<tr bgcolor="orange">
					<th width="10%">&nbsp;</th>
					<th width="10%">Country</th>
					<th width="10%">Date</th>
					<th width="50%">Description</th>
					<th width="10%">Method</th>
					<th width="10%">Agency</th>
				</tr>
			<tbody>
				<tr>
					<td><input type="submit" value="Add" /></td>
					<td><input name="country" type="text" /></td>
					<td><input type="text" name="date" /></td>
					<td><textarea rows="3" cols="50" name="description"></textarea></td>
					<td><input type="text" name="method" /></td>
					<td><input type="text" name="agency" /></td>
				</tr>
			</tbody>
		</table>
	</form>
	<br>
	<table border="1">
		<thead>
			<tr bgcolor="aqua">
				<th width="10%">&nbsp;</th>
				<th width="10%">Country</th>
				<th width="10%">Date</th>
				<th width="50%">Description</th>
				<th width="10%">Method</th>
				<th width="10%">Agency</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="alert" items="${alerts.alerts}">
				<tr>
					<td valign="top"><form action="/alerts/delete" method="post">
							<input type="submit" class="btn btn-danger btn-mini"
								value="Delete" /> <input name="id" type="hidden"
								value="${alert.id}" />
						</form></td>
					<td>${alert.country}</td>
					<td>${alert.date}</td>
					<td>${alert.description}</td>
					<td>${alert.method}</td>
					<td>${alert.agency}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
