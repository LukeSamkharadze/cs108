<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" session="false" %>
<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>

<h2>Shopping Cart</h2>

<form action="cart" method="post">
    <ul>
        <c:forEach items="${cart.cartProducts}" var="cartProduct">
            <li>
                <label>
                    <input type="text" name="${cartProduct.product.productId}" value="${cartProduct.count}">
                    <span>${cartProduct.product.name}, ${cartProduct.price}</span>
                </label>
            </li>
        </c:forEach>

        <br>

        <span>Total $${cart.price}</span>
        <input type="submit" value="Update Cart">
    </ul>
</form>

<a href="/store_war_exploded">Continue Shopping</a>

</body>
</html>
