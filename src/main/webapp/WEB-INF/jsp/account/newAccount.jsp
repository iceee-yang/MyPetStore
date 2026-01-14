<%@ include file="../common/top.jsp"%>

<div id="Catalog">
  <form action="register" method="post" id="newAccountForm">
    <h3>User Information</h3>

    <c:if test="${requestScope.registerMsg != null}">
      <p><font color="red">${requestScope.registerMsg}</font></p>
    </c:if>

    <table>
      <tr>
        <td>User ID:</td>
        <td>
          <input type="text" name="username" id="username" value="${account.username}" autocomplete="off" />
          <span id="usernameMsg" style="margin-left: 8px;"></span>
        </td>
      </tr>
      <tr>
        <td>New password:</td>
        <td><input type="password" name="password" id="password" /></td>
      </tr>
      <tr>
        <td>Repeat password:</td>
        <td><input type="password" name="repeatedPassword" id="repeatedPassword" /></td>
      </tr>
    </table>

    <%@ include file="accountFields.jsp"%>

    <input type="submit" name="newAccount" value="Save Account Information" />

  </form>
</div>

<script>
(function () {
  var usernameEl = document.getElementById('username');
  var usernameMsgEl = document.getElementById('usernameMsg');
  var formEl = document.getElementById('newAccountForm');

  function setMsg(text, ok) {
    usernameMsgEl.textContent = text || '';
    usernameMsgEl.style.color = ok ? 'green' : 'red';
  }

  function checkUsernameExists(username, cb) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'checkUsername?username=' + encodeURIComponent(username), true);
    xhr.onreadystatechange = function () {
      if (xhr.readyState !== 4) return;
      if (xhr.status !== 200) {
        cb(new Error('network'));
        return;
      }
      try {
        var data = JSON.parse(xhr.responseText);
        cb(null, data && data.exists === true);
      } catch (e) {
        cb(e);
      }
    };
    xhr.send();
  }

  function validateUsernameAsync(cb) {
    var username = (usernameEl.value || '').trim();
    if (!username) {
      setMsg('Username is required', false);
      cb(false);
      return;
    }
    setMsg('Checking...', false);
    checkUsernameExists(username, function (err, exists) {
      if (err) {
        setMsg('Validation failed, please try again later', false);
        cb(false);
        return;
      }
      if (exists) {
        setMsg('Username already exists', false);
        cb(false);
        return;
      }
      setMsg('Username is available', true);
      cb(true);
    });
  }

  usernameEl.addEventListener('blur', function () {
    validateUsernameAsync(function () {});
  });

  formEl.addEventListener('submit', function (e) {
    e.preventDefault();
    validateUsernameAsync(function (ok) {
      if (ok) {
        formEl.submit();
      }
    });
  });
})();
</script>

<%@ include file="../common/bottom.jsp"%>