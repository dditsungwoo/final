<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="kr">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Tables</title>

    <!-- Custom fonts for this template -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">


    <!-- Custom styles for this template -->
    <link href="/sbadmin2/css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="/sbadmin2/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<style>
/* Reset some default styles */
body, h1, h2, h3, p {
    margin: 0;
    padding: 0;
}

/* Basic styles for the body */
body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
  
}

/* Container styling */
.container {
    width: 100%;
    max-width: 800px;
    padding: 20px;
    box-sizing: border-box;
}

/* Card styling */
.card {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    padding: 20px;
    margin-bottom: 20px;
}

/* Card header styling */
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

/* Card body styling */
.card-body {
    line-height: 1.6;
}

/* Info styling */
.info {
    margin-bottom: 10px;
    color: #555;
}

/* Content styling */
.content {
	height:200px; 
    font-size: 16px;
	overflow-y: auto; 
}

/* Button styling */
.buttons {
    display: flex;
    gap: 10px;
}

.btn {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s;
}

.btn-edit {
    background-color: #4CAF50;
    color: #fff;
}

.btn-edit:hover {
    background-color: #45a049;
}

.btn-delete {
    background-color: #f44336;
    color: #fff;
}

.btn-delete:hover {
    background-color: #e53935;
}

.btn-submit {
    background-color: #2196F3;
    color: #fff;
}

.btn-submit:hover {
    background-color: #1976D2;
}

/* Comments section styling */
.comments-section {
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 20px;
}

/* Comments list styling */
.comments-list {
    margin-bottom: 20px;
}

.comment {
    border-bottom: 1px solid #eee;
    padding: 10px 0;
}

.comment-author {
    font-weight: bold;
    color: #333;
}

.comment-text {
    margin-top: 5px;
}

/* Comment form styling */
.comment-form {
    margin-top: 20px;
}

.comment-form h3 {
    margin-bottom: 10px;
}

.comment-form textarea {
    width: 100%;
    border-radius: 5px;
    border: 1px solid #ccc;
    padding: 10px;
    box-sizing: border-box;
}

.comment-form button {
    margin-top: 10px;
    width: 100%;
}
.buttons {
    display: flex;
    justify-content: flex-end; /* 오른쪽 정렬 */
    gap: 5px; /* 버튼 간의 간격 */
}

.btn-sm {
    font-size: 12px; /* 버튼 크기 조절 */
    padding: 5px 10px; /* 패딩 조절 */
}

