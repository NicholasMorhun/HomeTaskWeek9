<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <title>Cloud pocket login</title>
  <link rel="icon" type="image/png" href="/resources/images/favlogo.png" sizes="32x32">

  <link rel="stylesheet" type="text/css" href="/resources/css/parent.css">
  <link rel="stylesheet" type="text/css" href="/resources/css/forms.css">
  <link rel="stylesheet" type="text/css" href="/resources/css/footer_links.css">
  <link rel="stylesheet" type="text/css" href="/resources/css/login.css">

  <script src="/resources/lib/jquery-2.1.4.min.js"></script>
</head>
<body>
 <jsp:include page="/WEB-INF/snippets/error_message.jsp" />

  <%@ include file="/WEB-INF/snippets/enter_header.html" %>

  <div id="login_form">
    <form action="/login" method="post">
      <div class="input_row">
        <label for="login">Login:</label>
        <input type="text" id="login" name="login" value="${oldLogin}"/>
      </div>
      <div class="input_row">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"/>
      </div>
      <div id="button_login_row">
        <button type="submit">Login</button>
      </div>
    </form>
  </div>

  <div class="footer_invitation">
    <p>Do not have account? Register <a href="/register">here</a></p>
  </div>
</body>
</html>