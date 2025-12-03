<%@ include file="../common/top.jsp"%>

<div id="Catalog">
  <form action="signOn" method="post">
    <p>Please enter your username, password and captcha.</p>
    <c:if test="${requestScope.signOnMsg != null}">
      <p> <font color="red">${requestScope.signOnMsg} </font> </p>
    </c:if>
    <p>
      Username:<input type="text" name="username"> <br />
      Password:<input type="password" name="password"> <br />
      Captcha:
      <input type="text" name="captcha" size="6" />
      <img id="captchaImg" src="captcha" alt="captcha"
           style="vertical-align: middle; cursor: pointer; margin-left: 5px;"
           onclick="this.src='captcha?'+Math.random()" />
      <span style="font-size: 12px;">(Click image to refresh)</span>
    </p>
    <input type="submit" value="Login">
  </form>
  Need a username and password?
  <a href="registerForm">Register Now!</a>

</div>

<%@ include file="../common/bottom.jsp"%>

