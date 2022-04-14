<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
input.error, text.error {
	border: 2px solid #FD7D86;
}

label.error {
	color: #FD7D86;
	font-weight: 400;
	font-size: 0.75em;
	margin-top: 7px;
	margin-left: 6px;
	margin-right: 6px;
}
</style>

<title>회사 등록</title>

  	<script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
  	<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
  	<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
  	
  	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  	
  	<!-- validate -->

<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/plugins/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/plugins/functionValidate.js"></script>

<script>
	var searchWorkplaceCode = "#searchWorkplaceCode"; //사업장코드 조회
	var searchBusiness = "#searchBusiness"; //업태대분류 조회 버튼 	
	var AddWorkplace = "#AddWorkplace"; //사업장 추가

	var parentBusinessGrid = "#parentBusinessGrid" // 업태그리드
	var detailBusinessGrid = "#detailBusinessGrid" //업태소분류그리드

	var workplaceCode = "#workplaceCode"; //사업장조회 인풋 아이디	
	var codeCheck; //코드조회 변수
	var emptyWorkplaceBean;

	var status = "";
	$(document).ready(function() {

		//$(AddWorkplace).click(validate); //사업장추가  validate 메서드 오류
		$(AddWorkplace).click(workplaceAdd); //사업장추가 
		$("#valss input").focus(validate);

		createParentBusinessGrid(); //업태그리드
		createDetailBusinessGrid(); //업태소분류그리드getWorkplaceCode

		//버튼이벤트
		$(searchBusiness).click(function() { //업태 대분류 검색 버튼
			showParentBusiness();
		});

		$("#addCheck").click(function() { //사용하기 버튼 이벤트
			$(workplaceCode).val(codeCheck);
			$("#companyCodeModal").modal("hide");
			$("#seccondSearch").val("");
		});

	});

	function getWorkplaceCode(code) {
		if (code.type == "click") { // onclick 이벤트이므로 type == click
			codeCheck = $(workplaceCode).val();
			status = "first";
		}

		if (code.type == "button") {
			codeCheck = $("#seccondSearch").val();
			status = "seccond";
		} 

		$("#seccondModal").attr("hidden", false);
		$("#addCheck").attr("type", "button");

		$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/company/getWorkplace",
					data : {
						workplaceCode : codeCheck
					},
					dataType : "json",
					success : function(jsonObj) {
						var workplaceCode = jsonObj.workplaceBean;

						var getChecking = workplaceCode != null ? "사업장 중복 있습니다 , 다시 조회하세요."
								: codeCheck.length != 4 || isNaN(codeCheck) ? "사업장 코드는 숫자로만 구성된 4자리로 입력 부탁드립니다"
										: "사업장 등록이 가능합니다";
						// JSON 결과가 NULL이 아닐경우 중복 / 숫자 4자리가 아닐 경우 경고 / 모두다 아니면 등록 가능
						$("#codeCheck").html(getChecking); // modal 창 하단에 결과메세지 띄움
						
						if (workplaceCode != null || codeCheck.length != 4
								|| isNaN(codeCheck)) {
							$("#addCheck").attr("type", "hidden"); // 조건이 맞지 않을 경우 버튼을 숨긴다
							emptyWorkplaceBean = null;

						} else if (workplaceCode == null
								|| codeCheck.length == 4 || isNaN(codeCheck)) {
							$("#seccondModal").attr("hidden", true);       // 코드 입력 텍스트창 숨김
							emptyWorkplaceBean = jsonObj.emptyWorkplaceBean; 
							if (status == "first")
								$("#codeCheck").html(
										"사업장코드" + codeCheck + "    "
												+ getChecking);

							else if (status == "seccond")
								$("#codeCheck").html(
										"사업장코드" + codeCheck + "    "
												+ getChecking);
						}   // 사업장코드 0000 사업장 등록이 가능합니다.

					}
				});
	}

	//사업장추가
	function workplaceAdd() {
		var workplaceAddItems = emptyWorkplaceBean;

		for ( var index in workplaceAddItems) {
			if (index == "workplaceCode")
				workplaceAddItems[index] = "BRC-" + $("#" + index + "").val(); // code에 BEC- 추가
			else
				workplaceAddItems[index] = $("#" + index + "").val();
		}

		$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/company/workPlaceAdd",
					data : {
						workplaceAddItems : JSON.stringify(workplaceAddItems),
					},
					dataType : "json",
					success : function(jsonObj) {
						if (jsonObj.errorCode == 0) {
							alert("회사등록이 성공적으로 완료되었습니다");
							location.href = "${pageContext.request.contextPath}/hello.html"
						}
						if (jsonObj.errorCode < 0)
							alert("회사등록에 실패 되었습니다 사업장조회는 필수입니다.");
					}
				});
	}

	function createParentBusinessGrid() { //업태 그리드

		rowData=[];
	  	var columnDefs = [
		      {headerName: "업태대분류", field: "businessName",resizable:true,width:100},
		      {headerName: "업태코드", field: "classificationCode", hide : true},
		  ];	  	
		 gridOptions = {
				      columnDefs: columnDefs,
				      rowSelection:'single', //row는 하나만 선택 가능
				      defaultColDef: {editable: false }, // 정의하지 않은 컬럼은 자동으로 설정
		 			  onGridReady: function (event){// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
		        			event.api.sizeColumnsToFit();
		    		  },
		    		  onGridSizeChanged:function (event){ // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
		    		        event.api.sizeColumnsToFit();
		    		  },
		    		  onRowClicked:function (event){
		    			  console.log("Row선택");
		    			  console.log(event.data);
		    			  selectedRow=event.data;
		    			  showDetailBusinessGrid(selectedRow["classificationCode"]);
		    		  }
		   }
		 parentBusinessGrid = document.querySelector('#parentBusinessGrid');
		 	new agGrid.Grid(parentBusinessGrid,gridOptions);
	}

	function showParentBusiness() { //업태리스트
		
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/company/getBusinessList",
			dataType : "json",
			success : function(jsonObj) {
					 
				gridOptions.api.setRowData(jsonObj.businessList);

			}
		});
	}

	function createDetailBusinessGrid() { 	//업태소분류
		

		rowData=[];
	  	var columnDefs = [
		      {headerName: "업태소분류", field: "detailBusinessName",resizable:true,width:100},
		  ];	  	
		 gridOptions2 = {
				      columnDefs: columnDefs,
				      rowSelection:'single', //row는 하나만 선택 가능
				      defaultColDef: {editable: false }, // 정의하지 않은 컬럼은 자동으로 설정
		 			  onGridReady: function (event){// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
		        			event.api.sizeColumnsToFit();
		    		  },
		    		  onGridSizeChanged:function (event){ // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
		    		        event.api.sizeColumnsToFit();
		    		  },
		    		  onRowClicked:function (event){
		    			  console.log("Row선택");
		    			  console.log(event.data);
		    			  selectedRow2=event.data;
		    			  $("#businessConditions").val(selectedRow2["detailBusinessName"]);
		    			  $("#businessModal").modal("hide");
		    		  }
		   }
		 	detailBusinessGrid = document.querySelector('#detailBusinessGrid');
		 	new agGrid.Grid(detailBusinessGrid,gridOptions2);
	}

	function showDetailBusinessGrid(businessCode) { //업태소분류리스트
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/company/getDetailBusiness",
			data : {
				"businessCode" : businessCode
			},
			dataType : "json",
			success : function(jsonObj) {
					gridOptions2.api.setRowData(jsonObj.detailBusinessList);
			}
		});
	}
