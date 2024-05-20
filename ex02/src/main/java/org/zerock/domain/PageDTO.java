package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage;    //������ ���۹�ȣ
	private int endPage;      //������ ����ȣ
	private boolean prev,next;//����, ���� 
	
	private int total;        //��ü ��������
	private Criteria cri;     //�Է¹��� pagenum,amount
	
	public PageDTO(Criteria cri,int total) { 
		this.cri=cri;
		this.total=total;        //ceil�� �Ҽ��� �ø�
		this.endPage=(int)(Math.ceil(cri.getPageNum()/10.0))*10; //�������� ���ϱ�  endpage=10  paganum= 5
		this.startPage = this.endPage -9;
		int realEnd =(int)(Math.ceil((total*1.0)/cri.getAmount()));
		if(realEnd<this.endPage) {
			this.endPage =realEnd;
		}
		this.prev = this.startPage>1;
		this.next = this.endPage <realEnd;
	}
}