.comments-list {
    max-height: 180px; /* 원하는 높이로 설정 */
    overflow-y: auto; /* 세로 스크롤 활성화 */
    margin-bottom: 20px; /* 아래 여백 추가 */
}
</style>
</head>
	
  <div class="container" >
  	
        <div class="card">
            <div class="card-header">
                <h1><c:out value="${boardVO.brdSubject}"/></h1>
	                <div class="buttons">	                	
			      		<a class="btn btn-primary" href="/board/coolList.do?searchKeyword=${searchKeyword }">목록</a>
		                <c:if test="${boardVO.brdWriter == memVO.memId }">   
			                    <a class="btn btn-edit" href="/board/update.do?brdNo=${boardVO.brdNo }">수정</a>
			                    <button class="btn btn-delete" id="bigDelete" >삭제</button>
		                </c:if>
	                </div>
            </div>
            <div class="card-body">
                <p class="info writer"><strong>작성자:</strong><c:out value="${boardVO.brdWriter}"/></p>
                <p class="info regDate"><strong>작성일:</strong> <c:out value="${boardVO.brdRegdate}"/></p>
                <p class="info hit"><strong>조회수:</strong> <c:out value="${boardVO.brdHit}"/></p>
                <c:if test="${not empty attFileVO }">
                	<c:forEach items="${attFileVO}" var="att" varStatus="item">
                	<p class="info"><strong>첨부파일:</strong>
                	   <c:choose>
                	   		<c:when test ="${not empty att.attFileBase64}">
		                	   <a href="#" class="modalClass" data-base64="${att.attFileBase64}">${att.attFileOriname}</a>               	                	   		
                	   		</c:when>
                	   		<c:otherwise>
                	   			<a href="#" class="modalClass" >${att.attFileOriname}</a>
                	   		</c:otherwise>
                	   </c:choose>
                	   <a class="fileDown" href="/board/fileDown.do?storedFileName=${att.attFileModname}&originalFileName=${att.attFileOriname}"><img alt="#" src="/images/egovframework/example/arrow-alt-circle-down.svg" style="height:20px; width:10%;"></a>
                	</p>
                	</c:forEach>
                </c:if> 
                <p/><hr/>
                <p class="content"><c:out value="${boardVO.brdContent}" escapeXml="false"/></p>
            </div>
        </div>
        <div class="comments-section" >
		<c:if test="${replyVO != null && !empty replyVO }" >
            <h2>댓글</h2>
            <div class="comments-list">
            <c:forEach items="${replyVO}" var="bean" varStatus="item">
                <!-- 댓글 예시 -->
                <div class="comment">
                    <p class="comment-author">${bean.repWriter }</p>                   
                    <p class="comment-text">${bean.repContent }</p>
                     <div class="buttons"  style="text-align: right;">		                
                     <c:if test="${bean.repWriter == memVO.memId }">                     
		                <button class="btn btn-edit btn-sm smallEdit" data-no='${bean.repNo}' >수정</button>
		                <button class="btn btn-delete btn-sm smallDelete" data-no='${bean.repNo}' data-pass='${bean.repPassword}'>삭제</button>
            		</c:if>
            		</div>
                </div>             
                <!-- 댓글 예시 끝 -->
             </c:forEach>   
            </div>
			</c:if>
            <div class="comment-form">
                <h3>댓글 작성</h3>
               		<input type="hidden" name="repWriter" id="repWriter" value="${memVO.memId}" />
               		<input type="hidden" name="brdNo" id="brdNo" value="${boardVO.brdNo }" />
                    <textarea name="repContent" id="repContent" rows="1" placeholder="댓글을 입력하세요..." required="required"></textarea>
                    <input type="password" name="repPassword" id="repPassword"  placeholder="비밀번호 입력하세요..." required="required"/>
                    <button type="button" class="btn btn-submit">댓글 작성</button>
           
            </div>
        </div>
    </div>
    
    <!-- 이미지  modal  -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-dialog" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <h5 class="modal-title" id="myModalLabel">사진 보기</h5>
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                    <span aria-hidden="true">&times;</span>
		                </button>
		            </div>
		            <div class="modal-body">
		            	여기 img자리		               
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
		            </div>
		        </div>
		    </div>
		</div>
    
    
    
