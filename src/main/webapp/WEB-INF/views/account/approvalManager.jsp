<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>전표승인</title>


  <script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
  <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
  <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">
  
  <%--DatePicker--%>
 	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<style>
	.btnsize{
  		width: 90px;
		height:30px;
		font-size:0.9em;
		font-align:center;
		color:black;
  	}
  	.btnsize2{
  		width: 60px;
		height:30px;
		font-size:0.9em;
		color:black;
  	}
  	
	</style>

    <script type="text/javascript">
        var approvalSlipGrid = "table#approvalSlipGrid";
        var journalGrid = "table#journalGrid";
        var journalDetailGrid;
        
        
      //화폐 단위 설정
    	function currencyFormatter(params) {
        	return '￦' + formatNumber(params.value);
      	}
      
      	function formatNumber(number) {
        // this puts commas into the number eg 1000 goes to 1,000,
        // i pulled this from stack overflow, i have no idea how it works
        	return Math.floor(number)
          		.toString()
          		.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
      	}  
      	
		var selectedSlipRow;
        function createApprovalSlipGrid() {
        	rowData=[];
          	var columnDefs = [
        	      {headerName: "전표번호", field: "slipNo",sort:"desc",resizable:true,width:150, checkboxSelection: true},
        	      {headerName: "기수", field: "accountPeriodNo",width:50},
        	      {headerName: "부서코드", field: "deptCode",width:100},
        	      {headerName: "부서", field: "deptName", hide:true},
        	      {headerName: "구분", field: "slipType",editable:true,width:70},
        	      {headerName: "적요", field: "expenseReport",editable:true,resizable:true},
        	      {headerName: "승인상태", field: "slipStatus",width:100},
        	      {headerName: "상태", field: "status",hide:true},
        	      {headerName: "작성자코드", field: "reportingEmpCode",width:100},
        	      {headerName: "작성자", field: "reportingEmpName",hide:true},
        	      {headerName: "작성일", field: "reportingDate",width:100},
        	      {headerName: "직급", field: "positionCode",hide:true}
        	    ];
          	
        	  gridOptions = {
        			      columnDefs: columnDefs,
        			      rowSelection:'multiple', //row는 여러개 선택 가능
        			      defaultColDef: {editable: false }, // 정의하지 않은 컬럼은 자동으로 설정
/*         			      pagination:true, // 페이저
        			      paginationPageSize:8, // 페이저에 보여줄 row의 수 */
        	 			  onGridReady: function (event){// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
        	        			event.api.sizeColumnsToFit();
        	    		  },
        	    		  onGridSizeChanged:function (event){ // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
        	    		        event.api.sizeColumnsToFit();
        	    		  },
        	    		  onRowClicked:function (event){
        	    			  selectedSlipRow=event.data;
        	    			  showJournalGrid(selectedSlipRow["slipNo"]);
        	    		  }
        	  };
        	  approvalSlipGrid = document.querySelector('#approvalSlipGrid');
        	  new agGrid.Grid(approvalSlipGrid,gridOptions);
        }

        function showApprovalSlipGrid() {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/account/findDisApprovalSlipList",
                dataType: "json",
                success: function (jsonObj) {
                	gridOptions.api.setRowData(jsonObj);
                	gridOptions2.api.setRowData([]);
                }
            });
        }
        var selectedJournalRow
        var selectedJournalRowval
        function createJournal() {
        	var rowData2 = [];
			var columnDefs = [
				  {headerName: "분개번호", field: "journalNo",sort:"asc",resizable:true, onCellDoubleClicked : cellDouble}, 
			      {headerName: "구분", field: "balanceDivision"},
			      {headerName: "계정코드", field: "accountCode"},
			      {headerName: "계정과목", field: "accountName"},
			      {headerName: "차변", field: "leftDebtorPrice",
			    	 	valueFormatter:currencyFormatter
			      },
			      {headerName: "대변", field: "rightCreditsPrice",
			    		valueFormatter:currencyFormatter
			      },
			      {headerName: "거래처코드", field: "customerCode"},
			      {headerName: "거래처", field: "customerName",hide:true},
			      {headerName: "상태", field: "status"},
			]

            dataForBottomGrid = [ {
               journalNo : '합계',
               balanceDivision : null,
               accountCode : null,
               accountName : null,
               leftDebtorPrice : null,
               rightCreditsPrice : null,
               customerCode : null,
               customerName : null,
               status : null,
            } ]; 
    		gridOptions3 = {
               defaultColDef : {
                  editable : false,
                  sortable : true,
                  resizable : true,
                  filter : true,
                  flex : 1,
                  minWidth : 100
         		},
         	  	columnDefs : columnDefs,
         	  	rowData : dataForBottomGrid,
        	   	debug : true,
          	 	rowClass : 'bold-row',
           	// hide the header on the bottom grid
           		headerHeight : 0,
          		alignedGrids : [],
				onGridReady: function (event){// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
    			event.api.sizeColumnsToFit();
				},
				onGridSizeChanged:function (event){ // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
		        	event.api.sizeColumnsToFit();
				}
           	}
            gridOptions2 = {
               alignedGrids : [],
               columnDefs : columnDefs,
               rowSelection : 'single',
               defaultColDef : {
                  editable : false
               },
               pagination:true, // 페이저
			   paginationPageSize:8, // 페이저에 보여줄 row의 수
	 			onGridReady: function (event){// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
	        			event.api.sizeColumnsToFit();
	    		},
	    		onGridSizeChanged:function (event){ // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
	    		        event.api.sizeColumnsToFit();
	    		},
               	onRowClicked : function(event) {
                  selectedJournalRow = event.data;
                  selectedJournalRowval=event;
               }
            };
            journalGrid = document.querySelector('#journalGrid');
            new agGrid.Grid(journalGrid, gridOptions2);

            gridDivBottom = document.querySelector('#myGridBottom');
            new agGrid.Grid(gridDivBottom, gridOptions3);

            gridOptions2.api.setRowData(rowData2);

            gridOptions2.alignedGrids.push(gridOptions3);
            gridOptions3.alignedGrids.push(gridOptions2);
    }

        
        // 분개 보기
        function showJournalGrid(slipNo){ //slip rowid 선택한 전표행이다
                  // show loading message
			var rowData=[];
			gridOptions2.api.setRowData(rowData);
				$.ajax({
					type: "GET",
					url: "${pageContext.request.contextPath}/account/findSingleJournalList",
					data: {
						"slipNo": slipNo
					},
					dataType: "json",
					success: function (jsonObj) {
						gridOptions2.api.setRowData(jsonObj.journalList);
						compareDebtorCredits(); // 반환형 있는아인데 이렇게실행만 하면 의미가있나 ?(dong)
					}
				});
         }
        
        function cellDouble(event){
  			  $("#journalDetailGridModal").modal('show');
  			 //분개상세 보기  
  			  $.ajax({
          			type: "GET",
          			// JournalDetailDAO- ArrayList<JournalDetailBean> selectJournalDetailList(String journalNo)- return journalDetailBeans
          			url: "${pageContext.request.contextPath}/account/getJournalDetailList",
          			data: {
              			"journalNo": selectedJournalRow["journalNo"] //rowid 분개번호임 
          			},
          			dataType: "json",
         	 			success: function (jsonObj) {
         	 				gridOptions4.api.setRowData(jsonObj.journalDetailList); 			
         	 		  	}
    	            }); 
  	 	}  
        function createjournalDetailGrid(){
          	rowData=[];
            	var columnDefs = [
          	      {headerName: "계정 설정 속성", field: "accountControlType",width:150,sortable: true},
          	      {headerName: "분개 상세 번호", field: "journalDetailNo",width:150, sortable: true},
          	      {headerName: "-", field: "status",width:100, hide:true},
          	      {headerName: "-", field: "journalDescriptionCode",width:100, hide:true},
          	      {headerName: "분개 상세 항목", field: "accountControlName",width:150, },
          	      {headerName: "분개 상세 내용", field: "journalDescription",width:250},
          	    ];
            	
          	  gridOptions4 = {
          			      columnDefs: columnDefs,
          			      rowSelection:'single', //row는 하나만 선택 가능
          			      defaultColDef: {editable: false }, // 정의하지 않은 컬럼은 자동으로 설정
          	 			  onGridReady: function (event){// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
          	        			event.api.sizeColumnsToFit();
          	    		  },
          	    		  onGridSizeChanged:function (event){ // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
          	    		        event.api.sizeColumnsToFit();
          	    		  }
          	  };
          			journalDetailGrid = document.querySelector('#journalDetailGrid');
          			new agGrid.Grid(journalDetailGrid,gridOptions4);
           }  
        function approveSelectedSlip(isApprove){
           	var compare = compareDebtorCredits();
        	var approvalStatus = compare.isEqualSum;//차변 대변 같은지
        	var selectedRows = gridOptions.api.getSelectedRows();
          	var array=[];
			selectedRows.forEach( function(selectedRow, index){
                console.log(selectedRow.slipNo);
                array.push(selectedRow.slipNo);
         	});
         	if(approvalStatus || !isApprove){  // 차대변 합계가 같을때	
            	$.ajax({
                	type: "GET",
                	url: "${pageContext.request.contextPath}/account/approveSlip",
                	data: {
                    	"approveSlipList": JSON.stringify(array),
                    	"isApprove": isApprove
                	},
                	dataType: "json",
                	success: function (jsonObj) {
                    	showApprovalSlipGrid();
                    	if(jsonObj) alert("승인되었습니다.")
                    	else alert("반려되었습니다.")
						showApprovalSlipGrid();
                	}
            	});
        	}
        	else if(approvalStatus){ // 차대변 합계가 같지 않을때
        		alert("전표의 차변/대변 총계가 일치하지않아 전표승인이 거부되었습니다.\n전표의 분개에 차변/대변 수정 부탁드립니다.");
        	}
        }
       
        function compareDebtorCredits() {
        	var isEqualSum;
			var debtorPriceSum = 0 ;
			var creditsPriceSum = 0 ;
			var index=gridOptions3.api.getFirstDisplayedRow();
		  	var totalRow=gridOptions3.api.getRowNode(index);
			gridOptions2.api.forEachNode(function (node){
				console.log(node);
				
                debtorPriceSum += parseInt(node.data.leftDebtorPrice);
                creditsPriceSum += parseInt(node.data.rightCreditsPrice);
            });
			var result = {isEqualSum: debtorPriceSum == creditsPriceSum};//대변차변합확인
			gridOptions3.api.applyTransaction([totalRow.data["leftDebtorPrice"]=debtorPriceSum]);
			gridOptions3.api.applyTransaction([totalRow.data["rightCreditsPrice"]=creditsPriceSum]);

			return result;
        }
        
        
        $(document).ready(function () {
            createApprovalSlipGrid();
            showApprovalSlipGrid();
           	createJournal();
           	createjournalDetailGrid();

            $("#refresh").click(showApprovalSlipGrid);
			$("#approval").click(function () {
                approveSelectedSlip(true);
            });
            $("#disapproval").click(function () {
                approveSelectedSlip(false);
            }); 
       	 	$('input:button').hover(function() {
    		 	$(this).css("background-color","#D8D8D8");
    		}, function(){
    			$(this).css("background-color","");
    	 	});
        });

    </script>
