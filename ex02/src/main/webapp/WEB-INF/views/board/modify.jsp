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
				<form role="form" action="/board/modify" method="post">
					<input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum }"/>' >
					<input type='hidden' name='amount' value='<c:out value="${cri.amount }"/>' >
					<input type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
           			<input type='hidden' name='keyword' value='<c:out value="${cri.keyword }"/>'>
					
					<div class="form-group">
					<label>Bno</label>
					<input class="form-control" name='bno' value='<c:out value="${board.bno }"/>' readonly="readonly">
					</div>
					<div class="form-group">
					<label>Title</label>
					<input class="form-control" name='title' value='<c:out value="${board.title }"/>'>
					</div>
					<div class="form-group">
					<label>Text area</label><textarea class="form-control" rows="3" name='content' >
					<c:out value="${board.content }"/></textarea>
					</div>
					<div class="form-group">
					<label>Writer</label>
					<input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly">
					</div>
					<div class="form-group">
					<label>RegDate</label>
					<input class="form-control" name='regDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.regdate }"/>' readonly="readonly">
					</div>
					<div class="form-group">
					<label>Update Date</label>
					<input class="form-control" name='updateDate' value='<fmt:formatDate pattern="yyyy/MM/dd" value="${board.updatedate }"/>' readonly="readonly">
					</div>
					<button type="submit" data-oper='modify' class="btn btn-default" >Modify</button>
					<button type="submit" data-oper='remove' class="btn btn-danger" >Remove</button>
					<button type="submit" data-oper='list' class="btn btn-info" >List</button>
				</form>
				
			</div>
			<!-- end panel body -->
		</div>
		<!-- end panel-body -->
	</div>
	<!-- end panel -->
</div>
<!-- end row -->

<script type="text/javascript">
$(document).ready(function(){
	var formObj =$("form"); //폼 태그에서 받은거 ?
	$('button').on("click",function(e){
		e.preventDefault(); //새로고침 하거나 재실행 되는 것을 막음 (고유동작 제어)
		var operation  = $(this).data("oper");
		console.log("오퍼레이션 로그"+operation); //오퍼가 뭘까? 로그확인 
		
		if(operation ==='remove'){
			formObj.attr("action","/board/remove");
		}
		else if( operation ==='list'){
			//move to list
			//self.location ="/board/list";
			//다시 목록으로 가면 필요 파라미터만 전송하기 위해 폼내용 지우고 다시 작성
			formObj.attr("action","/board/list").attr("method","get");
			var pageNumTag =$("input[name='pageNum']").clone(); //다른 내용 삭제하고 이 두개만 담음
			var amountTag =$("input[name='amount']").clone();
			var keywordTag =$("input[name='keyword']").clone();
			var typeTag =$("input[name='type']").clone();
			formObj.empty(); //안에 내용 싹 비우기
			formObj.append(pageNumTag);
			formObj.append(amountTag);
			formObj.append(keywordTag);
			formObj.append(typeTag);
		}
		formObj.submit();
		});
	});
</script>

<%@include file="../includes/footer.jsp"%>