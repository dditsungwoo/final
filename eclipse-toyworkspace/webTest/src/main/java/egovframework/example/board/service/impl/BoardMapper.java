package egovframework.example.board.service.impl;

import java.util.List;
import egovframework.example.board.service.AttFileVO;
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

    public BoardVO selectLast();

    public int insertAttFile(AttFileVO attVo);

    public List<AttFileVO> attFileList(String brdNo);

    public int attDel(String attDel);

    public AttFileVO attFileOne(String brdNo);

    public int attYesUpdate(AttFileVO attVO);

    public AttFileVO attCount(String brdNo);



}
