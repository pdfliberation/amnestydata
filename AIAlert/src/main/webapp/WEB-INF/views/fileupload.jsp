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
<h2><a href="/home">Amnesty International Alerts Database</a></h2>
		<p>
			Use this page for uploading data in json format	
		</p>
		<form id="fileuploadForm" action="fileupload" method="POST" enctype="multipart/form-data" class="cleanform">
			<div>
		  		<h2>Upload Saved Alert Data</h2>
			</div>
			<label for="file">File</label>
			<input id="file" type="file" name="file" />
			<p><button type="submit">Upload</button></p>		
		</form>
</body>
</html>
