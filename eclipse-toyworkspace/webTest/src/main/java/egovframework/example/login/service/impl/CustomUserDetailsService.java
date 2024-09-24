package egovframework.example.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import egovframework.example.login.service.MemberVO;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	EgovEnvCryptoService cryptoService;
	
	@Autowired
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    // 사원 정보 검색(username : 로그인 시 입력받은 사번)		
        MemberVO empVO = memberMapper.detail(username);
        String depass=cryptoService.decrypt((empVO.getMemPass()));
        empVO.setMemPass(depass);
        log.info("empVO :", empVO);
        log.info("empVO : "+ empVO);

        if (empVO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        
        
        
        // CustomUser로 리턴
        return new CustomUser(empVO);
    }
    
    
    
}
	

	
	


