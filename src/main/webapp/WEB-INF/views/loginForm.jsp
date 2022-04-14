<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>로그인</title>

  <!-- Custom fonts for this template-->
  <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="${pageContext.request.contextPath}/resources/static/css/sb-admin-2.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  	<script>  // 아래 태그보면 hidden으로 쓰질않음
    window.onload=function(){
       var date = new Date();
        var year = date.getFullYear().toString();
        var month = (date.getMonth() + 1 > 9 ? date.getMonth()+1: '0' + (date.getMonth() + 1)).toString();
        var day = date.getDate() > 9 ? date.getDate() : '0' + date.getDate();
        var today =year + "-" + month + "-" + day;

        document.querySelector("#today").value=today;
    }
  	</script>
  	<script>
  		var errorCode = "${errorMsg}"
       if( errorCode !="")
       alert( errorCode)
   	</script>

</head>

<body class="bg-gradient-primary">

  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">오늘 하루도 힘차게!</h1>
                  </div>
                  <form class="user" method="post" action="${pageContext.request.contextPath}/login">
                    <div class="form-group">
                      <input type="hidden" id="today" name="today">
                      <input type="text" class="form-control form-control-user" id="empCode" name="empCode" placeholder="Enter empCode..." value="hyh">
                    </div>
                    <div class="form-group">
                      <input type="password" class="form-control form-control-user" id="userPw" name="userPw" placeholder="Password" value="hyh">
                    </div>
                    <div class="form-group">
                      <div class="custom-control custom-checkbox small">
                        <input type="checkbox" class="custom-control-input" id="customCheck">
                        <label class="custom-control-label" for="customCheck">ID 기억하기</label>
                      </div>
                    </div>
                     <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-user btn-block">로그인</button>
                     </div>
                    <hr>
               		담당부서 : 인사과 <br>
               		담당자 : Dong<br>
               		내선 :  0630 <br>
               		유선 : 055-753-3677
                  </form>
                  

                  <hr>
                  <div class="text-center">
                    <a class="small" href="#">Forgot Password?</a>
                  </div>
                  <div class="text-center">
                    <a class="small" href="${pageContext.request.contextPath}/hr/empinsertForm.html">Create an Account!</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>

  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>

</body>

</html>

