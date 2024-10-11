package egovframework.example.board.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import egovframework.example.board.service.AttFileVO;
import egovframework.example.board.service.BoardService;
import egovframework.example.board.service.BoardVO;
import egovframework.example.sample.service.SampleDefaultVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    String uploadPath;

    @Override
    public List<BoardVO> boardList(SampleDefaultVO searchVO) {

        return this.boardMapper.boardList(searchVO);
    }

    @Override
    public BoardVO view(String brdNo) {

        BoardVO boardVO = this.boardMapper.view(brdNo);
        /*
         * String bContent= boardVO.getBrdContent(); bContent= convertHtml(bContent);
         * boardVO.setBrdContent(bContent);
         */
        return boardVO;
    }

    public int submit(BoardVO brdVO) {
        File uploadPath1 = new File(uploadPath);

        if (!uploadPath1.exists()) {
            uploadPath1.mkdirs();
        }

        String content = brdVO.getBrdContent();
        content = convertText(content);
        brdVO.setBrdContent(content);

        int result = this.boardMapper.submit(brdVO);
        BoardVO lastNo = this.boardMapper.selectLast();
        String uploadFileName = "";

        // uploadFile 배열이 null이 아닌지, 그리고 길이가 0이 아닌지 확인
        if (brdVO.getUploadFile() != null && brdVO.getUploadFile().length > 0) {
            MultipartFile[] uploadFiles = brdVO.getUploadFile();

            for (int i = 0; i < uploadFiles.length; i++) {
                MultipartFile uploadFile = uploadFiles[i];
                String oriFile = uploadFile.getOriginalFilename();

                if (oriFile != null && !oriFile.isEmpty()) { // 원본 파일명이 null이 아니고 비어있지 않은지 확인
                    UUID uuid = UUID.randomUUID();
                    uploadFileName = uuid.toString() + "_" + oriFile;

                    log.info("원본 파일명 : " + oriFile);
                    log.info("파일 크기    : " + uploadFile.getSize());
                    log.info("MIME타입  : " + uploadFile.getContentType());

                    long size = uploadFile.getSize(); // 파일 크기
                    String mime = uploadFile.getContentType(); // mime 타입
                    String ext = oriFile.substring(oriFile.lastIndexOf("."));
                    File saveFile = new File(uploadPath + "\\" + uploadFileName);

                    try {
                        uploadFile.transferTo(saveFile);

                        AttFileVO attVo = new AttFileVO();
                        if (uploadFile.getContentType().startsWith("image")) {
                            String base64 = thumbnailAndBase64(uploadFile);
                            log.info("base64", base64);
                            attVo.setAttFileBase64(base64);
                        } ;
                        // 파일업로드 테이블에 insert
                        attVo.setBrdNo(lastNo.getBrdNo());
                        attVo.setAttFileOrder(i + 1);
                        attVo.setAttFileDir(uploadPath + "\\" + uploadFileName);
                        attVo.setAttFileOriname(oriFile);
                        attVo.setAttFileModname(uploadFileName);
                        attVo.setAttFileType(ext);
                        attVo.setAttFileSize(size);

                        log.info("serviceImp attvo: " + attVo);
                        int resultAtt = this.boardMapper.insertAttFile(attVo);
                    } catch (IllegalStateException | IOException e) {
                        log.error(e.getMessage());
                    }
                }
            } // for 끝
        } else {
            log.warn("업로드할 파일이 없습니다."); // 파일이 없을 때 경고 로그
        }

        return result;
    }

    @Override
    public int plusOne(String brdNo) {

        return this.boardMapper.plusOne(brdNo);
    }

    @Override
    public int updateSubmit(BoardVO brdVO) {

        String content = brdVO.getBrdContent();
        content = convertText(content);
        brdVO.setBrdContent(content);

        // 배열로 변환 attYes는 남아있는 파일들 , attNo는 삭제될 것들
        String[] attYesArray = brdVO.getAttYes();
        List<String> attYesList = new ArrayList<>();
        if (attYesArray != null) {
            for (String attNo : attYesArray) {
                if (attNo != null && !attNo.trim().isEmpty()) {

                    String[] splitAttNos = attNo.split(",");
                    for (String splitAttNo : splitAttNos) {
                        attYesList.add(splitAttNo.trim());
                    }
                }
            }
        }
        String[] filteredAttYesArray = attYesList.toArray(new String[0]);

        // attYes 변환
        String[] attDelArray = brdVO.getAttDel();
        List<String> attDelList = new ArrayList<>();
        if (attDelArray != null) {
            for (String attNo : attDelArray) {
                if (attNo != null && !attNo.trim().isEmpty()) {

                    String[] splitAttNos = attNo.split(",");
                    for (String splitAttNo : splitAttNos) {
                        attDelList.add(splitAttNo.trim());
                    }
                }
            }
        }
        String[] filteredAttDelArray = attDelList.toArray(new String[0]);

        int resYes = 0;

        // attYes는 순서만 재조정함
        for (int i = 0; i < filteredAttYesArray.length; i++) {
            AttFileVO attVO = new AttFileVO();
            attVO.setAttFileOrder(i + 1);
            attVO.setAttFileNo(filteredAttYesArray[i]);
            resYes += this.boardMapper.attYesUpdate(attVO);
        }
        int resDel = 0;
        log.info("resYes :" + resYes);

        // attNo는 delete후 실제 파일도 삭제시킴
        for (int i = 0; i < filteredAttDelArray.length; i++) {
            String attDel = filteredAttDelArray[i];
            AttFileVO attFileVO = this.boardMapper.attFileOne(attDel);

            File delFile = new File(attFileVO.getAttFileDir());

            delFile.delete();
            resDel += this.boardMapper.attDel(attDel);
            if (resDel > 0) {
            }
        }


        // 여기부터 새로운 파일 insert
        File uploadPath1 = new File(uploadPath);

        if (!uploadPath1.exists()) {
            uploadPath1.mkdirs();
        }


        String uploadFileName = "";
        if (brdVO.getUploadFile() != null && brdVO.getUploadFile().length > 0) {
            MultipartFile[] uploadFiles = brdVO.getUploadFile();

            for (int i = 0; i < uploadFiles.length; i++) {
                MultipartFile uploadFile = uploadFiles[i];
                String oriFile = uploadFile.getOriginalFilename();
                if (oriFile != null && !oriFile.isEmpty()) { // 원본 파일명이 null이 아니고 비어있지 않은지 확인
                    UUID uuid = UUID.randomUUID();
                    uploadFileName = uuid.toString() + "_" + oriFile;

                    log.info("원본 파일명 : " + oriFile);
                    log.info("파일 크기    : " + uploadFile.getSize());
                    log.info("MIME타입  : " + uploadFile.getContentType());

                    long size = uploadFile.getSize(); // 파일 크기
                    String mime = uploadFile.getContentType(); // mime 타입
                    String ext = oriFile.substring(oriFile.lastIndexOf("."));
                    File saveFile = new File(uploadPath + "\\" + uploadFileName);

                    try {
                        uploadFile.transferTo(saveFile);
                        AttFileVO attVo = new AttFileVO();
                        if (uploadFile.getContentType().startsWith("image")) {
                            String base64 = thumbnailAndBase64(uploadFile);
                            log.info("base64", base64);
                            attVo.setAttFileBase64(base64);
                        } ;

                        // attYes 순서 바로 다음부터
                        resYes++;
                        // 파일업로드 테이블에 insert
                        attVo.setBrdNo(brdVO.getBrdNo());
                        attVo.setAttFileOrder(resYes);
                        attVo.setAttFileDir(uploadPath + "\\" + uploadFileName);
                        attVo.setAttFileOriname(oriFile);
                        attVo.setAttFileModname(uploadFileName);
                        attVo.setAttFileType(ext);
                        attVo.setAttFileSize(size);

                        log.info("serviceImp attvo: " + attVo);
                        int resultAtt = this.boardMapper.insertAttFile(attVo);

                    } catch (IllegalStateException | IOException e) {
                        log.error(e.getMessage());
                    }
                }
            } // for 끝
        } else {
            log.warn("업로드할 파일이 없습니다."); // 파일이 없을 때 경고 로그
        }

        return this.boardMapper.updateSubmit(brdVO);
    }

    @Override
    public int delete(String brdNo) {

        List<AttFileVO> vo = this.boardMapper.attFileList(brdNo);
        for (int i = 0; i < vo.size(); i++) {
            String attDel = vo.get(i).getAttFileNo();
            AttFileVO attFileVO = this.boardMapper.attFileOne(attDel);

            File delFile = new File(attFileVO.getAttFileDir());

            delFile.delete();
            this.boardMapper.attDel(attDel);

        }
        return this.boardMapper.delete(brdNo);
    }



    public String convertText(String text) {
        return text.replaceAll("(\r\n|\n\r|\r|\n)", "<br>");
    }

    public String convertHtml(String text) {
        return text.replaceAll("<br>", "\n");
    }

    @Override
    public BoardVO selectLast() {
        return this.boardMapper.selectLast();
    }



    // 이미지인지 판단. 썸네일은 이미지만 가능하므로..
    public boolean checkImageType(File file) {
        // MIME(Multipurpose Internet Mail Extensions) : 문서, 파일 또는 바이트 집합의 성격과
        // 형식. 표준화
        // MIME 타입 알아냄. .jpeg / .jpg의 MIME(ContentType)타입 : image/jpeg
        String contentType;
        try {
            contentType = Files.probeContentType(file.toPath());
            log.info("contentType : " + contentType);
            // image/jpeg는 image로 시작함->true
            return contentType.startsWith("image");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 이 파일이 이미지가 아닐 경우
        return false;
    }

    @Override
    public List<AttFileVO> attFileList(String brdNo) {

        return this.boardMapper.attFileList(brdNo);
    }

    @Override
    public AttFileVO attFileOne(String brdNo) {

        return this.boardMapper.attFileOne(brdNo);
    }


    @Override
    public int attDel(String attDel) {

        return this.boardMapper.attDel(attDel);
    }

    @Override
    public int attYesUpdate(AttFileVO attVO) {

        return this.boardMapper.attYesUpdate(attVO);
    }


    public static String thumbnailAndBase64(MultipartFile uploadFile) throws IOException {
        // 업로드된 파일을 임시 파일로 저장
        File tempFile = File.createTempFile("temp", null);
        uploadFile.transferTo(tempFile);

        // 썸네일 생성
        BufferedImage originalImage = ImageIO.read(tempFile);
        BufferedImage thumbnail = Scalr.resize(originalImage, 300);

        // 썸네일을 파일로 저장 (optional)
        File thumbnailFile = File.createTempFile("thumbnail", null);
        ImageIO.write(thumbnail, "jpg", thumbnailFile);

        // 썸네일을 Base64로 인코딩
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thumbnails.of(thumbnailFile).scale(1).outputFormat("jpg").toOutputStream(baos);
        byte[] thumbnailBytes = baos.toByteArray();
        String thumbnailBase64 = Base64.getEncoder().encodeToString(thumbnailBytes);

        // 임시 파일 삭제
        tempFile.delete();
        thumbnailFile.delete();

        return thumbnailBase64;
    }

    @Override
    public AttFileVO attCount(String brdNo) {

        return this.boardMapper.attCount(brdNo);
    }



}
