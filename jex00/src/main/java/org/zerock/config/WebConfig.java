package org.zerock.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//���̻� web.xml�� ������Ʈ ������ �Ұ��� �������Ƿ� ����ϴ� Ŭ���� �ۼ� 
public class WebConfig extends
	AbstractAnnotationConfigDispatcherServletInitializer {

	@Override //root-context��ü. 
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {RootConfig.class}; //�������� rootconfig�� ��ü��Ŵ
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
