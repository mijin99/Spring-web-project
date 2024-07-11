package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {

	//외래키 등록작업
	public int insert (ReplyVO vo); 
	//댓글읽기
	public ReplyVO read(Long bno);
	//댓글삭제
	public int delete (Long rno);
	
	//수정
	public int update( ReplyVO reply);
	
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno);
	
	public int getCountByBno(Long bno);
}
