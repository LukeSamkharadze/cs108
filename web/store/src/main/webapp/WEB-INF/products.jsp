<%@ page contentType="text/html;charset=UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>Student Store</title>
</head>
<body>

<h2>Student Store</h2>
<p>Items available:</p>

<ul>
    <c:forEach items="${products}" var="product">
        <li><a href="/store_war_exploded/product?id=${product.productId}">${product.name}</a></li>
    </c:forEach>
</ul>

</body>
</html>
