<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    
	<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
    <title>SB Admin 2 - Register</title>

    <!-- Custom fonts for this template-->
    <link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="/css/sb-admin-2.min.css" rel="stylesheet">


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
                                <h1 class="h4 text-gray-900 mb-4">회원 가입</h1>
                            </div>
                            <form class="user" action="/login/add.do" method="post">
                        
                                <div class="form-group">
                                    <input type="text" class="form-control form-control-user" id="memId" name="memId"
                                        placeholder="아이디 입력" required>
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control form-control-user" id="memName" name="memName"
                                        placeholder="이름 입력" required>
                                </div>
                                <div class="form-group">
                                    <input type="password" class="form-control form-control-user" id="memPass" name="memPass"
                                        placeholder="비밀번호 입력" required>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control form-control-user" id="memEmail" name="memEmail"
                                        placeholder="이메일 입력" required>
                                </div>
 								<div class="form-group">
                                    <input type="text" class="form-control form-control-user" id="memTel" name="memTel"
                                        placeholder="휴대폰 입력" required>
                                </div>					 							
                                
                                <div class="form-group">
                                	<input type="text" id="sample6_postcode" class="form-control form-control-user" placeholder="우편번호" maxlength="8"><br/>
                               	</div>
                               	
                                <div class="col-sm-5 mb-3 mb-sm-0"> 	
                                	<input type="button" onclick="sample6_execDaumPostcode()" class='btn btn-secondary btn-user btn-block' value="우편번호 찾기"><br>
                                </div>
                                
                                <div class="col-sm-15 mb-3 mb-sm-0">
                                	<input type="text" id="memAddr1" class="form-control form-control-user" placeholder="주소 입력" name="memAddr"><br>
                                </div>
                               
								<div class="form-group">
									<input type="text" id="memAddr2" name="memAddr2" class="form-control form-control-user" placeholder="상세주소">
								</div>	
									<input type="text" id="sample6_extraAddress" placeholder="참고항목" style="display:none;">
									
                                <button type="submit" class="btn btn-primary btn-user btn-block" id="submitBtn">
									회원 가입
                                </button>
                                <hr>
   								<sec:csrfInput />
                            </form>
                            <hr>
                            <div class="text-center">
                               
                            </div>
                            <div class="text-center">
                                <a class="small" href="/">로그인 페이지로</a>
                            </div>
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
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

	<script>
	/*다음 주소 api  */
	    function sample6_execDaumPostcode() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var addr = ''; // 주소 변수
	                var extraAddr = ''; // 참고항목 변수
	
	                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                    addr = data.roadAddress;
	                } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                    addr = data.jibunAddress;
	                }
	
	                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	                if(data.userSelectedType === 'R'){
	                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있고, 공동주택일 경우 추가한다.
	                    if(data.buildingName !== '' && data.apartment === 'Y'){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                  if(extraAddr !== ''){
	                        extraAddr = ' (' + extraAddr + ')';
	                    }
	                    // 조합된 참고항목을 해당 필드에 넣는다.
	                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
	                } else {
	                    document.getElementById("sample6_extraAddress").value = '';
	                } 	 
	
	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('sample6_postcode').value = data.zonecode;
	                document.getElementById("memAddr1").value = addr;
	                // 커서를 상세주소 필드로 이동한다.
	                document.getElementById("memAddr2").focus();
	            }
	        }).open();
	    }
	
$(()=>{
	$("#memId").on("keyup",function(){
		let $memId= $("#memId").val();
		console.log("keyup :",$memId );
		let data={
			"memId": $memId	
		};
		console.log("keyup :",JSON.stringify(data));
		 $.ajax({
		        url: "/login/idCheck.do",
		        data: JSON.stringify(data),
		        contentType: "application/json;charset=utf-8",
		        type: "post",
		        beforeSend: function(xhr) {
		            xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
		        },
		        success: function(res){
		        	console.log("성공");		    
		        	console.log(res);
		        	if (res>0){
		        		$("#memId").css( "border-color", "#cf4f4a");
		        		$("#submitBtn").css("pointer-events","none");		        	
		        	}else{
		        		$("#memId").css( "border-color", "#bac8f3");
		        		$("#submitBtn").css("pointer-events","auto");
		        	}
		        	},
		        error:function(xhr){
		        	console.log(xhr.status);	
		        }
		        
		 });  
	});
	
	
})

$("#memTel").on("blur",function() {
	var args= $("#memTel").val();
		console.log(args);
	 	telValidator(args);
});


$("#memId").on("blur", function() {
    var args = $(this).val();
    if (args.length > 20) {
        $(this).val('');  
        alert("아이디는 20자를 넘을 수는 없습니다");
    }
});

$("#memName").on("blur", function() {
    var args = $(this).val();
    if (args.length > 60) {
        $(this).val('');  
        alert("이름은 20자를 넘을 수는 없습니다");
    }
});

$("#memPass").on("blur", function() {
    var args = $(this).val();
    if (args.length > 30) {
        $(this).val('');  
        alert("비밀번호는 20자를 넘을 수는 없습니다");
    }
});

$("#memEmail").on("blur", function() {
    var args = $("#memEmail").val();
    if (args.length > 40) {
        $(this).val('');  
        alert("이메일는 40자를 넘을 수는 없습니다");
        return;
    }
});

$("#memAddr1, #memAddr2").on("blur", function() {
    var args = $(this).val();
    if (args.length > 40) {
        $(this).val('');  
        alert("주소는  한글 40자를 넘을 수는 없습니다");
        return;
    }
});


function telValidator(args) {
    const msg = '유효하지 않는 전화번호입니다.';

    if (/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/.test(args)) {
        return true;
    }
   	alert(msg);
    $("#memTel").val("");
    return false;
}
	</script>

</body>

</html>