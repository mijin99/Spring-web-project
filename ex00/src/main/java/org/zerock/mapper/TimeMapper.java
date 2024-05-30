package org.zerock.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper { //MyBatis의 Mapper 클래스 작성을 위함 

	@Select("SELECT sysdate FROM dual") //Mapper annotation을 이용함
	public String getTime();
	
	public String getTime2(); //Mapper xml을 이용함, 실제 쿼리는 xml 파일에 

}
