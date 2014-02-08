<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Amnesty International Alert Database</title>
</head>
<body>
	<h2>
		<a href="/home">Amnesty International Alerts Database</a>
	</h2>
	<br>
	<table border="1">
		<tbody>
			<tr>
				<td><form action="/alerts/filter" method="get">
						SELECT COUNTRY:<select name="country">
							<option value="all">ALL</option>
							<c:forEach var="country" items="${model.countries}">
								<option value="${country}"
									<c:if test="${country == model.getCountry() }">selected</c:if>>${country}
							</c:forEach>
						</select>SELECT YEAR:<select name="year">
							<option value="all">ALL</option>
							<c:forEach var="year" items="${model.years}">
								<option value="${year}"
									<c:if test="${year == model.getYear()}">selected</c:if>>${year}</option>
							</c:forEach>
							<input type="submit" class="btn btn-danger btn-mini"
							value="FILTER" />
					</form></td>
			</tr>
		</tbody>
	</table>
	<table border="1">
		<thead>
			<tr bgcolor="aqua">
				<th width="10%">&nbsp;</th>
				<th width="15%">Country</th>
				<th width="60%">Description</th>
				<th width="15%">Notes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="alert" items="${model.alerts.alerts}">
				<tr>
					<form action="/alerts/update" method="post">
						<td valign="top" ><input type="submit"
							class="btn btn-danger btn-mini" value="UPDATE" /><br>
						<input name="id" type="hidden" value="${alert.id}" /></td>
						<td>${alert.country}<br>${alert.date}</td>
						<td>${alert.description}</td>
						<td><textarea rows="7" cols="25" name="notes">${alert.notes}</textarea></td>
					</form>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
