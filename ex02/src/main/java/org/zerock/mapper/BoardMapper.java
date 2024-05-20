package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	//Mapper ������̼� sqló��  (1) ������ �� ���� ���ŷ� ,resource�� ���� ��ο� xml ���Ϸ� ������.
	//@Select("select * from tb1_board where bno > 0")
	public List<BoardVO> getList();
	
	//paging ó�� ����Ʈ �ޱ� 
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	//�ڵ����� (squence�ϰ�) ���ԵǴ� ���� �� �ʿ� ���� ���
	public void insert(BoardVO board);
	//�ڵ����� (squence�ϰ�) ���ԵǴ� ���� �˾ƾ� �ϴ� ���
	public void insertSelectKey(BoardVO board);
	
	//read
	public BoardVO read(Long bno);
	
	//delete , �� ���� �����Ͱ� �����Ǿ����� ���� �� ����
	public int delete(Long bno);
	
	
	//update , �� ���� ������Ʈ �Ǿ����� ���� �� ����
	public int update(BoardVO board);
	
	//��ü ������ ��
	public int getTotalCount(Criteria cri);
	
}
