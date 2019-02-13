# 3. Authorization & Authentication

## Authorization & Authentication

- Authorization(어쏠라이제이션, 인가)
    + 권한 부여
    + 해당 자원에 대해서 사용자가 그 자원을 사용할 권한이 있는지 체크하는 권한 체크 과정
- Authentication(어쎈티케이션, 인증)
    + 인증 과정
    + 사용자가 서비스를 사용하는 것이 가능한지를 확인하는 절차

- 로그인 시나리오
    +1) 클라이언트로부터 아이디, 패스워드를 입력받는다 (post)
    +2) 해당 아이디, 패스워드에 일치한 회원이 있는지 검사한다.
    +3) 있다면 JWT(Json Web Token)을 발급한다.
    +4) 없다면 로그인 실패를 반환한다.
    +5) 그 이후 인증이 필요한 API를 요청 시 클라이언트는 JWT를 Http Header의 Authorization에 넣어서 보낸다.
    +6) 서버는 Header의 Authorization이 있는지 검사, 있다면 토큰 검증 작업 등을 통해 사용자를 확인한다.
    +7) 인가 절차가 완료되면 정상적으로 API를 실행하고 반환 값을 준다.
    > 1 ~ 4 : 인증 / 5 ~ 7 : 인가

## JWT
#### JSON (JavaScript Object Notation)
- 속성-값 / Key-Value / 키-값 쌍으로 이루어진 데이터 오브젝트를 전달하기 위해 사람이 읽을 수 있는 텍스트를 사용하는 개방형 표준 포맷이다.
- 인터넷에서 자료를 주고 받을 때 그 자료를 표현하는 방법 중 하나이다.
- 언어 독립형 데이터 포맷이다. 프로그래밍 언어나 플랫폼에 독립적이다.
- 문자열은 "", 숫자는 ""가 필요없다. 숫자에 ""를 같이 사용하면 문자로 인식한다.
- 키 값 역시 ""로 표현해야한다.
- 키-값 쌍이 여러개라면 , 로 연결한다.

#### Cookie (쿠키)
- 클라이언트(웹브라우저) Local에 저장되는 Key와 Value가 들어있는 작은 데이터 파일이다.
- 이름, 값, 만료 날짜(쿠키 저장기간), 경로 정보가 들어있다.
- 일정 시간동안 데이터를 저장할 수 있다.(로그인 상태 유지에 활용)
- 클라이언트의 상태 정보를 Local에 저장했다가 참조한다.
- 쿠키는 사용자가 따로 요청하지 않아도 브라우저가 요청시에 Header에 넣어서 자동으로 서버에 전송한다.
- 쿠키는 상태없는(stateless) HTTP 프로토콜에서 상태기반 정보를 기억한다.
- 클라이언트에 300개 까지 저장 가능하고, 하나의 도메인당 20개의 값만 가질 수 있으며, 하나의 쿠키 값은 4KB까지 저장 가능하다.
- 쿠키 시나리오
    + 브라우저에서 웹 페이지 접속
    + 클라이언트가 요청한 웹 페이지를 받으면서 쿠키를 클라이언트(Local Storage)에 저장
    + 클라이언트가 재 요청 시 요청과 함께 쿠키 값도 전송
    + 지속적으로 로그인 정보를 가지고 있는 것처럼 사용한다.

#### Session (세션)
- 일정 시간동안 같은 브라우저로 들어오는 일련의 요구를 하나의 상태로 보고 그 상태를 유지하는 기술
- 웹 브라우저를 통해 웹 서버에 접속한 이후로 브라우저를 종료할 때 까지 유지되는 상태
- 클라이언트가 요청을 보내면 Servlet Container가 클라이언트에게 유일한 세션ID를 부여한다.
- 세션은 서버 메모리에 저장된다. 따라서 서버가 재시작되면(메모리 리셋) 세션 데이터 역시 사라진다.
- 세션을 구분하기 위해 ID가 필요하고, 그 ID만 쿠키를 이용해 저장해 놓는다.
- 세션 시나리오
    + 클라이언트가 서버에 접속 시 세션ID를 발급한다.
    + 서버에서는 클라이언트로 발급해준 세션ID를 쿠키를 사용해 저장한다 (JSESSIONID)
    + 클라이언트는 다시 접속할 때 이 쿠키를 이용해서 세션 ID값을 서버에 전달한다.
    + 서버는 이 세션 ID 값으로 클라이언트를 구분한다.

