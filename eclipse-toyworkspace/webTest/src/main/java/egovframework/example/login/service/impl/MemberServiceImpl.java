package egovframework.example.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.login.service.MemberService;
import egovframework.example.login.service.MemberVO;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	EgovEnvCryptoService cryptoService;
	
	@Autowired 
	MemberMapper memberMapper;
	
	@Override
	public MemberVO detail(String memId) {
		MemberVO memVO=  this.memberMapper.detail(memId);
		String memPw= memVO.getMemPass();
		memPw=cryptoService.decrypt(memPw);
		log.info("MemberServiceImpl  " +memPw);
		memVO.setMemPass(memPw);
		return memVO;
	}

	@Override
	public int add(MemberVO memVO) {
		
		String memPass= memVO.getMemPass();
		memPass=cryptoService.encrypt(memPass);
		
		memVO.setMemPass(memPass);
		return this.memberMapper.add(memVO);
	}

	@Override
	public MemberVO idCheck(String memId) {
			
		
		return this.memberMapper.idCheck(memId);
	}

	
}
