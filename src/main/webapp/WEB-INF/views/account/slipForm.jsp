<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
    <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-balham.css">

    <%--DatePicker--%>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <title>메인</title>
    <style>
        .date {
            width: 140px;
            height: 30px;
            font-size: 0.9em;
        }

        .btnsize {
            width: 100px;
            height: 30px;
            font-size: 0.8em;
            font-align: center;
            color: black;
        }

        .btnsize2 {
            width: 60px;
            height: 30px;
            font-size: 0.9em;
            color: black;
        }

        .ag-header-cell-label {
            justify-content: center;
        }

    </style>
    <script>
        $(document).ready(function () {
            //버튼 이벤트
            $('input:button').hover(function () { // hover가 2개의 인자값이 있으면 첫번째 인자값은 마우스올렸을때 ,두번째는 땟을때 실행
                    $(this).css("background-color", "pink");
                },
                function () {
                    $(this).css("background-color", "");
                });

            $("#search").click(searchSlip);         // (전표)검색
            $("#addSlip").click(addslipRow);            // 전표추가
            $("#deleteSlip").click(deleteSlip);      // 전표삭제 - 전표, 분개, 분개상세
            $("#addJournal").click(addJournalRow);      // 분개추가
            $("#deleteJournal").click(deleteJournal);   // 분개삭제, 화영이가 구현
            $("#showPDF").click(createPdf); // pdf보기
            $("#showExcel").click(createExcel);
            $("#confirm").click(confirmSlip);         // 결재 버튼
            $("#Accountbtn").click(searchAccount); // 모달에서의 계정 검색 버튼
            $("#saveSlip").click(saveSlip);

            $("#accountCode").keydown(function (key) {
                if (key.keyCode == 13) {
                    searchAccount();
                }
            })
            $("#searchCodebtn").click(searchCode); // 모달에서의 부서 검색 버튼

            $("#email").click(showEmailModal);

            $("#naverEmailBtn").click(function(){
                slipEmailFunc("naver")
            });
            $("#googleEmailBtn").click(function(){
                slipEmailFunc("google")
            });

            /* DatePicker  */
            $('#from').val(today.substring(0, 8) + '01');
            // 오늘이 포함된 해당 달의 첫번째 날, 1월달이면 1월 1일로 세팅.    2020-xx 총 7자리
            $('#to').val(today.substring(0, 10));         // 오늘 날짜의 년-월-일.

            createSlip();
            createJournal();
            createjournalDetailGrid();
            createCodeGrid();
            showSlipGrid();
            createAccountGrid();
            showAccount();
            createAccountDetailGrid()
            showAccountDetail('0101-0145')

        });
        window.addEventListener("keydown", (key) => {
            if (key.keyCode == 113) {
                addslipRow();
            } else if (key.keyCode == 114) {
                saveSlip();
            } else if (key.keyCode == 115) {
                confirmSlip();
            }
        })


        var NEW_SLIP_NO = "NEW"; // 전표 이름.
        var NEW_JOURNAL_PREFIX = NEW_SLIP_NO + "JOURNAL"; // 분개 앞에 오는 이름
        var REQUIRE_ACCEPT_SLIP = "작성중";

        //그리드 선택자
        var slipGrid = "#slipGrid";
        var journalGrid = "#journalGrid";
        var journalDetailGrid;
        var accountGrid = "#accountGrid";

        // 로그정보
        var deptCode = "${sessionScope.deptCode}";
        var accountPeriodNo = "${sessionScope.periodNo}";
        var empName = "${sessionScope.empName}";
        var empCode = "${sessionScope.empCode}";

        //화폐 단위 원으로 설정 \100,000,000
        function currencyFormatter(params) {
            return '￦' + formatNumber(params.value);
        }

        function formatNumber(number) {
            // this puts commas into the number eg 1000 goes to 1,000,
            // i pulled this from stack overflow, i have no idea how it works
            return Math.floor(number)
                .toString()
                .replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');

            //Math.floor(Number(number).toLocaleString())
        }


        /* Event function 안에서 사용할 변수들 */
        var selectedSlipRow;
        var lastSelectedSlip;
        var lastSelectedJournal;
        var lastSelectedJournalDetail;
        var selectedJournalRow;
        var editRow;

        /* 날짜 */
        var date = new Date();
        var year = date.getFullYear().toString();
        //var month = (date.getMonth() + 1 > 9 ? date.getMonth() + 1 : '0' + (date.getMonth() + 1)).toString(); // getMonth()는 0~9까지
        var month = ("0" + (date.getMonth() + 1)).slice(-2);

        //var day = date.getDate() > 9 ? date.getDate() : '0' + date.getDate(); // getDate()는 1~31 까지
        var day = ("00" + date.getDate()).slice(-2)
        var today = year + "-" + month + "-" + day;


        // Map 내의 객체들 Disabled/Enabled
        function enableElement(obj) {
            console.log("enableElement(obj) 실행");
            // for key in iterable 반복문 : 객체의 모든 열거 가능한 속성에 대해 반복함. 배열요소의 순번을, 문자열이라면 키값을 뽑아냄
            // for value of iterable 반복문 : [Symbol.iterator] 속성을 가지는 컬렉션 전용, 배열의 값을 뽑아냄
            for (var key in obj)
                // prop() : JavaScript의 프로퍼티(property)를 취급 => prop("disabled",true)
                // attr() : HTML의 속성(attribute)을 취급 => attr("disabled","disabled")
                $(key).prop("disabled", !obj[key]);  //obj[key]부분은 true false밖에 올수없다.
        }


        // slipGrid
        var rowData;
        var gridOptions; //slipGrid 옵션
        var slipGrid;

        // slipGrid 생성
        function createSlip() {
            console.log("createSlip() 실행");
            rowData = [];
            var columnDefs = [
                {headerName: "전표번호", field: "slipNo", sort: "desc", resizable: true, width: 100},
                {headerName: "기수", field: "accountPeriodNo", resizable: true, width: 70},
                {headerName: "부서코드", field: "deptCode", resizable: true, width: 80},
                {headerName: "부서", field: "deptName", resizable: true, hide: true},
                {
                    headerName: "구분",
                    field: "slipType",
                    editable: true,
                    cellEditor: "agSelectCellEditor",
                    cellEditorParams: {values: ["결산", "대체", "김민재"]},
                    width: 70
                },
                {headerName: "적요", field: "expenseReport", editable: true, resizable: true},
                {headerName: "승인상태", field: "slipStatus", resizable: true, width: 100},
                {headerName: "상태", field: "status", resizable: true, hide: true},
                {headerName: "작성자코드", field: "reportingEmpCode", resizable: true, width: 100},
                {headerName: "작성자", field: "reportingEmpName", resizable: true, hide: true},
                {headerName: "작성일", field: "reportingDate", resizable: true, width: 100},
                {headerName: "직급", field: "positionCode", resizable: true, hide: true}
            ];

            gridOptions = {
                columnDefs: columnDefs,
                rowSelection: 'single', //row는 하나만 선택 가능
                defaultColDef: {editable: false}, // 정의하지 않은 컬럼은 자동으로 설정
                /*   	pagination: true, // 페이저
                   paginationPageSize: 10, // 페이저에 보여줄 row의 수 */
                stopEditingWhenGridLosesFocus: true,
                onGridReady: function (event) {// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
                    event.api.sizeColumnsToFit(); // 그리드의 사이즈를 자동으로정리 (처음 틀었을때 양쪽 폭맞춰주는거같음)
                },
                onGridSizeChanged: function (event) { // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리  (화면 비율바꿧을때 양쪽폭 맞춰주는거같음)
                    event.api.sizeColumnsToFit();
                },
                onRowClicked: function (event) {
                    console.log("sliprow선택");
                    selectedSlipRow = event.data;
                    console.log(selectedSlipRow);
                    showJournalGrid(event.data["slipNo"]);


                    if (selectedSlipRow.slipStatus != '승인완료') {
                        enableElement({
                            "#deleteSlip": true,
                            "#addJournal": true,
                            "#deleteJournal": true,
                            "#confirm": true
                        });
                    } else {
                        enableElement({
                            "#deleteSlip": false,
                            "#addJournal": false,
                            "#deleteJournal": false,
                            "#confirm": false
                        })
                    }
                    ;
                },
                onCellEditingStopped: function (event) {
                    console.log('slip편집종료');
                    if (event.data['slipType'] != "" && event.data['expenseReport'] != "") {// 이렇게하면 expenseReport값을 빈값으로 수정은 안되는거네 ..(dong)
                        $("#addJournal").prop("disabled", false); // 지정된 프로퍼티 값을 반환하거나, 전달받은값을 설정
                    }

                }
            };
            slipGrid = document.querySelector('#slipGrid');
            new agGrid.Grid(slipGrid, gridOptions);
            enableElement({
                "#addSlip": true,
                "#deleteSlip": false,
                "#addJournal": false,
                "#deleteJournal": false,
                "#showPDF": true,
            });

            gridOptions.api.setRowData([]);  // 왜 빈값으로 할당하지 ?(dong)
        }


        // 전표 추가 버튼 이벤트
        function addslipRow() {
            console.log("addslipRow() 실행");
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/account/callAccountingSettlementStatus",
                data: {
                    "accountPeriodNo": accountPeriodNo,
                    "callResult": "SEARCH"			/////// 회계결산현황 조회(SEARCH) 및 호출
                },
                dataType: "json",
                success: function (jsonObj) {
                    console.log(jsonObj);
                    console.log(jsonObj.totalTrialBalance);
                    if (jsonObj.totalTrialBalance == "Y") {
                        alert("결산이 완료가 된 상태가 입니다. \n전표추가 후 재결산을 해 주세요");
                        addslipShow();
                    } else {
                        addslipShow();
                    }
                }
            });
        }

        // 전표 빈 양식
        function addslipShow() {
            console.log("addslipShow() 실행");
            comfirm = false; //comfirm --> 확인창 안뜨게
            rowData = [];
            slipObj = {
                "slipNo": NEW_SLIP_NO,
                "accountPeriodNo": accountPeriodNo,
                "slipType": "결산",	// sliptye의 결산 삭제시 위의 event.data['slipType'] 동작
                "slipStatus": REQUIRE_ACCEPT_SLIP,
                "deptCode": deptCode,
                "reportingEmpCode": empCode,
                "reportingEmpName": empName,
                "reportingDate": today,
            };

            enableElement({"#addSlip": false});  // 버튼 비활성화 - 전표추가버튼 비활성화

            showSlipGrid(); // 근데 굳이 필요없어보임..(dong)
            var newObject = $.extend(true, {}, slipObj); //slipObj에 값이 전부 입력되면 newObject에 담긴다
            rowData.push(newObject); //rowData 집어넣는다
            gridOptions.api.applyTransaction({add: rowData});  // 행데이터를 업데이트, add/remove에 대한 목록이 있는 트랜잭션 객체를 전달
        }

        // 전표 삭제 이벤트
        function deleteSlip() {
            console.log("deleteSlip() 실행");
            console.log(selectedSlipRow);
            console.log(selectedSlipRow.slipNo);
            var selectedRows = gridOptions.api.getSelectedRows(); //내가 선택한 값을 selectRows에 담는다
            if (selectedSlipRow['slipStatus'] == "승인요청" || selectedSlipRow['slipStatus'] == "승인완료") {
                alert("전표 작성중이 아닙니다.\n현재상태 : " + selectedSlipRow['slipStatus']);
            } else {
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/account/deleteSlip",
                    data: {
                        "slipNo": selectedSlipRow.slipNo
                    },
                    dataType: "json",
                    success: function () {
                        enableElement({
                            "#addSlip": true,
                            "#deleteSlip": false,
                            "#addJournal": false,
                            "#deleteJournal": false,
                            "#createPdf": true,
                        });
                        gridOptions.api.applyTransaction({remove: [selectedSlipRow]});
                        showSlipGrid();
                    }
                });
            }
        }

        // 분개삭제
        function deleteJournal() {
            if (selectedJournalRow == null || selectedSlipRow.slipNo == NEW_SLIP_NO) {
                if (selectedJournalRow == null) {
                    alert("삭제할 분개를 선택해주세요.");
                    console.log("selectedJournalRow", selectedJournalRow);
                }
            } else {
                $.ajax({
                    type: "DELETE",
                    url: "${pageContext.request.contextPath}/account/deleteJournal",
                    data: {
                        "journalNo": selectedJournalRow["journalNo"]
                    },
                    dataType: "json",
                    success: function () {
                        console.log("deleteJournal성공");
                        enableElement({
                            "#addSlip": true,
                            "#deleteSlip": true,
                            "#addJournal": true,
                            "#deleteJournal": true,
                            "#createPdf": false,
                        });
                        gridOptions2.api.applyTransaction({remove: [selectedRow]});
                    }
                });
            }
        }

        // journalslip
        var rowData2;
        var gridOptions2 //jouranlGrid 옵션
        var journalGrid;
        var selectedJournalRow;

        //Journal 생성
        function createJournal() {
            console.log("createJournal() 실행");
            rowData2 = [];
            gridOptions2 = {
                columnDefs: [
                    {
                        headerName: "분개번호", field: "journalNo",
                        cellRenderer: 'agGroupCellRenderer',  // Style & Drop Down
                        sort: "asc", resizable: true, onCellDoubleClicked: cellDouble
                    },
                    {
                        headerName: "구분", field: "balanceDivision", editable: true,
                        cellEditor: "agSelectCellEditor",
                        cellEditorParams: {values: ["차변", "대변"]}
                    },
                    {
                        headerName: "계정코드", field: "accountCode"
                        , editable: true
                    },
                    {
                        headerName: "계정과목", field: "accountName",
                        onCellClicked: function open() {
                            $("#accountGridModal").modal('show');
                            searchAccount();
                        }
                    },
                    {
                        headerName: "차변", field: "leftDebtorPrice", editable: true,
                        valueFormatter: currencyFormatter		// 통화 값에 대한 로캘별 서식 지정 및 파싱을 제공
                    },
                    {
                        headerName: "대변", field: "rightCreditsPrice", editable: true,
                        valueFormatter: currencyFormatter
                    },
                    {headerName: "거래처코드", field: "customerCode", editable: true},
                    {headerName: "거래처", field: "customerName", hide: true},
                    {headerName: "상태", field: "status"}
                ],
                masterDetail: true,
                enableCellChangeFlash: true,
                detailCellRendererParams: {
                    detailGridOptions: {
                        rowSelection: 'single',
                        enableRangeSelection: true,		// 끌어서 선택옵션
                        pagination: true,
                        paginationAutoPageSize: true, //지정된 사이즈내에서 최대한 많은 행을 표시
                        columnDefs: [
                            {headerName: "분개번호", field: "journalNo", hide: true},
                            {headerName: "계정 설정 속성", field: "accountControlType", width: 150, sortable: true},
                            {headerName: "분개 상세 번호", field: "journalDetailNo", width: 150, sortable: true},
                            {headerName: "-", field: "status", width: 100, hide: true},
                            {headerName: "-", field: "journalDescriptionCode", width: 100, hide: true},
                            {headerName: "분개 상세 항목", field: "accountControlName", width: 150,},
                            {
                                headerName: "분개 상세 내용",
                                field: "journalDescription",
                                width: 250,
                                cellRenderer: cellRenderer
                            },
                        ],
                        defaultColDef: {
                            sortable: true,
                            flex: 1,			//  flex로 열 크기를 조정하면 해당 열에 대해 flex가 자동으로 비활성화
                        },
                        getRowNodeId: function (data) {
                            console.log("getRowNOdeId 실행");
                            // use 'account' as the row ID
                            console.log("getRowNodeId: " + data.journalDetailNo);
                            return data.journalDetailNo;
                        },
                        onRowClicked: function (event) {  // 상위 테이블에 있는 상세보기버튼으로도 실행됨.(dong)
                            console.log("onRowClicked 실행");
                            selectedJournalDetail = event.data;
                            selectedJournalRow = event.data;
                            //cellRenderer(event);  // cellRenderer 불필요한데 .. ?getDetailRowData() 와 겹침(dong)
                        },
                        onCellDoubleClicked: function (event) {
                            console.log("onCellDoubleClicked 실행");
                            var journalNo = event.data["journalNo"];
                            var detailGridApi = gridOptions2.api.getDetailGridInfo('detail_' + event.data["journalNo"]);

                            if (event.data["accountControlType"] == "SEARCH") {
                                $("#codeModal").modal('show');
                                detailGridApi.api.applyTransaction([selectedJournalDetail["journalDescription"] = searchCode()]);

                                return;
                            } else if (event.data["accountControlType"] == "SELECT") {
                                detailGridApi.api.applyTransaction([selectedJournalDetail["journalDescription"] = selectBank()]);

                                return;
                            } else if (event.data["accountControlType"] == "TEXT") {
                                var str = prompt('상세내용을 입력해주세요', '');
                                console.log('detail_' + event.data.journalDetailNo);
                                detailGridApi.api.applyTransaction([selectedJournalDetail["journalDescription"] = str]);
                                saveJournalDetailRow();

                                return;
                            } else {
                                detailGridApi.api.applyTransaction([selectedJournalDetail["journalDescription"] = selectCal()]);

                                return;
                            }

                        }
                    },
                    getDetailRowData: function (params) {
                        console.log("getDetailRowData 실행");
                        console.log(params.data.journalDetailList);
                        params.successCallback(params.data.journalDetailList); // detail table 에 값 할당
                    },
                    template: function (params) {
                        return (
                            '<div style="height: 100%; background-color: #EDF6FF; padding: 20px; box-sizing: border-box;">' +
                            '  <div style="height: 10%; padding: 2px; font-weight: bold;">분개상세</div>' +
                            '  <div ref="eDetailGrid" style="height: 90%;"></div>' +
                            '</div>'
                        );
                    },
                },
                getRowNodeId: function (data) {
                    console.log("getRowNodeId 실행");
                    // use 'account' as the row ID
                    console.log("getRowNodeId: " + data.journalNo);
                    return data.journalNo;
                },
                enterMovesDownAfterEdit: true,
                rowSelection: 'single',
                stopEditingWhenGridLosesFocus: true,
                onGridReady: function (event) {
                    event.api.sizeColumnsToFit();
                },
                onGridSizeChanged: function (event) { // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
                    event.api.sizeColumnsToFit();
                },

                onCellEditingStopped: function (event) {
                    console.log("onCellEditingStopped 실행");
                    if (event.colDef.field == 'leftDebtorPrice' || event.colDef.field == 'rightCreditsPrice') {
                        computeJournalTotal();  // 바뀐행이 이 차변,대변이면 실행하도록 수정(dong)

                    }
                    ;
                    if (event.colDef.field == 'accountCode') {// 항상 실행되던거 accouncode 수정될때만 실행되도록 수정(dong)
                        $.ajax({
                            type: "GET",
                            url: "${pageContext.request.contextPath}/account/getAccountControlList",
                            data: {
                                accountCode: event.data["accountCode"] // 계정코드
                            },
                            dataType: "json",
                            async: false,
                            success: function (jsonObj) {
                                console.log("계정검색"+JSON.stringify(jsonObj))
                                console.log(selectedJournalRow['journalNo'])
                                //jsonObj에는 account_control_code , account_control_name , account_control_type , account_control_description 만 가지고옴
                                jsonObj.forEach(function (element, index) { //accountControl은 map의 key 이름, accountControlList가 들어있음
                                    element['journalNo'] = selectedJournalRow['journalNo'];  // accountControlList에는 journalNo가 없어서 셋팅 후 아래 그리드옵션에 할당
                                })
                                gridOptions2.api.applyTransaction(selectedJournalRow['journalDetailList'] = jsonObj['accountControl']);
                            }
                        });
                        Price(event);

                    }
                    ;
                },
                onCellValueChanged: function (event) {  // onCellEditingStopped에 있으면 금액수정할때도 계속 실행되서 수정함(dong)
                    if (event.colDef.field == 'accountCode') {
                        getAccountName(event.data['accountCode']);
                    }
                },
                onRowClicked: function (event) {
                    console.log("onRowClicked 실행");
                    selectedJournalRow = event.data;
                    console.log(selectedJournalRow);
                }
            };
            journalGrid = document.querySelector('#journalGrid');
            new agGrid.Grid(journalGrid, gridOptions2);
        }

        function cellDouble(event) {
            console.log("cellDouble(event) 실행");
            if (selectedSlipRow["slipNo"] !== NEW_SLIP_NO) {
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
                        console.log(jsonObj)
                        gridOptions4.api.setRowData(jsonObj);
                    }
                });
            }
        }

        // 차변 대변 입력
        var lastIndex;
        var lastRow;

        function Price(event) {
            console.log("price(event) 실행");
            lastIndex = gridOptions2.api.getFirstDisplayedRow();  //0
            lastRow = gridOptions2.api.getRowNode(lastIndex);  // undifine  (dong)

            var sum = 0;
            if (event.data['journalNo'] != "Total") {
                if (event.data['balanceDivision'] == "차변") {
                    var price = prompt("차변의 금액을 입력해주세요", "");
                    price = price == null ? 0 : price;
                    if (!isNaN(price)) {
                        gridOptions2.api.applyTransaction([event.data['rightCreditsPrice'] = 0]);
                        gridOptions2.api.applyTransaction([event.data['leftDebtorPrice'] = price]);
                        computeJournalTotal();
                    } else {
                        alert("숫자만 입력해주세요");
                    }
                }

                if (event.data['balanceDivision'] == "대변") {
                    var price = prompt("대변의 금액을 입력해주세요", "");
                    price = price == null ? 0 : price;
                    if (!isNaN(price)) {
                        gridOptions2.api.applyTransaction([event.data['leftDebtorPrice'] = 0]);
                        gridOptions2.api.applyTransaction([event.data['rightCreditsPrice'] = price]);
                        computeJournalTotal();
                    } else {
                        alert("숫자만 입력해주세요");
                    }
                }
            }
            var totalIndex = (gridOptions2.api.getDisplayedRowCount()) - 1;
            var totalRow = gridOptions2.api.getDisplayedRowAtIndex(totalIndex); // lastRow.data 이게 먹통이라 소스 수정(dong)

            if (totalRow.data['leftDebtorPrice'] != 0 && totalRow.data['rightCreditsPrice'] != 0) {
                if (totalRow.data['leftDebtorPrice'] != totalRow.data['rightCreditsPrice']) {
                    alert("차변과 대변이 일치하지 않으면 승인이 거부될 수 있습니다.");

                }
            }
        }


        function saveSlip(confirm) {
            var JournalTotalObj = [];
            var slipStatus = confirm == "승인요청" ? confirm : null

            if (selectedSlipRow['slipStatus'] == "승인요청" || selectedSlipRow['slipStatus'] == "승인완료") {
                alert("전표 작성중이 아닙니다.\n현재상태 : " + selectedSlipRow['slipStatus']);
            } else {
                gridOptions2.api.forEachNode(function (node, index) {
                    if (index != lastIndex) {
                        JournalTotalObj.push(node.data);
                    }
                });
                if (selectedSlipRow['slipNo'] == NEW_SLIP_NO) {
                    console.log("결재신청 시작")
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/account/addSlip",
                        data: {
                            "slipObj": JSON.stringify(selectedSlipRow),
                            "journalObj": JSON.stringify(JournalTotalObj),
                            "slipStatus": slipStatus
                        },
                        async: false,		// 동기식   // 비동기식으로할경우 아래 showslipgrid에서 값을 못불러올수있다.
                        dataType: "json",
                        success: function () {
                            enableElement({"#addSlip": true});
                            showSlipGrid();
                        }
                    });
                } else if (selectedSlipRow['slipNo'] != NEW_SLIP_NO) {
                    var JournalTotalObj2 = [];
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/account/updateSlip",
                        data: {
                            "slipObj": JSON.stringify(selectedSlipRow),
                            "journalObj": JSON.stringify(JournalTotalObj),
                            "slipStatus": slipStatus
                        },
                        async: false,
                        dataType: "json",
                        success: function (jsonObj) {
                            enableElement({"#addSlip": true});
                        }
                    });
                }

                if (confirm == "승인요청") alert("결제 신청이 완료되었습니다.");
                else alert("저장 되었습니다.");
                showSlipGrid();
                showJournalGrid(selectedSlipRow.slipNo);
            }
        }

        function confirmSlip() {
            console.log("comfirmSlip() 실행");
            var result = true;
            var totalIndex = (gridOptions2.api.getDisplayedRowCount()) - 2;// count-1와 index-0 차이 및 total행 제거
            var compare = compareDebtorCredits();  // for문 안에서 계속 실행되서 밖으로 꺼냄, 한번만 실행되도됨(dong)
            var approvalStatus = compare.isEqualSum;
            gridOptions2.api.forEachNode(function (rowNode, index) { //forEachNode=forEach
                if (rowNode.data["journalNo"] != "Total") {
                    if (rowNode.data["balanceDivision"] == null) {
                        alert("구분(분개필수 항목)을 확인해주세요");
                        result = false;
                        return;
                    }
                    if (rowNode.data["accountCode"] == null) {
                        alert("계정코드(분개필수 항목)을 확인해주세요");
                        result = false;
                        return;
                    }
                    if (rowNode.data["accountName"] == null) {
                        alert("계정과목(분개필수 항목)을 확인해주세요");
                        result = false;
                        return;
                    }
                    if (!approvalStatus) {
                        alert("전표의 차변/대변 총계가 일치하지않습니다.\n 차변/대변 총계를 확인해주세요.");
                        result = false;
                        return;
                    }
                }
            });


            if (selectedSlipRow['slipStatus'] == "승인요청" || selectedSlipRow['slipStatus'] == "승인완료") {
                alert("전표 작성중이 아닙니다.\n현재상태 : " + selectedSlipRow['slipStatus']);
            } else if (result) {
                saveSlip("승인요청");
            }

        }

        function compareDebtorCredits() { // 대변차변 합계 일치 여부 확인
            console.log("compareDebotrCredits() 실행");
            var isEqualSum;
            var debtorPriceSum = 0;
            var creditsPriceSum = 0;
            gridOptions2.api.forEachNode(function (node) {
                debtorPriceSum += parseInt(node.data.leftDebtorPrice);
                creditsPriceSum += parseInt(node.data.rightCreditsPrice);
            });
            var result = {isEqualSum: debtorPriceSum == creditsPriceSum};
            return result;
        }


        //분개 추가
        function addJournalRow() {
            console.log("addJournalRow 실행");
            if (selectedSlipRow["expenseReport"] == "") {
                alert("적요란을 기입하셔야 합니다.");
                return;
            }
            var journal = gridOptions2.api.getDisplayedRowCount() == 0 ? 1 : gridOptions2.api.getDisplayedRowCount();// 현재 보여지는 로우의 수를 반환
            var journalObj = {
                "journalNo": NEW_JOURNAL_PREFIX + journal, //이부분이 분개번호
                "leftDebtorPrice": 0,  //차변 금액
                "rightCreditsPrice": 0,  //대변 금액
                "status": "insert"
            };
            var newObject2 = $.extend(true, {}, journalObj);
            gridOptions2.api.applyTransaction({add: [newObject2]});

            enableElement({
                "#addSlip": false,
                "#deleteSlip": true,
                "#addJournal": true,
                "#deleteJournal": true,
                "#createPdf": true,

            });
        }


        //찾기
        function searchSlip() {
            console.log("searchSlip() 실행");
            enableElement({
                "#addSlip": true,
                "#deleteSlip": false, //비활성화
                "#addJournal": false,
                "#deleteJournal": false,
                "#showPDF": true,
            });
            showSlipGrid();
        }

        //전표 보기
        function showSlipGrid() {   // 먼저 날짜 데이트를 받고 / 전표추가시 오늘날짜를 actual argument로 넘긴다.
            console.log("findRangedSlipList")
            $.ajax({
                type : "GET",
                url: "${pageContext.request.contextPath}/account/findRangedSlipList",
                data: {
                    "from": $("#from").val(),
                    "to": $("#to").val(),
                    "slipStatus": $("#selTag").val()
                },
                dataType: "json",
                success: function (jsonObj) {
                    console.log(jsonObj)
                    gridOptions.api.setRowData(jsonObj);
                    gridOptions2.api.setRowData([]);

                },
                async: false //비동기방식설정 - 순서대로 처리
            });
        }

        // 분개 보기
        function showJournalGrid(slipNo) { //slip rowid 선택한 전표행이다
            console.log("showJournalGrid(slipNo) 실행");
            rowData2 = [];

            var journalObj = { // footer를 사용하면 간다하게 해결할 수 있지만 ... footer를 사용하기 위해서는 돈을 내야한다. ;;
                "journalNo": "Total", //이부분이 분개번호
                "leftDebtorPrice": 0,  //차변 금액
                "rightCreditsPrice": 0,  //대변 금액
                "status": ""
            };
            var totalObject = $.extend(true, {}, journalObj); // 이렇게까지 안써도될거같은데.. .(dong)
            rowData2.push(totalObject);
            if (selectedSlipRow["slipNo"] !== NEW_SLIP_NO) {  //NEW_SLIP_NO이거 default값은 NEW 즉 NEW가 아니어야 작동한다
                $.ajax({
                    type: "GET",
                    async: false,
                    url: "${pageContext.request.contextPath}/account/findSingleJournalList",
                    data: {
                        "slipNo": slipNo
                    },
                    dataType: "json",
                    success: function (jsonObj) {
                        console.log(jsonObj)
                        jsonObj.forEach(function (element) {
                                rowData2.push(element);
                            }
                        );
                        console.log(rowData2);
                        jsonObj.forEach(function (element, index) {
                            console.log(element["journalNo"]);

                            $.ajax({
                                type: "GET",
                                async: false,
                                url: "${pageContext.request.contextPath}/account/getJournalDetailList",
                                data: {
                                    "journalNo": element["journalNo"] //rowid 분개번호임
                                },
                                dataType: "json",
                                success: function (jsonObj) {
                                    console.log(jsonObj);
                                    element.journalDetailList = jsonObj;
                                }
                            });
                        });
                    }
                });
            } else {
                var journalObj = {
                    "journalNo": NEW_JOURNAL_PREFIX + 1, //이부분이 분개번호
                    "balanceDivision": "차변",
                    "leftDebtorPrice": 0,  //차변 금액
                    "rightCreditsPrice": 0,  //대변 금액
                    "status": "insert"
                };
                var journalObj1 = {
                    "journalNo": NEW_JOURNAL_PREFIX + 2, //이부분이 분개번호
                    "balanceDivision": "대변",
                    "leftDebtorPrice": 0,  //차변 금액
                    "rightCreditsPrice": 0,  //대변 금액
                    "status": "insert"
                };
                var newJournal1 = $.extend(true, {}, journalObj);  // 굳이 이렇게 변수에 담은다음에 배열에 넣을필요있나 ?바로넣지..? 수정하쟈(dong)
                var newJournal2 = $.extend(true, {}, journalObj1);
                rowData2.push(newJournal1);
                rowData2.push(newJournal2);

            }
            gridOptions2.api.setRowData(rowData2);
            computeJournalTotal();
        }


        var rowData;

        var accountGrid;
        var gridOptionsAccount;

        function createAccountGrid() {
            rowData = [];
            var columnDefs1 = [
                {
                    headerName: "계정과목 코드", field: "accountInnerCode", sort: "asc", width: 120, resizable: true,
                    cellClass: "grid-cell-centered"
                },
                {headerName: "계정과목", field: "accountName", resizable: true, cellClass: "grid-cell-centered"},
            ];
            gridOptionsAccount = {
                columnDefs: columnDefs1,
                rowSelection: 'single', //row는 하나만 선택 가능
                defaultColDef: {editable: false}, // 정의하지 않은 컬럼은 자동으로 설정
                onGridReady: function (event) {// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
                    event.api.sizeColumnsToFit();
                },
                onGridSizeChanged: function (event) { // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
                    event.api.sizeColumnsToFit();
                },
                onRowClicked: function (event) {
                    console.log("Row선택");
                    console.log(event.data);
                    selectedRow = event.data;
                    showAccountDetail(selectedRow["accountInnerCode"]);
                }
            }

            accountGrid = document.querySelector('#accountGrid');
            new agGrid.Grid(accountGrid, gridOptionsAccount);
        }

        function showAccount() {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/account/findParentAccountList",
                dataType: "json",
                success: function (jsonObj) {
                    gridOptionsAccount.api.setRowData(jsonObj);
                }
            });
        }

        var gridOpionsAccountDetail

        function createAccountDetailGrid() {
            console.log("createAccountDetailGrid() 실행");
            rowData = [];
            var columnDefs = [
                {headerName: "계정과목 코드", field: "accountInnerCode", width: 120, sortable: true, resizable: true,},
                {headerName: "계정과목", field: "accountName", sortable: true, resizable: true,},
            ];

            gridOpionsAccountDetail = {
                columnDefs: columnDefs,
                rowSelection: 'single', //row는 하나만 선택 가능
                defaultColDef: {editable: false}, // 정의하지 않은 컬럼은 자동으로 설정
                /*                             pagination: true, // 페이저
                                            paginationPageSize: 15, // 페이저에 보여줄 row의 수 */
                onGridReady: function (event) {// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
                    event.api.sizeColumnsToFit();
                },
                onGridSizeChanged: function (event) { // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
                    event.api.sizeColumnsToFit();
                },
                onCellDoubleClicked: function (event) {
                    $("#accountGridModal").modal('hide');
                    gridOptions2.api.applyTransaction([selectedJournalRow['accountCode'] = event.data["accountInnerCode"]]);
                    gridOptions2.api.applyTransaction([selectedJournalRow['accountName'] = event.data["accountName"]]);

                    $.ajax({
                        type: "GET",
                        url: "${pageContext.request.contextPath}/account/getAccountControlList",
                        data: {
                            accountCode: event.data["accountInnerCode"] //이값이 겁색한 값이다. ex)매출
                        },
                        dataType: "json",
                        success: function (jsonObj) {
                            console.log(JSON.stringify(jsonObj))
                            console.log(selectedJournalRow['journalNo'])
                            jsonObj.forEach(function (element, index) {
                                element['journalNo'] = selectedJournalRow['journalNo'];
                            })
                            console.log(jsonObj['accountControl']);
                            gridOptions2.api.applyTransaction([selectedJournalRow['journalDetailList'] = jsonObj['accountControl']]);
                            gridOptions2.api.redrawRows();
                        }
                    });
                }
            };
            accountDetailGrid = document.querySelector('#accountDetailGrid');
            new agGrid.Grid(accountDetailGrid, gridOpionsAccountDetail);
        }


        function showAccountDetail(code) {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/account/findDetailAccountList",
                data: {
                    "code": code
                },
                dataType: "json",
                success: function (jsonObj) {
                    console.log(jsonObj)
                    gridOpionsAccountDetail.api.setRowData(jsonObj);
                }
            });
        }

        ///  모달 내부에서 검색
        function searchAccount() {
            console.log("searchAccount() 실행");
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/account/getAccountListByName",
                data: {
                    accountName: $("#accountCode").val() //이값이 겁색한 값이다. ex)매출
                },
                dataType: "json",
                success: function (jsonObj) {
                    gridOpionsAccountDetail.api.setRowData(jsonObj);
                    $("#accountCode").val(""); // 검색한다음에 지우기 셋팅(dong)
                }
            });
        }

        // 계정코드 입력시 계정과목 검색
        function getAccountName(accountCode) {
            console.log("getAccountName(accountCode) 실행");
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/account/getAccount",
                data: {
                    accountCode: accountCode
                },
                dataType: "json",
                success: function (jsonObj) {
                    var accountName = jsonObj.accountName;
                    gridOptions2.api.applyTransaction([selectedJournalRow['accountName'] = accountName]);
                },
                async: false
            });
        }


        //분개 상세

        var selectedJournalDetail;
        var gridOptions4;

        function createjournalDetailGrid() {
            console.log("createjournalDetailGrid() 실행");
            rowData = [];
            var columnDefs = [
                {headerName: "계정 설정 속성", field: "accountControlType", width: 150, sortable: true},
                {headerName: "분개 상세 번호", field: "journalDetailNo", width: 150, sortable: true},
                {headerName: "-", field: "status", width: 100, hide: true},
                {headerName: "-", field: "journalDescriptionCode", width: 100, hide: true},
                {headerName: "분개 상세 항목", field: "accountControlName", width: 150,},
                {headerName: "분개 상세 내용", field: "journalDescription", width: 250, cellRenderer: cellRenderer}
            ];


            gridOptions4 = {
                columnDefs: columnDefs,
                rowSelection: 'single', //row는 하나만 선택 가능
                defaultColDef: {editable: false}, // 정의하지 않은 컬럼은 자동으로 설정
                onGridReady: function (event) {// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
                    event.api.sizeColumnsToFit();
                },
                onGridSizeChanged: function (event) { // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
                    event.api.sizeColumnsToFit();
                },
                onRowClicked: function (event) {
                    selectedJournalDetail = event.data;

                    (event);
                    console.log('분개 상세 선택됨');
                },
                onCellDoubleClicked: function (event) {
                    console.log("onCellDoubleClicked 실행");
                    if (event.data["accountControlType"] == "SEARCH") {
                        $("#codeModal").modal('show');
                    } else if (event.data["accountControlType"] == "SELECT") {
                        gridOptions4.api.applyTransaction([selectedJournalDetail["journalDescription"] = selectBank()]);

                    } else if (event.data["accountControlType"] == "TEXT") {
                        var str = prompt('상세내용을 입력해주세요', '');
                        gridOptions4.api.applyTransaction([selectedJournalDetail["journalDescription"] = str]);
                        saveJournalDetailRow();
                    } else {
                        gridOptions4.api.applyTransaction([selectedJournalDetail["journalDescription"] = selectCal()]);
                    }

                },
            };
            journalDetailGrid = document.querySelector('#journalDetailGrid');
            new agGrid.Grid(journalDetailGrid, gridOptions4);
        }

        function cellRenderer(params) {  // 중복되는일이라 불필요(dong)
            console.log("cellRenderer(params) 실행");
            var result;
            if (params.value != null)
                result = params.value;
            else
                result = ''

            return result;
        }

        var gridOptions5;

        function createCodeGrid() {
            console.log("createCodeGrid() 실행");
            rowData = [];
            var columnDefs = [{
                headerName: "코드",
                field: "detailCode",
                width: 100,
                sortable: true,
            }, {
                headerName: "부서이름",
                field: "detailCodeName",
                width: 100,
                sortable: true,
            }];

            gridOptions5 = {//분개상세에서 직원부서 고를때
                columnDefs: columnDefs,
                rowSelection: 'single', //row는 하나만 선택 가능
                defaultColDef: {
                    editable: false
                }, // 정의하지 않은 컬럼은 자동으로 설정
                onGridReady: function (event) {// onload 이벤트와 유사 ready 이후 필요한 이벤트 삽입한다.
                    event.api.sizeColumnsToFit();
                },
                onGridSizeChanged: function (event) { // 그리드의 사이즈가 변하면 자동으로 컬럼의 사이즈 정리
                    event.api.sizeColumnsToFit();
                },
                onRowClicked: function (event) {
                    console.log("onRowClicked 실행");
                    var detailCodeName = event.data["detailCodeName"]
                    var detailCode = event.data["detailCode"]
                    gridOptions4.api.applyTransaction([selectedJournalDetail["journalDescription"] = detailCodeName]);
                    gridOptions4.api.applyTransaction([selectedJournalDetail["journalDescriptionCode"] = detailCode]);
                    saveJournalDetailRow();
                    gridOptions2.api.getDetailGridInfo('detail_' + selectedJournalRow);
                    $("#codeModal").modal('hide');
                }
            };
            codeGrid = document.querySelector('#codeGrid');
            new agGrid.Grid(codeGrid, gridOptions5);
        }

        function searchCode() {
            console.log("searchCode 실행");
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/system/base/getDetailCodeList",
                data: {
                    divisionCodeNo: selectedJournalDetail["accountControlDescription"],
                    detailCodeName: $("#searchCode").val()
                },
                dataType: "json",
                success: function (jsonObj) {
                    gridOptions5.api.setRowData(jsonObj.detailCodeList);
                },
                async: false
            });
        }

        function selectBank() {
            console.log("selectBank() 실행");
            ele = document.createElement("select");
            ele.id = "selectId"
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/base/getDetailCodeList",
                data: {
                    divisionCodeNo: selectedJournalDetail["accountControlDescription"]
                },
                dataType: "json",
                async: false,
                success: function (jsonObj) {
                    console.log(jsonObj);
                    $("<option></option>").appendTo(ele).val('').html('')  //val()은 빼도 상관없을듯(dong)
                    $.each(jsonObj.detailCodeList, function (index, obj) {
                        $("<option></option>").appendTo(ele).val(obj.detailCode + ", " + obj.detailCodeName).html(obj.detailCodeName);
                    }); //위에서 빈칸넣고 each에서 값넣고
                }
            });

            $(ele).change(function () {
                console.log("$(ele).change 실행");
                gridOptions4.api.applyTransaction([selectedJournalDetail["journalDescription"] = $(this).children("option:selected").text()]);
                gridOptions4.api.applyTransaction([selectedJournalDetail["journalDescriptionCode"] = $(this).val()]);
                saveJournalDetailRow();
            })

            return ele;
        }

        function selectCal() {
            console.log("selectCal 실행");
            ele = document.createElement("input");
            ele.type = "date"
            $(ele).change(function () {
                gridOptions4.api.applyTransaction([selectedJournalDetail["journalDescription"] = $(ele).val()]);
                saveJournalDetailRow();
            })
            return ele;
        }


        function saveJournalDetailRow() {
            console.log("saveJournalDetailRow() 실행");
            console.log(selectedJournalRow);
            var rjournalDescription;
            if (selectedJournalDetail["accountControlType"] == "SELECT" || selectedJournalDetail["accountControlType"] == "SEARCH")
                rjournalDescription = selectedJournalDetail["journalDescriptionCode"];

            else
                rjournalDescription = selectedJournalDetail["journalDescription"];

            $.ajax({
                type: "PUT",
                url: "${pageContext.request.contextPath}/account/editJournalDetail",
                data: {
                    journalNo: selectedJournalRow["journalNo"],
                    accountControlType: selectedJournalDetail["accountControlType"],
                    journalDetailNo: selectedJournalDetail["journalDetailNo"],
                    journalDescription: rjournalDescription

                },
                dataType: "json",
                async: false,
                success: function (jsonObj) {
                    console.log("분개 상세  저장 성공");
                }
            });
        }

        /*분개 합계 계산*/

        function computeJournalTotal() {
            console.log("computeJournalTotal 실행");
            var totalIndex = (gridOptions2.api.getDisplayedRowCount()) - 1;


            //표시된 행의 총 수를 반환합니다.
            var totalRow = gridOptions2.api.getDisplayedRowAtIndex(totalIndex);
            //지정된 인덱스에 표시된 RowNode를 반환합니다. 즉 마지막 total의 정보를 담고있음
            var leftDebtorTotal = 0;
            var rightCreditsTotal = 0;

            gridOptions2.api.forEachNode(function (node, index) {
                if (node != totalRow) {  //index는 숫자고 totalRow 는 arraylike인데 어떻게 비교를 한다는거지 ??무슨의미지..; node랑 비교해야되네! ,일단바꿔봄 확인필요(dong)
                    if (node.journalNO != "Total") {
                        leftDebtorTotal += parseInt(node.data.leftDebtorPrice);
                        rightCreditsTotal += parseInt(node.data.rightCreditsPrice);
                    }
                }
            });
            totalRow.setDataValue('leftDebtorPrice', leftDebtorTotal);
            totalRow.setDataValue('rightCreditsPrice', rightCreditsTotal);
        }


        // PDF로 보기
        function createPdf() {
            console.log("createPdf() 실행");
            window.open("${pageContext.request.contextPath}/base/FinancialPosition?slipNo=" + selectedSlipRow.slipNo);
        }


        function createExcel() {
            console.log("createExcel() 실행")
            var dateData = [];
            gridOptions.api.forEachNode(function (rowNode, index) {
                dateData[index] = JSON.stringify(rowNode.data);
                console.log(dateData);
            })

            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/system/base/createslipExcel",
                data: {
                    sendData : JSON.stringify(dateData),
                },
                dataType: "json",
                success: function (JSON) {
                    alert("엑셀이 생성되었습니다\n"+"파일명 : "+JSON.slipNo+".xlsx");
                },

            });

        }

        function showEmailModal(){
            $("#emailModal").modal('show')
        }

        function slipEmailFunc(parameter){

            if(parameter == "naver"){
                alert("이메일 호출"+$("#naverEmailNumber").val())
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/base/naverEmail",
                    data: {
                        "eMail" : $("#naverEmailNumber").val(),
                        "slipNo" : selectedSlipRow.slipNo
                    },
                    dataType: "json",
                    async: false,
                    success: function (jsonObj) {
                        alert("이메일 전송 성공");
                    }
                });
            }else if (parameter == "google"){
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/base/googleEmail",
                    data: {
                        "eMail" : $("#googleEmailNumber").val(),
                        "slipNo" : selectedSlipRow.slipNo
                    },
                    dataType: "json",
                    async: false,
                    success: function (jsonObj) {
                        alert("이메일 전송 성공");
                    }
                });
            }
        }


    </script>
