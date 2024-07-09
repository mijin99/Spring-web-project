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

<div class='bigPictureWrapper'>
	<div class='bigPicture'>
	</div>
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
	.uploadResult ul li span{
		color:white;
	}
	.bigPictureWrapper {
		position :absolute;
		display : none;
		justify-content : center;
		align-itmes : center;
		top : 0%;
		width : 100%;
		height : 100%;
		background-color : gray;
		z-index :100;
		background:rgba(255,255,255,0.5);
	}
	.bigPicture{
		position :relative;
		display : felx;
		justify-content : center;
		align-itmes : center;
	}
	.bigPicture img{
		width :600px;
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
//썸네일 이미지 보여주기
function showImage(fileCallPath){
	//alert(fileCallPath);
	$(".bigPictureWrapper").css("display","flex").show();
	$(".bigPicture").html("<img src='/display?fileName=" + encodeURI(fileCallPath)+"'>").animate({width:'100%', height:'100%'},1000);
	$(".bigPictureWrapper").on("click",function(e){
		$(".bigPicture").animate({width:'0%', height:'0%'},1000);
		setTimeout(()=> {
			$(this).hide();
		},1000);
	});
}

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
				var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
				
				var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
				
				str+= "<li><div><a href='/download?fileName="+fileCallPath+"'>"+"<img src='/resources/img/folder.png'>"
					+ obj.fileName +"</a><span data-file=\'"+fileCallPath+"\' data-type='file'> x </span><div></li>";

			}else{
				//str+="<li>" +obj.fileName + "</li>";
				var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
				str+="<li><img src='/display?fileName="+fileCallPath+"'><li>";
				
				var originPath =obj.uploadPath+"\\"+obj.uuid+"_"+obj.fileName;
				originPath=originPath.replace(new RegExp(/\\/g),"/");
				
				str+= "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='/display?fileName="+fileCallPath+"'></a>"+
						"<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span><div><li>";
			}
		});
		uploadResult.append(str);
	}
	
	$(".uploadResult").on("click","span",function(e){
		var targetFile = $(this).data("file");
		var type =$(this).data("type");
		console.log(targetFile);
		
		$.ajax({
			url:'/deleteFile',
			data:{fileName:targetFile,type:type},
			dataType:'text',
			type:'POST',
				success:function(result){
					alert(result);
				}
		}); //$.ajax
	});
	
});

</script>

</body>
</html>