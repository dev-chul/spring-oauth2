package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ClientMapper;
import com.example.demo.vo.OAuthClientDetails;

@Service("oauthClientDetailsService")
public class OAuthClientDetailsService implements ClientDetailsService {

	@Autowired
	private ClientMapper mapper;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		OAuthClientDetails client = null;
		try {
			client = mapper.getClientById(clientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (client == null) {
			throw new ClientRegistrationException(clientId);
		}
		
		return client;
	}
}
