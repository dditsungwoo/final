package egovframework.example.reply.service.impl;

import java.util.List;

import egovframework.example.reply.service.ReplyVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("replyMapper")
public interface ReplyMapper {

    public List<ReplyVO> replyList(String brdNo);

    public int replyInsert(ReplyVO replyVO);

    public int replyDelete(ReplyVO replyVO);

    public int replyEdit(ReplyVO replyVO);

}
