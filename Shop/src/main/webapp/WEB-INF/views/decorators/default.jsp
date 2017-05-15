<%--
  Created by IntelliJ IDEA.
  User: GZR
  Date: 2017/3/7
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/taglibs.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><sitemesh:write property='title' /></title>
    <link rel="Shortcut Icon" href="<c:url value='/static/favicon/bitbug_favicon_48.ico'/>" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/css/slider.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/css/common.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/css/index.css'/>" />
    <script type="text/javascript" src="<c:url value='/static/bootstrap-3.3.7-dist/js/jquery-1.11.1.js'/>"></script>
    <sitemesh:write property='head' />
</head>
<body>
    <div class="container header">
        <jsp:include page="../includes/header.jsp"/>
    </div>
    <div class="allContent">
        <sitemesh:write property='body'></sitemesh:write>
    </div>
    <div class="container footer">
        <jsp:include page="../includes/footer.jsp"/>
    </div>
</body>
</html>
