# 3. Tomcat

## Tomcat

1. Apache 소프트웨어 재단에서 개발한 [servlet container(Web container)](./read1.md#servlet)만 있는 web application server(WAS)
2. Web Server와 연동해 실행할 수 있는 자바 환경 제공
3. servlet이나 jsp를 실행하기 위한 serlet container를 제공
4. 정적 컨텐츠를 로딩하는데 웹 서버보다 수행 속도가 느리다.
5. tomcat 8.5 기준
   maxTreadPool : 200  
   minSpareThreads : 25  
   maxQueueSize : Interger.MAX_VALUE  

## Spring boot app 배포

1. 내장 Tomcat이용
   1-1. Jar로 build   
   1-2. Maven 명령어 이용  

2. 다른 WAS(Tomcat)이용  
   2-1. War로 build  

#### - war로 build

pom.xml 파일  
application의 각종 속성들이 적혀있다.  
```<artifactId> <version>``` 을 조합하여 build 파일을 생성한다.  
```<packaging>``` : war 파일로 build파일 저장  

첫 build : build > build artifacts > title.war  
이후 build : build > build_project

build를 하게되면 프로젝트폴더 > tartget폴더에 title.war가  다운로드되고  
tomcat의 startup.bat을 사용해서 title.war 파일을 실행하여 앱 서버를 돌린다.  
(http:127.0.0.1:8080/title)로 접속할 수 있다. - 자신의 application이름이 필요하다.  

#### - maven 명령어 이용

cmd에서 프로젝트 폴더 최 상단으로 이동 (shift + 우클릭 > powershell) 후 maven 명령어 입력
<pre><code> mvn spring-boot:run </code></pre> 
(http:127.0.0.1:8080)로 접속할 수 있다. - 자신의 application이름이 필요 없다.

#### - jar로 build

```<packaging>``` : jar 파일로 build파일 저장  
cmd에서 프로젝트 폴더 최 상단으로 이동 후 maven 명령어 입력  
<pre><code>mvn package</code></pre>
build가 완료되고, 내프로젝트에 title.jar 파일이 생성되어있다.  
이 파일은 tomcat을 내장하고 있기 때문에 tomcat의 bat을 키지 않아도 독립적으로 실행 가능하다. 실행을 위해 cmd를 이용한다.
<pre><code>java -jar ./title.jar</code></pre>  
(http:127.0.0.1:8080)로 접속할 수 있다. - 자신의 application이름이 필요 없다.  

