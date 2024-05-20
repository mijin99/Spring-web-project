<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Read</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Read Page</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<div class="form-group">
				<label>Bno</label><input class="form-control" name='bno' value='<c:out value="${board.bno }"/>' readonly="readonly">
				</div>
				<div class="form-group">
				<label>Tilte</label><input class="form-control" name='title' value='<c:out value="${board.title }"/>' readonly="readonly">
				</div>
				<div class="form-group">
				<label>Text area</label><textarea class="form-control" rows="3" name='content' readonly="readonly">
				<c:out value="${board.content }"/></textarea>
				</div>
				<div class="form-group">
				<label>Writer</label><input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly">
				</div>
				<button data-oper='modify' class="btn btn-default"> Modify</button>
				<button data-oper='list' class="btn btn-info">List</button>
				
				<!-- 버튼에 링크를 처리하는 방식이었지만, form 태그로 수정 -->
				<form id='operForm' action="/board/modify" method= "get">
					<input type="hidden" id="bno" name='bno' value='<c:out value="${board.bno}"/>' >
					<input type="hidden" name='pageNum' value='<c:out value="${cri.pageNum}"/>' >
					<input type="hidden" name='amount' value='<c:out value="${cri.amount}"/>' >
				</form>		<!-- id는 -->
			</div>
			<!-- end panel body -->
		</div>
		<!-- end panel-body -->
	</div>
	<!-- end panel -->
</div>
<!-- end row -->

<!-- operForm javascript작성 -->
<script type="text/javascript">
$(document).ready(function(){
	var operForm = $("#operForm"); //form 태그의 id #붙이는 이유? 
	$("button[data-oper='modify']").on("click",function(e){   //태그button에 속성 data-oper이 modify인 것의 클릭 함수
		operForm.attr("action","/board/modify").submit();        //폼태그 action 속성 지정하여 submit 
	});
	$("button[data-oper='list']").on("click",function(e){     //태그 button에 속성 data-oper이 list인 것의 클릭 함수
		operForm.find("#bno").remove();					      //태그중 #bno를 찾아서 remove (리스트 페이지에 전달 필요하지 않아서)
		operForm.attr("action","/board/list")                 //폼태그 액션속성 지정
		operForm.submit();
	});
});
</script>

<%@include file="../includes/footer.jsp"%>