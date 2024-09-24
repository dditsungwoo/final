package egovframework.example.board.service;

import java.util.List;

import egovframework.example.sample.service.SampleDefaultVO;

public interface BoardService {

	public List<BoardVO> boardList(SampleDefaultVO searchVO);

	public BoardVO view(String brdNo);

	public int submit(BoardVO brdVO);

	public int plusOne(String brdNo);

	public int updateSubmit(BoardVO brdVO);

	public int delete(String brdNo);

}
