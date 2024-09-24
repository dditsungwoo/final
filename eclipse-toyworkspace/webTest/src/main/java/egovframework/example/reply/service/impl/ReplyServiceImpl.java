package egovframework.example.reply.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import egovframework.example.reply.service.ReplyService;
import egovframework.example.reply.service.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyMapper mapper;

    @Override
    public List<ReplyVO> replyList(String brdNo) {
        
        return this.mapper.replyList(brdNo);
    }

    @Override
    public int replyInsert(ReplyVO replyVO) {
        
        return this.mapper.replyInsert(replyVO);
    }

    @Override
    public int replyDelete(ReplyVO replyVO) {
        
        return this.mapper.replyDelete(replyVO);
    }

    @Override
    public int replyEdit(ReplyVO replyVO) {
        
        return this.mapper.replyEdit(replyVO);
    }
}
