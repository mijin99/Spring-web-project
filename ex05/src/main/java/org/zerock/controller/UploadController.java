package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.print.attribute.standard.Media;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
		
		
		
		
}
