package org.zerock.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) //spring�� �����ҰŶ�� ������̼�
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") //�ʿ� ��ü�� ������ �� ���
@Log4j //�Һ��̿� �α� logger ���� ���� 
public class SampleTests {
	@Setter(onMethod_ = {@Autowired}) //autowired ���������κ��� �ڵ� �����ش޶�� ǥ��
	private Restaurant restaurant;
	
	@Test  //junit ����  �׽�Ʈ ����� ǥ���ϴ� ������̼�
	public void testExist() {
		assertNotNull(restaurant);
		log.info(restaurant);
		log.info("-------------------------------");
		log.info(restaurant.getChef());
	}
}
