<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<head>
    <title></title>
    <link rel="icon" type="image/png" href="/resources/images/favlogo.png" sizes="32x32">

    <link rel="stylesheet" type="text/css" href="/resources/css/parent.css">

    <script src="/resources/lib/jquery-2.1.4.min.js"></script>
</head>
<body>
    <%@include file="/WEB-INF/snippets/logout.html" %>

    <jsp:include page="/WEB-INF/snippets/path_widget.jsp" />

    <jsp:include page="/WEB-INF/snippets/files_table.jsp" />

    <%@include file="/WEB-INF/snippets/upload_item.html" %>
</body>
</html>
