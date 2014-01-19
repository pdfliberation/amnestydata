<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta charset="utf-8">
    <title>Amnesty International Alert Database</title>

    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
    <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!--
      IMPORTANT:
      This is Heroku specific styling. Remove to customize.
    -->
    <link href="http://heroku.github.com/template-app-bootstrap/heroku.css" rel="stylesheet">
    <!-- /// -->

</head>

<body>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a href="/" class="brand">Sample Amnesty International Alert Database</a>
            <a href="/" class="brand" id="heroku">by <strong>heroku</strong></a>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="span8 offset2">
            <div class="page-header">
                <h1>Simple Alert Admin Page</h1>
            </div>
            <form:form method="post" action="add" commandName="alert" class="form-vertical">

                <form:label path="country">Country</form:label>
                <form:input path="country" />
                <form:label path="date">Date</form:label>
                <form:input path="date" />
                <form:label path="description">Description</form:label>
                <form:input size="50" path="description" />
                <input type="submit" value="Add Alert" class="btn"/>
            </form:form>


            <c:if  test="${!empty alertList}">
                <h3>Alert</h3>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>Alert</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${alertList}" var="alert">
                        <tr>
                            <td>${alert.country}, ${alert.date}, ${alert.description}</td>
                            <td><form action="delete/${alert.id}" method="post"><input type="submit" class="btn btn-danger btn-mini" value="Delete"/></form></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>
