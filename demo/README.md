# 1. spring-oauth2 > demo App <br>
**! Spring Security와 Oauth 2.0을 이용한 인증 서버 초기 예시이다.**<br>

# 토큰 요청
Request URL : 127.0.0.1:[포트]/oauth/token<br>
Header<br>

    authorization : Basic Y2xpZW50OnNlY3JldA==
	
    Content-Type : application/x-www-form-urlencoded
	
Body(raw)<br>

    grant_type=password&username=user&password=pass&scope=read_profile&
	
Result<br>

    {
	
        "access_token": "1c3798a0-cfcb-47a3-9436-2efe716531c6",
		
        "token_type": "bearer",
		
        "refresh_token": "15160731-f413-4119-bc3a-985d40b63597",
		
        "expires_in": 35999,
		
        "scope": "read_profile"
		
    }
	
# 2. authorization의 Y2xpZW50OnNlY3JldA== 값은 어디서?
**아래서 조합 후 Base64 인코드를 통해 관리된다.**
```java
    private static void makeAuthorizationRequestHeader() {
        String oauthClientId = "client";
        String oauthClientSecret = "secret";

        Encoder encoder = Base64.getEncoder();
        try {
            String toEncodeString = String.format("%s:%s", oauthClientId, oauthClientSecret);
            String authorizationRequestHeader = "Basic " + encoder.encodeToString(toEncodeString.getBytes("UTF-8"));
            log.debug("AuthorizationRequestHeader : [{}] ", authorizationRequestHeader);            // Y2xpZW50OnNlY3JldA==
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }
```
