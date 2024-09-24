package egovframework.example.board.service.impl;

import java.util.List;

import egovframework.example.board.service.BoardVO;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("boardMapper")
public interface BoardMapper {

	public List<BoardVO> boardList(SampleDefaultVO searchVO);

	public BoardVO view(String brdNo);

	public int submit(BoardVO brdVO);

	public int plusOne(String brdNo);

	public int updateSubmit(BoardVO brdVO);

	public int delete(String brdNo);

}
