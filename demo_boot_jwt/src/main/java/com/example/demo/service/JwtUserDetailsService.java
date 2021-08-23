package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.JwtMapper;
import com.example.demo.vo.JwtUserDetails;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	JwtUserDetails member = null;
    	try {
    		member = mapper.getUserById(username);
		} catch (Exception e) {
			new UsernameNotFoundException(username);
		}
    	
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        if (username.equals("admin")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return new User(member.getUsername(), member.getPassword(), grantedAuthorities);
    }

    public JwtUserDetails authenticateByEmailAndPassword(String username, String password) {
    	JwtUserDetails member = null;
    	try {
    		member = mapper.getUserById(username);
		} catch (Exception e) {
			new UsernameNotFoundException(username);
		}

//    	여기부터... 어차피 이 부분에 대기업은 키보드 보안 솔루션 도입 후 솔루션 로직이 적용될 가능성이 크다.
//    	만약 해당 로직을 그냥 적용한다면 패스워드를 저장할 때 아래 encode한 값을 직접 넣으면 되겠다.
        if(!passwordEncoder.matches(password, passwordEncoder.encode(member.getPassword()))) {
            throw new BadCredentialsException("Password not matched");
        }
//      여기까지...

        return member;
    }

}