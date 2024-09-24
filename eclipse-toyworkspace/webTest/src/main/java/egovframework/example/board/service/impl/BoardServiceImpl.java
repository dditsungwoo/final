package egovframework.example.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.sample.service.SampleDefaultVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper boardMapper;

	@Override
	public List<BoardVO> boardList(SampleDefaultVO searchVO) {
		
		return this.boardMapper.boardList(searchVO);
	}

	@Override
	public BoardVO view(String brdNo) {

		return this.boardMapper.view(brdNo);
	}

	@Override
	public int submit(BoardVO brdVO) {
		
		return this.boardMapper.submit(brdVO);
	}

	@Override
	public int plusOne(String brdNo) {
		
		return this.boardMapper.plusOne(brdNo);
	}

	@Override
	public int updateSubmit(BoardVO brdVO) {
	
		return this.boardMapper.updateSubmit(brdVO);
	}

	@Override
	public int delete(String brdNo) {
		
		return this.boardMapper.delete(brdNo);
	}

}
