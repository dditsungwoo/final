package egovframework.example.reply.service;

import egovframework.example.sample.service.SampleDefaultVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyVO extends SampleDefaultVO {
	private String repNo;
	private String brdNo;
	private String repContent;
	private String repWriter;
	private String repPassword;
	private String repDelYn;
	private String repRegdate;
	private String repRegip;
	private String repModdate;
	private String repModip;
	private String repUpperNo;
	private int total;
}
