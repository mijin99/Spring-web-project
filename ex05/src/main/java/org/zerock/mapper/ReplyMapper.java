package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {

	//�ܷ�Ű ����۾�
	public int insert (ReplyVO vo); 
	//����б�
	public ReplyVO read(Long bno);
	//��ۻ���
	public int delete (Long rno);
	
	//����
	public int update( ReplyVO reply);
	
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno);
	
	public int getCountByBno(Long bno);
}
