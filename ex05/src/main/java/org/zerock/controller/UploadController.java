package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.print.attribute.standard.Media;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
		@GetMapping("/uploadForm")
		public void uploadForm() {
			log.info("upload form");
		}
		
		@GetMapping("/uploadAjax")
		public void uploadAjax() {
			log.info("upload ajax");
		}
		
		@PostMapping("/uploadFormAction")
		public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
			String uploadFolder ="C:\\upload";
			for(MultipartFile multipartFile : uploadFile) {
				log.info("--------------------------------------");
				log.info("Upload File Name:"+multipartFile.getOriginalFilename());
				log.info("Upload File Size:"+multipartFile.getSize());
				
				File saveFile =new File(uploadFolder, multipartFile.getOriginalFilename());
				
				try {
					multipartFile.transferTo(saveFile);
				}catch(Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		
		@PostMapping(value ="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
		@ResponseBody
		public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
			List<AttachFileDTO> list  = new ArrayList<>();

			log.info("upload ajax post.......");
			String uploadFolder = "C:\\upload";
		
			String uploadFolderPath = getFolder();
			//make folder--
			File uploadPath = new File(uploadFolder, uploadFolderPath);
			log.info("upload path:"+uploadPath);
			
			if(uploadPath.exists()==false) {
				uploadPath.mkdirs(); //경로에 포더 없으면 생성 
			}
			
			for (MultipartFile multipartFile : uploadFile) {
				AttachFileDTO attachDTO = new AttachFileDTO();
				log.info("----------------------------");
				log.info("Upload File Name : "+multipartFile.getOriginalFilename());
				log.info("Upload File Size : "+multipartFile.getSize());
				String uploadFileName = multipartFile.getOriginalFilename();
				
				//IE has File path
				uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
				log.info("only file name: "+uploadFileName);
				attachDTO.setFileName(uploadFileName);
				
				//파일 중복명칭 업로드 허용
				UUID uuid = UUID.randomUUID(); 
				uploadFileName =uuid.toString()+"_"+uploadFileName;
				
				
				
				try {
					//File saveFile = new File(uploadFolder, uploadFileName);
					File saveFile = new File(uploadPath, uploadFileName);
					multipartFile.transferTo(saveFile);
					attachDTO.setUuid(uuid.toString());
					attachDTO.setUploadPath(uploadFolderPath);
					//썸네일 만들기
					if(checkImageType(saveFile)) {
						attachDTO.setImage(true);
						FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath,"s_"+uploadFileName));
						Thumbnailator.createThumbnail(multipartFile.getInputStream(),thumbnail,100,100);
						thumbnail.close();
					}
					list.add(attachDTO);
				}catch(Exception e) {
					log.error(e.getMessage());
				}
			}
			return new ResponseEntity<>(list,HttpStatus.OK);
		}
		
		private boolean checkImageType(File file) {
			try {
				String contentType = Files.probeContentType(file.toPath());
				return contentType.startsWith("image");
			}catch(IOException E) {
				E.printStackTrace();
			}
			return false;
		}
		
		//너무 많은 업로드 지양을 위해 폴더 경로 생성
		private String getFolder() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String str = sdf.format(date);
			return str.replace("-", File.separator);
		}
		
		@GetMapping("/display")
		@ResponseBody
		public ResponseEntity<byte[]> getFile(String fileName){
			log.info("fileName:"+fileName);
			File file = new File("c:\\upload\\"+fileName);
			log.info("file:"+file);
			ResponseEntity<byte[]> result =null;
			
			try {
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Type", Files.probeContentType(file.toPath()));
				result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),
						header, HttpStatus.OK);
			}catch(IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		
		@GetMapping(value="/download", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
		@ResponseBody
		public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) throws UnsupportedEncodingException{
			log.info("download file:"+fileName);
			Resource resource = new FileSystemResource("c:\\upload\\"+ fileName);
			log.info("resource:"+resource);
			
			
			if(resource.exists()==false) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			String resourceName=resource.getFilename();
			//remove UUID
			String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
			
			HttpHeaders headers = new HttpHeaders();
			try {
				//ie는 chrome과 다르게 content-disposition에 대한 처리가 달라 한글이 꺠짐. 이에 대한 처리
				String downloadName=null;
				if(userAgent.contains("Trident")) {
					log.info("IE browser");
					downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+", " ");
				}else if (userAgent.contains("Edge")) {
					log.info("Edge browser");
					downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
					log.info("Edge name:"+downloadName);
				}else {
					log.info("Chrome browser");
					downloadName = new String (resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
				}
				log.info("downloadNmae:"+downloadName);
				headers.add("Content-Disposition",  "attachment;filename="+ downloadName);
			}catch(UnsupportedMediaTypeStatusException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
		}
		
		@PostMapping("/deleteFile")
		@ResponseBody
		public ResponseEntity<String> deleteFile(String fileName, String type){
			log.info("deleteFile:"+fileName);
			File file;
			try {
				file =new File("c:\\upload\\"+URLDecoder.decode(fileName,"UTF-8")); //이미지 없애기
				file.delete();
				if(type.equals("image")) {
					String largeFileName= file.getAbsolutePath().replace("s_", ""); //이미지라서 썸네일 있으면 썸네일도 없애기 
					log.info("largeFileName"+largeFileName);
					file =new File(largeFileName);
					file.delete();
				}
			}catch(UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<String>("deleted",HttpStatus.OK);
		}
		
		
}
