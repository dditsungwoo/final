<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>    
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>새로운 게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link href="https://startbootstrap.com/sb-admin-2/css/sb-admin-2.min.css" rel="stylesheet">
    <style>
        .table-hover tbody tr:hover {
            background-color: #f1f3f6;
        }
        .table thead {
            background-color: #4e73df;
            color: white;
        }
        .table-striped tbody tr:nth-of-type(odd) {
            background-color: #f8f9fc;
        }
        .btn-primary, .btn-info, .btn-danger {
            margin-left: 5px;
        }
        .search-bar {
            width: 50%;
        }
        .btn-custom {
            background-color: #1cc88a;
            color: white;
        }
        .btn-custom:hover {
            background-color: #17a673;
        }
        .pagination-search {
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .pageSearch {
            width: 130px; /* 더 작게 조정 */
            margin-left: 10px; /* 페이지네이션과 검색창 간격 조정 */
          	left: 850px;
   			 top: -10px;
            
        }
        .pagination-search .input-group input {
            height: 40px; /* 입력 필드의 높이 축소 */
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <!-- 상단 검색 및 필터 -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="font-weight-bold text-primary">게시판</h2>
        <div class="input-group search-bar">
            <input type="text" class="form-control" id="searchKeyword" value=""  placeholder="검색어를 입력하세요">
            <button class="btn btn-primary searchBtn" type="button">검색</button>
        </div>
        <a href="/board/write.do" class="btn btn-custom">새 글 작성하기</a>
		<form class="nav-link logoutBtn" action="/login/logout.do"
			method="post">
			<button type="button" class="btn btn-secondary">로그아웃</button>
			<sec:csrfInput />
		</form>
		
       
    </div>

    <!-- 게시판 테이블 -->
    <div class="card shadow mb-4">
        <div class="card-header py-3 d-flex justify-content-between">
            <h6 class="m-0 font-weight-bold totalBoard">게시글 갯수 : </h6>
            <select class="form-select w-auto" id="perPageSelect" aria-label="게시글 표시 갯수">
                <option value="10" selected>10개씩 </option>
                <option value="20">20개씩</option>
                <option value="30">30개씩</option>
                <option value="50">50개씩</option>
            </select>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-striped table-hover" width="100%" cellspacing="0">
                    <thead>
							<tr>
								<th style="width: 5%;">순번</th>
								<th style="width: 45%;">제목</th>
								<th style="width: 12%;">작성자</th>
								<th style="width: 8%;">작성일</th>
								<th style="width: 10%;">아이피</th>
								<th style="width: 8%;">조회수</th>
								<th style="width: 8%;">첨부파일</th>
							</tr>
					</thead>
                    <tbody id="boardTable">                     	
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- 페이징 처리 및 검색 -->
    <div class="pagination-search">
        <nav aria-label="Page navigation example" class="pagingExample">
           
        </nav>
    </div>
        <div class="input-group pageSearch">
            <input type="text" class="form-control pageSearchBox" placeholder="페이지 " oninput="this.value=this.value.replace(/[^0-9]/g,'');">
            <button class="btn btn-primary pageSearchBtn" type="button">이동</button>
        </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://startbootstrap.com/sb-admin-2/js/sb-admin-2.min.js"></script>
	<script src="/sbadmin2/vendor/jquery/jquery.min.js"></script>
	<script src="/sbadmin2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="/sbadmin2/vendor/jquery-easing/jquery.easing.min.js"></script>
<script>



$(()=>{
	let perPage = $("#perPageSelect").val();
	let searchKeyword =$("#searchKeyword").val();
    let currentPage = 1; 
	let submitResult= "${submit}"; 
	let deleteResult= "${delete}";
	let message = "${message}";
	
	//리스트 처음 호출 
	
    secondList(currentPage, perPage, searchKeyword);


    if (submitResult != null && submitResult != '') {
        alert("등록 성공");
    }
    if (deleteResult != null && deleteResult != '') {
        alert("삭제 성공");
    }
    if (message != null && message != '') {
        alert("내용 없음");
    }

	
	
// 페이지 숫자 누르면 그 페이지로 	
$(document).on("click",".page-item .page-num",function(){
	event.stopPropagation();
	let currentPage=parseInt($(this).text());
	secondList(currentPage, perPage, searchKeyword);
});	

//한 페이지에 게시물 갯수 정하기
$("#perPageSelect").on("change",function(){
	perPage =$(this).val();
	console.log("perpage",perPage);
	currentPage = 1;
	secondList(currentPage,perPage,searchKeyword);	
})

//상세페이지 들어가기
$(document).on("click",".viewBrd",function(){	
	
	let param= $(this).data("brdno");
	console.log("param"+param);
	location.href="/board/view.do?brdNo="+param;
}); 

//검색버튼
$(".searchBtn").on("click",function(){
	searchKeyword =$("#searchKeyword").val();
	console.log("searchKeyword"+searchKeyword);
	secondList(1,perPage,searchKeyword);	
});

//로그아웃 버튼
$(".logoutBtn button").on("click",function(){
	var result = confirm("정말 로그아웃 하시겠습니까?");
	
    if (result) {       
        alert("로그아웃 완료됐습니다");       
        $(".logoutBtn").submit() ;  
    } else {     
        alert("로그아웃이 취소되었습니다.");
    }	
});	

//페이지 바로가기 버튼
$(".pageSearchBtn").on("click",function(){
	let pageSearchBox= $(".pageSearchBox").val();
	let maxPage = 0;
    $(".page-link").each(function() {
        let currentPage = parseInt($(this).text().trim()); 
        if (!isNaN(currentPage) && currentPage > maxPage) {
            maxPage = currentPage;
        }
    }); 
	
	if(pageSearchBox> maxPage){
		alert("페이지검색: 현재 최대 "+maxPage+"페이지 까지 가능합니다");
		return;
	}
	secondList(pageSearchBox,perPage,searchKeyword);	
	$(".pageSearchBox").val("");	
	
});

//다음버튼입니다
$(document).on("click",".page-item .next", function(){
	event.stopPropagation();
	let minPage =Infinity; 
	$(".page-link").each(function() {
	    let pageNum = parseInt($(this).text().trim());
	    if (!isNaN(pageNum) && pageNum < minPage) {
	        minPage = pageNum;
	    }
	});	
	currentPage=minPage+10;
	secondList(currentPage, perPage, searchKeyword);	    
});




//이전버튼 10단위로 바뀝니다
$(document).on("click",".page-item .prev", function(){
	event.stopPropagation();
	let minPage =Infinity; 
	$(".page-link").each(function() {
	    let pageNum = parseInt($(this).text().trim());
	    if (!isNaN(pageNum) && pageNum < minPage) {
	        minPage = pageNum;
	    }
	});
	currentPage=minPage-10;
	secondList(currentPage, perPage, searchKeyword);	      
});


}); //$(()=>{}) 끝



//리스트 보여주기 (현재페이지, 한페이지 게시물 겟수 ,검색어 )
function secondList(currentPage,perPage,searchKeyword){

	perPage =perPage;
	searchKeyword= searchKeyword;	
	let data ={
			"searchKeyword":searchKeyword
	};
	
	$.ajax({
	    type: "post",
	    contentType: "application/json;",
	    url: "/board/secondList.do",
	    data: JSON.stringify(data),	    
	    beforeSend: function(xhr) {
	        xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	    },
	    success: function(res){
	        $("#boardTable").html(''); 
	        let str = '';
	        currentPage= currentPage;
	        let itemsPerPage = perPage; 
	        let length = res.length; 
			//console.log(length);
	        $(".totalBoard").html("게시글 갯수 : "+length);	      
	        let pagedData = res.sort((a, b) => b.brdNo - a.brdNo).slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

	        pagedData.forEach(function(vo, idx) {
	            let brdNo = vo.brdNo; 
	            let brdSubject = vo.brdSubject; 
	            let brdRegdate = vo.brdRegdate; 
	            let brdWriter = vo.brdWriter; 
	            let brdRegip = vo.brdRegip;
	            let brdHit = vo.brdHit;
	            let attNum = vo.attNum;

	            str += "<tr class='viewBrd' data-brdno='"+brdNo+"'>";
	            str += "<td><p>" + (idx + 1) + "</p></td>"; 
	            str += "<td><p>" + brdSubject + "</p></td>";
	            str += "<td><p>" + brdWriter + "</p></td>";
	            str += "<td><p>" + brdRegdate + "</p></td>";
	            str += "<td><p>" + brdRegip + "</p></td>";
	            str += "<td><p>" + brdHit + "</p></td>";
	            str += "<td><p>" + attNum + "개</p></td>";  
	            str += "</tr>";
	        });
	     	if(res == null || res.length === 0){
	     		str = "<tr><td colspan='7'>게시글이 없습니다.</td></tr>";
	     	}
	        $("#boardTable").html(str);
	        
	        //페이징 시작 
	        let startPage = Math.floor((currentPage - 1) / 10) * 10 + 1;
	        $(".pagingExample").html("");
	   			let pag="";
	    		pag+="<ul class='pagination justify-content-center'>"
	    			pag+=" <li class='page-item'>";
	    			pag+="<a class='page-link prev' href='#' tabindex='-1' aria-disabled='true'>이전</a></li>";
	    		 
	    			let maxPageLinks = 10; 
	    			let totalPages = Math.ceil(length / itemsPerPage); 
	    			let pageLinks = Math.min(maxPageLinks, totalPages - startPage + 1);

	    			if (length / itemsPerPage <= 10) { 
	    			    // 페이지가 10 이하일 경우
	    			    for (let i = 1; i <= totalPages; i++) {
	    			        pag += "<li class='page-item" + (i === currentPage ? " active" : "") + "'><a class='page-link page-num' href='#'>" + i + "</a></li>";
	    			    }
	    			} else {
	    			    // 페이지가 10 초과일 경우
	    			    for (let i = startPage; i < startPage + pageLinks; i++) {
	    			        pag += "<li class='page-item" + (i === currentPage ? " active" : "") + "'><a class='page-link page-num' href='#'>" + i + "</a></li>";
	    			    }

	    			    if (totalPages >= startPage + maxPageLinks) {
	    			        pag += "<li class='page-item disabled'><a class='page-link' href='#'>...</a></li>";
	    			        pag += "<li class='page-item'><a class='page-link page-num' href='#'>" + totalPages + "</a></li>";
	    			    }
	    			}


	    		 pag+=" <li class='page-item'>";
	    		 pag+=" <a class='page-link next' href='#'>다음</a>";
	    		 pag+="  </li></ul>";
	        
	        $(".pagingExample").html(pag);
	        
	        
	    	if(currentPage<10+1){ // 이전버튼 비활성화
	    		$(".page-item .prev").addClass("disabled");
	    	}
	    	
	  	    	
	    	if (pageLinks> totalPages-  startPage  ) {  //다음버튼 비활성화
	    	    $(".page-item .next").addClass("disabled");
	    	}
	    	
	        
	        $(".page-item").filter(function() { // 현재 페이지 강조
			    return $('.page-link',this).text().trim() == currentPage;
			}).addClass("active");
	        
	    	},
	    error:function(xhr){	   
	   	   console.log("Response:", xhr.responseText);	   	  
		     }
	});

}	





</script>


</body>
</html>

