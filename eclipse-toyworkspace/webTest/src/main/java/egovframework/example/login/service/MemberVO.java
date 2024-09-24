package egovframework.example.login.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO implements UserDetails {
	private String memId;
	private String memName;
	private String memPass;
	private String memEmail;
	private String memTel;
	private String memAddr;
	private String memAddr2;
	private String memDelYn;
	private String memRole;
	private String memBirth;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	     String roleGrant =memRole;
	        
	      GrantedAuthority myGrant = new SimpleGrantedAuthority(roleGrant);
	        
	      List<GrantedAuthority> authorities = new ArrayList<>();	       
	        
	      authorities.add(myGrant);
	        
	     return authorities;
	}
	@Override
	public String getPassword() {
		
		return memPass;
	}
	@Override
	public String getUsername() {

		return memId;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
