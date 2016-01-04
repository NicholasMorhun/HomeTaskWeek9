<%@ page session="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>404 not found</title>
  <link rel="icon" type="image/png" href="/resources/images/favlogo.png" sizes="32x32">

  <link rel="stylesheet" type="text/css" href="/resources/css/parent.css">
  <link rel="stylesheet" type="text/css" href="/resources/css/footer_links.css">
  <link rel="stylesheet" type="text/css" href="/resources/css/errors.css">
</head>
<body>
<p class="error_text_header">Page not found</p>
<div class="center_div">
  <img src="/resources/images/pictograms/error404.png">
</div>
<div class="footer_invitation">
  <a href="${sessionScope.lastPath}">back</a>
</div>
</body>
</html>