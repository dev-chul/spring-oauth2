# spring-oauth2<br>
**! 폴더명에 속지 마라... 실수로 boot가 필요없는 프로젝트에 boot를 붙였고, 수정 예정이다.**

+ Spring 5.0 이하는 오히려 문제가 없지만, 5.0 사용 시 문제가 되는 부분까지 수정한 버전<br>
    > Fork한 lostjc001/spring-example의 자료를 "MyBatis의 Mapper&Interface"로 적용한 버전<br>
    > CrossOriginFilter를 통해 CORS 이슈까지 적용한 버전

# 2021-01-11
+ demo_boot_jwt 및 demo_boot_jw_api 프로젝트는 boot를 최소화하고 jwt 라이브러리를 이용한 버전<br>
    > 필터를 이용하여 권한을 검증하는 버전<br>
    > jwt 라이브러리에 RSA 인증까지 적용한 

# 2021-09-29
+ STS + opnjdk 1.8에서 실행 시 주의할 점<br>
    > java 버전으로 인해 --add-opens java.base/java.lang=ALL-UNNAMED jvm 옵션을 주어야 한다.<br>
    > 실행 인스턴스 우클릭 > Open Config > Spring boot App > 실행 인스턴스 선택 > Arguments > Vm arguments에 "--add-opens java.base/java.lang=ALL-UNNAMED" 추가

# 2022-02-27
+ STS 다운로드 받았을 때 현재 java 16 보안이슈로 java 11을 직접 다운로드 받아 실행해야 에러가 안난다.<br>
