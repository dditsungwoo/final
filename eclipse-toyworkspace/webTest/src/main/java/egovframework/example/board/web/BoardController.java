package egovframework.example.board.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.reply.service.ReplyService;
import egovframework.example.reply.service.ReplyVO;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardService boardService;
    
    @Autowired(required = false)
    ReplyService replyService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;


    // 리스트 보여줌
    @GetMapping("/list.do")
    public String boardList(@ModelAttribute("searchVO") SampleDefaultVO searchVO, ModelMap model) {
        /** EgovPropertyService.sample */
        searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
        searchVO.setPageSize(propertiesService.getInt("pageSize"));

        /** pageing setting */
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
        paginationInfo.setPageSize(searchVO.getPageSize());

        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<BoardVO> sampleList = boardService.boardList(searchVO);
        log.info("searchVO ",searchVO);
        log.info("sampleList "+sampleList);
        model.addAttribute("resultList", sampleList);

        int totCnt = sampleList.get(0).getTotal();
        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        return "sample/sampleList";
    }

    // 상세 페이지로 가기
    @GetMapping("/view.do")
    public String view(@RequestParam String brdNo, Model model) {

        int count = this.boardService.plusOne(brdNo);
        BoardVO boardVO = this.boardService.view(brdNo);
        List<ReplyVO> replyVO= this.replyService.replyList(brdNo);
        model.addAttribute("boardVO", boardVO);
        if(replyVO !=null) {
        model.addAttribute("replyVO", replyVO);
        }
        return "sample/sampleView";
    }


    // 게시글 작성 페이지로
    @GetMapping("/write.do")
    public String write() {


        return "sample/sampleWrite";
    }

    // 게시글 작성
    @PostMapping("submit.do")
    public String submit(BoardVO brdVO) {
        log.info("brdVO", brdVO);
        int result = this.boardService.submit(brdVO);
        if (result > 0) {
            return "redirect:/board/list.do";

        } else {

            return null;
        }

    }

    // 게시글 수정 페이지로
    @GetMapping("/update.do")
    public String update(@RequestParam String brdNo, Model model) {
        BoardVO boardVO = this.boardService.view(brdNo);

        model.addAttribute("boardVO", boardVO);
        return "sample/sampleUpdate";
    }

    // 게시글 수정 버튼 누르면
    @PostMapping("/updateSubmit.do")
    public String updateSubmit(BoardVO brdVO) {
        log.info("updateSubmit ", brdVO);
        int result = this.boardService.updateSubmit(brdVO);
        if (result > 0) {
            return "redirect:/board/list.do";

        } else {

            return null;
        }

    }

    @GetMapping("/delete.do")
    public String delete(@RequestParam String brdNo) {

        int result = this.boardService.delete(brdNo);
        if (result > 0) {
            return "redirect:/board/list.do";

        } else {

            return null;
        }
    }

    

        


}
