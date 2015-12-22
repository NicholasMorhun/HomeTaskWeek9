<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Cloud pocket registration</title>

  <link rel="stylesheet" type="text/css" href="/resources/css/parent.css">
  <link rel="stylesheet" type="text/css" href="/resources/css/forms.css">
  <link rel="stylesheet" type="text/css" href="/resources/css/footer_links.css">
  <link rel="stylesheet" type="text/css" href="/resources/css/registration.css">

  <script src="/resources/lib/jquery-2.1.4.min.js"></script>
</head>
<body>
  <jsp:include page="/WEB-INF/snippets/error_message.jsp" />

  <%@ include file="/WEB-INF/snippets/enter_header.html" %>

  <div id="registration_form">
    <form action="#" method="post">
      <div class="input_row">
        <label for="login">Login:</label>
        <input type="text" id="login" name="login" value="${loginValue}"/>
      </div>
      <div class="input_row">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"/>
      </div>
      <div class="input_row">
        <label for="confirm_password">Confirm password:</label>
        <input type="password" id="confirm_password" name="confirm_password"/>
      </div>
      <div id="button_registration_row">
        <button type="submit">Register</button>
      </div>
    </form>
  </div>

  <div class="footer_invitation">
    <p>Already have account? Login <a href="/login">here</a></p>
  </div>
</body>
</html>
