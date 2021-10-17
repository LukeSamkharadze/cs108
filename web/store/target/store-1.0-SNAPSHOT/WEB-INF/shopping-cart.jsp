<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" session="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Shopping Cart</h1>

<form action="shopping-cart" method="post">
    <ul>
        <c:forEach items="${cart.shoppingCartProducts}" var="shoppingCartProduct">
            <li>
                <input type="text" name="${shoppingCartProduct.product.productId}" value="${shoppingCartProduct.count}">
                ${shoppingCartProduct.product.name}, ${shoppingCartProduct.price}
            </li>
        </c:forEach>
        <br>
        Total $${cart.price}
        <button>Update Cart</button>
    </ul>
</form>
    <a href="/store_war_exploded">Continue Shopping</a>
</body>
</html>
