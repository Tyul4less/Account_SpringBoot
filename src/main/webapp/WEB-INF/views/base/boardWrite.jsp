<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.springframework.web.context.annotation.SessionScope"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page import="kr.co.yooooon.base.to.BoardTO" %>
<%@ page import="kr.co.yooooon.common.to.ListForm"%>--%>
<%@ page import="java.util.ArrayList" %>

<c:set var="boardlist" value="${requestScope.boardlist}" />
<html>
<head>
    <style type="text/css">
        td { font-size:0.8em; }

        .card1 {
            background-color: #FFFFFF !important;
            border-radius: 10px;
        }
        th{
            font-weight: bold;
        }
        h3  {
            font-weight: bold;
        }
        .content{
            text-align:left;
        }
        textarea {
            height: auto;
            min-height: 200px;
            padding: 10px 10px 10px 10px;
            width: 100%;
            border-radius: 3px 3px 0 0;

        }
    </style>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">


    <script type="text/javascript">
        $(document).ready(function(){
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////edit
            if (boardNum != null){
                $.ajax({
                    url:"${pageContext.request.contextPath}/base/findPost",
                    data:{
                        "boardNum" : <%=request.getParameter("boardNum")%>,
                    },
                    dataType:"json",
                    success : function(data){

                        console.log(JSON.stringify(data))

                        var postImpl=data.postInfo[0]
                        $("#title").html(postImpl.title)
                        $("#contents").html(postImpl.contents)

                        var fileList = data.fileList
                        if (fileList != null){
                            console.log("fileList = "+JSON.stringify(fileList))

                            var num = 1;
                            var limit = fileList.length
                            var fileListTag = document.querySelector("#fileList")
                            for (var fileData of fileList){
                                if (fileData == null){
                                    continue;
                                }
                                console.log(fileData)
                                console.log(fileData.FILEORINAME)

                                var pTag = document.createElement("p")
                                pTag.innerHTML = fileData.FILEORINAME;
                                pTag.setAttribute("name", fileData.FILEURL)
                                var btnTag = document.createElement("button")//버튼만듬
                                btnTag.setAttribute("class", "close")//속성 추가
                                btnTag.setAttribute("style", "position: absolute")
                                btnTag.setAttribute("onclick", "deleteFile(this)")
                                console.log("버튼 만듬")
                                var spanTag = document.createElement("span")//span태그 만듬
                                spanTag.setAttribute("aria-hidden", "true")
                                spanTag.innerHTML="X";//x표시
                                btnTag.appendChild(spanTag)
                                console.log("span만듬")
                                pTag.appendChild(btnTag)

                                //pTag.setAttribute("onclick", "deleteFile(this)")

                                pTag.style.margin="2px";
                                $("#fileList").append(pTag)

                            }

                        }
                        ///////////////////////////////////////////////////////

                    }
                });
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //등록버튼
            $("#registButton").click(function(){
                if (boardNum == null){ registFunc } else { editFunc }
            });
            //파일 업로드 버튼
            $("#fileBtn").click(function(){
                $("#selectFile").click()
            });
            $("#return").click(function(){
                if (boardNum == null){
                    location.href="${pageContext.request.contextPath}/base/board.html";
                }else{
                    location.href="${pageContext.request.contextPath}/base/postForm.html?boardNum="+boardNum;
                }
            })

            $("input#selectFile").change(function () {
                console.log("파일 담음")
                //formData = new FormData($('#infoForm')[0]);
                if(formData == null){
                    console.log("FormData 만듬")
                    formData = new FormData();
                }
                console.log($("#selectFile")[0].files)

                if (window.FileReader) {
                    var fileBuffer = [];
                    Array.prototype.push.apply( fileBuffer, $("#selectFile")[0].files );
                    fileBuffer.forEach(function(node, index){
                        fileData.push(node)
                    })
                    console.log(fileData);
                    for (var number=0; number<$("#selectFile")[0].files.length; number++) {

                        var filename = $(this)[0].files[number].name;
                        var pTag = document.createElement("p")
                        pTag.setAttribute("id", number)//쓸일이 없어졌지만 냅두겠다 //제거할때 써먹어보자 null로해도 되겠지만...
                        pTag.innerHTML = filename;
                        pTag.setAttribute("name", filename)
                            var btnTag = document.createElement("button")//버튼만듬
                            btnTag.setAttribute("class", "close")//속성 추가
                            btnTag.setAttribute("style", "position: absolute")
                            btnTag.setAttribute("onclick", "deleteFile(this)")
                            console.log("버튼 만듬")
                                var spanTag = document.createElement("span")//span태그 만듬
                                spanTag.setAttribute("aria-hidden", "true")
                                spanTag.innerHTML="X";//x표시
                                btnTag.appendChild(spanTag)
                                console.log("span만듬")
                            pTag.appendChild(btnTag)

                        //pTag.setAttribute("onclick", "deleteFile(this)")

                        pTag.style.margin="2px";
                        $("#fileList").append(pTag)
                    }
                } else {
                        var filename = $(this).val().split('/').pop().split('\\').pop();
                }
                $("#selectFile").val("");
            });
        })

        var formData;
        var fileData = [];
        var fileSendData = [];
        var updateFileData = [];

        var todayTime = new Date();
        var rrrr = todayTime.getFullYear();
        var mm = todayTime.getMonth()+1;
        var dd = todayTime.getDate();
        var today = rrrr+"-"+addZeros(mm,2)+"-"+addZeros(dd,2);

        function adjustHeight() {
            textEle = $('textarea');
            textEle[0].style.height = 'auto';
            var textEleHeight = textEle.prop('scrollHeight');
            textEle.css('height', textEleHeight);
        };

        $("#textEle").on('keyup', function() {
            adjustHeight();
        });


        var fileData=[];
        var sendData={};
        function setInfo(){
            sendData.writeDate = today
            sendData.title =  $("#title").val()
            sendData.contents = $("#contents").val()
            sendData.id = "${empCode}"
        }


        var boardNum = <%=request.getParameter("boardNum")%>;

        //저장 눌렀을때 시작
        function registFunc(){
            console.log("저장 시도")
            setInfo()
            if(sendData.title == ""){
                Swal.fire({
                    icon : '경고',
                    title : '경고: 제목 없음',
                    text : '제목을 기재해주세요.',
                    footer : '<a href>관리자에게 문의하세요</a>'
                })
                return
            }

            //console.log("파일 있을거 아녀"+JSON.stringify($("#infoForm")[0]))
            if (fileData != null){
                for (var x = 0; x < fileData.length; x++) {
                        // 삭제 안한것만 담아 준다.
                        formData.append("article_file", fileData[x]);
                    }
                    for (var pair of formData.entries()) {
                        console.log(pair[0]);
                        console.log(pair[0]);
                    }
                    console.log("업로드 시도")

                    $.ajax({
                        type: "POST",
                        enctype: 'multipart/form-data',
                        url : "${pageContext.request.contextPath }/common/boardFileUpload",
                        data: formData,
                        processData: false,
                        contentType: false,
                        cache: false,
                        async: false,
                        success: function (result) {
                            console.log(JSON.stringify("파일 경로"+JSON.stringify(result)))
                            fileSendData = [];
                            result.forEach(function(node, index){
                                var path = node.fileUrl.replace("\\\\\\\\", "\\")
                                var fileOriname = node.fileOriname
                                var array = {"fileUrl":path, "fileOriname":fileOriname}
                                fileSendData.push(array)
                                console.log(array)
                            })

                            setInfo()
                            $.ajax({
                                type:"POST",
                                url:"${pageContext.request.contextPath}/base/registBoard",
                                data:{
                                    "sendData" : JSON.stringify(sendData),
                                    "fileSendData" : JSON.stringify(fileSendData)
                                },
                                dataType:"json",
                                success : function(data){
                                    console.log("작동 좀 되어라")
                                    console.log(data)
                                    if(data.errorCode<0){
                                        alert("게시글이 등록에 실패했습니다!");
                                        Swal.fire({
                                            icon : '실패',
                                            title : '실패: 등록 실패',
                                            text : '게시글이 등록에 실패했습니다!',
                                            footer : '<a href>관리자에게 문의하세요</a>'
                                        })
                                    }
                                    location.href="${pageContext.request.contextPath}/base/board.html";
                                }
                            });

                        }

                    });
                }else{

                    setInfo()
                    $.ajax({
                        type:"POST",
                        url:"${pageContext.request.contextPath}/base/registBoard",
                        data:{
                            "sendData" : JSON.stringify(sendData)
                        },
                        dataType:"json",
                        success : function(data){
                            console.log("작동 좀 되어라")
                            console.log(data)
                            if(data.errorCode<0){
                                alert("게시글이 등록에 실패했습니다!");
                            }
                            location.href="${pageContext.request.contextPath}/base/board.html";
                        }
                    });
                }
        }

        function editFunc(){
            console.log("업데이트 시도")
            setInfo()
            if(sendData.title == ""){
                Swal.fire({
                    icon : '경고',
                    title : '경고: 제목 없음',
                    text : '제목을 기재해주세요.',
                    footer : '<a href>관리자에게 문의하세요</a>'
                })
                return
            }

            if (fileData != null){
                for (var x = 0; x < fileData.length; x++) {
                    // 삭제 안한것만 담아 준다.
                    formData.append("article_file", fileData[x]);
                }
                for (var pair of formData.entries()) {
                    console.log(pair[0]);
                }
                console.log("업로드 시도")

                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    url : "${pageContext.request.contextPath }/common/boardFileUpload",
                    data: formData,
                    processData: false,
                    contentType: false,
                    cache: false,
                    async: false,
                    success: function (result) {
                        console.log(JSON.stringify("파일 경로"+JSON.stringify(result)))
                        fileSendData = [];
                        result.forEach(function(node, index){
                            var path = node.fileUrl.replace("\\\\\\\\", "\\")
                            var fileOriname = node.fileOriname
                            var array = {"fileUrl":path, "fileOriname":fileOriname}
                            fileSendData.push(array)
                            console.log(array)
                        })

                        setInfo()
                        $.ajax({
                            type:"POST",
                            url:"${pageContext.request.contextPath}/base/updateBoard",
                            data:{
                                "sendData" : JSON.stringify(sendData),
                                "boardNum" : boardNum,
                                "fileSendData" : JSON.stringify(fileSendData),
                                "updateFileData" : JSON.stringify(updateFileData),
                            },
                            dataType:"json",
                            success : function(data){
                                console.log("작동 좀 되어라")
                                console.log(data)
                                if(data.errorCode<0){
                                    alert("게시글이 등록에 실패했습니다!");
                                    Swal.fire({
                                        icon : '실패',
                                        title : '실패: 등록 실패',
                                        text : '게시글이 등록에 실패했습니다!',
                                        footer : '<a href>관리자에게 문의하세요</a>'
                                    })
                                }
                                location.href="${pageContext.request.contextPath}/base/board.html";
                            }
                        });

                    }

                });
            }else{

                setInfo()
                $.ajax({
                    type:"POST",
                    url:"${pageContext.request.contextPath}/base/registBoard",
                    data:{
                        "sendData" : JSON.stringify(sendData)
                    },
                    dataType:"json",
                    success : function(data){
                        console.log("작동 좀 되어라")
                        console.log(data)
                        if(data.errorCode<0){
                            alert("게시글이 등록에 실패했습니다!");
                        }
                        location.href="${pageContext.request.contextPath}/base/board.html";
                    }
                });
            }
        }

        function addZeros(num, digit) {
            var zero = '';
            num = num.toString();
            if (num.length < digit) {
                for (i = 0; i < digit - num.length; i++) {
                    zero += '0';
                }
            }
            return zero + num;
        }

        var fileBuffer=[];

        function deleteFile(v){
            var pTag = $(v).parent()
            console.log("작동")
            //console.log($("#selectFile")[0].files)
            $(pTag).remove();
            var target = $(pTag).attr('name');
            console.log(target)
            //var isSuccess = false
            if( $("#pTag").attr("id") !=null ){
                for(var cnt=0; cnt < fileData.length; cnt++) {
                    if (fileData[cnt].name == target) {
                        fileData.splice(cnt, 1);
                        isSuccess = true
                    }
                }
            }else{
                updateFileData.push( $("#pTag".attr("name")) )
            }
/*            if (!isSuccess){
                updateFileData.push(target)
            }*/
                console.log(fileData)
                console.log(updateFileData)
            //Array.prototype.push.apply( fileBuffer, $("#selectFile")[0].files );
            //var file = fileBuffer.splice(id, 1);
            //console.info( file.name + ", " + file.size + ", " + file.type );
        }



    </script>
