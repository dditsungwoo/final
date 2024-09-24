package egovframework.example.login.service.impl;

import egovframework.example.login.service.MemberVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("memberMapper")
public interface MemberMapper {

	public MemberVO detail(String memId);

	public int add(MemberVO memVO);

	public MemberVO idCheck(String memId);
}
