<%@ include file="../common/top.jsp"%>

<div id="Catalog">
  <form action="register" method="post">
    <h3>User Information</h3>

    <c:if test="${requestScope.registerMsg != null}">
      <p><font color="red">${requestScope.registerMsg}</font></p>
    </c:if>

    <table>
      <tr>
        <td>User ID:</td>
        <td><input type="text" name="username" value="${account.username}" /></td>
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

    <input type="submit" name="newAccount" value="Save Account Information" />

  </form>
</div>

<%@ include file="../common/bottom.jsp"%>