<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.springframework.web.context.annotation.SessionScope"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>

<c:set var="boardlist" value="${requestScope.boardlist}" />
<html>
<head>
    <style type="text/css">
        #line{opacity:0;}
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
            $("#editButton").click(function(){
                location.href="${pageContext.request.contextPath}/base/boardWrite.html?boardNum=<%=request.getParameter("boardNum")%>"
            });
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

                            var aTag = document.createElement("a")
                            aTag.setAttribute("href", "${pageContext.request.contextPath}/utill/fileDownload"+"?localPath="+fileData.FILEURL+"&fileOriname="+fileData.FILEORINAME)
                            aTag.innerHTML=num+". "+fileData.FILEORINAME;
                            fileListTag.appendChild(aTag)
                            if (num != limit) {
                                var brTag = document.createElement("br")
                                fileListTag.appendChild(brTag)
                                num++
                            }

                        }

                    }
                }
            });
            $("#backButton").click(function(){
                location.href="${pageContext.request.contextPath}/base/board.html"
            });
        })

        function adjustHeight() {
            textEle = $('textarea');
            textEle[0].style.height = 'auto';
            var textEleHeight = textEle.prop('scrollHeight');
            textEle.css('height', textEleHeight);
        };


    </script>
</head>
<body>
<h3><small>&nbsp;</small></h3>
<div class="container">
    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body">
            <div class="card-body">
                <button class="btn btn-outline dark-text font-weight-bold text-dark" id="backButton" style="float:left; margin: -30px 0px 0px -30px">
                    <img src="https://www.pinpng.com/pngs/b/88-881472_menu-icon-png.png" style="width:10px; height:20px">
                </button>

                <div class="col text-left">
                    <div class="col col-sm-2 ">

                    </div>
                    <h6><small>&nbsp;</small></h6>
                </div>
                <div class="table-responsive">
                    <table class="table text-center table-striped table-bordered table-sm" id="dtBasicExample">
                        <thead>
                            <tr>
                                <th class="th-sm font-weight-bold text-dark" width="10%">제목</th>
                                <th class="th-sm font-weight-bold text-dark" width="90%" id=title></th>
                            </tr>
                        </thead>
                    </table>
                    <table class="table text-center table-striped table-bordered table-sm" id="dtBasicExample">
                        <tbody id="tbody">
                            <thead>
                                <tr>
                                    <th class="th-sm font-weight-bold text-dark" width="10%">파일</th>
                                    <th class="th-sm font-weight-bold text-dark" width="90%" id=fileList style="text-align: left;"></th>
                                </tr>
                            </thead>
                        </tbody>
                    </table>
                    <div id="page"></div>
                </div>
                <textarea class="card1" onchange="adjustHeight()" id="contents" readonly style="overflow:hidden;"></textarea>
                <div class="col text-right">
                    <input style="width: 100px; float:right; margin-left: 10px" class="btn btn-user btn-block btn-primary" type="button" value="수정" id="editButton" >

                    <%-- 					<form action="${pageContext.request.contextPath}/base/registForm.html">
                                            <input type="hidden" name="board_seq" value="0">
                                            <input class="btn btn-outline dark-text font-weight-bold text-dark" type="submit" value="등록">
                                        </form> --%>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>