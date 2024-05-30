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

@Controller  //��Ʈ�ѷ� ���
@RequestMapping("/sample/*") //�� ��ο� �ش��ϴ� request�� ���⼭ ó����
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
	//get�� ������ , @data ���� ������ �˾Ƽ� setter �������� -> SampleDTO(name=AAA ,age= 10)
	//http://localhost:8080/sample/ex01?name=aaa&age=10
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("" + dto);
		return "ex01";
	}
	
	//requestparam�� ���޵Ǵ� �Ķ���Ϳ� �����̸��� �ٸ� ��� ������! name=AAA, age=10
	@GetMapping("/ex02") 
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name :" + name);
		log.info("age :"  + age);
		return "ex02";
	}
	
	//���ڰ� �迭�� �� �� ����, ArrayList , ids : [111,222,333]
	//http://localhost:8080/sample/ex02List?ids=111&ids=2222&ids=3333
	@GetMapping("/ex02List") 
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("id :" + ids);
		return "ex02List";
	}
	
	//�迭�� ����   , ids : [111,222,333]
	//http://localhost:8080/sample/ex02Array?ids=111&ids=2222&ids=3333
	@GetMapping("/ex02Array") 
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("array id :" + Arrays.toString(ids));
		return "ex02Array";
	}
	//SampleDTO��� ��üŸ���� �迭�� �ް� �ʹٸ� , SampleDTOList(list=SampleDTO(name=aaa,age=0), SampleDTO(name=null,age=0)...
	//http://localhost:8080/sample/ex02Bean?list%5B0%5D.name=aaa&list%5B2%5D.name=bbb
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos :" + list);
		return "ex02Bean";
	}
	
	//TOdoDTO-----------------------
	//@InitBinder ����Ͽ� java.util.Date Ÿ�Ժ�ȯ , 
	//�� ����� �ƴ϶�� @DateTimeFormat(pattern ="yyyy/MM/dd")�� DTO���� ���� ��ȯ�� ���� 
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
	
	//model�� �� �����ϱ�--------------------------
	//@ModelAttribute , ��ü �� ������(bean������ ������)�ڵ����� �Ѿ�µ�, �Ʒ� �޼ҵ忡 int page�� �����Ѱ� �ȳѾ
/*	@GetMapping("/ex04")
	public String ex04(SampleDTO dto,int page) {
		log.info("dto : " + dto);
		log.info("page :"+page);
		
		return "/sample/ex04";
	} */
	//�Ͽ� �Ʒ��� ���� �߰� �Ķ���͸� ������ �� @ModleAttribute�� �����������
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto,@ModelAttribute("page") int page) {
		log.info("dto : " + dto);
		log.info("page :"+page);
		
		return "/sample/ex04";
	}
	
	
	//json ��ü ����� ���� 
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06.........");
		SampleDTO dto= new SampleDTO();
		dto.setAge(10);
		dto.setName("ȫ�浿");
		
		return dto;
	}
	
	//ResponseEntity Ÿ�� ���ϴ� ��� ������ ������ ���� 
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("/ex07...........");
		
		//{"name :", "ȫ�浿"}
		String msg= "{\"name\":\"ȫ�浿\"}";
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(msg,header,HttpStatus.OK);
	}
	
	
	//���� ���ε�
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
