<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>${fileName}</title>
  <link rel="icon" type="image/png" href="/resources/images/favlogo.png" sizes="32x32">

  <style>
    body { background-color: black; }
    #player_wrapper {
      height:100%;
      width: 100%;
      margin: 0;
      padding: 0;
      border: 0;
      text-align: center;
    }
  </style>
</head>
<body>
  <table id="player_wrapper">
    <tr>
      <td><img src="${urlToFile}"></td>
    </tr>
  </table>
</body>
</html>
