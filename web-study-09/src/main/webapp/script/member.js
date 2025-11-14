function loginCheck(){
	// 1. 아이디 입력란이 비어있는지 확인
	if(document.frm.userid.value.length == 0){
		alert("아이디를 입력해주세요.");		// 경고창 표시
		frm.userid.focus();				// 아이디 입력란에 커서 이동
		return false;					// submit 진행 막기
	}
	
	// 2. 비밀번호 입력란이 비어있는지 확인
	if(document.frm.pwd.value ==""){
		alert("비밀번호를 입력해주세요.");		// 경고창 표시
		frm.pwd.focus();				// 비밀번호 입력란에 커서 이동
		return false;					// submit 진행 막기
	}
	// 3. 아이디/비번 모두 입력되었으면 true 반환 → submit 진행
	return true;
	
}

function idCheck(){
	if(document.frm.userid.value == ""){
		alert("아이디를 입력해주세요.");		// 경고창 표시
		frm.userid.focus();				// 아이디 입력란에 커서 이동
		return;							// submit 진행 막기
	}
	
	//idCheck.do?userid='somi'
	let url="idCheck.do?userid=" +document.frm.userid.value;
	
	window.open(url, "_blank_1", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=450, height=200");
}

/*
window.open(
    url,               // 새 창(또는 탭)에서 열 URL
    "_blank_1",        // 창 이름. "_blank"와 달리 특정 이름을 주면 동일 이름의 창 재사용 가능
    "toolbar=no,       // 브라우저 기본 툴바 표시 여부 (no: 숨김)
     menubar=no,       // 메뉴바 표시 여부 (no: 숨김)
     scrollbars=yes,   // 스크롤바 표시 여부 (yes: 표시)
     resizeable=no,    // 창 크기 조절 가능 여부 (no: 불가능) -> 오타: 'resizable'이 정상 표기
     width=450,        // 창 너비(px) -> 오타: 'width'가 정상 표기
     height=200"       // 창 높이(px)
);
*/

function idok(){
	opener.frm.userid.value = document.frm.userid.value;
	opener.frm.reid.value = document.frm.userid.value;
	self.close();
}

function joinCheck() {
   if (document.frm.name.value.length == 0) {
      alert("이름을 써주세요.");
      frm.name.focus();
      return false;
   }
   if (document.frm.userid.value.length == 0) {
      alert("아이디를 써주세요");
      frm.userid.focus();
      return false;
   }
   if (document.frm.userid.value.length < 3) {
      alert("아이디는 3글자 이상이어야 합니다.");
      frm.userid.focus();
      return false;
   }
   if (document.frm.pwd.value == "") {
      alert("암호는 반드시 입력해야 합니다.");
      frm.pwd.focus();
      return false;
   }
   if (document.frm.pwd.value != document.frm.pwd_check.value) {
      alert("암호가 일치하지 않습니다.");
      frm.pwd.focus();
      return false;
   }
   if (document.frm.reid.value.length == 0) {
      alert("중복 체크를 하지 않았습니다.");
      frm.userid.focus();
      return false;
   }
   return true;
}



