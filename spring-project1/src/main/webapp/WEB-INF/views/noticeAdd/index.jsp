<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카네스블랙카페</title>
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/noticeAdd/style.css"/>
</head>
<body>

<%@include file="/WEB-INF/views/common/header.jsp" %>
   


<!--javascript코드로 form태그의 action기능을 대신하는 기능을 만듬.일종의 rest api 기능 방식의 일부분--->
<form id="menuForm">
   <div id="container">
    <div id="menuAdmin">
      <h2 id="menuAdminH2">공지사항 작성</h2>


<!-- 
  사용자에게 로그인된 아이디를 보여주기 위한 input 필드입니다.
  value="${username}" : 서버(JSP)에서 전달받은 사용자 아이디를 입력창에 자동으로 채움
  disabled : 사용자가 이 필드를 수정하지 못하도록 비활성화 (폼 전송 시 서버로 값도 전송되지 않음)
-->
<label for="memID">회원아이디</label>
<input type="text" id="memID" name="memID" placeholder="회원아이디" maxlength="20" value="${username}" readonly>
 <br>
 <label for="title">제목</label>
 <input type="text" id="title" name="title" placeholder="제목" maxlength="10">
 <br>
 <label for="content">내용</label>
 <input type="text" id="content" name="content" placeholder="내용" maxlength="30" >
  <br>
  <label for="writer">작성자</label>
  <input type="text" id="writer" name="writer" placeholder="작성자" maxlength="10" value="${writer}" readonly>

  <br>


  <input type="hidden" id="indate" name="indate">


<!-- 
  확인 버튼입니다. 
  type="button" : 폼 제출 기능은 없고, JavaScript로 동작을 제어할 때 사용
  id="buttonSubmit" : 자바스크립트에서 이 버튼을 제어하기 위해 ID 지정
-->
  <button type="button" id="buttonSubmit">확인</button>
  </div>
</div>
 

</form>

<%@include file="/WEB-INF/views/common/footer.jsp" %>

<script src="${pageContext.request.contextPath}/resources/js/noticeAdd/script.js"></script>
</body>
</html>