<%@ include file="../common/top.jsp"%>

<div id="BackLink">
    <a href="mainForm">Return to Main Menu</a>
</div>

<div id="Catalog">
    <h2>My Orders</h2>

    <c:if test="${empty requestScope.orderList}">
        <p>You have no orders.</p>
    </c:if>

    <c:if test="${not empty requestScope.orderList}">
        <table>
            <tr>
                <th>Order ID</th>
                <th>Date</th>
                <th>Total</th>
                <th>Status</th>
            </tr>
            <c:forEach var="order" items="${requestScope.orderList}">
                <tr>
                    <td>
                        <a href="viewOrder?orderId=${order.orderId}">
                            <c:out value="${order.orderId}"/>
                        </a>
                    </td>
                    <td>
                        <fmt:formatDate value="${order.orderDate}" pattern="yyyy/MM/dd" />
                    </td>
                    <td>
                        <fmt:formatNumber value="${order.totalPrice}" pattern="$#,##0.00" />
                    </td>
                    <td><c:out value="${order.status}"/></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>

<%@ include file="../common/bottom.jsp"%>
