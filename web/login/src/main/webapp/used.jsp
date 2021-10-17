<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Create Account </title>
</head>
<body>
<h2> The Name ${userName} is </h2>
<br> Already In Use
Please enter another name and password.
<br> <br>
<form action="logger" method="post">
    <label>
        <span>User Name: <input type="text" name="userName"/> <br> <br></span>
    </label>
    <label>
        <span>Password: <input type="text" name="password"/></span>
    </label>
    <input type="submit" value="LogIn"/> <br> <br>
</form>

</body>
</html>
