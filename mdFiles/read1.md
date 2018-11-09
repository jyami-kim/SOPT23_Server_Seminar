# 1. 서버 배경지식

## server & client

- Client : 서비스 요청자, 사용자, 다른 server 등

- Server : 서비스 제공자

- Server - Client Model : 작업을 분리해주는 분산 애플리케이션 구조, 네트워크 아키텍쳐

- Client가 사용자 입력에 초점을 맞춘 반면, Server는 공유 데이터의 처리 및 저장, 웹 페이지 지원, 또는 네트워크 활동 관리 등의 역할을 수행한다

## HTTP
##### HTTP란?
Hyper Text Transfer Protocol : 인터넷 상에서 정보를 주고받을 수 있는 프로토콜

- TCP, UDP, IP 프로토콜 사용
- 80번 포트 사용 (HTTPS = 443 포트)  
- 연결 상태를 유지하지 않는 프로토콜이다  
- 서버에 접속해 클라이언트 요청에 대한 응답을 전송한 후 연결 종료, 전산 자원이 적게 들지만, 클라이언트 구분이 힘들다. 요청이 많아질 경우 문제가 발생한다. Cookie, Session, Token등을 사용해 단점 해소  
- 클라이언트와 서버 사이에 이루어지는 요청(Request)/ 응답(Response) 프로토콜이다.  

##### HTTP 메세지의 구성

- 서버와 클라이언트 간 데이터 교환 방식
  +  Request: 클라이언트에 의해 전달되어 서버의 동작을 일으킨다. 요청 메시지   
  +  Response : Request에 대한 서버의 회신이다. 응답 메시지  

- 포함되어야 하는 것 : HTTP method, Request Target(요청 대상, URL, 도메인), HTTP 버전정보, 상태코드

<pre><code>Requests : POST/HTTP/1.1 (method / HTTP 버전정보)
Responses : HTTP/1.1 403 Forbidden (method / HTTP 버전정보 / 상태코드)</code></pre>

- Header

<pre><code>Host, User-Agent, Accept, Accept-Language, Accept-Encoding (Request headers)
Connection, Upgrade-Insecure-Requests (General headers)
Content-Type, Content-Length (Entity headers)</code></pre>

+ Body : 전송하고 전송 받은 데이터 (화면 표시 및 서버에 데이터 저장 등)  
+ POST 요청인 경우, 리소스를 갱신하기 위한 목적으로 데이터를 전송한다.

##### HTTP Method
<pre>
GET - 조회 - Read

POST - 생성 - Create

PUT - 수정 - Update

DELETE - 삭제 - Delete</pre>

##### HTTP Status Code

HTTP 응답(response) 상태 코드

클라이언트에게 정보를 보낼 때 숫자 값으로 상태를 알 수 있다.

<pre>
2XX (성공)
- 200 : OK : 서버가 요청을 처리했다. 서버가 요청한 페이지를 제공했다.
- 201 : Created : 성공적으로 요청되었으며 서버가 새 리소스를 작성했다.
- 204 : No Content : 서버가 요청을 성공적으로 처리했지만 컨텐츠를 제공하지 않는다.

4XX (실패)
- 400 : Bad Request : 서버가 요청을 이해하지 못했다.
- 401 : Unauthorized : 인증이 필요하다. (인증 실패) 코드 이름은 Unauthorized(권한없음)이지만 실제뜻은 Unauthenticated(인증 안됨)에 가깝다 
- 403 : Forbidden : 요청을 거부하고있다. (인가 실패) 사용자가 리소스에 대한 필요 권한을 갖고있지 않다.
- 404 : Not Found : 리소스, 페이지를 찾을 수 없다. 서버에 존재하지 않는 페이지에 대한 요청

5XX (서버오류)
- 500 : Internal Server Error: 서버 내부 오류
- 503 : Service Unavailable : 일시적으로 서비스를 사용할 수 없다. 서버가 오버로드 되거나 유지관리를 위해 다운되었기 때문</pre>

## MVC

Model View Controller Architecture

사용자가 Controller를 조작하면 Controller는 Model을 통해서 데이터를 가져오고 그정보를 바탕으로 시작적인 표현을 담당하는 View를 제어해서 사용자에게 전달한다.

+  Model : 정보(데이터), 데이터 베이스

+ View : 사용자 인터페이스, 프론트엔드

+ Controller : 데이터와 비즈니스 로직사이의 상호작용 관리

사용자가 접근한 URL에 따라 요청에 맞는 데이터를 Model로 처리를 위임하고, 데이터를 View에 반영해 사용자에게 알려준다.

