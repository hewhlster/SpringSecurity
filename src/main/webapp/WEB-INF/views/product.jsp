<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 2019/12/3
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>操作产品页</h2>

    <a href="/product/add">增加</a>
    <a href="/product/delete">删除</a>
    <a href="/product/update">更新</a>
    <a href="/product/select">查找</a>

    <br/>
    <a href="/logout">退出</a>
</body>

<script type="text/javascript" src="/static/js/jquery/jquery-1.6.js"></script>
</html>
