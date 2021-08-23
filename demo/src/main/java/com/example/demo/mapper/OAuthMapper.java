package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.OAuthAccessToken;
import com.example.demo.vo.OAuthApproval;
import com.example.demo.vo.OAuthCode;
import com.example.demo.vo.OAuthRefreshToken;
import com.example.demo.vo.OAuthUserDetails;

@Repository
@Mapper
public interface OAuthMapper {

	public OAuthUserDetails getUserById(String clientId) throws Exception;

	public int refreshApproval(OAuthApproval approval);

	public int saveApproval(OAuthApproval approval);

	public int expireApproval(OAuthApproval appr);

	public int deleteApproval(OAuthApproval appr);

	public List<OAuthApproval> findByUserIdAndClientId(String userId, String clientId);

	public void save(OAuthCode approval);

	public OAuthCode findByCode(String code);

	public void delete(String code);
	
	
	

	public OAuthAccessToken findByTokenId(String extractTokenKey);

	public void saveAccessToken(OAuthAccessToken cat);

	public void deleteAccessToken(OAuthAccessToken accessToken);

	public void saveRefreshToken(OAuthRefreshToken crt);

	public OAuthRefreshToken findRefreshTokenByTokenId(String extractTokenKey);

	public void deleteRefreshToken(OAuthRefreshToken rtk);

	public OAuthAccessToken findByRefreshToken(String extractTokenKey);

	public OAuthAccessToken findByAuthenticationId(String authenticationId);

	public List<OAuthAccessToken> findByClientIdAndUsername(String clientId, String userName);

	public List<OAuthAccessToken> findByClientId(String clientId);
	
}
