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
     #brdContent{
       height: 250px;
       border-radius: 25px;
     }
 </style>

</head>


<body class="bg-gradient-primary">

	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="row">
					<div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
					<div class="col-lg-7">
						<div class="p-5">
							<div class="text-center">
								<h1 class="h4 text-gray-900 mb-4">글 작성</h1>
							</div>
							<form class="user" action="/board/submit.do" method="post">

								<div class="form-group">
									<input type="text" class="form-control form-control-user"
										id="brdWriter" name="brdWriter" placeholder="아이디 입력" value="${memVO.memId }" required readonly>
								</div>
								<div class="form-group">
									<input type="text" class="form-control form-control-user"
										id="brdSubject" name="brdSubject" placeholder="글 제목 입력" required>
								</div>
								<br/><br/>
							
								
								<div class="form-group form-control-large mb-4 h4">
									<textarea  class="form-control form-control-user"
										id="brdContent" name="brdContent" placeholder="글 내용 입력" required></textarea>
								</div>
								<div class="form-group">
									<input type="text" class="form-control form-control-user"
										id="brdPassword" name="brdPassword" placeholder="글 비밀번호 입력" required>
								</div>
						
								<button type="submit" class="btn btn-primary btn-user btn-block"
									id="submitBtn">글 작성</button>
								<hr>
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

</html>