</head>

<body class="bg-gradient-primary">
<h4>전표</h4>
<hr>
<div class="row">

    <input id="from" type="date" class="date" required style="margin-left:12px;">
    <input id="to" type="date" class="date" required>
    <select id="selTag" class="date" id="selTag">
        <option>승인여부</option>
        <option>작성중</option>
        <option>승인요청</option>
        <option>승인완료</option>
        <option>작성중(반려)</option>
    </select>
    <input type="button" id="search" value="검색" class="btn btn-Light shadow-sm btnsize2"
           style="margin-left:5px;">
</div>

<div>

    <div style="text-align:right;">

        <input type="button" id="showPDF" value="PDF보기" class="btn btn-Light shadow-sm btnsize">
        <input type="button" id="showExcel" value="Excel보기" class="btn btn-Light shadow-sm btnsize">
        <input type="button" id="email" value="이메일" class="btn btn-Light shadow-sm btnsize">
        <input type="button" id="addSlip" value="전표 추가(F2)" class="btn btn-Light shadow-sm btnsize">
        <input type="button" id="deleteSlip" value="전표 삭제" class="btn btn-Light shadow-sm btnsize">
        <input type="button" id="saveSlip" value="전표 저장(F3)" class="btn btn-Light shadow-sm btnsize">
        <input type="button" id="confirm" value="결재 신청(F4)" class="btn btn-Light shadow-sm btnsize">

    </div>
