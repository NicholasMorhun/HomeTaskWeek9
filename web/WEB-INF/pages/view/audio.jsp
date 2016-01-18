<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>HTML5 MediaElement</title>

    <script src="/resources/lib/jquery-2.1.4.min.js"></script>

    <script src="/resources/lib/johndyer-mediaelement/mediaelement-and-player.min.js"></script>
    <link rel="stylesheet" href="/resources/lib/johndyer-mediaelement/mediaelementplayer.min.css" />

    <style>
        body {
            background-color: black;
        }
        #track_name {
            display: block;
            margin: 150px auto 50px;
            text-align: center;
            font-family: monospace;
            font-size: 36px;
            color: rgba(69, 69, 245, 1);
            text-shadow: 0 0 10px blue;
        }
    </style>

</head>
<body>

  <div id="track_name">${fileName}</div>
  <div id="audio_player_wrapper" align="center" >
      <audio id="player" width="600px" preload="metadata"
             src="${urlToFile}" type="audio/${fileExt}" controls="controls">
      </audio>
  </div>

  <script>
    $('audio').mediaelementplayer();
  </script>

</body>
</html>


