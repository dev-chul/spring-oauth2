package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.JwtUserDetails;

@Repository
@Mapper
public interface JwtMapper {

	public JwtUserDetails getUserById(String username) throws Exception;

}