</script>

</head>
<body class="bg-gradient-primary">

    <div class="card o-hidden border-0 shadow-lg my-5" >
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-7">
            <div class="p-5">
               <form class="user" id="infoForm" method="post">
               
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                  	<label for="example-text-input" class="col-form-label">사업장코드</label>
                    <input type="text" class="form-control form-control-user" id="workplaceCode" name="workplaceCode" placeholder="workplaceCode">
                  </div>
                  <div class="col-sm-6" style="margin-top:43px; margin-left:-12px;">
                  	<button class="btn btn-primary" type="button" data-toggle="modal"  data-target="#companyCodeModal" id="searchWorkplaceCode">
                  		<i class="fas fa-search fa-sm"></i>
                  	</button>
                  </div>
                </div>
               
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                  	<label for="example-text-input" class="col-form-label">회사코드</label>
                    <input type="text" class="form-control form-control-user" id="companyCode" name="companyCode" placeholder="companyCode">
                  </div>
                  <div class="col-sm-6">
                  	<label for="example-text-input" class="col-form-label">사업장명</label>
                    <input type="text" class="form-control form-control-user" id="workplaceName" name="workplaceName" placeholder="workplaceName">
                  </div>
                </div>
                
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                  	<label for="example-text-input" class="col-form-label">사업자등록번호</label>
                    <input type="text" class="form-control form-control-user" id="businessLicense" name="businessLicense" placeholder="businessLicense">
                  </div>
                  <div class="col-sm-6">
                  	<label for="example-text-input" class="col-form-label">법인등록번호</label>
                    <input type="text" class="form-control form-control-user" id="corporationLicence"name="corporationLicence"
								placeholder="corporationLicence" >
                  </div>
                </div>
                
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                  	<label for="example-text-input" class="col-form-label">대표자명</label>
                    <input type="text" class="form-control form-control-user" id="workplaceCeoName" name="workplaceCeoName" placeholder="ceoName">
                  </div>
                </div>
                
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                  	<label for="example-text-input" class="col-form-label">업태</label>
                    <input type="text" class="form-control form-control-user" id="businessConditions" placeholder="businessConditions" disabled="disabled">
                  </div>
                  <div class="col-sm-6" style="margin-top:43px; margin-left:-12px;">
                  	<button class="btn btn-primary" type="button" data-toggle="modal"
                  		data-target="#businessModal" id="searchBusiness">
                  		<i class="fas fa-search fa-sm"></i>
                  	</button>
                  </div>
                </div>
                
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                  	<label for="example-text-input" class="col-form-label">사업장전화번호</label>
                    <input type="text" class="form-control form-control-user" id="workplaceTelNumber" name="workplaceTelNumber"	placeholder="workplaceTelNumber">
                  </div>
                  <div class="col-sm-6">
                  	<label for="example-text-input" class="col-form-label">사업장팩스번호</label>
                    <input type="password" class="form-control form-control-user" id="workplaceFaxNumber" name="workplaceFaxNumber" placeholder="workplaceFaxNumber">
                  </div>
                </div>
                
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                  	<label for="example-text-input" class="col-form-label">사업장주소</label>
                    <input type="text" class="form-control form-control-user" id="workplaceBasicAddress" name="workplaceBasicAddress"	placeholder="workplaceAdress">
                  </div>
                  <div class="col-sm-6">
                  	<label for="example-text-input" class="col-form-label">거래처등록유무</label>
                    <input type="text" class="form-control form-control-user" id="approvalStatus" value="미등록" placeholder="approvalStatus" disabled="disabled">
                  </div>
                </div>
                <hr>
                <input type="submit" class="btn btn-user btn-block btn-primary" id="AddWorkplace" value = "등록">
                <input type="reset" class="btn btn-user btn-block btn-danger " id="reset" value = "취소">
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>


	<div align="center" class="modal fade" id="businessModal" tabindex="-1"
		role="dialog" aria-labelledby="businessLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="businessLabel">업태</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
			<hr>
			<div class="modal-body" >
				<div style="float: left; width: 50%;">
					<div align="center">
						<div id="parentBusinessGrid" class="ag-theme-balham"
							style="height: 400px; width: auto;"></div>
					</div>
				</div>
				<div style="float: left; width: 50%;">
					<div align="center">
						<div id="detailBusinessGrid" class="ag-theme-balham"
							style="height: 400px; width: auto;"></div>
					</div>
				</div>
				</div>
			</div>
		</div>
	</div>
	
	<div align="center" class="modal fade" id="companyCodeModal" tabindex="-2"
		role="dialog" aria-labelledby="companyCodeLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="companyCodeLabel">사업장코드확인</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<hr>
				<div class="modal-body" >
						<div style="float: left;">
							<input type="text" id="seccondSearch"
								class="form-control form-control-user" maxlength="4">
						</div>
						<div style="float: left;">
							<button class="btn btn-primary" type="button"
								onclick="getWorkplaceCode(this)">
								<i class="fas fa-search fa-sm"></i>
							</button>
						</div>
				 <div class="modal-footer" >
				 	<div id="codeCheck">
				 	</div>
					<input class="btn btn-secondary btn-icon-split" type="button" id="addCheck" value="사용하기">
					<table id="companyCodeGrid"></table>
        		</div>
				</div>
			</div>
		</div>
	</div>

</body>

</html>
