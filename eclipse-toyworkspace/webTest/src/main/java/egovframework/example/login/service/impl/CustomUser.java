package egovframework.example.login.service.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import egovframework.example.login.service.MemberVO;

public class CustomUser extends User{
	
	private MemberVO empVO;
	
	// USER의 생성자를 처리해주는 생성자
	public CustomUser(String username, String password
				, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	// 생성자.return empVO == null?null:new CustomUser(empVO);
	public CustomUser(MemberVO empVO) {
//		//사용자가 정의한 (select한) EmployeeVO 타입의 객체 empVO를
//		//스프링 시큐리티에서 제공해주고 있는 UsersDetails 타입으로 변환
//		// 회원정보를 보내서 스프링이 관리
		super(empVO.getMemId()+"",empVO.getMemPass(),
			empVO.getAuthorities()
			);
		this.empVO = empVO;
	}
	
	public MemberVO getEmployeeVO() {
		return empVO;
	}
	
	public void setEmployeeVO(MemberVO empVO) {
		this.empVO = empVO;
	}
}
