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
        String content = replyVO.getRepContent();
        content = convertText(content);
        replyVO.setRepContent(content);
        
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

    @Override
    public ReplyVO replyCount(String brdNo) {
        
        return this.mapper.replyCount(brdNo);
    }
    
    
    public String convertText(String text) {
        return text.replaceAll("(\r\n|\n\r|\r|\n)", "<br>");
    }

    public String convertHtml(String text) {
        return text.replaceAll("<br>","\n");
    }

    
    
}
