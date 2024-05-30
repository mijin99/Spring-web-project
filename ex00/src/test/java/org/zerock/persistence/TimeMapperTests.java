package org.zerock.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.mapper.TimeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") //여기 스캔해서 객체 얻어옴(설정파일)
@Log4j
public class TimeMapperTests {

	@Setter(onMethod_ = @Autowired )
	private TimeMapper timeMapper; //인터페이스만 만들어서 @select 지정만 했는데 주입이 됐고 
	/*
	@Test
	public void testGetTime() {
		log.info(timeMapper.getClass().getName()); //적당한 클래스를 알아서 만들었음 
		log.info(timeMapper.getTime());
	}
	*/
	
	@Test
	public void testGetTime2() {
		log.info("getTime2"); //적당한 클래스를 알아서 만들었음 
		log.info(timeMapper.getTime2());
	}
	
	
}
