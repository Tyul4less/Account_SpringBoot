<%@page import="net.plang.HoWooAccount.system.authority.to.AuthorityEmpBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <!--JQuery-->
    <script src="../../../plugins/jquery-3.6.0.min.js"></script>
  <!--JQuery CDN-->
    <%--      <script
              src="https://code.jquery.com/jquery-3.6.0.min.js"
              integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
              crossorigin="anonymous"></script>--%>
    <!--ajaxForm-->
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js"
            integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i"
            crossorigin="anonymous" type="text/javascript"></script>--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.3.0/jquery.form.min.js" integrity="sha384-qlmct0AOBiA2VPZkMY3+2WqkHtIQ9lSdAsAn5RUJD/3vA5MKDgSGcdmIv4ycVxyn" crossorigin="anonymous"></script>
    <!--AG GRID-->
    <script src="https://unpkg.com/@ag-grid-enterprise/all-modules@24.1.0/dist/ag-grid-enterprise.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>


    <title><sitemesh:write property='title'/> - 69th Accounting</title>

    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/resources/static/css/sb-admin-2.min.css" rel="stylesheet">
    <sitemesh:write property='head'/>

</head>

<body id="page-top">
	<!-- Page Wrapper -->
  <div id="wrapper">

    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/hello.html">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">69th Accounting<sup>2</sup></div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath}/base/board.html"><!-- 만들어야함!! -->
          <i class="fa fa-window-maximize" aria-hidden="true"></i>
          <span>게시판</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

		<!--  메뉴권한 세션 -->


      <!-- Heading -->
      <div class="sidebar-heading">업무단</div>

      <!-- 전표입력 -->
      <li class="nav-item slip">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSilp" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-folder"></i>
          <span class="confirmMenu">전표입력</span>
        </a>
        <div id="collapseSilp" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">전표</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/account/slipForm.html">일반전표</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/account/approvalManager.html">전표승인</a>
          </div>
        </div>
      </li>

      <!--장부 관리-->
      <li class="nav-item journal">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseJournal" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-folder"></i>
          <span class="confirmMenu">장부관리</span>
        </a>
        <div id="collapseJournal" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">장부</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/statement/cashJournal.html">현금출납장</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/account/journalForm.html">분개장</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/statement/detailTrialBalance.html">일(월)계표</a>
          </div>
        </div>
      </li>
      
      <!--결산 -->
         <li class="nav-item statement">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSettlement" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-folder"></i>
          <span class="confirmMenu">결산 및 재무제표</span>
        </a>
        <div id="collapseSettlement" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <a class="collapse-item" href="${pageContext.request.contextPath}/statement/totalTrialBalance.html">합계잔액시산표</a>
           <!-- <a class="collapse-item" href="${pageContext.request.contextPath}/statement/totalTrialBalance.html">합계잔액시산표</a>  -->
            <a class="collapse-item" href="${pageContext.request.contextPath}/statement/incomeStatement.html">손익계산서</a> 
           <!--  <a class="collapse-item" href="${pageContext.request.contextPath}/statement/financialPosition.html">재무상태표</a> -->
            <a class="collapse-item" href="${pageContext.request.contextPath}/statement/financialPosition.html">재무상태표</a>
          </div>
        </div>
      </li>
      
       <!--전기분재무제표 -->
       <li class="nav-item early">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTemify" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-folder"></i>
          <span class="confirmMenu">전기분재무제표</span>
        </a>
        <div id="collapseTemify" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">전기분 재무</h6>
            <a class="collapse-item" href="themify.html">거래처별초기이월</a>
            <a class="collapse-item" href="themify.html">전기분손익계산서</a>
            <a class="collapse-item" href="fontawesome.html">전기분재무상태표</a>
          </div>
        </div>
      </li>
      
       <!--권한관리 -->
       <li class="nav-item authority">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseAuthority" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-folder"></i>
          <span class="confirmMenu">권한관리</span>
        </a>
        <div id="collapseAuthority" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">권한설정</h6>
          <a class="collapse-item" id="authority" href="${pageContext.request.contextPath}/hr/empAuthorityForm.html">권한관리</a>
          </div>
        </div>
      </li>
      
             <!--예산관리 -->
       <li class="nav-item budget">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#test" aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-folder"></i>
          <span class="confirmMenu">예산관리　　　　　　</span>
        </a>
        <div id="test" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">예산</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/account/budgetApplForm.html">예산신청</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/account/budgetInsertForm.html">예산편성</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/account/budgetStatusForm.html">예산실적현황</a>
          </div>
        </div>
      </li>
      
      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Nav Item - Pages Collapse Menu -->
      <c:if test="${sessionScope.empName!=null}">
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true" aria-controls="collapsePages">
          <i class="fas fa-fw fa-cog"></i>
          <span class="confirmMenu" id="btn">기본설정</span>
        </a>
        <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">설정</h6>
            <a class="collapse-item" href="${pageContext.request.contextPath}/hr/myProfileForm.html">내프로필</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/hr/employeeForm.html">사원관리</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/company/WorkplaceInserForm.html">회사등록</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/base/projectInsertForm.html">프로젝트등록</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/company/WorkplaceManagement.html">거래처 관리</a>
            <a class="collapse-item" href="${pageContext.request.contextPath}/account/accountForm.html">계정과목 및 적요</a>
          </div>
        </div>
      </li>
       <hr class="sidebar-divider d-none d-md-block">
      </c:if>
      <!-- Divider -->
    

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>

          <!-- Topbar Search -->
          <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
            <div class="input-group">
              <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
              <div class="input-group-append">
                <button class="btn btn-primary" type="button">
                  <i class="fas fa-search fa-sm"></i>
                </button>
              </div>
            </div>
          </form>

          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">
			<c:if test="${sessionScope.empName!=null}">
            <!-- Nav Item - Search Dropdown (Visible Only XS) -->
            <li class="nav-item dropdown no-arrow d-sm-none">
              <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-search fa-fw"></i>
              </a>
              <!-- Dropdown - Messages -->
              <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                <form class="form-inline mr-auto w-100 navbar-search">
                  <div class="input-group">
                    <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                    <div class="input-group-append">
                      <button class="btn btn-primary" type="button">
                        <i class="fas fa-search fa-sm"></i>
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </li>

            <!-- Nav Item - Alerts -->
            <li class="nav-item dropdown no-arrow mx-1">
              <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-bell fa-fw"></i>
                <!-- Counter - Alerts -->
                <span class="badge badge-danger badge-counter"></span> <!-- 알림 갯수가 날아와야함 -->
              </a>
             <!-- Dropdown - Alerts -->
               <!-- <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="alertsDropdown">
                <h6 class="dropdown-header">
                  Alerts Center
                </h6>
                <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="mr-3">
                    <div class="icon-circle bg-primary">
                      <i class="fas fa-file-alt text-white"></i>
                    </div>
                  </div>
                  <div>
                    <div class="small text-gray-500">December 12, 2019</div>
                    <span class="font-weight-bold">A new monthly report is ready to download!</span>
                  </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="mr-3">
                    <div class="icon-circle bg-success">
                      <i class="fas fa-donate text-white"></i>
                    </div>
                  </div>
                  <div>
                    <div class="small text-gray-500">December 7, 2019</div>
                    $290.29 has been deposited into your account!
                  </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="mr-3">
                    <div class="icon-circle bg-warning">
                      <i class="fas fa-exclamation-triangle text-white"></i>
                    </div>
                  </div>
                  <div>
                    <div class="small text-gray-500">December 2, 2019</div>
                    Spending Alert: We've noticed unusually high spending for your account.
                  </div>
                </a>
                <a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
              </div> -->
            </li>

            <!-- Nav Item - Messages -->
            <li class="nav-item dropdown no-arrow mx-1">
              <a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-envelope fa-fw"></i>
                <!-- Counter - Messages -->
                <span class="badge badge-danger badge-counter"></span> <!-- 메세지 갯수가 날아와야함   -->
              </a>
              <!-- Dropdown - Messages -->
            <!--   <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="messagesDropdown">
                <h6 class="dropdown-header">
                  Message Center
                </h6>
                <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="dropdown-list-image mr-3">
                    <img class="rounded-circle" src="https://source.unsplash.com/fn_BT9fwg_E/60x60" alt="">
                    <div class="status-indicator bg-success"></div>
                  </div>
                  <div class="font-weight-bold">
                    <div class="text-truncate">Hi there! I am wondering if you can help me with a problem I've been having.</div>
                    <div class="small text-gray-500">Emily Fowler · 58m</div>
                  </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="dropdown-list-image mr-3">
                    <img class="rounded-circle" src="https://source.unsplash.com/AU4VPcFN4LE/60x60" alt="">
                    <div class="status-indicator"></div>
                  </div>
                  <div>
                    <div class="text-truncate">I have the photos that you ordered last month, how would you like them sent to you?</div>
                    <div class="small text-gray-500">Jae Chun · 1d</div>
                  </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="dropdown-list-image mr-3">
                    <img class="rounded-circle" src="https://source.unsplash.com/CS2uCrpNzJY/60x60" alt="">
                    <div class="status-indicator bg-warning"></div>
                  </div>
                  <div>
                    <div class="text-truncate">Last month's report looks great, I am very happy with the progress so far, keep up the good work!</div>
                    <div class="small text-gray-500">Morgan Alvarez · 2d</div>
                  </div>
                </a>
                <a class="dropdown-item d-flex align-items-center" href="#">
                  <div class="dropdown-list-image mr-3">
                    <img class="rounded-circle" src="https://source.unsplash.com/Mv9hjnEUHR4/60x60" alt="">
                    <div class="status-indicator bg-success"></div>
                  </div>
                  <div>
                    <div class="text-truncate">Am I a good boy? The reason I ask is because someone told me that people say this to all dogs, even if they aren't good...</div>
                    <div class="small text-gray-500">Chicken the Dog · 2w</div>
                  </div>
                </a>
                <a class="dropdown-item text-center small text-gray-500" href="#">Read More Messages</a>
              </div> -->
            </li>

            <div class="topbar-divider d-none d-sm-block"></div>
			</c:if>
            <!-- Nav Item - User Information -->
            <li class="nav-item dropdown no-arrow">
            	        <c:choose>
        				<c:when test="${sessionScope.empName!=null}">
              			<a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                			<span class="mr-2 d-none d-lg-inline text-gray-600 small">${sessionScope.empName}</span>
                			<img id=profileImg class="img-profile rounded-circle" src="https://source.unsplash.com/QAB-WJcbgJk/60x60">
             			 </a>
             			               <!-- Dropdown - User Information -->
              			<div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                			<a class="dropdown-item" href="${pageContext.request.contextPath}/hr/myProfileForm.html">
                  			<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                  				프로필
                			</a>
                			<div class="dropdown-divider"></div>
                			<a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                  			<i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                  				로그아웃
                			</a>
              			</div>
        				</c:when>
        				<c:otherwise>
						<a class="nav-link dropdown-toggle" href="${pageContext.request.contextPath}/loginForm.html">
                			<span class="mr-2 d-none d-lg-inline text-gray-600 small">로그인</span>
                			<img class="img-profile rounded-circle" src="${pageContext.request.contextPath}/resources/static/img/profile.png">
             			</a>
        				</c:otherwise>
        				</c:choose>
            </li>
          </ul>
        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
  		 <div class="container-fluid">
		<!-- 	  <sitemesh:write property='body'/> hello.jsp의 body 부분 -->
		<sitemesh:write property='body'/>
        </div>  
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Your 권대원 2021</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">퇴근하시나요?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">
        		잊으신 중요한 업무가 없으신가요?<br>
        		업무를 마칠 준비를 다 하셨다면 로그아웃을 눌러주세요!
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="${pageContext.request.contextPath}/base/logout">Logout</a>
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

    <!-- Page level plugins
    <script src="${pageContext.request.contextPath}/assets/vendor/chart.js/Chart.min.js"></script>
    -->
    <!-- Page level custom scripts
    <script src="${pageContext.request.contextPath}/assets/js/demo/chart-area-demo.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/demo/chart-pie-demo.js"></script>
    -->
    <%-- <% ArrayList<String> menuList = (ArrayList<String>) session.getAttribute("menuList");%> --%>

<script>

    $(document).ready(function(){
      var imgUrl = "${empProfileImg}";
      if(imgUrl != ""){
        $("#profileImg").attr("src", "${pageContext.request.contextPath}"+imgUrl);
      }
    });

		var arr = "<%=(ArrayList<String>) session.getAttribute("menuList")%>";
		/* $('.confirmMenu').click(function(e){
			console.log(arr);
			var text = "";
			var isAuthority = "";
			text = $(this).text();
			isAuthority = arr.includes(text);
			console.log(isAuthority);
			if(isAuthority == false){
				alertFunc();
				e.preventDefault();
			}
		}) */
		
		$(document).on('click', '.confirmMenu', function(e){
			console.log("여기");
			console.log(arr);
			var text = "";
			var isAuthority = "";
			text = $.trim($(this).text());
			console.log(text);
			isAuthority = arr.includes(text);
			console.log(isAuthority);
			if(isAuthority == false){
				e.stopPropagation(); 
				alertFunc();
			}
		});

function alertFunc(){
	Swal.fire({
		icon : 'error',
		title : '접근 불가',
		text : '접근 권한을 확인해주세요',
		footer : '<a href>관리자에게 문의하세요</a>'
	})
}
</script>
</body>

</html>
