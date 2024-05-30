package org.zerock.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoDTO {

	private String title;
	//@DateTimeFormat(pattern = "yyyy/MM/dd")
	//이 경우는 @InitBinder 있어야함
	private Date dueDate;
	
	
}
