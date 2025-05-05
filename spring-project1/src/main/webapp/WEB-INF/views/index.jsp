<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <title>카네스블랙 카페</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login/style.css"/>
</head>
<body>

<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div id="container">
    <div id="menuAdmin">
        <h2 id="menuAdminH2">공지사항!!</h2>

        <!-- 세션에 저장된 MANAGER 값이 true일 때만 버튼 표시 -->
        <c:if test="${MANAGER == true}">
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/noticeAddPage'">작성</button>
        </c:if>

        <div id="menuList">
            <!-- 여기에 공지사항 목록이나 다른 내용이 들어갈 수 있습니다. -->
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>

</body>
</html>
