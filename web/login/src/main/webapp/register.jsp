<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Create Account </title>
</head>
<body>
<h1> Create New Account </h1>
Please enter proposed name and password. <br> <br>
<form action="registration" method="post">
    <label>
        User Name: <input type="text" name="userName"/> <br> <br>
    </label>
    <label>
        Password: <input type="text" name="password"/>
    </label>
    <input type="submit" value="LogIn"/> <br> <br>
</form>
</body>
</html>