</div>
<div align="center">
    <div id="slipGrid" class="ag-theme-balham" style="height:250px;width:auto;"></div>
</div>
<hr/>
<h3>분개</h3>
<div align="right">
    <input type="button" id="addJournal" value="분개 추가" class="btn btn-Light shadow-sm btnsize">
    <input type="button" id="deleteJournal" value="분개 삭제" class="btn btn-Light shadow-sm btnsize">
    <div id="journalGrid" class="ag-theme-balham" style="height:450px;width:auto;"></div>
</div>


<div class="modal fade" id="accountGridModal" tabindex="-1" role="dialog"
     aria-labelledby="accountGridLabel" style="padding-right: 210px;">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 645px; margin-top: 130px">
            <div class="modal-header">
                <h5 class="modal-title" id="accountGridLabel">계정 코드 조회</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-header">
                <input type="text" class="form-control bg-light border-0 small" placeholder="계정과목을 입력해주세요"
                       id="accountCode" aria-label="AccountSearch" aria-describedby="basic-addon2">
                <div class="input-group-append">
                    <button class="btn btn-primary" type="button" id="Accountbtn">
                        <i class="fas fa-search fa-sm"></i>
                    </button>
                </div>
            </div>
            <div class="modal-body">
                <div style="float: left; width: 50%;">
                    <div align="center">
                        <div id="accountGrid" class="ag-theme-balham"
                             style="height: 500px; width: 100%; margin-left:-10px;"></div>
                    </div>
                </div>

                <div style="float: left; width:50%;">
                    <div align="center">
                        <div id="accountDetailGrid" class="ag-theme-balham"
                             style="height: 500px; width: 100%; margin-left:5px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div align="center" class="modal fade" id="journalDetailGridModal" tabindex="-1" role="dialog"
     aria-labelledby="journalDetailGridLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:700px;">
            <div class="modal-header">
                <h5 class="modal-title" id="journalDetailGridLabel">분개 상세</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <div align="center" id="journalDetailGrid" class="ag-theme-balham"
                     style="width:100%;height:200px"></div>
            </div>
        </div>
    </div>
