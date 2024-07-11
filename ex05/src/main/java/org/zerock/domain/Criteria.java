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
		return type ==null? new String [] {}: type.split(""); //type null이면 빈배열, 아니면 나누기?
	}
	
	//form 태그 등으로 검색 조건을 많이 가지고 가야 할 때 쓸 수 있음, 주로 javascript 못 쓸 때사용 가능
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		return builder.toUriString();
	}
	
}
