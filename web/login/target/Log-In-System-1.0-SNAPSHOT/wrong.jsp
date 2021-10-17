<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Wrong Information </title>
</head>
<body>

<h2> Please try again! </h2>

Either your user name or password is incorrect. Please try again.

<br>
<br>

<form action="logger" method="post">
    <label>
        User Name: <input type="text" name="userName"/> <br> <br>
    </label>

    <label>
        Password: <input type="text" name="password"/>
    </label>

    <input type="submit" value="LogIn"/> <br> <br>
</form>

<a href="register.jsp"> Create New Account </a>

</body>
</html>
