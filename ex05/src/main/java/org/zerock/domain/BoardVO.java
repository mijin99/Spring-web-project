package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data //getter, setter 등을 만들어 내기 위한 data 어노테이션  
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updatedate;
	private int replyCnt;
	
	private List<BoardAttachVO> attachList;
	
}
