<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="../common/top.jsp" %>

<div id="BackLink">
    <a href="productForm?productId=${item.product.productId}">
        Return to ${item.product.name}
    </a>
</div>

<div id="Catalog">

    <h2>${item.product.name}</h2>

    <table>
        <tr>
            <td>Item ID:</td>
            <td><b>${item.itemId}</b></td>
        </tr>
        <tr>
            <td>Product ID:</td>
            <td>${item.product.productId}</td>
        </tr>
        <tr>
            <td>Description:</td>
            <td>
                ${item.attribute1} ${item.attribute2} ${item.attribute3}
                ${item.attribute4} ${item.attribute5}
            </td>
        </tr>
        <tr>
            <td>List Price:</td>
            <td><fmt:formatNumber value="${item.listPrice}" pattern="$#,##0.00"/></td>
        </tr>
        <tr>
            <td>Status:</td>
            <td>${item.status}</td>
        </tr>
        <tr>
            <td>In Stock:</td>
            <td>
                <c:choose>
                    <c:when test="${item.quantity > 0}">true</c:when>
                    <c:otherwise>false</c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>

    <p>
        <a href="addItemToCart?workingItemId=${item.itemId}" class="Button">Add to Cart</a>
    </p>

</div>

<%@ include file="../common/bottom.jsp" %>
