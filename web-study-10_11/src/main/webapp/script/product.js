function productCheck() {
	
/*	// 가격값에서 쉼표 제거*/
	var priceInput = document.frm.price;
	priceInput.value = priceInput.value.replace(/,/g, '');

	// 정수 전용: 입력값에 소수점 있으면 잘라냄 (맨앞 숫자만 남김)
	  if (priceInput.value.indexOf('.') !== -1) {
	     priceInput.value = priceInput.value.split('.')[0];
	  }
	
	   if (document.frm.name.value.length == 0) {
      alert("상품명을 써주세요.");
      frm.name.focus();
      return false;
   }
   if (document.frm.price.value.length == 0) {
      alert("가격을 써주세요");
      /**///frm.price.focus();
	  priceInput.focus();
	  return false;
   }
   if (isNaN(document.frm.price.value)) {
      alert("숫자를 입력해야 합니다");
	  priceInput.focus();
/*      frm.price.focus();*/
      return false;
   }
   return true;
}