package com.example.demo.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

//    private static final String secret = "jwtpassword";

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
    	KeyPair keypair = new KeyStoreKeyFactory(new ClassPathResource("oauth2.server.jks"), "storepass".toCharArray()).getKeyPair("oauth", "keypass".toCharArray());
    	
        return Jwts.parser()
//        		.setSigningKey(secret)
        		.setSigningKey(keypair.getPublic())
        		.parseClaimsJws(token)
        		.getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(String id) {
        return generateToken(id, new HashMap<>());
    }

    public String generateToken(String id, Map<String, Object> claims) {
        return doGenerateToken(id, claims);
    }

    private String doGenerateToken(String id, Map<String, Object> claims) {
    	KeyPair keypair = new KeyStoreKeyFactory(new ClassPathResource("oauth2.server.jks"), "storepass".toCharArray()).getKeyPair("oauth", "keypass".toCharArray());
    	
        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, secret)
                .signWith(SignatureAlgorithm.RS256, keypair.getPrivate())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    
    public static String asString(Resource resource) throws IOException {
		Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8");
		return FileCopyUtils.copyToString(reader);
	}

}