package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter //Lombok getter,setter 
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.amount =amount;
		this.pageNum =pageNum;
	}
	
	public String[] getTypeArr() {
		return type ==null? new String [] {}: type.split(""); //type null�̸� ��迭, �ƴϸ� ������?
	}
	
	//form �±� ������ �˻� ������ ���� ������ ���� �� �� �� �� ����, �ַ� javascript �� �� ����� ����
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		return builder.toUriString();
	}
	
}
