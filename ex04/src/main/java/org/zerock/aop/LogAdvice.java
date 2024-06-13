package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect  //aspect구현을 위한 객체임을 나타냄
@Log4j   //로그 사용 
@Component //bean 으로 인식
public class LogAdvice {
	//cutpoint 작성     *접근제한자						//*클래스, *메소드
	@Before("execution(* org.zerock.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("=========================");
	}
	
	
	@Before("execution(* org.zerock.service.SampleService*.doAdd(String,String)) && args(str1,str2)")
	public void logBeforeWithParam(String str1,String str2) { 
		log.info("str1 :"+ str1);
		log.info("str2 :"+ str2);
	}
	
	
	@AfterThrowing(pointcut="execution(* org.zerock.service.SampleService*.*(..))", throwing="exception")
	public void logException(Exception exception) {
		log.info("Exception......!!!!!");
		log.info("exception:"+exception);
	}
	
	//메소드 전후 실행권한 가짐
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start =System.currentTimeMillis();
		log.info("Target: "+ pjp.getTarget());
		log.info("Param : "+Arrays.toString(pjp.getArgs()));
		
		//invoke Method
		Object result= null;
		try {
			result = pjp.proceed();
		}catch(Throwable e){
			e.printStackTrace();
		}
		
		long end =System.currentTimeMillis();
		log.info("Time:"+(end-start));
		return result;
	}
	
	
	
}
