package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.vo.OAuthUserDetails;

@Service("oauthUserDetailsService")
public class OAuthUserDetailsService implements UserDetailsService {

	@Autowired
	private UserMapper mapper;

	/* 
	 * 2021.01.07
	 * DB 데이터를 살펴보면 알겠지만 패스워드 컬럼에 {noop}를 명시하여 스프링 시큐리티 5.0 이상에서 필수인
	 * PasswordEncoder 설정을 무시하도록 되어 있다.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		OAuthUserDetails user = null;
		try {
			user = mapper.getUserById(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
}