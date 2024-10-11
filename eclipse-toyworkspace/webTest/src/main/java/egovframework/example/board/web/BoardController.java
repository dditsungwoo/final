package egovframework.example.board.web;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import egovframework.example.board.service.AttFileVO;
import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.reply.service.ReplyService;
import egovframework.example.reply.service.ReplyVO;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.impl.CommonUtills;
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
    public String boardList(@ModelAttribute("searchVO") SampleDefaultVO searchVO, ModelMap model,
            HttpSession session) {
        /** EgovPropertyService.sample */
        // searchVO.setPageUnit(8);
        // searchVO.setPageSize(5);

        /** pageing setting */
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
        paginationInfo.setPageSize(searchVO.getPageSize());

        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        List<BoardVO> sampleList = boardService.boardList(searchVO);

        if (sampleList.isEmpty()) {
            model.addAttribute("message", "게시물이 없습니다.");
            return "sample/sampleList";
        }

        log.info("searchVO ", searchVO);
        log.info("sampleList " + sampleList);
        for (BoardVO vo : sampleList) {
            vo.getBrdSubject();
            String cutSubject = CommonUtills.textSubstring(vo.getBrdSubject(), 40);
            AttFileVO attCnt = this.boardService.attCount(vo.getBrdNo());
            vo.setAttNum(attCnt.getTotal());
            ReplyVO repCnt = this.replyService.replyCount(vo.getBrdNo());
            vo.setBrdSubject(cutSubject + " [" + repCnt.getTotal() + "]");
        }
        if (searchVO.getSearchKeyword() != null && searchVO.getSearchKeyword() != "") {
            session.setAttribute("searchKeyword", searchVO.getSearchKeyword());
        } else {
            session.removeAttribute("searchKeyword");
        } ;

        model.addAttribute("resultList", sampleList);

        int totCnt = sampleList.get(0).getTotal();
        paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        return "sample/sampleList";
    }

    // 상세 페이지로 가기
    @GetMapping("/view.do")
    public String view(@RequestParam String brdNo, Model model,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {

        if (searchKeyword != null && searchKeyword != "") {
            model.addAttribute("searchKeyword", searchKeyword);
        } ;

        int count = this.boardService.plusOne(brdNo);
        BoardVO boardVO = this.boardService.view(brdNo);

        String cutSubject = CommonUtills.textSubstring(boardVO.getBrdSubject(), 20);
        ReplyVO repCnt = this.replyService.replyCount(boardVO.getBrdNo());
        boardVO.setBrdSubject(cutSubject + " [" + repCnt.getTotal() + "]");

        List<ReplyVO> replyVO = this.replyService.replyList(brdNo);
        List<AttFileVO> attFileVO = this.boardService.attFileList(brdNo);

        model.addAttribute("boardVO", boardVO);
        if (replyVO != null) {
            model.addAttribute("replyVO", replyVO);
        }
        if (attFileVO != null) {
            model.addAttribute("attFileVO", attFileVO);
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
    public String submit(BoardVO brdVO, RedirectAttributes attr) {
        log.info("submit");
        log.info("brdVO submit" + brdVO);


        MultipartFile[] files = brdVO.getUploadFile();
        if (files != null) {
            for (MultipartFile file : files) {
                log.info("Uploaded file - Name: {}, Size: {} bytes", file.getOriginalFilename(),
                        file.getSize());
            }
        } else {
            log.info("No files uploaded.");
        }

        int result = this.boardService.submit(brdVO);
        if (result > 0) {
            attr.addFlashAttribute("submit", result);
            return "redirect:/board/coolList.do";

        } else {

            return null;
        }

    }

    // 게시글 수정 페이지로
    @GetMapping("/update.do")
    public String update(@RequestParam String brdNo, Model model) {
        BoardVO boardVO = this.boardService.view(brdNo);

        String cont = this.boardService.convertHtml(boardVO.getBrdContent());
        boardVO.setBrdContent(cont);
        List<AttFileVO> attFileVO = this.boardService.attFileList(brdNo);

        model.addAttribute("boardVO", boardVO);
        model.addAttribute("attFileVO", attFileVO);
        return "sample/sampleUpdate";
    }

    // 게시글 수정 버튼 누르면
    @PostMapping("/updateSubmit.do")
    public String updateSubmit(@ModelAttribute BoardVO brdVO, AttFileVO attVO,
            RedirectAttributes attr) {
        log.info("updateSubmit " + brdVO);
        log.info("updateSubmit " + brdVO.getUploadFile());


        int result = this.boardService.updateSubmit(brdVO);
        if (result > 0) {
            attr.addFlashAttribute("update", result);
            return "redirect:/board/view.do?brdNo=" + brdVO.getBrdNo();

        } else {

            return null;
        }

    }

    // 글 삭제
    @GetMapping("/delete.do")
    public String delete(@RequestParam String brdNo, RedirectAttributes attr) {

        int result = this.boardService.delete(brdNo);
        if (result > 0) {
            attr.addFlashAttribute("delete", result);
            return "redirect:/board/coolList.do";

        } else {

            return null;
        }
    }

    // 파일 다운로드
    @RequestMapping(value = "/fileDown.do")
    public void fileDown(@RequestParam("originalFileName") String originalFileName,
            @RequestParam("storedFileName") String storedFileName, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        AttFileVO fileVO = new AttFileVO();
        fileVO.setAttFileOriname(originalFileName);
        fileVO.setAttFileModname(storedFileName);
        CommonUtills.fileDownload(fileVO, request, response);
    }


    // 새로만든 리스트 페이지
    @GetMapping("/coolList.do")
    public String coolList() {
        return "sample/coolList";
    }


    // 리스트 불러오는거
    @ResponseBody
    @PostMapping("/secondList.do")
    public List<BoardVO> secondList(@RequestBody SampleDefaultVO searchVO) {
        String searchKeyword = searchVO.getSearchKeyword();
        log.info("searchVO" + searchKeyword);

        List<BoardVO> sampleList = boardService.boardList(searchVO);


        for (BoardVO vo : sampleList) {

            String cutSubject = CommonUtills.textSubstring(vo.getBrdSubject(), 40);
            AttFileVO attCnt = this.boardService.attCount(vo.getBrdNo());
            ReplyVO repCnt = this.replyService.replyCount(vo.getBrdNo());

            vo.setAttNum(attCnt.getTotal());
            vo.setBrdSubject(cutSubject + " [" + repCnt.getTotal() + "]");
        }

        return sampleList;
    }



}