#### Token (토큰)
- Stateless 방식이다. 상태유지를 하지 않는다.
- 사용자의 인증 정보를 서버나 세션, 쿠키에 담아두지 않는다.
- 세션이 존재하지 않으니 로그인 되어있는지 안 되어있는지는 신경 쓸 필요가 없다.
- 따라서 서버를 손 쉽게 확장할 수 있다.
- 모바일 애플리케이션에 적합하다.
- 인증 정보를 다른 애플리케이션에 전달할 수 있다. (OAuth)
- 서버 기반 인증의 문제점 : 세션, 확정성 CORS의 문제점을 보완할 수 있다.

#### JWT(JSON Web Token)
- 토크 기반 인증 시스템의 구현체이다.
- JSON 객체를 사용한다.
- 가볍고 자가수용적이다.(self-contained)
- 수많은 프로그래밍 언어에서 지원된다.
- 쉽게 HTTP 헤더, URL 파라미터 등으로 전달될 수 있다.
```
aaaaaa.bbbbbb.cccccc
 헤더 . 내용 . 서명
```
- 헤더(header) : 토큰의 타입(JWT), 해싱 알고리즘 명시(HMAC SHA256)
- 내용(payload) : 실제 정보(claim)가 들어가게 된다.
- 서명(signature) : 토큰의 유효성 검증을 위해 서명 작업을 거친다. 헤더의 인코딩 값과 내용의 인코딩 값을 합친 후 비밀키로 해쉬를 하여 생성한다.
- 토큰 자체에 많은 내용을 넣어서 사용하지 않는다.
- 토큰 자체에 내용을 답고 있어서 자가 수용적이다.

- 토큰 값 확인 ```https://jwt.io```
    + Encode에 토큰 값을 넣고, signature verified가 뜨면 정상적인 토큰이다.
    + Encode에 토큰 값을 넣고, Invalid Signature가 뜨면 비 정상적인 토큰이다.

## 로그인 서비스 구현
- pom.xml에 새로 추가할 dependency
```
<!-- AOP -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.11</version>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.4.0</version>
</dependency>
```
- application.properties파일 내용 추가
    + ISSUER : 토큰 발급자
    + SECRET : 토큰 해쉬 키 값, 쓰레기 값일수록 안전하지만 암호화 시간이 오래걸린다.
```
#JWT 토큰 발급자
JWT.ISSUER=JYAMI
#JWT 키 값, 아무 쓰레기값 입력, 쓰레기일 수록 안전하다.
JWT.SECRET=ahf!#$DDFA%$
```

- Auth annotation
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Auth {
}
```
    + @Target(ElementType.METHOD) : 메소드에 적용
    + @Retention(RetentionPolicy.RUNTIME) : 런타임시까지 참조 가능
    + @Documented : Java Doc에도 표시
    + @Inherited : 상속가능
> 이 Auth annotation을 controller에 붙이면, 그 controller는 인증이 필요한 API가 되도록 만든다.

- JwtService 
> 토큰을 해석했을 때 user_idx이 들어가있게 한다.  
> 토큰 생성 : user_idx 값이 들어간 토큰을 생성한다.
> 토큰 해독 : 토큰을 검증했을 때, 정상적이면 토큰안에 들어있는 user_idx 값 반환, 아니라면 -1 반환
[JwtService.java](../src/main/java/org/sopt/seminar5/service/JwtService.java)

- AuthAspect
> @Around를 이용해서, 괄호안의 annotation이 있을 시, 아래 메소드를 불러온다  
> 토큰 존재여부 확인 - 토큰 해독 - 토큰 검사 - 유효 사용자 검사
> 만약 @Around가 가리키는 Annotation이 Auth이면, 어떤 메소드에 @Auth를 붙이게 되면, 여기의 @Around 아래있는 메소드가 그 위에 삽입되서 코드가 실행된다고 생각하면된다.
[AuthAspect.java](../src/main/java/org/sopt/seminar5/utils/Auth/AuthAspect.java)

- AuthService
> 로그인 서비스! JwtService로 토큰 값을 로그인시 생성하게 한다
>> JwtService는 토큰을 생성하는 서비스 (DefaultRes가 아니다.), 다른 서비스의 중간과정이 될 수 있다.  
>> AuthService는 로그인시 작동할 서비스 (DefaultRes이다.), 컨트롤러에서 Data에 담기위한 정보를 가져오는 최종 서비스이다.
[AuthService.java](../src/main/java/org/sopt/seminar5/service/AuthService.java)

> 이 두개는 같이 따라오는 것이라고 생각해도 될 것같다.
```java
@Auth
@RequestHeader("Authrozation" final String header);
log.info("ID : " + jwtService.decode(header));
```













