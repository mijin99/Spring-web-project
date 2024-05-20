package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	//Mapper 어노테이션 sql처리  (1) 수정시 재 빌드 번거롭 ,resource내 같은 경로에 xml 파일로 변경함.
	//@Select("select * from tb1_board where bno > 0")
	public List<BoardVO> getList();
	
	//paging 처리 리스트 받기 
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	//자동으로 (squence하게) 주입되는 값을 알 필요 없는 경우
	public void insert(BoardVO board);
	//자동으로 (squence하게) 주입되는 값을 알아야 하는 경우
	public void insertSelectKey(BoardVO board);
	
	//read
	public BoardVO read(Long bno);
	
	//delete , 몇 건의 데이터가 삭제되었는지 받을 수 있음
	public int delete(Long bno);
	
	
	//update , 몇 건이 업데이트 되었는지 받을 수 잇음
	public int update(BoardVO board);
	
	//전체 데이터 수
	public int getTotalCount(Criteria cri);
	
}
