package egovframework.example.reply.web;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import egovframework.example.reply.service.ReplyService;
import egovframework.example.reply.service.ReplyVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    ReplyService replyService;
    
    
    
    @PostMapping("/insert.do")  
    @ResponseBody
    public  List<ReplyVO> replyInsert(@RequestBody ReplyVO replyVO, HttpSession session){
        log.info("replyVO",replyVO);
        int result= this.replyService.replyInsert(replyVO);

  
        session.setAttribute("replyInsert",result);
        List<ReplyVO> list =this.replyService.replyList(replyVO.getBrdNo());
        return list;
       
           
        };
        
      @PostMapping("/replyDelete.do")  
      @ResponseBody  
      public  List<ReplyVO> replyDelete(@RequestBody ReplyVO replyVO ){
          log.info("replyInsert");
          log.info("replyVO",replyVO);
          int result= this.replyService.replyDelete(replyVO);
          log.info("RESULT",result);
          
          
          List<ReplyVO> list =this.replyService.replyList(replyVO.getBrdNo());
          return list;
         
             
          };
          
          @PostMapping("/replyEdit.do")  
          @ResponseBody  
          public  List<ReplyVO> replyEdit(@RequestBody ReplyVO replyVO, Model model){
              log.info("replyInsert");
              log.info("replyVO",replyVO);
              int result= this.replyService.replyEdit(replyVO);
              log.info("RESULT",result);
              
             
              List<ReplyVO> list =this.replyService.replyList(replyVO.getBrdNo());
              return list;
             
                 
              };  
              
          @PostMapping("/list.do")  
          @ResponseBody  
          public  List<ReplyVO> list(@RequestBody ReplyVO replyVO){            
             
              List<ReplyVO> list =this.replyService.replyList(replyVO.getBrdNo());
              return list;
             
                 
           };      
              
          
    
}
