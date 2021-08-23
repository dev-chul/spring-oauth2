package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.OAuthUserDetails;

@Repository
@Mapper
public interface UserMapper {

	public OAuthUserDetails getUserById(String clientId) throws Exception;
	
}
