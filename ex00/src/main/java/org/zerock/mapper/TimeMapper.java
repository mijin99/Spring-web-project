package org.zerock.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper { //MyBatis�� Mapper Ŭ���� �ۼ��� ���� 

	@Select("SELECT sysdate FROM dual") //Mapper annotation�� �̿���
	public String getTime();
	
	public String getTime2(); //Mapper xml�� �̿���, ���� ������ xml ���Ͽ� 

}
