package org.zerock.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/")
@RestController
@Log4j
@AllArgsConstructor //setter 대신 쓰는거임 자동주입 (sts 4.3이상)
public class ReplyController {
	@Setter(onMethod_ = @Autowired)
	private ReplyService service;
	
	@PostMapping(value ="/new",
				consumes="application/json",
				produces= {MediaType.TEXT_PLAIN_VALUE}) //@requestbody를 이용해 JSON타입의데이터를 ReplyVO 타입으로 변환하여지정
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		log.info("ReplyVO:"+vo);
		int insertCount = service.register(vo);
		log.info("Reply INSERT COUNT:"+insertCount);
		return insertCount ==1? new ResponseEntity<>("success",HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
