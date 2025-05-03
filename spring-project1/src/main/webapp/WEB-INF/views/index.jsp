<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<%@include file="/WEB-INF/views/common/header.jsp" %>

<div id="container">
    <div id="menuAdmin">
        <h2 id="menuAdminH2">공지사항!!</h2>

      <!---특별한 기능(jstl이라는 라이브러리를 이용해서, 세션에 있는 변수를 셋팅 조건을 겁니다.)-->
      <!---세션공간에 저장되어있는 "MANAGER의 값이 true일 때 작성이라는 버튼이 보이게끔 할 것임 ".)-->
      <c:if test="${MANAGER == true}">
        <button type="button" onclick="location.href='${pageContext.request.contextPath}/noticeAddPage'">작성</button>
    </c:if>
    
     
      <!--location.href=localhost:8080/noticeAdd---> 





        <div id="menuList">
        </div>
    </div>
</div> 

<%@include file="/WEB-INF/views/common/footer.jsp" %>
<%@include file="/WEB-INF/views/common/footer.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script>

</body>
</html>
