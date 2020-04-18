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
    <title>Authentication</title>
</head>
<body>
<h2>Authentication</h2>
<form action="/auth/login" method="post">
    用户名:<input type="text" name="username"/><br>
    密码：<input type="password" name="password"/><br>
    验证码:<input type="text" name="vcode"><img src="/auth/vcode"><br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <input type="submit">
</form>
</body>
</html>
