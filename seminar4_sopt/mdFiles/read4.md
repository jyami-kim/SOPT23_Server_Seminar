# 4. AWS RDS
## AWS RDS
- 클라우드 관계형 테이터베이스를 더욱 간편하게 설정, 운영 및 확장할 수 있다.
- 소모적인 관리작업(DB 설정, 패치 및 백업)을 자동화하여 애플리케이션 개발에 집중할 수 있게 해준다.
- 익숙한 데이터베이스 엔진 중에서 선택하여 기존 데이터베이스를 쉽게 복제할 수 있다.
- 내 PC에 DBMS를 설치하지 않아도 서버를 사용할 수 있다.
- 쉽게 생각해 DB만 설치된 서버를 사용하는 것이다.

## AWS RDS Instance 생성
- 생성전 주의사항 : 지역 서울인지 확인하기 : Free Tier은 RDS Instacne가 꼭 1개여야 한다
- Aurora DB 인스턴스 시작
    + 엔진 옵션 : MySQL / RDS 프리티어 옵션 사용
    + DB 세부 정보 지정: DB엔진=mysql5.7.21 / 설정 DB인스턴스 식별자, 사용자이름, 암호 (꼭 기억하기)
    + 네트워크 및 보안 : 퍼블릭 엑세스 가능성 예 / 가용영역 인스턴스 설정
- RDS 인스턴스 확인 > 연결 (엔드포인트, 보안그룹 확인)
    + 엔드포인트 = DB주소
    + 포트확인 = 3306 기본값
    + 보안그룹 inbound 0.0.0.0/0, outbound 0.0.0.0/0
- 보안그룹 inbound 클릭 > EC2 보안그룹으로 이동
    + 보안그룹 > 인바운드 > 편집 : 규칙 편집하기
    
## AWS RDS Encoding
- RDS > 파라미터그룹
- 파라미터그룹 생성
    + 파라미터 그룹 패밀리: mysql5.7
    + 그룹이름: korean (Korean encoding)
- 파라미터 편집
    + 모두 utf8 설정
        + character_set_client
        + character_set_connection
        + character_set_database
        + character_set_filesystem
        + character_set_results
        + character_set_server
    + 모두 utf8_general_ci 설정
        + collation_connection
        + collation_server
- RDS > 인스턴스 > 세부정보 > 수정
- 데이터 베이스 옵션
    + DB 파라미터 그룹 > Korean encoding 선택
    + 계속
    + DB 인스턴스 수정
    
## AWS RDS Instance 연동
- Workbench Connection 추가
    + Hostname : RDS 엔드포인트
    + Port : RDS 포트 (기본값 3306)
    + Username : RDS 사용자이름 / Password : RDS 비밀번호
- application.properties 파일 수정
- 이후 Workbench에서 schema와 table 생성하기!