package egovframework.example.board.service;

import org.springframework.web.multipart.MultipartFile;
import egovframework.example.sample.service.SampleDefaultVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO extends SampleDefaultVO {
    private String brdNo;
    private String brdWriter;
    private String brdSubject;
    private String brdContent;
    private int brdHit;
    private String brdRegdate;
    private String brdRegip;
    private String brdModdate;
    private String brdModip;
    private String brdPassword;
    private String brdDelYn;
    private String brdCate;
    private int total;
    private int repNum;
    private int attNum;

    private MultipartFile[] uploadFile;
    private String[] attYes;
    private String[] attDel;

}
