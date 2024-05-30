package org.zerock.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//더이상 web.xml로 프로젝트 구동이 불가능 해졌으므로 대신하는 클래스 작성 
public class WebConfig extends
	AbstractAnnotationConfigDispatcherServletInitializer {

	@Override //root-context대체. 
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {RootConfig.class}; //직접만든 rootconfig로 대체시킴
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return null;
	}

}
