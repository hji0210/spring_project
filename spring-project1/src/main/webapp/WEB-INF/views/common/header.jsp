<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    
    <%-- 
        link 태그에서 CSS 파일을 불러올 때 사용되는 경로입니다.
        ${pageContext.request.contextPath}는 현재 웹 애플리케이션의 루트 경로를 가져오는 표현입니다.
        예: http://localhost:8080/mycafe 라면 contextPath는 "/mycafe"
        따라서 전체 경로는 "/mycafe/resource/css/common/header.css"가 됩니다.
    --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/common/header.css">
</head>
<body>

<div id="header">

    <%-- 좌측 상단 로고 또는 카페 이름 표시 영역 --%>
    <div style="color: white; text-align: left; float: left; cursor: pointer;"
    onclick="location.href='${pageContext.request.contextPath}/'">
    CanesblackCafe
</div>


    <%-- 로그인 여부에 따라 로그아웃 또는 로그인 버튼을 표시 --%>
    <c:choose>
        <%-- 로그인 된 경우 (세션에 isAuthenticated 값이 true일 때) --%>
        <c:when test="${isAuthenticated != null && isAuthenticated == true}">
            <div style="float:right">
                <%-- 로그아웃 링크 --%>
                          <!---localhost:8080/logout-->
                          <a href="${pageContext.request.contextPath}/logout" style="color: white; margin-right: 15px; text-decoration: none; font-size: 15px;">
                            로그아웃
                        </a>
                        
            </div>
        </c:when>
        
        <%-- 로그인되지 않은 경우 --%>
        <c:otherwise>
            <div style="float:right">
                <%-- 로그인 페이지 이동 링크 --%>
                <!---localhost:8080/login-->
             
                <a href="${pageContext.request.contextPath}/loginPage" style="color: white; margin-right: 15px; text-decoration: none; font-size: 15px;">
                    로그인
                </a>
            
            </div>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>