<body>

	<!-- Bootstrap core JavaScript-->
    <script src="/sbadmin2/vendor/jquery/jquery.min.js"></script>
    <script src="/sbadmin2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="/sbadmin2/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="/sbadmin2/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="/sbadmin2/vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="/sbadmin2/vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="/sbadmin2/js/demo/datatables-demo.js"></script>

 <script>
   
 $("#bigDelete").on("click",function(){
	 let pass="${boardVO.brdPassword}";
	 
	showPrompt(pass); 
 });  	
 
 function showPrompt(pass) {
     // Prompt 사용자로부터 입력 받기
     var userInput = prompt("삭제 비밀번호 입력하세요:");
     
     // 입력값이 null이 아닌 경우 (사용자가 취소하지 않았을 때)
     if (userInput == pass ) {        
         location.href="/board/delete.do?brdNo=${boardVO.brdNo}";
         
     } else {
         alert("비밀번호가 맞지 않습니다");
     }
 }
 

 
 
 $(document).on("click",".smallDelete",function(){
	 event.preventDefault();
	 let repNo= $(this).data("no");
	 let brdNo= "${boardVO.brdNo }";
     let pass = $(this).data("pass");

     var userInput = prompt("삭제 비밀번호 입력하세요:");
     
     // 입력값이 null이 아닌 경우 (사용자가 취소하지 않았을 때)
     if (userInput == pass ) {        
        
     } else{
         alert("비밀번호가 맞지 않습니다");
         return;
     }
	 let data= {
			 "repNo":repNo,
			 "brdNo":brdNo
	 };
	 console.log(data);
	  
	 $.ajax({
	     type: "post",
	     contentType: "application/json;",
	     url: "/reply/replyDelete.do",
	     data: JSON.stringify(data),	    
	     beforeSend: function(xhr) {
	         xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	     },
	     success: function(res){
	     	console.log("성공");		    
	     	console.log(res);
	     	$(".comments-list").html("");

	        let str = ''; // 초기화
	        $.each(res, function(idx, vo) {
		           str += "<div class='comment'>"
		           str +=  "<p class='comment-author'>"+vo.repWriter+"</p>"
		           str +=  "<p class='comment-text'>"+vo.repContent+"</p>"							                        
		           if (vo.repWriter === "${memVO.memId}") {
			           str +=  "<div class='buttons' style='text-align: right;'>"
			           str +=  "<button class='btn btn-edit btn-sm smallEdit' data-no='"+vo.repNo+"'>수정</button>"
			           str +=  "<button class='btn btn-delete btn-sm smallDelete' data-no='"+vo.repNo+"' data-pass='"+vo.repPassword+"'>삭제</button>"
			           str +=  "</div>"
		           }
		           str+= '</div>';
		        });

	        $(".comments-list").html(str); // str을 comments-list에 설정
			$("#repContent").val("");
			$("#repPassword").val("");
			
			alert("삭제 완료 했습니다!");
	     	},
	     error:function(xhr){
	    	   console.log("Error occurred:", status, error);
	    	   console.log("Response:", xhr.responseText);
		     }
 	});  	
 });
 
  
 
 
 $(document).on("click",".btn-submit",function(){
 //$(".btn-submit").on("click",function(){
	 event.preventDefault();
	 let repWriter =$("#repWriter").val();
	 let repContent =$("#repContent").val();
	 let repPassword =$("#repPassword").val();
	 let brdNo= $("#brdNo").val();
	 
	    let maxContentLength = 100; 
	    let minContentLength = 2; 
	    
	    let maxPasswordLength = 10;  
	    let minPasswordLength = 1;   

	    // 유효성 검사 - 내용 길이 확인
	    if (repContent.length < minContentLength) {
	        alert("내용은 최소 " + minContentLength + "자 이상 입력해야 합니다.");
	        return;
	    }
	    if (repContent.length > maxContentLength) {
	        alert("내용은 최대 " + maxContentLength + "자까지 입력할 수 있습니다.");
	        return;
	    }

	    // 유효성 검사 - 비밀번호 길이 확인
	    if (repPassword.length < minPasswordLength) {
	        alert("비밀번호는 최소 " + minPasswordLength + "자 이상 입력해야 합니다.");
	        return;
	    }
	    if (repPassword.length > maxPasswordLength) {
	        alert("비밀번호는 최대 " + maxPasswordLength + "자까지 입력할 수 있습니다.");
	        return;
	    }
	 let data={
		 "repWriter":repWriter,
		 "repContent":repContent,
		 "repPassword":repPassword,		 
		 "brdNo":brdNo
	 };
	
	 $.ajax({
	     url: "/reply/insert.do",
	     data: JSON.stringify(data),	    
	     contentType: "application/json;",
	     type: "post",
	     aysnc:false,
	     beforeSend: function(xhr) {
	         xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	     },
	     success: function(res){
	    	 if(res!=null){
	    		 location.reload();
	    		 
	    		 alert("댓글 생성 완료!");
	    	 }else{
	    		 alert("댓글 작성 오류!");
	    	 }
	  
	     	},
	     error:function(xhr){
	     	console.log(xhr.status);	
	     	console.log(xhr.responseText);	
	     }
	     
	}); 
});// 버튼 누른후 insert 끝 
 
$(document).on("click",".smallEdit",function(){ 
	
    let $commentText = $(this).closest('.comment').find('.comment-text');
    let $commentContent = $(this).closest('.comment').find('.comment-text').text();
    let $repNo= $(this).data("no");
 	$commentText.text('');
 	$(this).closest('.buttons').css("display","none");
 	$commentText.append( `<input type="text" class="edit-input" value="\${$commentContent}"/>
				 			<div class='buttons' style='text-align: right;'>
					            <button class='btn btn-edit btn-sm updateBtn' data-no='\${$repNo}'>수정 완료</button>
					            <button class='btn btn-edit btn-sm reset'>돌아가기</button>
        					</div>`);
});

