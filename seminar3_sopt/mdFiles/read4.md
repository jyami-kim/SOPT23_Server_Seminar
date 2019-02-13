# 4. AWS EC2

## AWS 소개

#### AWS
- Amazon Web Services
- 아마존닷컴이 제공하는 클라우드 컴퓨팅 시스템
- 비용은 실제 사용량에 따라 결정되지만, 처음 가입 1년 동안은 프리티어 혜택
- 다른 웹사이트나 클라이언트측 응용프로그램에 대한 온라인 서비스를 제공한다.
- Paas(Platform as a Service)이다.

>> 서버 컴퓨터를 cloud 상으로 하나 제공하는 것!

#### Cloud Computing
- 클라우드(인터넷)을 통해 서버, 저장소, 데이터 베이스, 네트워킹, 소프트웨어, 분석 등의 서비스를 제공하는 것
- 클라우드 서비스 플랫폼을 통해 종량제 결제 방식으로 제공되는 서비스형 컴퓨팅
- 확장성, 속도, 생산성, 비용에 장점을 가진다.
- cloud Computing의 종류
    + 1.IasS(Infrastructure as a Service)
    + 2.PaaS(Platform as a Service)
    + 3.SaaS(Software as a Service)

- IaaS(Infrastructure as a Service)
    + 서버를 운영하기 위한 자원, IP, Network, Storage, 전력 등을 가상환경에서 제공하는 서비스
    + PaaS, SaaS의 기반이 되는 기술이다.
    + 이용자가 직접 데이터센터를 구축할 필요 없이 클라우드 환경에서 필요한 인프라를 꺼내쓰면 되는 형식
    + ex ) NETFLIX는 자체 데이터센터를 구축하지 않고 AWS의 IaaS서비스를 이용
 
- PaaS(Platform as a Service)
    + IaaS로 제공되는 인프라 위에 서비스를 개발할 수 있는 환경을 제공하는 서비스
    + 인프라를 만들고 유지보수 하는 복잡함 없이 애플리케이션을 개발, 관리, 실행할 수 있게 플랫폼을 제공
    + ex ) 구글, 네이버, 카카오에서 제공하는 open API
 
- SaaS(Software as a Service)
    + 클라우드 환경에서 운영되는 애플리케이션 서비스
    + 모든 서비스가 클라우드에서 이루어짐
    + PC나 기업 서버에 소프트웨어를 설치할 필요 없이 비용만 내면 어디서든 바로 쓸 수 있음
    + ex ) 구글 Apps, MS 365 같은 클라우드 스토리지 서비스
    

#### AWS EC2
- Amazon Elastic Compute Cloud (Amazon EC2)
- 크기 조정이 가능한 컴퓨팅 파워를 클라우드에서 제공하는 웹 서비스
- Instance(가상 컴퓨팅 환경) 제공
- 미국리전 : 광활한 서부 어딘가에 내 서버가 동작하고 있는 데이터 센터가 잇다.
             네트워크 속도가 느리지만 비용이 싸다 (데이터 센터의 땅값이 싸다.)
- 서울리전 : 한국 어딘가 데이터 센터에 내 서버가 동작하고 있는 서버가 있다.
             네트워크 속도가 빠르지만 비용이 비싸다 (데이터 센터의 땅값이 비싸다)
- 보통 땅값에 따라 가격차이가 결정된다.
- 서울에서 멀 수록 네트워크 속도가 느리다.

## EC2 Instance 생성

+ EC2
+ 인스턴스 시작
    - 단계1 : Amzon Machine Image(AMI) 선택
    - 단계2 : 인스턴스 유형 선택
    - 단계6 : 보안 그룹 구성
    - 단계7 : 인스턴스 사직 검토
    - 키페어 선택, 및 생성
    - 인스턴스 시작 상태
+ 탄력적 IP (Elastic IP)   
  = 혼자 놀고 있으면 과금, 서버 중단/정지/삭제 할 경우 Elastic IP를 먼저 해제한후 instance 중단
    - 새주소 설정 클릭
+ 인스턴스 + 탄력적 IP 연결
    - 인스턴스 > 작업 > 주소연결 > 인스턴스 선택 + 프라이빗 IP 선택 >어소시 에이트
+ Git Bash이용해 EC2 접속 시도
    - 인스턴스 > 연결할 인스턴스 클릭 > 연결 > 인스턴스 액세스 방법의 가장 아래 ssh -i  .... 복사하기 (git bash로 EC2 접속)
    - 접속 성공!
+ EC2 서버안 환경설정
    - JAVA 8 설치
    ```
    sudo apt get install software-properties-common
    sudo add-apt-repository ppa:webupd8team/java
    sudo apt-get update
    sudo apt-get install oracle-java8-installer
    java -version
    ```
    - Maven 설치
    ```
    sudo apt list maven
    sudo apt-get install maven
    mvn -v
    ```
    -TimeZone 변경
    ```
    sudo dpkg-reconfigure tzdata
    Seoul
    ```
+ FileZilla
    - 무료 GUI FTP 프로그램 (서버안의 내용을 window의 파일 트리처럼 볼 수 있다.)
    - EC2와 연결
    ```
    왼쪽위 사이트 관리자 열기 클릭 (관리할 서버를 선택할 수 있다.)
    새사이트 생성 (여기에 EC2 서버를 새로 등록할 것이다.)
    프로토콜 SFTP선택 / 호스트 탄력적 IP 입력 / 포트 22
    키파일 : 저장해둔 pem 파일 등록
    ```
+ jar 배포
    - packaging jar 확인 <pom.xml에서 확인하기>
    - cmd 혹은 intellij terminal에서 jar 배포 명령어 입력
    ```
    mvn package
    ```
    - 배포된 jar 파일 확인
        * 프로젝트 디렉터리 > target
        * artifactId + version 이름으로 생성된 .jar파일 확인
    - EC2 서버에 jar파일 전송 (filezilla 드래그앤 드랍)
    - EC2 연결된 git bash에서 jar 파일 실행
    ```
    java -jar seminar3-0.0.1-SNAPSHOT.jar
    ```
    - Spring 실행 확인 가능
+ Demon 실행
    - 위 방법으로 실행하면, 항상 git bash가 켜져있어야 한다. 따라서, EC2 서버에서 background로 jar 파일을 실행하게 한다.
    - nohup
        * 프로세스 중단 (hangup)을 무시하고 명령어를 실행하는 명령어
        * Demon처럼 실행하고 싶은 경우 명령어 &를 사용하면 되지만, 프로세스 로그아웃(원격접속 창 X)후에도 종료없이 실행하고 싶은 경우
        * 표준 출력(console)을 nohup.out(다른 파일 가능)으로 돌릴 수 있다.
    ```
    nohup java -jar [파일이름].jar&
    nohup [명령어]& : 백그라운드 프로그램 실행
    ```
    - 현재 프로세스 확인
    ```
    netstat -tnlp
    ```
    - 프로세스 종료
    kill {프로세스ID}
    
추가 참고 자료 : [AWS 자습서](https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/EC2_GetStarted.html)

