<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Upload with Ajax</h1>
<div class='uploadDiv'>
	<input type='file' name='uploadFile' multiple>
</div>

<!-- 첨부파일 모양  -->
<style>
	.uploadResult{
		width:100%;
		background-color:gray;
	}
	.uploadResult ul{
		display:flex;
		flex-flex :row;
		justify-content :center;
		align-items:center;
	}
	.uploadResult ul li {
		list-style :none;
		padding :10px;
	}
	.uploadResult ul li img {
		width :20px;
	}
</style>
<!-- 업로드 결과 표시구역 -->
<div class='uploadResult'>
	<ul>
	</ul>
</div>

<button id='uploadBtn'>Upload</button>

<!-- <script 
src="https://code.jquery.com/jquery-3.3.1.min.js"
integrity="sha256-FgpCb/KJQlLNfOu91ta320o/NMZxltwRo8QtmkMRdAu8="
crossorign="anonymous">
</script> -->

<script src="https://code.jquery.com/jquery-3.7.1.min.js" 
integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" 
crossorigin="anonymous"></script>



<script>
$(document).ready(function(){
	//file 확장자 및 크기 검사
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880; //5MB
	
	function checkExtension(fileName,fileSize){
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과");
			return false;
		}
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		return true;
	}
	//업로드 후 초기화를 위한 기존 데이터 복제
	var cloneObj = $(".uploadDiv").clone();
	
	$("#uploadBtn").on("click",function(e){

		var formData = new FormData();
		var inputFile =$("input[name='uploadFile']");
		var files =inputFile[0].files;
		console.log(files);
		
		//jquery를 이용한 펌부파일 전송, form data타입 객체에 각 파일 데이터를 추가
		for (var i=0;i<files.length;i++){
			if(!checkExtension(files[i].name, files[i].size)){
				return false;
			}
			formData.append("uploadFile",files[i]);
		}
		$.ajax({
			url : '/uploadAjaxAction',
			processData:false, //비동기
			contentType :false,
			data : formData,
			type :'POST',
			dataType:'json',
			success: function(result){//>???
				console.log(result);
				showUploadedFile(result);
				
				//업로드 후 복사 객체로 대체시키기
				$(".uploadDiv").html(cloneObj.html());
			}	
		}); //$.ajax
	});
	//파일 업로드 목록 ul에 표시
	var uploadResult = $(".uploadResult ul");
	function showUploadedFile(uploadResultArr){
		var str="";
		$(uploadResultArr).each(function(i,obj){
			if(!obj.image){
				str+= "<li><img src='/resources/img/folder.png'>"
					+ obj.fileName +"</li>";

			}else{
				//str+="<li>" +obj.fileName + "</li>";
				var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj_uuid+"_"+obj.fileName);
				str+="<li><img src='/display?fileName="+fileCallPath"'><li>";
			}
		});
		uploadResult.append(str);
	}
	
});
</script>

</body>
</html>