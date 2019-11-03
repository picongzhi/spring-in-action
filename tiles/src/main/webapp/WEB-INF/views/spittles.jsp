<%--
  Created by IntelliJ IDEA.
  User: picongzhi
  Date: 2019-10-22
  Time: 01:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page session="false"%>
<div class="spittleForm">
    <h1>Spit it out...</h1>
    <form method="POST" name="spittleForm">
        <input type="hidden" name="latitude"/>
        <input type="hidden" name="longitude"/>
        <textarea name="message" cols="80" rows="5"></textarea><br/>
        <input type="submit" value="Add"/>
    </form>
</div>
<div class="listTitle">
    <h1>Recent Spittles</h1>
    <ul class="spittleList">
        <c:forEach items="${spittleList}" var="spittle">
            <li id="spittle_<c:out value="spittle.id"/>">
                <div class="spittleMessage"><c:out value="${spittle.message}"/></div>
                <div>
                    <span class="spittleTime"><c:out value="${spittle.time}"/></span>
                    <span class="spittleLocation">
                        (<c:out value="${spittle.latitude}"/>, <c:out value="${spittle.longitude}"/>)
                    </span>
                </div>
            </li>
        </c:forEach>
    </ul>
    <c:if test="${fn:length(spittleList) gt 20}">
        <hr/>
        <s:url value="/spittles?count=${nextCount}" var="more_url"/>
        <a href="${more_url}">Show more</a>
    </c:if>
</div>
