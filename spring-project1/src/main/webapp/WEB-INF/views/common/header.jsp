<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    
    <%-- 
        link 태그에서 CSS 파일을 불러올 때 사용되는 경로입니다.
        ${pageContext.request.contextPath}는 현재 웹 애플리케이션의 루트 경로를 가져오는 표현입니다.
        예: http://localhost:8080/mycafe라면 contextPath는 "/mycafe"
        따라서 전체 경로는 "/mycafe/resource/css/common/header.css"가 됩니다.
    --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/common/header.css">
</head>
<body>


<div id="header">

    <div style="color: white; text-align: left; float: left; cursor: pointer;">
        CanesblackCafe
    </div>
</div>


</body>
</html>
