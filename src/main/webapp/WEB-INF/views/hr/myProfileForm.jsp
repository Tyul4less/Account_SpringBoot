`<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="https://malsup.github.io/jquery.form.js"></script>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title> 내정보관리</title>
    <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
    <%--<script src="../../../assets/plugins/jquery-3.6.0.min.js"></script>--%>

    <script>

        $(document).ready(function(){


            $("#UpdateEmployee").click(UpdateEmployee); //저장 버튼 클릭시 UpdateEmployee 함수 호출
            $("#reset").click(reset); //취소버튼 클릭시 reset함수 호출
            $.ajax({
                type:"POST",
                dataType:"json",
                url: "${pageContext.request.contextPath}/hr/findEmployee",
                data: {
                    "empCode": "${empCode}"
                },
                success: function(obj){
                    console.log(JSON.stringify(obj))
                    setInfo(obj);
                    if(obj.image != null || obj.image != ""){
                        $("#preview-image").attr("src",${pageContext.request.contextPath}obj.image);
                    }
                },
                error: function(){
                    alert("조회 오류!");
                }
            });

            // input file에 change 이벤트 부여
            const inputImage = document.getElementById("selectProfileImage")
            inputImage.addEventListener("change", e => {
                readImage(e.target)
            });

            $("input#selectProfileImage").change(function () {
                $("#emp_img_empCode").val($("#empCode").val())
                formData = new FormData($('#infoForm')[0]);
            });

        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        function imageUploadTrigger() {
            $("input#selectProfileImage").click();
            //document.getElementById('imageForm').submit();
        }











        function setInfo(employee){ //폼태그에 데이터 셋팅
            $("#empName").val(employee.empName);
            $("#empCode").val(employee.empCode);
            $("#eMail").val(employee.email);
            $("#userPw").val(employee.userPw);
            $("#phoneNumber").val(employee.phoneNumber);
            $("#socialSecurityNumber").val(employee.socialSecurityNumber);
            $("#zipCode").val(employee.zipCode);
            $("#basicAddress").val(employee.basicAddress);
            $("#detailAddress").val(employee.detailAddress);

            $("#companyCode").val(employee.companyCode);
            $("#workPlaceCode").val(employee.workPlaceCode);
            $("#deptCode").val(employee.deptCode);
            $("#positionCode").val(employee.positionCode);
            $("#birthDate").val(employee.birthDate);
            $("#userOrNot").val(employee.userOrNot);
            $("#deptName").val(employee.deptName);
            $("#positionName").val(employee.positionName);
            $("#image").val(employee.image);
        }




        var empBean={};
        function saveInfo(){
            empBean.empCode=$("#empCode").val();
            empBean.empName=$("#empName").val();
            empBean.userPw=$("#userPw").val();
            empBean.basicAddress=$("#basicAddress").val();
            empBean.detailAddress=$("#detailAddress").val();
            empBean.zipCode=$("#zipCode").val();
            empBean.eMail=$("#eMail").val();
            empBean.socialSecurityNumber=$("#socialSecurityNumber").val()

            empBean.companyCode = $("#companyCode").val();
            empBean.workPlaceCode = $("#workPlaceCode").val();
            empBean.deptCode = $("#deptCode").val();
            empBean.positionCode = $("#positionCode").val();
            empBean.birthDate = $("#birthDate").val();
            empBean.userOrNot = $("#userOrNot").val();
            empBean.deptName = $("#deptName").val();
            empBean.positionName = $("#positionName").val();
            empBean.image = $("#image").val();
        }

        var formData;

        function UpdateEmployee(){

            saveInfo();
            var fileCheck = document.getElementById("selectProfileImage").value
            if (fileCheck!=""){
                $("#emp_img_empCode").val(empBean.empCode)
                console.log("업로드 시도")
                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    url : "${pageContext.request.contextPath }/common/imgFileupload",
                    data: formData,
                    processData: false,
                    contentType: false,
                    cache: false,
                    async: false,
                    success: function (result) {
                        console.log("파일 업로드 데이터"+JSON.stringify(result))
                        if($("#preview-image").attr("src") != "https://dummyimage.com/500x500/ffffff/000000.png&text=preview+image")
                            empBean.image = result.url;
                    }
                });
            }
            console.log("회원 정보 업데이트 데이터"+JSON.stringify(empBean))
            $.ajax({
                type:"POST",
                url : "${pageContext.request.contextPath}/hr/batchEmpInfo",
                data : {
                    "sendData" : JSON.stringify(empBean)
                },
                dataType : "json",
                async: false,
                success : function(data) {

                    alert("사원을 등록했습니다");
                    location.reload();
                }
            });
        }

        function zipCodeListFunc() { //우편번호검색 함수
            new daum.Postcode({
                oncomplete : function(data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
                    // 예제를 참고하여 다양한 활용법을 확인해 보세요.
                    $("#zipCode").val(data.zonecode);
                    $("#basicAddress").val(data.address);
                    $("#detailAddress").val(data.buildingName);
                }
            }).open();
        }

        function reset(){ //취소버튼 누르면 페이지가 리로드 된다.
//             	 console.log("취소버튼실행");
            location.reload();
        }

        function inputPhoneNumber(obj) { //하이픈이 자동으로 생기게 하는 함수, 숫자값만 입력되는 기능 추가해야함

            var number = obj.value.replace(/[^0-9]/g, "");
            var phone = "";


            if(number.length < 4) {
                return number;
            } else if(number.length < 7) {
                phone += number.substr(0, 3);
                phone += "-";
                phone += number.substr(3);
            } else if(number.length < 11) {
                phone += number.substr(0, 3);
                phone += "-";
                phone += number.substr(3, 3);
                phone += "-";
                phone += number.substr(6);
            } else {
                phone += number.substr(0, 3);
                phone += "-";
                phone += number.substr(3, 4);
                phone += "-";
                phone += number.substr(7);
            }
            obj.value = phone;
        }



        function readImage(input) {
            // 인풋 태그에 파일이 있는 경우
            if(input.files && input.files[0]) {
                // 이미지 파일인지 검사 (생략)
                // FileReader 인스턴스 생성
                const reader = new FileReader()
                // 이미지가 로드가 된 경우
                reader.onload = e => {
                    const previewImage = document.getElementById("preview-image")
                    previewImage.src = e.target.result
                }
                // reader가 이미지 읽도록 하기
                reader.readAsDataURL(input.files[0])
            }
        }



    </script>
</head>

<body class="bg-gradient-primary">
<div class="container" style="width:auto;height:300px" >

    <div class="card o-hidden border-0 shadow-lg my-5" >
        <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
                <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <%--action="${pageContext.request.contextPath }/base/imgFileupload.do" enctype="multipart/form-data"--%>

                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0">
                                    <label for="Name">Preview</label>
                                    <%--<input type="hidden" name="method" value="image">--%>
                                    <div id="profileImageArea">
                                        <div class="row imageControl">
                                            <div class="image-container">
                                                <img style="width: 250px; padding:10px;" id="preview-image" src="https://dummyimage.com/500x500/ffffff/000000.png&text=preview+image" >
                                            </div>
                                        </div>
                                        <!--<input type="text" class="form-control form-control-user" id="empName" readonly>-->
                                    </div>
                                </div>
                                <div class="col-sm-6 mb-3 mb-sm-0"><!--------------------------------------------------------------------사진업로드-->
                                    <form id="infoForm" method="post" enctype="multipart/form-data"> <!--FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFORM>-->
                                        <label for="Name">프로필 사진</label>    <i class="fa fa-upload" aria-hidden="true"></i>
                                        <input type="hidden" name="empCode" id="emp_img_empCode">
                                        <div>
                                            <button id="selectProfileButton" class="btn btn-primary" type="button" onclick=imageUploadTrigger()>
                                                <i class="fa fa-upload" aria-hidden="true"></i>
                                            </button>
                                            <input id="selectProfileImage" name="image" type="file" style="display: none;" accept="image/gif, image/jpeg, image/png">
                                        </div>
                                    </form>
                                </div>
                            </div>

                        <div class="form-group row">
                            <div class="col-sm-6 mb-3 mb-sm-0">
                                <label for="Name">이름</label>
                                <input type="text" class="form-control form-control-user" id="empName" readonly>
                            </div>
                            <div class="col-sm-6">
                                <label for="JuminNum">주민번호</label>
                                <input type="text" class="form-control form-control-user" id="socialSecurityNumber">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-6 mb-3 mb-sm-0">
                                <label for="Code">코드</label>
                                <input type="text" class="form-control form-control-user" id="empCode" readonly>
                            </div>
                            <div class="col-sm-6">
                                <label for="Password">비밀번호</label>
                                <input type="password" class="form-control form-control-user" id="userPw" >
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="Email">이메일</label>
                            <input type="email" class="form-control form-control-user" id="eMail" placeholder="입력된 정보가 없습니다">
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-6 mb-3 mb-sm-0">
                                <label for="zipCode" class="col-form-label">우편번호</label>
                                <input type="text" class="form-control form-control-user" id="zipCode" readonly>
                            </div>
                            <div class="col-sm-6" style="margin-top:43px; margin-left:-12px;">
                                <button class="btn btn-primary" type="button" onclick="zipCodeListFunc()">
                                    <i class="fas fa-search fa-sm"></i>
                                </button>
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-sm-6 mb-3 mb-sm-0">
                                <label for="basicAddress">기본주소</label>
                                <input type="text" class="form-control form-control-user" id="basicAddress" readonly>
                            </div>
                            <div class="col-sm-6">
                                <label for="detailAddress">상세주소</label>
                                <input type="text" class="form-control form-control-user" id="detailAddress" >
                            </div>
                        </div>
                        <hr>
                        <button class="btn btn-user btn-block btn-primary" id="UpdateEmployee">
                            저장
                        </button>
                        <button class="btn btn-user btn-block btn-danger " id="reset">
                            취소
                        </button>
                        <input type="text" id="companyCode" style="display:none">
                        <input type="text" id="workPlaceCode" style="display:none">
                        <input type="text" id="deptCode" style="display:none">
                        <input type="text" id="positionCode" style="display:none">
                        <input type="text" id="birthDate" style="display:none">
                        <input type="text" id="image" style="display:none">
                        <input type="text" id="userOrNot" style="display:none">
                        <input type="text" id="deptName" style="display:none">
                        <input type="text" id="positionName" style="display:none">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>

`