package egovframework.example.login.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.example.login.service.MemberService;
import egovframework.example.login.service.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	MemberService memberService;
	
	@GetMapping("/login.do")
	public String login() {
		
		return "sample/login";
	}
	
	@PostMapping("/enter.do")
	public String enter(@RequestParam("username") String username, @RequestParam("password") String password) {
		log.info(username);
		log.info(password);
		
		if (username.equals("user") && password.equals("password")) {
			
			return "sample/egovSampleList";
		} else {
			return "redirect:/?error";
		}
	}
	
	
	@GetMapping("/register.do")
	public String register() {
		
		return "sample/register";
	}
	
	
	@PostMapping("/add.do")
	public String add( MemberVO memVO) throws Exception {
		log.info("memVO  ",memVO);
		log.info("memVO  "+memVO);
		
		int result = this.memberService.add(memVO);
		
		if(result>0) {
			return "sample/login";
			
		}else {
			return "sample/register";
		}
		
	}
	
	@ResponseBody
	@PostMapping("/idCheck.do")
	public String idCheck(@RequestBody MemberVO memVO) {
		log.info("inCheck  ");
		log.info("memVO  "+memVO.getMemId());
		MemberVO result =this.memberService.idCheck(memVO.getMemId());
		log.info("result "+result);
		
		if(result !=null ) {
			return "1";	
		}else {
			return "0";
		}
	}
	
	
	 //로그아웃
	
	@PostMapping("/logout.do")
	 public String logout(HttpServletRequest request, HttpServletResponse response) {
	        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		 return "sample/login"; 
	}
	

	
	
}
