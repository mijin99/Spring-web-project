package org.zerock.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) //spring을 실행할거라는 어노테이션
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") //필요 객체들 스프링 내 등록
@Log4j //롬복이용 로그 logger 변수 생성 
public class SampleTests {
	@Setter(onMethod_ = {@Autowired}) //autowired 스프링으로부터 자동 주입해달라는 표시
	private Restaurant restaurant;
	
	@Test  //junit 에서  테스트 대상을 표시하는 어노테이션
	public void testExist() {
		assertNotNull(restaurant);
		log.info(restaurant);
		log.info("-------------------------------");
		log.info(restaurant.getChef());
	}
}
