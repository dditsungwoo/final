<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">

<head>

<meta charset="utf-8">
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
								<h1 class="h4 text-gray-900 mb-4">글 작성</h1>
							</div>
							<form class="user" action="/board/submit.do" method="post" enctype="multipart/form-data">

								<div class="form-group">
									<input type="text" class="form-control form-control-user"
										id="brdWriter" name="brdWriter" placeholder="아이디 입력" value="${memVO.memId }" required readonly>
								</div>
								<div class="form-group">
									<input type="text" class="form-control form-control-user"
										id="brdSubject" name="brdSubject" placeholder="글 제목 입력"   maxlength="250" required>
								</div>
								<br/>
							
								
								<div class="form-group form-control-large mb-2 h3">
									<textarea  class="form-control form-control-user"
										id="brdContent" name="brdContent" placeholder="글 내용 입력" required></textarea>
								</div>
								<div class="form-group">
									<input type="text" class="form-control form-control-user"
										id="brdPassword" name="brdPassword" placeholder="글 비밀번호 입력"  maxlength="10" required>
								</div>

								<div class="form-group col-sm-6 fileForm">
									<input type="file" class="form-control form-control-user 
										uploadFile" name="uploadFile" placeholder="파일 업로드"
										>
								<div class="btn btn-secondary btn-sm fileAddBtn" >+</div></div>
								<button type="submit" class="btn btn-primary btn-user btn-block"
									id="submitBtn">글 작성</button>
								<hr>
								<sec:csrfInput />
							</form>
							<hr>
							<a class="btn btn-secondary"
									href="/board/coolList.do"	>목록으로</a>
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
$(".fileAddBtn").on("click",function(){
	let str  = "";
    var textBoxCount = $('.fileForm input[type="file"]').length;
    if (textBoxCount >= 5) {
    	alert("파일업로드는 5개까지만 가능합니다");
        return;
    }else{
	 str += "<input type='file' class='form-control form-control-user uploadFile' name='uploadFile' placeholder='파일 업로드'>";
	
	$(this).before(str);
    }
});

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

$(()=>{
	
	$("#brdSubject").on("blur", function() {
	    var args = $(this).val();
	    console.log(args.length);
	    if (args.length > 100) {
	        $(this).val('');  
	        alert("제목이 100자를 넘을 수는 없습니다");
	    }
	});
	
	
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
	
	
	
});



</script>	
</html>