// 돌아가기버튼
$(document).on("click",".reset",function(){ 
	event.preventDefault();
	 let brdNo= "${boardVO.brdNo }";
		let data={				
				"brdNo":brdNo
		};
		
	 $.ajax({
	     type: "post",
	     contentType: "application/json;",
	     data: JSON.stringify(data),	    
	     url: "/reply/list.do",
	     beforeSend: function(xhr) {
	         xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	     },
	     success: function(res){
	     	console.log("성공");		    
	     	console.log(res);
	     	$(".comments-list").html("");

	        let str = ''; // 초기화
	        $.each(res, function(idx, vo) {
		           str += "<div class='comment'>"
		           str +=  "<p class='comment-author'>"+vo.repWriter+"</p>"
		           str +=  "<p class='comment-text'>"+vo.repContent+"</p>"							                        
		           if (vo.repWriter === "${memVO.memId}") {
			           str +=  "<div class='buttons' style='text-align: right;'>"
			           str +=  "<button class='btn btn-edit btn-sm smallEdit' data-no='"+vo.repNo+"'>수정</button>"
			           str +=  "<button class='btn btn-delete btn-sm smallDelete' data-no='"+vo.repNo+"' data-pass='"+vo.repPassword+"'>삭제</button>"
			           str +=  "</div>"
		           }
		           str+= '</div>';
		        });

	        $(".comments-list").html(str); // str을 comments-list에 설정
	       	$("#repContent").val("");
			$("#repPassword").val("");

			
			
	     	},
	     error:function(xhr){
	    	   
	    	   console.log("Response:", xhr.responseText);
	    	   console.log("Response:", xhr.status);
		     }
 	}); 
	
});	


//수정완료버튼
$(document).on("click",".updateBtn",function(){ 
	event.preventDefault();
 	let repContent=$(".edit-input").val();
 	let brdNo= "${boardVO.brdNo }";
 	let repNo= $(this).data("no");
 	
    let maxContentLength = 100; 
    let minContentLength = 2; 
    
    let maxPasswordLength = 10;  
    let minPasswordLength = 1;   

    // 유효성 검사 - 내용 길이 확인
    if (repContent.length < minContentLength) {
        alert("내용은 최소 " + minContentLength + "자 이상 입력해야 합니다.");
        return;
    }
    if (repContent.length > maxContentLength) {
        alert("내용은 최대 " + maxContentLength + "자까지 입력할 수 있습니다.");
        return;
    }

 	
	let data={
			"repNo":repNo,
			"repContent":repContent,
			"brdNo":brdNo
	};
	 $.ajax({
	     type: "post",
	     contentType: "application/json;",
	     url: "/reply/replyEdit.do",
	     data: JSON.stringify(data),	    
	     beforeSend: function(xhr) {
	         xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	     },
	     success: function(res){
	     	console.log("성공");		    
	     	console.log(res);
	     	$(".comments-list").html("");

	        let str = ''; // 초기화
	        $.each(res, function(idx, vo) {
		           str += "<div class='comment'>"
		           str +=  "<p class='comment-author'>"+vo.repWriter+"</p>"
		           str +=  "<p class='comment-text'>"+vo.repContent+"</p>"							                        
		           if (vo.repWriter === "${memVO.memId}") {
			           str +=  "<div class='buttons' style='text-align: right;'>"
			           str +=  "<button class='btn btn-edit btn-sm smallEdit' data-no='"+vo.repNo+"'>수정</button>"
			           str +=  "<button class='btn btn-delete btn-sm smallDelete' data-no='"+vo.repNo+"' data-pass='"+vo.repPassword+"'>삭제</button>"
			           str +=  "</div>"
		           }
		           str+= '</div>';
		        });

	        $(".comments-list").html(str); // str을 comments-list에 설정
			$("#repContent").val("");
			$("#repPassword").val("");
			alert("댓글 수정 완료!");
	     	},
	     error:function(xhr){
	    
	    	   console.log("Response:", xhr.responseText);
		     }
 	});  	
	
});	


$(()=>{
	
	
	let updateResult= "${update}"; 
	if(updateResult !=null && updateResult!=''){
		alert("수정성공");
	}
	
	 $(document).on("click", ".modalClass", function() {
		 	  let base64= $(this).data("base64");
		 	 if (base64 == null || base64 == '') {
		 		  alert ("이미지 파일만 미리보기 가능합니다");
		 		  return;
		 	  }
		 	  $(".modal-body").html("");
			  $('#myModal').modal('show');
			  let str= "<img src='data:image/jpeg;base64,"+base64+"'/>";
			  $(".modal-body").html(str);
		});
		
	
	
});



</script>


</body>

</html>