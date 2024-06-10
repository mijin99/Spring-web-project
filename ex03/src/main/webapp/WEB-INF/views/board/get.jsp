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
			<div class="panel-heading">
				heading
			</div>
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
					<input type='hidden' name='type' value='<c:out value="${cri.type }"/>'>
           			<input type='hidden' name='keyword' value='<c:out value="${cri.keyword }"/>'>
				</form>		<!-- id는 -->
			</div>
			<!-- end panel body -->
		</div>
		<!-- end panel-body -->
	</div>
	<!-- end panel -->
</div>
<!-- end row -->

<div class='row'>
	<div class="col-lg-12">
		<!--./ panel -->
		<div class="panel panel-default">
			 <div class="panel-heading">
        		<i class="fa fa-comments fa-fw"></i> Reply
        		<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New Reply</button>
      		</div>    
			
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<ul class="chat">
					<!-- start reply -->
				<!-- <li class="left clearfix" data-rno='12'>
						<div>
							<div class="header">
								<strong class="primary-font">user00</strong> 
								<small class="pull-right text-muted">2018-01-01 13:13</small>
							</div>
							<p>Good job!</p>
						</div>
					</li> -->	
					<!-- end reply -->
				</ul>
				<!-- end ul -->
			</div>
		<!-- ./panel .char-panel -->
		<div class="panel-footer"></div>
	</div>
</div>
	<!-- ./end row -->

<!-- 댓글 추가 모달창 -->

<!-- Modal -->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
        aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">&times;</button>
              <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>
            <div class="modal-body">
              <div class="form-group">
                <label>Reply</label> 
                <input class="form-control" name='reply' value='New Reply!!!!'>
              </div>      
              <div class="form-group">
                <label>Replyer</label> 
                <input class="form-control" name='replyer' value='replyer'>
              </div>
              <div class="form-group">
                <label>Reply Date</label> 
                <input class="form-control" name='replyDate' value='2018-01-01 13:13'>
              </div>
      
            </div>
<div class="modal-footer">
        <button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
        <button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
        <button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
        <button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
      </div>          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->


<!-- jsp 파일 모듈화 -> 댓글처리 -->
<script type="text/javascript" src="/resources/js/reply.js"></script>
<script type="text/javascript"> 
	$(document).ready(function(){
		//reply.js에 즉시실행함수 실행 확인 
		console.log(replyService);
	});
</script>
<script>
$(document).ready(function(){
	var bnoValue='<c:out value="${board.bno}"/>';
	var replyUL =$(".chat");
	showList(1);
	function showList(page){
		replyService.getList({bno:bnoValue,page:page||1},function(list){
			var str="";
		if(list==null ||list.length==0){
			replyUL.html("");
			return;
		}
		for(var i=0, len=list.length||0; i<len; i++){
			str+="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
			str+="<div><div class='header'><strong class='primary-font'>"+list[i].replyer+"</strong>";
			str+="<small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
			str+=" <p>"+list[i].reply+"</p></div></li>";
		}
		replyUL.html(str);
		}); //end of function
	} //end of show list
	
	//<!-- 모달 댓글창처리  -->
    var modal = $(".modal");
    var modalInputReply = modal.find("input[name='reply']");
    var modalInputReplyer = modal.find("input[name='replyer']");
    var modalInputReplyDate = modal.find("input[name='replyDate']");
    
    var modalModBtn = $("#modalModBtn");
    var modalRemoveBtn = $("#modalRemoveBtn");
    var modalRegisterBtn = $("#modalRegisterBtn");
    
    $("#modalCloseBtn").on("click", function(e){
    	modal.modal('hide');
    });
    
    $("#addReplyBtn").on("click", function(e){
      modal.find("input").val("");
      modalInputReplyDate.closest("div").hide();
      modal.find("button[id !='modalCloseBtn']").hide();
      modalRegisterBtn.show();
      $(".modal").modal("show");
      
    });
    

    modalRegisterBtn.on("click",function(e){
      var reply = {
            reply: modalInputReply.val(),
            replyer:modalInputReplyer.val(),
            bno:bnoValue
          };
      replyService.add(reply, function(result){
        alert(result);
        modal.find("input").val("");
        modal.modal("hide");
        showList(1);
        //showList(-1);   
      });
    });
    
 //class chat, 이벤트는 ul 태그chat클래스에 걸었지만 li (각 댓글)에 위임.하여 this =각 댓글
	 $(".chat").on("click", "li", function(e){
      var rno = $(this).data("rno");
      replyService.get(rno, function(reply){
    	console.log(reply);
        modalInputReply.val(reply.reply);
        modalInputReplyer.val(reply.replyer);
        modalInputReplyDate.val(replyService.displayTime( reply.replyDate)).attr("readonly","readonly");
        modal.data("rno", reply.rno); 
        modal.find("button[id !='modalCloseBtn']").hide();
        modalModBtn.show();
        modalRemoveBtn.show();
        
        $(".modal").modal("show");    
      });
    });
	
	//수정
    modalModBtn.on("click", function(e){	  
   	  var reply = {rno:modal.data("rno"), reply: modalInputReply.val()};	  
   	  replyService.update(reply, function(result){    
   	    alert(result);
   	    modal.modal("hide");
   	    showList(1);   
   	  });  
   	});
	
	//삭제
    modalRemoveBtn.on("click", function (e){
     	  
     	  var rno = modal.data("rno");
     	  
     	  replyService.remove(rno, function(result){
     	        
     	      alert(result);
     	      modal.modal("hide");
     	      showList(1);
     	  });  
     	});
	
	
}); 

</script>
<script>
console.log("==========");
console.log("JS TEST");
var bnoValue='<c:out value="${board.bno}"/>';


//for replyService add test
/* replyService.add(
		{reply:"JS Test",replyer:"tester",bno:bnoValue}
		,
		function(result){
			alert("RESULT:"+ result);
		}
);  */

//reply List Test
replyService.getList({bno:bnoValue,page:1},function(list){
	for(var i=0, len=list.length||0;i<len;i++){
		console.log(list[i]);
	}
});


//댓글 삭제 테스트
/* replyService.remove(24, function(count){
	console.log(count);
	if(count==="success"){
		alert("REMOVED");
		}
	},function(err){
	alert('ERROR........');
}); */
	
//update
/* replyService.update({
	rno : 22,
	bno : bnoValue,
	reply : "Modified Reply......."
	},function(result){
		alert("수정 완료....");
	});
	 */
//get
replyService.get(10,function(data){
	console.log(data);
});

	
</script>

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