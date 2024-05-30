package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller  //컨트롤러 명시
@RequestMapping("/sample/*") //이 경로에 해당하는 request는 여기서 처리함
@Log4j
public class SampleController {

	@RequestMapping("")
	public void basic() {
		log.info("basic...........");
	}
	@RequestMapping(value = "/basic", method = {RequestMethod.GET,RequestMethod.POST})
	public void basicGet() {
		log.info("basic get...........");
	}
	
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basic get only get...........");
	}
	//get만 가능함 , @data 인자 받으면 알아서 setter 맵핑해줌 -> SampleDTO(name=AAA ,age= 10)
	//http://localhost:8080/sample/ex01?name=aaa&age=10
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("" + dto);
		return "ex01";
	}
	
	//requestparam은 전달되는 파라미터와 변수이름이 다를 경우 유용함! name=AAA, age=10
	@GetMapping("/ex02") 
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name :" + name);
		log.info("age :"  + age);
		return "ex02";
	}
	
	//인자값 배열로 줄 수 있음, ArrayList , ids : [111,222,333]
	//http://localhost:8080/sample/ex02List?ids=111&ids=2222&ids=3333
	@GetMapping("/ex02List") 
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("id :" + ids);
		return "ex02List";
	}
	
	//배열도 가능   , ids : [111,222,333]
	//http://localhost:8080/sample/ex02Array?ids=111&ids=2222&ids=3333
	@GetMapping("/ex02Array") 
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("array id :" + Arrays.toString(ids));
		return "ex02Array";
	}
	//SampleDTO라는 객체타입을 배열로 받고 싶다면 , SampleDTOList(list=SampleDTO(name=aaa,age=0), SampleDTO(name=null,age=0)...
	//http://localhost:8080/sample/ex02Bean?list%5B0%5D.name=aaa&list%5B2%5D.name=bbb
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos :" + list);
		return "ex02Bean";
	}
	
	//TOdoDTO-----------------------
	//@InitBinder 사용하여 java.util.Date 타입변환 , 
	//이 방법이 아니라면 @DateTimeFormat(pattern ="yyyy/MM/dd")로 DTO에서 직접 변환도 가능 
	@InitBinder
	public void initBinder(WebDataBinder binder){	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
	}
	//http://localhost:8080/sample/ex03?title=test&dueDate=2018-01-01
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo : "+ todo);
		return "ex03";
	}
	
	//model에 값 전달하기--------------------------
	//@ModelAttribute , 객체 내 값들은(bean구조에 맞으면)자동으로 넘어가는데, 아래 메소드에 int page로 선언한건 안넘어감
/*	@GetMapping("/ex04")
	public String ex04(SampleDTO dto,int page) {
		log.info("dto : " + dto);
		log.info("page :"+page);
		
		return "/sample/ex04";
	} */
	//하여 아래와 같이 추가 파라미터를 전달할 땐 @ModleAttribute로 지정해줘야함
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto,@ModelAttribute("page") int page) {
		log.info("dto : " + dto);
		log.info("page :"+page);
		
		return "/sample/ex04";
	}
	
	
	//json 객체 사용을 위함 
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06.........");
		SampleDTO dto= new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		
		return dto;
	}
	
	//ResponseEntity 타입 원하는 헤더 정보나 데이터 전달 
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("/ex07...........");
		
		//{"name :", "홍길동"}
		String msg= "{\"name\":\"홍길동\"}";
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(msg,header,HttpStatus.OK);
	}
	
	
	//파일 업로드
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload.............");
	}
	
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file -> {
			log.info("----------------------------------");
			log.info("name" + file.getOriginalFilename());
			log.info("size:"+ file.getSize());
		});
		
	}
	
}
