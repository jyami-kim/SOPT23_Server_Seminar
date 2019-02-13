# 2. MySQL
## MySQL
- 가장 많이 쓰이는 오픈소스 괜계형 데이터 베이스 관리 시스템(RDBMS)이다.
- 다중 사용자, 다중 쓰레드를 지원한다.
- SQL을 사용한다.
- 현재는 Oracle에게 라이선스가 넘어갔다.
- 불확실한 라이선스 문제를 해결하기 위해 나온 오픈소스 DBMS가 Maria DB이다. MySQL과 사용법이 같다.
- 기본 포트는 3306번이다.

#### MySQL 엔진
- InnoDB
    + 기본값 스토리지 엔진이다.
    + 트랜잭션 safe, Commit과 Rollback, 데이터 복구 기능을 제공한다.
    + Row-level locking을 제공한다.
    + 데이터를 Clusterd Index에 저장하여 Pk기반 Query의 I/O 비용을 줄여준다.
    + Fk 제약을 제공하여 데이터 무결성을 보장한다.
    > 우리가 사용하는게 Inno DB인 것 같다.
- MyISAM
    + 트랜잭션을 지원하지 않는다.
    + 테이블 단위의 Locking을 제공한다.
    + 특정 세션이 테이블을 변경하는 동안 테이블 단위로 Lock이 잡힌다.
- Archive
    + 로그 수집에 적합한 엔진이다.
    + 데이터가 메모리 상에서 압축되고 압축된 상태로 디스크에 저장된다.
    + Row-level locking이 가능하다.
    + 한번 insert된 데이터는 update/delete가 불가능하다.
    + index를 지원하지 않는다.
    + 거의 가공하지 않을 원시 로그 데이터를 관리하는데 효율적이고, Partitioning도 지원한다.
    + 트랜잭션은 지원하지 않는다.

## Workbench
- SQL 개발과 관리, 데이터베이스 설계, 생성, 유지를 위한 단일 개발 통합 환경 제공 도구
- GUI 도구
- Maria DB도 관리 가능

#### workbench 사용하기
- setup New Connection (local) > hostname : 127.0.0.1, port : 3306
- Administration : DB 관리 메뉴
    + INSTANCE > startup/shutdown : 서버 중지, 시작
- Schemas : 스키마 및 테이블 관리 메뉴
- Schema 생성 : create Schema
    + name : schema 이름
    + 한글 데이터 깨짐 방짐을 위해 Charset=utf8, Collation=utf8_general_ci 설정
    + apply 버튼 선택으로 스키마 생성
- Table 생성 : schema-Table > 우클릭 > create table
    ![image](https://user-images.githubusercontent.com/26458200/49023535-c3815e00-f1da-11e8-8b14-3cd6ad3b1920.png)
> NN: Not Null / AI : Auto Increment : 보통 pk에 같이 걸어둔다. (pk = primary key)
- Index 설정
    ![image](https://user-images.githubusercontent.com/26458200/49023611-ef9cdf00-f1da-11e8-9026-531d43fa57d6.png)
- 외래키 설정
   ![image](https://user-images.githubusercontent.com/26458200/49023660-080cf980-f1db-11e8-9c01-8c984a2c3283.png)
    

## DDL
- DDL : Data Definition Language
- 데이터 정의어
- 테이블과 같은 데이터 구조를 정의하는데 사용되는 명령어
- CREATE, ALTER, DROP, RENAME, TRUNCATE

> DDL을 사용하는 대신에, http://aquerytool.com/을 사용해서 간단히 DB모델링을 하자  
[Aquery 링크](http://aquerytool.com/)  
[Aquery 사용법](http://aquerytool.com/help/index/)  

#### CREATE 
* 테이블 생성 ```CREATE TABLE 테이블이름 (컬럼명 컬럼타입 [기본값],);```
```mysql
CREATE TABLE `sopt`.`user`(
`userIdx` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(45) NULL,
`part` VARCHAR(45) NULL,
PRIMARY KEY (`userIdx`)
)
```
#### ALTER
* 테이블 수정 
* 컬럼 추가 ```ALTER TABLE 테이블이름 ADD (추가할 컬럼명 데이터 유형);```
* 컬럼 삭제 ```ALTER TABLE 테이블이름 DROP COLUMN 삭제할 컬럼명;```
+ 컬럼 수정 ```ALTER TABLE 테이블이름 MODIFY (컬럼명 데이터 유형);```
* 컬럼명 수정 ```RENAME COLUMN 변경할 컬럼명 TO 새로운 컬럼명;```
* 제약 조건 추가 ```ALTER TABLE 테이블이름 ADD CONSTRAINT 제약조건명 제약조건 (컬럼명);```
* 제약 조건 삭제 ```ALTER TABLE 테이블이름 ADD CONSTRANIT (컬럼명 컬럼타입 [기본값], ...);```
#### Unique Key 추가
* 유니크 키 추가 ```ALTER TABLE 테이블이름 ADD CONSTRAINT 제약조건이름 UNIQUE(컬럼이름 혹은 이름들);```
* 유니크 키 삭제 ```ALTER TABLE 테이블이름 DROP CONSTRAINT 제약조건이름```
+ 사용 예시
    - 회원-게시글 좋아요 테이블 (userIdx+contentIdx, commentIdx)
    - 회원-팔로워/팔로잉 테이블 (userIdx+followerIdx, followingIdx)
    - N:M 테이블 상황이지만 기본키가 존재하지 않을 때
    - 기본키를 설정하지 않으면 Table은 READ Only 상태이다.
#### DROP
+ 테이블 삭제 ```DROP TABLE 테이블이름```

## DML
- DML : Data Manipulation Language
- 데이터 조작어
- SELECT, INSERT, UPDATE, DELETE
#### SELECT
- 데이터 조회 ```SELECT 조회할 컬럼이름들 FROM 테이블이름 WHERE 조건```
- JOIN ```SELECT a.* FROM a JOIN b ON a.id=b.id WHERE 조건```
- SUB QUERY ```SELECT * FROM 테이블 이름 WHERE 조건 = (SELECT * FROM 테이블이름)```
- 중복제거 ```SELECT DISTINCT 중복 제거할 컬럼 FROM 테이블이름 WHERE 조건```
- 카운트 ```SELECT COUNT (*) FROM 테이블 이름 WHERE 조건```

#### JOIN
- ```SELECT a.* FROM a JOIN b ON a.id=b.id WHERE 조건```
- a 테이블과 b 테이블의 a.id와 b.id가 같은 데이터 중 조건이 맞는 a의 모든 컬럼 출력
- 시간복잡도 : O(a.length*b.length)

#### INSERT
- 데이터 삽입 ```INSERT INTO 테이블이름 (컬럼리스트) VALUE (컬럼 값들);```

#### DELETE
- 데이터 삭제 ```DELETE FROM 테이블이름 WHERE 조건```
> WHERE 조건이 없으면 전부 사라진다.