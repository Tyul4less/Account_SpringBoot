<%@ page import="org.springframework.web.context.annotation.SessionScope"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="boardlist" value="${requestScope.boardlist}" />
<html>
<head>
    <style type="text/css">
        td { font-size:0.8em; }

        th{
            font-weight: bold;
        }
        hr#line {
            height: 0.5px;
            background-color: #5D5D5D;
            opacity: 50%;
        }
        h3  {
            font-weight: bold;
        }
        .content{
            text-align:left;
        }
        a:link { color: black; text-decoration: none;}
        a:visited { color: black; text-decoration: none;}
        a:hover { color: black; text-decoration: none;}
    </style>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">


    <script type="text/javascript">
        $(document).ready(function () {
            $.ajax({
                url:"${pageContext.request.contextPath}/base/findBoardList",
                type:"POST",
                dataType:"json",
                success : function(data){
                    console.log(data)
                    var cnt=0;
                    for (var index of data){
                        var trTag=document.createElement("tr")
                        var thTag1=document.createElement("th")
                        thTag1.className="th-sm font-weight-bold text-dark"
                        thTag1.style.width="15%"
                        $(thTag1).html(cnt+1)
                        trTag.appendChild(thTag1)

                        var thTag2=document.createElement("th")
                        thTag2.className="th-sm font-weight-bold text-dark"
                        thTag2.style.width="40%"
                        var a=document.createElement("a")
                        $(a).html(index.title)
                        a.setAttribute("href","${pageContext.request.contextPath}/base/postForm.html?boardNum="+index.boardNum)
                        //var hidden=document.createElement("input")
                        //hidden.setAttribute("type","hidden")
                        //hidden.setAttribute("value","왱")//'<input type=hidden value={boardSeq:'+index.boardSeq+',refSeq:'+index.refSeq+'}>'
                        thTag2.appendChild(a)
                        //thTag2.appendChild(hidden)
                        trTag.appendChild(thTag2)

                        var thTag3=document.createElement("th")
                        thTag3.className="th-sm font-weight-bold text-dark"
                        thTag3.style.width="13%"
                        $(thTag3).html(index.writtenBy)
                        trTag.appendChild(thTag3)

                        var thTag4=document.createElement("th")
                        thTag4.className="th-sm font-weight-bold text-dark"
                        thTag4.style.width="17%"
                        var date=index.writeDate;
                        $(thTag4).html(
                            date.substring(0,4)+"년"+
                            date.substring(5,7)+"월"+
                            date.substring(8,10)+"일"+
                            date.substring(11,13)+"시"+
                            date.substring(13,15)+"분"
                        )
                        trTag.appendChild(thTag4)

                        /* 					var thTag5=document.createElement("th")
                                            thTag5.className="th-sm font-weight-bold text-dark"
                                            thTag5.style.width="15%"
                                            $(thTag5).html(0)
                                            trTag.appendChild(thTag5) */

                        document.querySelector("#list").appendChild(trTag);
                        cnt++

                    }
                    /* 				$("#tbody").empty();
                                    $("#tbody").append(html);
                                    board=data.board;
                                    console.log(board);
                                    makePage(); */
                }
            });

        });

        /* 	function makePage(){
                var html="";
                if(board.previous){
                    html += "<a href=${pageContext.request.contextPath}/base/listBoard1.html?pn="+(board.pagenum-1)+">&lt &lt </a>&nbsp&nbsp"
		}
		for(var num=board.startpage; num<(board.endpage)+1 ; num++){
			html += "<a href=${pageContext.request.contextPath}/base/listBoard1.html?pn="+num+">"+num+"</a>&nbsp&nbsp";
		}
		if(board.next){
			html += "<a href=${pageContext.request.contextPath}/base/listBoard1.html?pn="+(board.pagenum+1)+">&gt&gt</a>"
		}
		$("#page").empty();
		$("#page").append(html);
	} */

        /* 	function reFindBoardList(selectValue){
                findBoardList(selectValue);
            } */

    </script>
</head>
<body>

<h3><small>&nbsp;</small></h3>
<div class="container">
    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body">
            <h3 style="display: table; margin-left: auto; margin-right: auto;" class="display-10 text-dark font-weight-bold">게시판목록[${empCode}]</h3>
            <div class="col text-left">
                <div class="col col-sm-2 ">
                    <select class="form-control" id="selectValue" style="width: 170px" onchange="reFindBoardList(this.value)">
                        <option value="" disabled selected>정렬 선택~</option>
                        <option value="" disabled>사실 없음~</option>
                    </select>
                </div>
                <h6><small>&nbsp;</small></h6>
            </div>
            <div class="table-responsive">
                <table class="table text-center table-striped table-bordered table-darkt" id="dtBasicExample">
                    <thead>
                    <tr>
                        <th class="th-sm font-weight-bold text-dark" width="15%">번호</th>
                        <th class="th-sm font-weight-bold text-dark" width="40%">제목</th>
                        <th class="th-sm font-weight-bold text-dark" width="13%">작성자</th>
                        <th class="th-sm font-weight-bold text-dark" width="17%">작성일자</th>
                    </tr>
                    </thead>
                </table>
                <table class="table text-center table-striped table-bordered table-sm" id="dtBasicExample">
                    <thead id=list>
                    </thead>
                </table>
                <div id="page">
                    <a></a>
                </div>
            </div>
            <hr id="line"/>
            <div class="col text-right">
                <form action="${pageContext.request.contextPath}/base/boardWrite.html">
                    <input type="hidden" name="board_seq" value="0">
                    <input class="btn o-hidden border- shadow-sm" type="submit" value="글쓰기">
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>