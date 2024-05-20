package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage;    //페이지 시작번호
	private int endPage;      //페이지 끝번호
	private boolean prev,next;//이전, 이후 
	
	private int total;        //전체 페이지수
	private Criteria cri;     //입력받은 pagenum,amount
	
	public PageDTO(Criteria cri,int total) { 
		this.cri=cri;
		this.total=total;        //ceil은 소수점 올림
		this.endPage=(int)(Math.ceil(cri.getPageNum()/10.0))*10; //끝페이지 구하기  endpage=10  paganum= 5
		this.startPage = this.endPage -9;
		int realEnd =(int)(Math.ceil((total*1.0)/cri.getAmount()));
		if(realEnd<this.endPage) {
			this.endPage =realEnd;
		}
		this.prev = this.startPage>1;
		this.next = this.endPage <realEnd;
	}
}
