<%@ include file="../common/top.jsp"%>

<div id="Catalog">
  <form action="editAccount" method="post">
    <h3>User Information</h3>

    <c:if test="${requestScope.editMsg != null}">
      <p><font color="red">${requestScope.editMsg}</font></p>
    </c:if>

    <table>
      <tr>
        <td>User ID:</td>
        <td>${account.username}</td>
      </tr>
      <tr>
        <td>New password:</td>
        <td><input type="password" name="password" /></td>
      </tr>
      <tr>
        <td>Repeat password:</td>
        <td><input type="password" name="repeatedPassword" /></td>
      </tr>
    </table>

    <%@ include file="accountFields.jsp"%>

    <input type="submit" name="editAccount" value="Save Account Information" />

  </form>
  <a href="listOrders">My Orders</a>
</div>

<%@ include file="../common/bottom.jsp"%>