</head>
<body>
<h3><small>&nbsp;</small></h3>
<div class="container">
    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body">
            <h3 class="display-10 text-dark font-weight-bold" style="display: table; margin-left: auto; margin-right: auto;">글쓰기</h3>
            <div class="col text-left">
                <div class="col col-sm-2 ">

                </div>
                <h6><small>&nbsp;</small></h6>
            </div>
            <div class="table-responsive">
                <table class="table text-center table-striped table-bordered table-sm" id="dtBasicExample">
                    <thead>
                    <tr>
                        <th class="th-sm font-weight-bold text-dark" width="10%" style="border: 2px black solid">제목</th>
                        <th class="th-sm font-weight-bold text-dark" width="90%">
                            <input type=text id="title" style="border:0px; width:100%;">
                        </th>
                    </tr>
                    </thead>
                </table>
                <table class="table text-center table-striped table-bordered table-sm" id="dtBasicExample">
                    <thead>
                    <tr>
                        <th width="10%"  style="border: 2px black solid;">
                            <input class="th-sm font-weight-bold text-dark" type=button value=업로드 style="padding: 3px;border: none;background: none; margin-top: -1px;">
                        </th>

                        <th width="10%">
                            <form id="infoForm" method="post" enctype="multipart/form-data">
                                <input class="th-sm font-weight-bold text-dark" type=button value=파일 style="padding: 3px;border: none;background: none;" id="fileBtn">
                                <input id="selectFile" name="file" type="file" style="display: none;" multiple>
                            </form>
                        </th>

                        <th class="th-sm font-weight-bold text-dark" width="80%" style="text-align: left;" id="fileList">

                        </th>
                    </tr>
                    </thead>
                    <tbody id="tbody"></tbody>
                </table>
                <div id="page"></div>
            </div>
            <textarea class="card1" placeholder="글을 적으세요" onkeyup="adjustHeight()"  id="contents" style="overflow:hidden"></textarea>
            <div class="col text-right" style="float:right">
                <input style="width: 100px; float:right; margin-left: 10px"  class="btn btn-user btn-block btn-primary" type="button" value="등록" id="registButton">
                <input style="width: 100px; float:right; margin: auto"  class="btn btn-user btn-block btn-primary" type="button" value="취소" id="return">
            </div>
        </div>
    </div>
</div>
</body>
</html>