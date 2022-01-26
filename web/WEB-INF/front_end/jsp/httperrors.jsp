<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@page contentType="text/html" pageEncoding="UTF-8"%><%@page session="false"%><!DOCTYPE html>
<html>
    <head>
        <title>Error ${error}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <link rel="icon" type="image/png" href="/icons/favicon.ico"/>
        <style>
            html,code{
                font:15px/22px arial,sans-serif;
            }
            center {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }
            img {
                width: 50%;
            }
            ins{
                color:#777;
                text-decoration:none;
            }
        </style>
    </head>
    <body>
    <center>
        <a href="//tac.wookes.com">
            <img src="//static.wookes.com/wookes_tac_logo.png" alt="Wookes">
        </a>
        <p><b>${error}.</b> <ins>That’s an error.</ins></p>
        <c:if test="${error==404}">
            <p>
                <%if (request.getAttribute("javax.servlet.forward.query_string") != null) {%>
                The requested URL <%out.print(request.getAttribute("javax.servlet.forward.request_uri") + "?" + request.getAttribute("javax.servlet.forward.query_string"));%> was not found on this server.
                <%} else {%>
                The requested URL <%out.print(request.getAttribute("javax.servlet.forward.request_uri"));%> was not found on this server.
                <%}%>
                <ins>That's all we know.</ins>
            </p>
        </c:if>
        <c:if test="${error==500}">
            <p>
                Service is currently unavailable.
                <ins>We are working on it.</ins>
            </p>
        </c:if>
    </center>
</body>
</html>
