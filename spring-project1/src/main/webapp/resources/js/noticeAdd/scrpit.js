document.getElementById("buttonSubmit").addEventListener("click", function() {
  const formData = {
    memeID: document.getElementById("memeID").value,
    title: document.getElementById("title").value,
    content: document.getElementById("content").value,
    writer: document.getElementById("writer").value,
    indate: new Date().toISOString().slice("T")[0], // 현재 날짜와 시간
    //ID가 없을 때 글자 입력한 값이  value
  }

});



//index.jsp파일에서 만든 메타 CSRF태그 두개를 js파일로 가져온다.
//<meta name="_csrf" content="${_csrf.token}">
//<meta name="_csrf_header" content="${_csrf.headerName}"></meta>
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");