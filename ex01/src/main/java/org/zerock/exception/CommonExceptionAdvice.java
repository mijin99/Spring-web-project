package org.zerock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

@ControllerAdvice //AOP(공통관심사) 처리 이용방식
@Log4j //람다
public class CommonExceptionAdvice {
	
	//예외처리를 위한 로직 분리
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		log.error("Exception ......." +ex.getMessage());
		model.addAttribute("exception",ex);
		log.error(model);
		return "error_page";
	}
	
	//404 에러같은 잘못된 url일 경우 처리 
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		return "custom404";
	}
}
