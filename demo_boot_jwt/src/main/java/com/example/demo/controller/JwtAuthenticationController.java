package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.JwtUserDetailsService;
import com.example.demo.util.JwtTokenUtil;
import com.example.demo.vo.JwtUserDetails;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestParam String username, @RequestParam String password) throws Exception {
        final JwtUserDetails member = userDetailService.authenticateByEmailAndPassword(username, password);
        final String token = jwtTokenUtil.generateToken(member.getUsername());
        return ResponseEntity.ok(token);
    }

}