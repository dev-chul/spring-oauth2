package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.OAuthMapper;
import com.example.demo.vo.OAuthCode;

@Service("oauthCodeService")
public class OAuthCodeService extends RandomValueAuthorizationCodeServices {

	@Autowired
	private OAuthMapper mapper;

	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		OAuthCode approval = new OAuthCode();
		approval.setCode(code);
		approval.setAuthenticationObject(authentication);
		mapper.save(approval);
	}

	public OAuth2Authentication remove(String code) {
		OAuth2Authentication authentication = null;

		try {
			OAuthCode oauthcode = mapper.findByCode(code);
			authentication = oauthcode != null ? oauthcode.getAuthenticationObject() : null;
		} catch (Exception e) {
			return null;
		}

		if (authentication != null) {
			mapper.delete(code);
		}

		return authentication;
	}
}