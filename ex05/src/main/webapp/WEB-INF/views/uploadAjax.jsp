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
	$("#uploadBtn").on("click",function(e){
		var formData = new FormData();
		var inputFile =$("input[name='uploadFile']");
		var files =inputFile[0].files;
		console.log(files);
		
		//jquery를 이용한 펌부파일 전송, form data타입 객체에 각 파일 데이터를 추가
		for (var i=0;i<files.length;i++){
			formData.append("uploadFile",files[i]);
		}
		$.ajax({
			url : '/uploadAjaxAction',
		processData:false, //비동기
		contentType :false,
		data : formData,
		type :'POST',
		success: function(result){
			alert("Uploaded");
		}	
		}); //$.ajax
	});
});
</script>

</body>
</html>