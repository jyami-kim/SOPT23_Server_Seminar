# 3. MyBatis

#### MyBatis
- Java PersistenceFrameWork의 하나이다.
- XML이나 Annotation을 사용해서 Stored Procedure나 SQL문으로 객체들을 연결시킨다.
- JDBC로 처리하는 상당부분의 코드와 파라미터 설정 및 결과 매핑을 대신해준다.
- SQL을 명시해 줘야하기 때문에 ORM으로 보기 힘들다.
- 동적 SQL을 처리할 수 있다.

## 프로젝트 시작하기
- jar 선택
- core:lombok / web: web / SQL: MySQL, MyBatis
- Maven projects need to be imported : Enable Auto-import
- Git에 프로젝트를 올린다면 올리기 전에 .gitgnore 수정하기  
    + application.properties를 꼭 추가한다. (DB 접속 정보 포함 중요 정보가 저장된다.)
- application.properties에 DB정보 추가하기
    ```
    # MySQL
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver (드라이버 이름)
    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/sopt?useSSL=false&serverTimezone=UTC
    spring.datasource.username=mjungDB
    spring.datasource.password=mjungDB
    
    logging.level.org.sopt.seminar4_sopt_p.mapper=TRACE
    ```
    ```
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver 
    spring.datasource.url=jdbc:mysql://[DB주소:포트번호/스키마이름]?userSSL=false&serverTimezone=UTC
    spring.datasource.username=[DB아이디]
    spring.datasource.username=[DB비밀번호]
    
    logging.level.패키지.mapper=TRACE //sql문을 남기면 log를 보내준다 (작동 확인)
    ```
    
## @Mapper
- Spring IoC 컨테이너에 서비스 Bean으로 등록
- 해당 인터페이스에 등록된 SQL Annotation을 토대로 실제 SQL문을 실행시켜 준다.
- 3 이상 버전부터 mapper.xml대신 inserface 클래스의 Annotation을 통해 SQL을 사용할 수 있다.
- 가급적 SQL 키워드는 대문자, 나머지는 소문자로 작성하는 것이 가독성이 좋다.
- @Service와 사용방법은 똑같다.
> SELECT WHERE FROM 등은 대문자로 나머지는 소문자로  
> 무조건 인터페이스에 넣어야한다. annotation을 토대로 실제 SQL문을 실행시켜준다.

## @Select
- 조회 Annotation
- ```#{value}```으로 동적바인딩을 처리하고 ```@Param("value")```로 값을 명시한다.
```java
@Mapper
public interface UserMapper {
    //모든회원 리스트 조회
    @Select("SELECT * FROM user")
    List<User> findAll();

    //회원이름으로 조회
    @Select("SELECT * FROM user WHERE name = @{name}")
    User findByName(@Param("name") final String name);

    //회원 고유 번호로 조회
    @Select("SELECT * FROM user WHERE userIdx=#{userIdx}")
    User findByUserIdx(@Param("userIdx") final int useridx);
}
```
> dto에 있는 User 클래스 필드대로 데이터 베이스에 있는 유저 속성들을 채워준다.   
> 그래서 db에 있는 필드 속성중 사용하고 싶지 않은 속성이 있으면 User객체에서 필드로 사용하지 않으면 된다. (ex : passwd)  
> annotation을 넣으면 해당하는 메소드를 자동으로 생성시킨다.   
> 즉, User class 메소드로 findAll()을 일일히 멤버값이랑 매칭해서 코딩하지 않고,  
> SQL문을 이용해서, SQL문의 결과값을 해당 메소드의 return값으로 설정한다.  
> 주의 : SQL의 param은 @Param / url의 param은 @RequestParam  
> @param("value")로 해당 함수의 parameter값을 SQL문에 전달한다.

#### Mapper의 사용
- 서비스(@Service)와 사용방법은 똑같다.
- 인터페이스에 @Mapper 선언, 인터페이스 작성
- 서비스 로직에서 의존성 주입
```java
private final userMapper userMapper;
public UserService(final userMapper){
    this.userMapper = userMapper;
}
```
- Mapper 사용

## @Insert
- 데이터 삽입 Annotation
- 반환 값으로 AI값을 받아오고 싶으면 @Options Annotation을 이용해 AI키 값을 명시해준다. 반환타입은 int (AI 컬럼 타입과 일치)
```java
@Mapper
public interface userMapper{
    //회원등록, Auto Increment는 회원 고유번호
        //Auto Increment 값을 받아오고 싶으면 리턴타입을 int로 하면된다.
        @Insert("INSERT INTO user(name, part) VALUES(#{user.name}, #{user.part})")
        @Options(useGeneratedKeys = true, keyColumn = "userIdx")
        int save(@Param("user") final User user);
    
        //Auto Increment 값을 받아오고 싶지않다면 필요 없다.
        @Insert("INSERT INTO user(name, part) VALUES(#{user.name}, #{user.part})")
        int save2(@Param("user") final User user);
}
```
## @Update
- 수정 Annotation
@Mapper
```java
public interface userMapper{
        //회원 삭제
        @Delete("DELETE FROM user WHERE userIdx = #{userIdx}")
        void deleteByUserIdx(@Param("userIdx")final int userIdx);
}
```

## @Delete
- 삭제 Annotation
@Mapper
```java
public interface userMapper{
        //회원 삭제
        @Delete("DELETE FROM user WHERE userIdx = #{userIdx}")
        void deleteByUserIdx(@Param("userIdx")final int userIdx);
}
```

> 내 생각 개발 로직  
> Mapper 설정 > Service 설정 > Controller 설정
> Mapper에서 서비스에서 다룰 data들만 DB에서 SQL문을 이용해서 가져온다  
> Service에서 Mapper의 return 값을 변수에 저장한 후 사용한다.  
> Controller에서 해당 Service의 URI를 설정한다.

### DTO, DAO, VO, Model
- DTO(Data Transfer Object) : 각 계층간 데이터 교환을 위한 객체
- DAO(Data Access Object) : DB에 접근하는 객체, DB를 사용해 데이터를 조작하는 기능을 하는 객체
- Model : View <-> Presentation Layer <-> Business Logic Layer
- VO(Value Object) : DTO와 동일하지만 read only
![image](https://user-images.githubusercontent.com/26458200/49031793-511a7900-f1ee-11e8-91b3-086b9b50db2f.png)

>서버로 DB를 작성하고 수정된 내용은 바로바로 workbrench에 반영된다.  