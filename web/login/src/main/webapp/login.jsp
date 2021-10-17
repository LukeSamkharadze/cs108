<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title> Log In </title>
</head>
<body>

<h2> Welcome to HW5! </h2>
Please log in.
<br> <br>

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
