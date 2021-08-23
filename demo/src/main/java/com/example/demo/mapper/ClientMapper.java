package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.OAuthClientDetails;

@Repository
@Mapper
public interface ClientMapper {

	public OAuthClientDetails getClientById(String clientId) throws Exception;
	
}