##### Spring MVC

1. 클라이언트 요청에 대해 **DispatcherServlet**에 최초 진입한다.
2. **DispatcherServlet**은 Spring Bean Definition에 설정되어있는 **Handler Mapping** 정보를 참조해 요청을 처리하기 위한 **Controller**를 찾는다.
3. **DispatcherServlet**이 선택된 **Controller**를 호출하여 클라이언트가 요청한 작업을 처리한다.
4. **Controller**는 Business Layer와의 통신을 통해 원하는 작업을 처리한 다음 요청에 대한 성공유무에 따라 **ModelAndView** 인스턴스를 반환한다.  
**ModelAndView** : UI Layer에서 사용할 Model 데이터와 UI Layer로 사용할 View에 대한 정보가 포함되어있다. 
5. **DispatcherServlet**은 **ModelAndView**의 View의 이름이 논리적인 View 정보일 때 **ViewResolver**를 참조하여 논리적인 View 정보를 실질적으로 처리해야할 View로 생성한다.
6. **Dispatcherservlet**은 **ViewResolver**를 통하여 전달된 View에게 **ModelAndView**를 전달하여 마지막으로 클라이언트에게 원하는 UI를 제공할 수 있도록한다.
7. 마지막으로 클라이언트에게 UI를 제공할 책임은 View 클래스가 담당하게 된다.

## Servlet

- java를 사용해 웹 페이지를 동적으로 생성하는 서버 프로그램
- 웹 서버의 성능을 향상하기 위해 사용되는 자바 클래스의 일종
- JSP vs Servlet  
  **jsp** : html에 java코드 포함 VS **servlet** : java코드에 html포함
- node.js vs servlet  
  **node** : single thread  VS  **servlet** : multi thread
- java로 구현되므로 다양한 플랫폼에서 동작

##### Web container (Servlet Container)

웹 클라이언트로부터 http 요청이 전달되면 해당 http 요청을 해석하여 적절한 서블릿의 service 메서드(Get, Post)를 ServletRequest, ServletResponse 매개 변수와 함께 호출  

init > service (get, post) > destroy  
이떄 service 단계에서 request와 response가 호출된다.

- 웹서버 컴포넌트중 하나로 java servlet과 상호작용한다.
- servlet의 생명 주기 관리, URL과 특정 servlet을 Mapping하여 URL 요청이 올바른 접근 권한을 갖게 한다.
- Servlet, JSP, Server Side Code가 포함된 다른 타입의 파일들에 대한 요청을 다른다.
- servlet객체를 생성하고, servlet을 로드, 언로드하여 요청과 응답 객체를 생성, 관리하고 다른 servlet 관리 작업을 수행한다.

대표적인 Servlet Container가 Tomcat!

> Servlet container와 WAS가 비슷한 개념인 것 같다.

## WAS

WAS : Web Applicaion Server

- 인터넷 상에서 HTTP를 통해 사용자 컴퓨터나 장치에 애플리케이션을 수행해 주는 미들웨어
- 동적 서버 컨텐츠를 수행하는 것, 주로 DB서버와 같이 수행된다.
- 대부분 자바 기반이다.
- 일반적으로 Web Server의 기능을 포함하고 있어, Web Server가 없어도 서비스가 가능하다.
- 여러개의 트랜잭션을 관리한다.
- Tomcat 

> Spring은 WAS가 있어야해서 Tomcat을 깔지만, Spring boot는 WAS, Tomcat 자체를 갖고있어서 그냥 실행가능

##### WAS의 생명 주기
1. Web Server로 부터 요청이 들어오면 제일 먼저 컨테이너가 이를 알맞게 처리한다.
2. 컨테이너는 web.xml(배포서술자)를 참조하여 해당 servlet에 대한 thread를 생성하고, httpservletRequest와 httpServletResponse 객체를 생성하여 전달한다.
3. 컨테이너가 servlet을 호출한다. (service())
4. 호출된 servlet의 작업을 담당하는 스레드는 request에 따라 doPost(), doGet()을 호출한다.
5. 호출된 doPost(), doGet() 메소드는 생성된 동적 페이지를 Response 객체에 실어서 컨테이너에 전달한다.
6. 컨테이너는 전달받은 Response 객체를 HTTPResponse 형태로 전환하여 웹서버에 전달하고 생성된 스레드 종료, httpServletRequest, httpServletResponse 객체를 소멸시킨다.

## Web Server

HTTP를 통해 웹 브라우저에서 용청하는 HTML, CSS, Image 파일 등 정적 컨텐츠를 전송해주는 서버이다.  
Nginx