<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Amnesty International Alerts Database</title>
</head>
<body>
<h2><a href="<c:url value="/" />">Amnesty International Alerts Database</a></h2>
<div style="margin-bottom: 20px">
  This is proof of concept web application to serve as an Alert Database. 
</div>
<div style="margin-bottom: 20px">
To Inspect, Add and Delete Individual records go to the <a href="/alerts">alerts page</a>. 
</div>
<div style="margin-bottom: 20px">
To upload bulk data go to the <a href="/fileupload">uploads page</a>. 
</div>
<div style="margin-bottom: 20px">
To use the RESTful API, try these URL's<ul>
<li>All Data = "<a href="/data/">/data</a>"</li>
<li>All Data for US = "<a href="/data?country=US">/data?country=US</a>"</li>
<li>All Data for US for 2012 = "<a href="/data?country=US&date=2012">/data?country=US&amp;date=2012</a>"</li>
</ul> 
</div>
</body>
</html>