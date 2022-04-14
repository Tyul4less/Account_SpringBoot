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

  <title>메인</title>
<style>
	.bg{
		background-image:url("${pageContext.request.contextPath}/resources/static/img/desk.jpg");
		background-size:contain;
	}
	.text-rainbow {
  		background-image: linear-gradient(45deg, lightcyan, powderblue, royalblue);
  		-webkit-background-clip: text;
  		color: transparent;
		font-weight: bold;
  		font-size: large;
	}
	.rowMar{
		margin-top:50px;
	}
	.text{
		background-image: linear-gradient(dodgerblue, 10%, lavender);
  		-webkit-background-clip: text;
		color: transparent;
		font-weight: bold;
  		font-size: x-large;
	}
	
	.text2{
		background-image: linear-gradient(blue, 50%, pink);
  		-webkit-background-clip: text;
		color: transparent;
		font-weight: bold;
  		font-size: large;
	}
	
</style>
</head>

<body class="bg-gradient-primary">
	  <div class="container ">

		
  
  

    <!-- Outer Row -->
    <div class="row justify-content-center rowMar">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg"></div>
              <div class="col-lg-6">
                <!-- Card Header -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between" style="margin-left:-12px;">
                <h6 class="m-0 font-weight-bold text-primary">69th ACOUNTING PORTAL</h6>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                  <h5><font class="text-primary">Vision</font></h5>
                  <hr>
                  <div>
                  	We connect science to life for a better future.<br>
                  	<span style="font-size:small">더 나은 미래를 위해 과학을 인류의 삶에 연결합니다.</span>
                  </div>
                  <hr>
                  <h5><font class="text-primary">Slogan</font></h5>
                  <hr>
                  <div>
                  	<span class="text-rainbow">WeConnectScience</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  
    
  </div>
      <div style="width: 100%; padding: 10px;">
        <div align="center">
          <h5><font class="text">미세먼지 경보 현황 정보 조회</font></h5>
        </div>
      </div>

      <div id="openApiGrid" class="ag-theme-balham" style="height:250px;width:auto;"></div>
      <script>
        $(document).ready(function () {
          createBusInfo();
          showBusList();
        });
        //var selectedRow;

        function createBusInfo(){
          rowData=[];
          var columnDefs = [
            {headerName: "발령일", field: "dataDate", width:40},
            {headerName: "발령농도", field: "issueVal",width:40},
            {headerName: "발령시간", field: "issueTime",width:40},
            {headerName: "해제일", field: "clearDate",width:40},
            {headerName: "발령일", field: "issueDate",width:40},
            {headerName: "권역명", field: "moveName",width:40},
            {headerName: "해제시간", field: "clearTime",width:40},
            {headerName: "경보단계", field: "issueGbn",width:30}
          ];
          gridOptions = {
            columnDefs: columnDefs,
            defaultColDef: {editable: false }, // 정의하지 않은 컬럼은 자동으로 설정
            onGridReady: function (event){// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
              event.api.sizeColumnsToFit();
            },
            onGridSizeChanged:function (event){ // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
              event.api.sizeColumnsToFit();
            }
          }
          accountGrid = document.querySelector('#accountGrid');
          new agGrid.Grid(openApiGrid,gridOptions);
        }
        function showBusList(){
          console.log(" API호출");
          $.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/base/getAPI",
            dataType: "json",
            success: function (jsonObj) {
              console.log(jsonObj)
              gridOptions.api.setRowData(jsonObj.api);
            },

          });
        }
      </script>
  
  
</body>

</html>

