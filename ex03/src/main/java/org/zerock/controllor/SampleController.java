package org.zerock.controllor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {
	
	@GetMapping(value ="/getText", produces="text/plain; charset=UTF-8")
	public String getText() {
		log.info("MIME TYPE: "+ MediaType.TEXT_PLAIN_VALUE);
		return "안녕하세요";
	}
	
	//sampleVO리턴, produces 필수 아님 !   http://localhost:8080/sample/getSample.json
	@GetMapping(value= "/getSample",
			produces= {MediaType.APPLICATION_JSON_UTF8_VALUE,
					MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		return new SampleVO(112,"스타","로드");
	}
	//위와 기능 동일
	@GetMapping(value= "/getSample2")
	public SampleVO getSample2() {
		return new SampleVO(112,"스타","로드");
	}
	
	//리스트 p363
	@GetMapping(value="/getList")
	public List<SampleVO> getList(){
		return IntStream.range(1, 10).mapToObj(i-> new SampleVO(i,i+"First", i+"Last")).collect(Collectors.toList());
	}
	
	@GetMapping(value="/getMap")
	public Map<String,SampleVO> getMap(){
		Map<String,SampleVO> map=  new HashMap<>();
		map.put("First",new SampleVO(111,"그루트","주니어"));
		return map;
	}
	//처리상태 확인 http://localhost:8080/sample/check.json?height=140&weight=60
	@GetMapping(value="/check" , params= {"height","weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		SampleVO vo = new SampleVO(0,""+height,""+weight);
		
		ResponseEntity<SampleVO> result =null;
		if(height<150) {
			result=ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result=ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	//쿼리스트링, 경로 변수처리 http://localhost:8080/sample/product/bags/1234
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(
		@PathVariable("cat") String cat,
		@PathVariable("pid")  Integer pid ) {
		return new String[] {"category: "+cat, "productid: "+pid};
	}
	//타입변환 요청
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert............ticket"+ticket);
		return ticket;
	}
	
	
}
 