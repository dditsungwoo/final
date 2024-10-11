<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="kr">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>SB Admin 2 - Tables</title>

<!-- Custom fonts for this template -->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/sbadmin2/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="/sbadmin2/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">

</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">
		

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<nav
					class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

					<!-- Sidebar Toggle (Topbar) -->
					<form class="form-inline">
						<button id="sidebarToggleTop"
							class="btn btn-link d-md-none rounded-circle mr-3">
							<i class="fa fa-bars"></i>
						</button>
					</form>

					<!-- Topbar Search -->
					<form
						class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search"
						action="/board/list.do">
						<div class="input-group">
							<input type="text" class="form-control bg-light border-0 small"
								placeholder="Search for..." aria-label="Search"
								aria-describedby="basic-addon2" name='searchKeyword' value='${searchKeyword }'>
							<div class="input-group-append">
								<button class="btn btn-primary" type="submit">
									<i class="fas fa-search fa-sm">검색</i>
								</button>
							</div>
						</div>
					</form>

					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">



						<div class="topbar-divider d-none d-sm-block"></div>

						<!-- Nav Item - User Information -->

						<form class="nav-link logoutBtn" action="/login/logout.do" method="post">
							<button type="button" class="btn-secondary">로그아웃</button>
							<sec:csrfInput/>
						</form>

					</ul>

				</nav>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">

					<!-- Page Heading -->
					<h1 class="h3 mb-2 text-gray-800">게시판</h1>


					<!-- DataTales Example -->
					<div class="card shadow mb-4">

						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr>
											<th>순번</th>
											<th>제목</th>
											<th>작성자</th>
											<th>작성일</th>
											<th>아이피</th>
											<th>조회수</th>
											<th>첨부파일</th>
											

										</tr>
									</thead>
										
										
									<tbody>
									<c:if test= "${not empty resultList}">
											<c:forEach var="result" items="${resultList}"
												varStatus="status">
												
												<tr>
													<td align="center" class="listtd"><c:out
															value="${paginationInfo.totalRecordCount+1 - ((searchVO.pageIndex-1) * searchVO.pageSize + status.count)}" /></td>
													<td align="left" class="listtd"><a href="javascript:fn_egov_select('<c:out value="${result.brdNo}"/>')"><c:out
															value="${result.brdSubject}" />&nbsp;</a></td>
													<td align="center" class="listtd"><c:out
																value="${result.brdWriter}" /></td>
													<td align="center" class="listtd"><c:out
															value="${result.brdRegdate}" />&nbsp;</td>
													<td align="center" class="listtd"><c:out
															value="${result.brdRegip}" />&nbsp;</td>
													<td align="center" class="listtd"><c:out
															value="${result.brdHit}" />&nbsp;</td>
													<td align="center" class="listtd"><c:out
															value="${result.attNum}개" />&nbsp;</td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
								<a class="btn btn-primary" href="/board/write.do" >
									<span>게시글 작성</span>
								</a>
							</div>
						</div>
					</div>

				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- End of Main Content -->

			<!-- Footer -->
			<footer class="sticky-footer bg-white">
				<div class="container my-auto"></div>
			</footer>
			<!-- End of Footer -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>

	<!-- Logout Modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="login.html">Logout</a>
				</div>
			</div>
		</div>
	</div>

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
	function fn_egov_select(brdNo){
		console.log(brdNo);
		location.href="/board/view.do?brdNo="+brdNo;
	}
	
	$(".logoutBtn button").on("click",function(){
		  var result = confirm("정말 로그아웃 하시겠습니까?");
	      if (result) {
	         
	          alert("로그아웃 완료됐습니다");
	         
	          $(".logoutBtn").submit() ;  
	      } else {
	       
	          alert("로그아웃이 취소되었습니다.");
	      }
		
	});	

$(()=>{
	$("#dataTable_filter").css("display","none");
	

let submitResult= "${submit}"; 
let deleteResult= "${delete}";
let message = "${message}";

if(submitResult !=null && submitResult!=''){
	alert("등록 성공");
}
if(deleteResult !=null && deleteResult!=''){
	alert("삭제 성공");
}
if(message !=null && message!=''){
	alert("내용 없음");
}


});


</script>

</body>

</html>