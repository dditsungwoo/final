package egovframework.example.board.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttFileVO {
    private String attFileNo;
    private String brdNo;
    private int attFileOrder;
    private String attFileDir;
    private String attFileOriname;
    private String attFileModname;
    private String attFileType;
    private long attFileSize;
    private String attFileRegdate;
    private String attFileDelYn;
    private String attFileBase64;
    private int total;
}
