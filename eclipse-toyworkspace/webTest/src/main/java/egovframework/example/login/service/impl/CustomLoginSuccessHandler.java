package egovframework.example.login.service.impl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import egovframework.example.login.service.MemberService;
import egovframework.example.login.service.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	MemberService memberService;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws ServletException, IOException {

		log.warn("onAuthenticationSuccess");
		
		//auth.getPrincipal() : 사용자 정보를 가져옴
	    //시큐리티에서 사용자 정보는 User 클래스의 객체로 저장됨(CustomUser.java를 참고)
		User customUser = (User)auth.getPrincipal();
		
		log.info("username : " + customUser.getUsername());
		MemberVO memVO= this.memberService.detail(customUser.getUsername());
		//auth.getAuthorities -> 권한들(ROLE_MEMBER, ROLE_ADMIN)
		//authority.getAuthority() : ROLE_MEMBER
 		List<String> roleNames = new ArrayList<String>();
		auth.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		
		log.warn("ROLE_NAMES : " + roleNames);
		
		HttpSession session =request.getSession();
		session.setAttribute("memId",customUser.getUsername() );
		session.setAttribute("memRole",roleNames );
		session.setAttribute("memVO", memVO);
			
//		if(roleNames.contains("ROLE_MEMBER")) {
//			response.sendRedirect("dashboard");
//			return;
//		}
		
		super.onAuthenticationSuccess(request, response, auth);
	}

}
