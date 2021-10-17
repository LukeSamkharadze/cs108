<%@ page contentType="text/html;charset=UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>Product</title>
</head>
<body>

<h2>${product.name}</h2>

<img src="${pageContext.request.contextPath}/images/${product.imageFile}" alt="store photo">

<br>

<form action="cart" method="post">
    <span>$${product.price}</span>
    <input name="productId" type="hidden" value="${product.productId}"/>
    <input type="submit" value="Add to Cart">
</form>

</body>
</html>