</head>
<body>
      <h4>전표 승인</h4>
      <hr>
      <div class="row">

      </div>
         <div  style="padding-bottom: 10px;" >
      		<div style="text-align:right;">
      		<input type="button" id="approval" value="승인" class="btn btn-Light shadow-sm btnsize"  >
      		<input type="button" id="disapproval" value="반려" class="btn btn-Light shadow-sm btnsize" >
      		<input type="button" id="refresh" value="새로고침" class="btn btn-Light shadow-sm btnsize" >
      		</div>
      	</div>
      <div align="center">
				<div id="approvalSlipGrid" class="ag-theme-balham"  style="height:250px;width:auto;" ></div>
      </div>
      <div align="center" >
				<div id="journalGrid" class="ag-theme-balham"  style="height:250px;width:auto;" ></div>
      </div>
       <div align="center" >
			
				<div id="myGridBottom" class="ag-theme-balham"  style="height:31px;width:auto;" ></div>
      </div>
 
         <div align="center" class="modal fade" id="journalDetailGridModal" tabindex="-1" role="dialog" aria-labelledby="journalDetailGridLabel">
       		<div class="modal-dialog" role="document">
      			<div class="modal-content"  style="width:700px;">
       			 <div class="modal-header">
          			<h5 class="modal-title" id="journalDetailGridLabel">분개 상세</h5>
         			 <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            			<span aria-hidden="true">×</span>
         			 </button>
        		</div>
              <div class="modal-body">
              		<div align="center" id="journalDetailGrid" class="ag-theme-balham"  style="width:100%;height:200px"></div>
			  </div> 
      		</div>
    	</div>
      </div>
</body>
</html>
