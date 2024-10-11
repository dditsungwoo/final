package egovframework.example.reply.service;

import java.util.List;
import egovframework.example.board.service.AttFileVO;

public interface ReplyService {

   public List<ReplyVO> replyList(String brdNo);

   public int replyInsert(ReplyVO replyVO);

   public int replyDelete(ReplyVO replyVO);

   public int replyEdit(ReplyVO replyVO);

   public ReplyVO replyCount(String brdNo);
}
