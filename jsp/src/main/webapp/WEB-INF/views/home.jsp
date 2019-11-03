<%--
  Created by IntelliJ IDEA.
  User: picongzhi
  Date: 2019-10-22
  Time: 00:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ page session="false" %>
<html>
<head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css"/>">
</head>
<body>
<h1><s:message code="spitter.welcome" text="Welcome" /></h1>
<s:url value="/spitter/register" var="registerUrl"/>

<a href="<c:url value="/spittles" />">Spittles</a> |
<a href="${registerUrl}">Register</a>
</body>
</html>
