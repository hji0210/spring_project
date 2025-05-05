document.getElementById("buttonSubmit").addEventListener("click", function() {

  // 객체
  const formData = {
    memID: document.getElementById("memID").value,  
    title: document.getElementById("title").value,
    content: document.getElementById("content").value,
    writer: document.getElementById("writer").value,
    // ID가 없을 때 글자 입력한 값이 value
    // count는 default로 0으로 설정되서 따로 0으로 설정할 필요가 없음
  }



  // index.jsp 파일에서 만든 메타 CSRF 태그 두 개를 js 파일로 가져온다.
  const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");

  const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

  // form 태그만으로 처리하면 새로고침이 발생하니까, 
  // fetch()는 새로고침 없이 깔끔하게 데이터를 전송하는 비동기 방식
  // Validate formData fields
  if (!formData.memID || !formData.title || !formData.content || !formData.writer) {
    alert("모든 필드를 올바르게 입력해주세요."); // Alert user to fill all fields
    return;
  }

  fetch("/menu/add", {
    method: "POST",
    headers: {
      'Content-Type': 'application/json',
      [csrfHeader]: csrfToken  // CSRF헤더와 토큰을 동적으로 추가
    },
    body: JSON.stringify(formData) // formData를 JSON 문자열로 변환하여 전송
  }).then(response => {
    if (!response.ok) {
      throw new Error("공지사항 작성 실패.");
    }
    return response.text(); 
  }).then(_=> {
    console.log("Success");
    alert("공지사항이 성공적으로 작성되었습니다."); // 성공 메시지 표시
    window.location.href = "/"; // 페이지 이동: 작성 완료 후 리다이렉트
  }).catch(error => {
    console.error("An error occurred:", error);
  })
});
