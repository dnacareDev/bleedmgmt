window.addEventListener("load",function(){
	var n=0; //클릭된 인덱스
	
	var btn_view = document.getElementsByClassName("btn-group-normal")[0]; 
	var btn = btn_view.children; //왼쪽 상단 버튼 리스트
	
	
	var content_view = document.getElementsByClassName("table-responsive")[0];
	var content_table = content_view.children; // 테이블 컨텐츠 리스트
	
	btn[n].classList.add("btn-active");
	content_table[n].classList.add("table_active");
	
	for(let i=0; i<btn.length; i++){
		btn[i].idx=i;
		
		btn[i].addEventListener("click", function(e){
			n = e.currentTarget.idx;
			for(let k=0; k<btn.length; k++){
				btn[k].classList.remove("btn-active");
				content_table[k].classList.remove("table_active");
			}
			
			btn[n].classList.add("btn-active");
			content_table[n].classList.add("table_active");
		});
	}
});