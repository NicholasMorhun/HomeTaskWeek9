<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<head>
    <title>${fileName}</title>
    <link rel="icon" type="image/png" href="/resources/images/favlogo.png" sizes="32x32">

    <script type="text/javascript" src="/resources/lib/syntaxhighlighter/scripts/shCore.js"></script>
    <script type="text/javascript" src="/resources/lib/syntaxhighlighter/scripts/${brushName}.js"></script>

    <link href="/resources/lib/syntaxhighlighter/styles/shCore.css" rel="stylesheet" type="text/css" />
    <link href="/resources/lib/syntaxhighlighter/styles/shThemeDefault.css" rel="stylesheet" type="text/css" />

    <script src="/resources/lib/jquery-2.1.4.min.js"></script>
    <script>
        $(document).ready(function() {
            function loadContent() {
                $.ajax({
                    url: "${urlToFile}",
                    async: false,
                    success: function(result){
                        $("#source_code").text(result);
                    }});
            }

            loadContent();
            SyntaxHighlighter.all();
        });
    </script>
</head>
<body>
    <pre id="source_code" class="brush: ${fileExt}"></pre>
</body>
</html>
