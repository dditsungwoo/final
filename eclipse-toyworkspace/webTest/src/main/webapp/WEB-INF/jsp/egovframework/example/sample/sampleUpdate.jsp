<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">

<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<title>SB Admin 2 - Register</title>

<!-- Custom fonts for this template-->
<link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="/css/sb-admin-2.min.css" rel="stylesheet">

<style>
.form-control-large {
	height: 300px;
	resize: vertical;
	box-sizing: content-box;
}

#brdContent {
	height: 250px;
	border-radius: 25px;
}

#preview {
	width: 270px;
	height: 246px;
	padding: 3px;
	position: relative;
	top: 30%;
	left: 8%;
}
</style>

</head>


<body class="bg-gradient-primary">

	<div class="container">
		
		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="row">
					<div class="col-lg-5 d-none d-lg-block bg-register-image">
						<img src="/images/egovframework/example/file.svg" id="preview" alt="이미지 파일의 미리보기입니다. "  />
					</div>
					<div class="col-lg-7">
						<div class="p-5">
							<div class="text-center">
								<h1 class="h4 text-gray-900 mb-4">글  수정</h1>
							</div>
							<form class="user" action="/board/updateSubmit.do" method="post" enctype="multipart/form-data">

								<div class="form-group">
									<input type="text" class="form-control form-control-user"
										id="brdWriter" name="brdWriter" placeholder="아이디 입력" value="${memVO.memName }" required readonly>
								</div>
								<div class="form-group">
									<input type="text" class="form-control form-control-user"
										id="brdSubject" name="brdSubject" value="${boardVO.brdSubject }" required>
								</div>
								<br/>
									<input type="hidden" value="${boardVO.brdNo }" name="brdNo"/>
								
								<div class="form-group form-control-large mb-3 h2">
									<textarea  class="form-control form-control-user"
										id="brdContent" name="brdContent" required>${boardVO.brdContent }</textarea>
								</div>
								
								<div class="form-group fileForm">
									<c:choose>
										<c:when test="${not empty attFileVO}">
											<c:forEach var="attFile" items="${attFileVO}">
												<p class="info">
													<strong>파일명 :</strong> <c:choose>
													    <c:when test="${not empty attFile.attFileBase64}">
													        <a class="viewImg" data-url="${attFile.attFileBase64}">
													            ${attFile.attFileOriname}
													        </a>
													    </c:when>
													    <c:otherwise>
													        <a class="viewImg">
													            ${attFile.attFileOriname}
													        </a>
													    </c:otherwise>
													</c:choose>&nbsp;
													<span class="btn btn-sm btn-warning delYn" data-del="${attFile.attFileNo}">X</span>
													<input type="hidden" class="attYes" name="attYes" value="${attFile.attFileNo}">
												</p>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<p>글 안에 수정할 파일이 없습니다.</p>
										</c:otherwise>
									</c:choose>

									<!-- 파일 입력 필드 -->
									<c:set var="uploadedFilesCount"
										value="${not empty attFileVO ? attFileVO.size() : 0}" />

									<c:forEach var="i" begin="${uploadedFilesCount}" end="4">
										<div class="form-group">
											<input type="file"
												class="form-control form-control-user uploadFile"
												name="uploadFile">
										</div>
									</c:forEach>
								</div>
								
								<input type ="hidden" name= "attDel" id="attDel"/>			
								<button type="submit" class="btn btn-primary btn-user btn-block"
									id="submitBtn">글 수정</button>
								
								<a  href="/board/view.do?brdNo=${boardVO.brdNo }" class="btn btn-secondary btn-user btn-block"
								 >상세페이지로</a>
								<sec:csrfInput />
							</form>
							<hr>
						
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<!-- Bootstrap core JavaScript-->
	<script src="/vendor/jquery/jquery.min.js"></script>
	<script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="/js/sb-admin-2.min.js"></script>

	<!--  다음 주소  -->
	<script
		src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</body>

<script type="text/javascript">

$(()=>{
	
	$(document).on("change", ".uploadFile",function(event) {
	    const fileInput = $(this)[0]; 
	    const maxSize = 4 * 1024 * 1024;
	    const allowedExtensions = /(\.txt|\.jpg|\.jpeg|\.png|\.gif|\.xlsx|\.xls|\.hwp|\.zip|\.pdf)$/i; // 허용되는 확장자 정규식
	    let totalSize = 0;
	    const files = fileInput.files;

	    for (let i = 0; i < files.length; i++) {
	        totalSize += files[i].size; // 각 파일의 크기를 합산
	        if (!allowedExtensions.exec(files[i].name)) {
	            alert("허용되지 않는 파일 형식입니다. TXT, IMG, 엑셀(xlsx, xls), HWP, ZIP, PDF 파일만 업로드할 수 있습니다.");
	            $(this).val(''); // 입력값 초기화
	            return; 
	        }
	    }

	    
	 
	    if (totalSize > maxSize) {
	        alert("업로드할 파일의 총 크기는 5MB를 초과할 수 없습니다.");
	        $(this).val(''); // 입력값 초기화
	        return; 
	    } else {
	        alert("파일 크기가 적절합니다.");
	    }
	});	
	
	
	$("#brdSubject").on("blur", function() {
	    var args = $(this).val();
	    console.log(args.length);
	    if (args.length > 100) {
	        $(this).val('');  
	        alert("제목이 100자를 넘을 수는 없습니다");
	    }
	});
	
	// 첨부파일 옆에 x버튼 누르면 
	$(document).on("click",".delYn",function(){
		let delYn= $(this).parent(); 
		console.log("this"+$(this).data("del"));
		delYn.css("display","none");
		delYn.find(".attYes").val("");
		attNos=$(this).data("del");
				 
	    let existingAttNos = $("#attDel").val();

	    if (existingAttNos) {
	        existingAttNos += ',' + attNos; // 쉼표로 구분
	    } else {
	        existingAttNos = attNos; // 처음 추가하는 경우
	    }

	    // hidden input에 새 값 설정
	    $("#attDel").val(existingAttNos);		
			
		
		let str  = "<div class='form-group'>";
			str += "<input type='file'";
			str += "class='form-control form-control-user uploadFile'";
			str += "name='uploadFile'>";
			str += "</div>";
		$(".fileForm").append(str);		
		
	});
	
	
	//파일 이미지 미리보기
	$(document).on('change','input[name="uploadFile"]',function(){
	    setImageFromFile(this, '#preview');
	});

	function setImageFromFile(input, expression) {
	    if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    reader.onload = function (e) {
	    $(expression).attr('src', e.target.result);
	  }
	  reader.readAsDataURL(input.files[0]);
	  }
	}		
	
	$(document).on("click",".viewImg",function(){
		event.preventDefault();
		viewImg(this);
	})
	
	

	
});

	function viewImg(args){
		let viewUrl= $(args).data("url");
		viewUrl= "data:image/jpeg;base64,"+viewUrl;		
		$("#preview").attr("src",viewUrl);
	}
</script>





  
</html>