</div>

<div align="center" class="modal fade" id="codeModal" tabindex="-1" role="dialog"
     aria-labelledby="codeLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="codeLabel">부서 코드 조회</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-header">
                <input type="text" class="form-control bg-light border-0 small" placeholder="부서를 입력해주세요"
                       id="searchCode" aria-label="deptSearch" aria-describedby="basic-addon2">
                <div class="input-group-append">
                    <button class="btn btn-primary" type="button" id="searchCodebtn">
                        <i class="fas fa-search fa-sm"></i>
                    </button>
                </div>
            </div>
            <div class="modal-body">
                <div align="center" id="codeGrid" class="ag-theme-balham" style="width:auto;height:150px">
                </div>
            </div>
        </div>
    </div>
</div>

<div  class="modal fade" id="emailModal" tabindex="-1" role="dialog"
      aria-labelledby="accountGridLabel" style="padding-right: 210px;">
    <div class="modal-dialog" role="document" >
        <div class="modal-content" style="width: 645px; margin-top: 130px">
            <div class="modal-header">
                <h5 class="modal-title" id="accountGridLabel">전표 전송</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-header">
                <input type="text" class="form-control bg-light border-0 small" placeholder="네이버메일을 입력해주세요"
                       id="naverEmailNumber" aria-label="AccountSearch" aria-describedby="basic-addon2">
                <div class="input-group-append">
                    <button class="btn btn-primary" type="button" id="naverEmailBtn">
                        <i class="fas fa-search fa-sm"></i>
                    </button>
                </div>
            </div>
            <div class="modal-header">
                <input type="text" class="form-control bg-light border-0 small" placeholder="구글메일을 입력해주세요"
                       id="googleEmailNumber" aria-label="AccountSearch" aria-describedby="basic-addon2">
                <div class="input-group-append">
                    <button class="btn btn-primary" type="button" id="googleEmailBtn">
                        <i class="fas fa-search fa-sm"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>