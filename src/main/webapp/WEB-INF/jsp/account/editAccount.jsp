<%@ include file="../common/top.jsp"%>

<div id="Catalog">
  <form action="editAccount" method="post" id="editAccountForm">
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
        <td>
          <input type="password" name="password" id="password" />
          <span id="passwordMsg" style="margin-left: 8px;"></span>
        </td>
      </tr>
      <tr>
        <td>Repeat password:</td>
        <td>
          <input type="password" name="repeatedPassword" id="repeatedPassword" />
          <span id="repeatedPasswordMsg" style="margin-left: 8px;"></span>
        </td>
      </tr>
    </table>

    <%@ include file="accountFields.jsp"%>

    <input type="submit" name="editAccount" value="Save Account Information" />

  </form>
  <a href="listOrders">My Orders</a>
</div>

<script>
(function () {
  var passwordEl = document.getElementById('password');
  var passwordMsgEl = document.getElementById('passwordMsg');
  var repeatedPasswordEl = document.getElementById('repeatedPassword');
  var repeatedPasswordMsgEl = document.getElementById('repeatedPasswordMsg');
  var formEl = document.getElementById('editAccountForm');

  function setMsg(el, text, ok) {
    el.textContent = text || '';
    el.style.color = ok ? 'green' : 'red';
  }

  function validatePassword(password) {
    if (!password) {
      return null; // empty means do not change password
    }
    if (password.length < 8 || password.length > 15) {
      return 'Password must be 8-15 characters long';
    }
    if (!/[a-zA-Z]/.test(password) || !/\d/.test(password)) {
      return 'Password must contain both letters and numbers';
    }
    return null;
  }

  function validatePasswordsMatch(password, repeatedPassword) {
    if (!password) {
      return null;
    }
    if (!repeatedPassword) {
      return 'Please repeat the password';
    }
    if (password !== repeatedPassword) {
      return 'Passwords do not match';
    }
    return null;
  }

  function validatePasswordSync() {
    var password = passwordEl.value || '';
    var msg = validatePassword(password);
    if (msg) {
      setMsg(passwordMsgEl, msg, false);
      return false;
    }
    if (password) {
      setMsg(passwordMsgEl, 'OK', true);
    } else {
      setMsg(passwordMsgEl, '', true);
    }
    return true;
  }

  function validateRepeatedPasswordSync() {
    var password = passwordEl.value || '';
    var repeatedPassword = repeatedPasswordEl.value || '';
    var msg = validatePasswordsMatch(password, repeatedPassword);
    if (msg) {
      setMsg(repeatedPasswordMsgEl, msg, false);
      return false;
    }
    if (password) {
      setMsg(repeatedPasswordMsgEl, 'OK', true);
    } else {
      setMsg(repeatedPasswordMsgEl, '', true);
    }
    return true;
  }

  passwordEl.addEventListener('blur', function () {
    validatePasswordSync();
    if (repeatedPasswordEl.value) {
      validateRepeatedPasswordSync();
    }
  });

  passwordEl.addEventListener('input', function () {
    if (passwordMsgEl.textContent) {
      validatePasswordSync();
    }
    if (repeatedPasswordEl.value) {
      validateRepeatedPasswordSync();
    }
  });

  repeatedPasswordEl.addEventListener('blur', function () {
    validateRepeatedPasswordSync();
  });

  repeatedPasswordEl.addEventListener('input', function () {
    if (repeatedPasswordMsgEl.textContent) {
      validateRepeatedPasswordSync();
    }
  });

  formEl.addEventListener('submit', function (e) {
    var passwordOk = validatePasswordSync();
    var repeatedOk = validateRepeatedPasswordSync();
    if (!passwordOk || !repeatedOk) {
      e.preventDefault();
    }
  });
})();
</script>

<%@ include file="../common/bottom.jsp"%>

