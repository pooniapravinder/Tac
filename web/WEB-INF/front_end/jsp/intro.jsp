<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@page contentType="text/html" pageEncoding="UTF-8"%><%@page session="false"%><!DOCTYPE html>
<html lang="en">
    <head>
        <title>Tac</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <link rel="icon" type="image/png" href="/icons/favicon.ico"/>
        <style>
            html {
                font-family: Arial, Helvetica, sans-serif;
            }
            img {
                width: 50%;
            }
            center {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }
            .footer {
                position: fixed;
                bottom:0; 
                display:block;
                width: 100%;
                text-align: center;
                font-size: 12px;
                margin-bottom: 5px;
            }
        </style>
    </head>
    <body>
        <jsp:useBean id="date" class="java.util.Date" />
    <center>
        <img src="//static.wookes.com/wookes_tac_logo.png" alt="Wookes Tac">
    </center>
    <div class="footer">Wookes &copy; <fmt:formatDate value="${date}" pattern="yyyy"/></div>
</body>
</html>
