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
@AllArgsConstructor //setter ��� ���°��� �ڵ����� (sts 4.3�̻�)
public class ReplyController {
	@Setter(onMethod_ = @Autowired)
	private ReplyService service;
	
	@PostMapping(value ="/new",
				consumes="application/json",
				produces= {MediaType.TEXT_PLAIN_VALUE}) //@requestbody�� �̿��� JSONŸ���ǵ����͸� ReplyVO Ÿ������ ��ȯ�Ͽ�����
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		log.info("ReplyVO:"+vo);
		int insertCount = service.register(vo);
		log.info("Reply INSERT COUNT:"+insertCount);
		return insertCount ==1? new ResponseEntity<>("success",HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
