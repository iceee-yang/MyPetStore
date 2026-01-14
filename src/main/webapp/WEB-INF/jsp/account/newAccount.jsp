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

    <input type="submit" name="newAccount" value="Save Account Information" />

  </form>
</div>

<script>
(function () {
  var usernameEl = document.getElementById('username');
  var usernameMsgEl = document.getElementById('usernameMsg');
  var passwordEl = document.getElementById('password');
  var passwordMsgEl = document.getElementById('passwordMsg');
  var repeatedPasswordEl = document.getElementById('repeatedPassword');
  var repeatedPasswordMsgEl = document.getElementById('repeatedPasswordMsg');
  var formEl = document.getElementById('newAccountForm');

  function setMsg(el, text, ok) {
    el.textContent = text || '';
    el.style.color = ok ? 'green' : 'red';
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

  function validatePassword(password) {
    if (!password) {
      return 'Password is required';
    }
    if (password.length < 8 || password.length > 15) {
      return 'Password must be 8-15 characters long';
    }
    if (!/[a-zA-Z]/.test(password) || !/\d/.test(password)) {
      return 'Password must contain both letters and numbers';
    }
    return null; // 返回 null 表示验证通过
  }

  function validatePasswordsMatch(password, repeatedPassword) {
    if (password !== repeatedPassword) {
      return 'Passwords do not match';
    }
    return null;
  }

  function validateUsernameAsync(cb) {
    var username = (usernameEl.value || '').trim();
    if (!username) {
      setMsg(usernameMsgEl, 'Username is required', false);
      cb(false);
      return;
    }
    setMsg(usernameMsgEl, 'Checking...', false);
    checkUsernameExists(username, function (err, exists) {
      if (err) {
        setMsg(usernameMsgEl, 'Validation failed, please try again later', false);
        cb(false);
        return;
      }
      if (exists) {
        setMsg(usernameMsgEl, 'Username already exists', false);
        cb(false);
        return;
      }
      setMsg(usernameMsgEl, 'Username is available', true);
      cb(true);
    });
  }

  function validatePasswordSync() {
    var password = passwordEl.value || '';
    var msg = validatePassword(password);
    if (msg) {
      setMsg(passwordMsgEl, msg, false);
      return false;
    }
    setMsg(passwordMsgEl, 'OK', true);
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
    if (!repeatedPassword) {
      setMsg(repeatedPasswordMsgEl, 'Please repeat the password', false);
      return false;
    }
    setMsg(repeatedPasswordMsgEl, 'OK', true);
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

  usernameEl.addEventListener('blur', function () {
    validateUsernameAsync(function () {});
  });

  formEl.addEventListener('submit', function (e) {
    e.preventDefault();

    var passwordOk = validatePasswordSync();
    var repeatedOk = validateRepeatedPasswordSync();
    if (!passwordOk || !repeatedOk) {
      return;
    }

    validateUsernameAsync(function (ok) {
      if (ok) {
        formEl.submit();
      }
    });
  });
})();
</script>

<%@ include file="../common/bottom.jsp"%>