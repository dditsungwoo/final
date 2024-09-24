package egovframework.example.login.service;

public interface MemberService {

	public MemberVO detail(String memId);

	public int add(MemberVO memVO);

	public MemberVO idCheck(String memId);
}
