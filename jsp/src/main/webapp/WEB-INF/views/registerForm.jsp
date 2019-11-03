<%--
  Created by IntelliJ IDEA.
  User: picongzhi
  Date: 2019-10-22
  Time: 01:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="sd" uri="http://www.springframework.org/tags/form" %>
<%@page session="false" %>
<html>
<head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />">
</head>
<body>
<h1>Register</h1>
<sf:form method="post" commandName="spitter">
    <sf:errors path="*" element="div" cssClass="errors"/>
    <sf:label path="firstName" cssErrorClass="error">First Name</sf:label>:
    <sd:input path="firstName" cssErrorClass="error"/><br/>
    <sf:label path="lastName" cssErrorClass="error">Last Name</sf:label>:
    <sd:input path="lastName" cssErrorClass="error"/><br/>
    <sf:label path="Email" cssErrorClass="error">Email</sf:label>:
    <sd:input path="email" cssErrorClass="error"/><br/>
    <sf:label path="username" cssErrorClass="error">Username</sf:label>:
    <sd:input path="username" cssErrorClass="error"/><br/>
    <sf:label path="password" cssErrorClass="error">Password</sf:label>:
    <sf:password path="password" cssErrorClass="error"/><br/>
    <input type="submit" value="Register">
</sf:form>
</body>
</html>
