# 3. Spring BusinessLogic Layer

- 비즈니스 로직(핵심기능)을 구현하는 웹 애플리케이션의 중심 : 모든 기능을 여기서 구현
- 웹 애플리케이션의 성공은 비즈니스 로직에 달려있다.
- 보통 트랜잭션의 단위가 된다 (트랜잭션 = 일처리)
- 부품화
- 유지보수의 핵심
- 정답은 없다 : 코드 스타일이 드러나는 부분이다.

#### 트랜잭션의 속성 (일처리의 단위)
+ Atomicity : 원자성 : 트랜잭션 내의 모든 처리는 전부 성공하거나, 전부 실패해야한다.
+ Consistency : 일관성 : 데이터에 일관성이 있어야한다.
+ Isolation : 고립성 : 서로다른 트랜잭션끼리 영향을 줄 수 없다.
+ Durability : 영속성/ 지속성 : 트랜잭션의 결과는 지속되야 한다.

## ResponseEntity
- HTTP StatusCode를 함께 전송해주기 위해 사용한다.
- ResponseEntity는 StatusCode, Headers, Body를 설정할 수 있다.
- 내부적으로 HttpEntity를 상속한다.

```java
public class HttpEntity<T>{
    public static final HttpEntity<?> ENTITY = new HttpEntity<>();
    private final HttpHeaders headers;
    @Nullable
    private final T body;
}
```
```java
public class ResponseEntity<T> extends HttpEntity<T>{
    private final Object status;
}
```
```java
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

   @GetMapping
   public ResponseEntity getAllusers(){
       log.info("get All Users");
       return new ResponseEntity(HttpStatus.OK);
   }
}
// status : 200 OK
```
```java
    return new ResponseEntity(HttpStatus.UNAUTHORIZED);
// status : 401 Unauthorized
```
> responseEntity의 내용으로 OK를 보내고 postman으로 확인할 수 있다.  
> ResponseEntity라는 타입으로 res를 받는다. 이때 return 타입이 Object인데, 이 Object를 일정 형식의 응답값으로 만들어 줘서 client와의 데이터 전달에서 혼동이 없게 한다.  
> 이 return 타입 Object의 형식에 대한 정의가 있는 파일이 DefaultRes class  
> DefaultRes 타입의 객체를 생성한 후, 이 객체안에 생성자를 이용해 data를 담는다.  
> 그 후 이 DefaultRes 타입의 객체를 ResponseEntity 생성자안 parameter로 넣어 res를 받는다.  

#### DefaultRes
```
T: 제네릭타입, 각종 class타입이 올 수 있다. 여기서는 User라고 쉽게 생각하자.

- field
    + statusCode:int
    + responseMessage:String
    + responseData:T

- Constructor
    + DefaultRes()
        - statusCode, responseMessage만 주어진 경우 : 생성자
        
- Method
    + DefaultRes<T>.res()
        - statusCode, responseMessage만 주어진 경우 : 아래의 res method 호출해서 객체 build
        - statusCode, responseMessage, T 주어진 경우 : builder를 이용해서 객체 생성
          이경우 responseData가 json형식으로 된 T의 필드값이 return 된다.
```
```java
User user = new User(1, "김민정", "서버");
DefaultRes<User> defaultRes = new DefaultRes<User>(HttpStatus.OK.value(), "sucess Find User", user);
return new ResponseEntity<>(defaultRes, HttpStatus.OK);
```
```
{
    "statusCode": 200,
    "responseMessage": "Success Find User",
    "responseData": {
        "userIdx": 1, 
        "name": "김민정", 
        "part": "서버"
    }
}
```

> ReponseEntity객체 생성자  
  new ResponseEntity(T body, HttpHeader headers)  
  여기의 body에 DefaultRes로 client에 일정 형식의 response를 보내주고, header에 http 응답에 대한 내용을 담는다.  
  DefaultRes.statusCode = 실제로 발생한 HttpError의 값을 frontend에 보낸다.  
  header = StatusCode.OK or HttpStatus.UNAUTHRIZED 를 직접 입력해준다.  
  이유? header 값에 실제로 발생한 HttpError를 보내주게 되면, Body의 내용을 client가 확인 할 수 없다.  
  Client가 확인 할 수 있게, 일단 HttpStatus.OK로 보내주고 실제 에러 내용을 body를 통해 알려줘야 개발 속도가 향상된다.

## @Service

- Service Layer: Business Logic 중에서 애플리케이션에서 사용할 수 있게 컴포넌트(부품화)한 부분
- Bean으로 등록해 언제, 어디서든 사용할 수 있는 Class (재사용)
- @Service : SpringIoC 컨테이너에 서비스 Bean으로 등록

#### UserService에 대한 구현 실습
- public class 위에 @Service annotation을 달아 준다.
- 이때 Interface 및 구현체를 만들지 않고 바로 @Service를 선언하나 만들고 @Service를 사용하나 같은 기능을 한다.

- Interface 및 구현체를 사용한 경우
    + 거의 대부분의 기능은 interface와 구현체가 1대1 매핑 - 불피룡한 작업이 많아진다.
    + 같은 기능이지만 상황에 따라 다르게 구현하고 싶을 때는 유용하다.
    + 설계가 어렵지만 유지 보수가 더 쉬워진다.
    + 좀 더 안전하다. (강제성)
    + interface만 보고도 무슨 기능인지 알 수 있게 해야한다.
```
<파일 생성>
+ service
    - impl
        ㄴ UserServiceImpl (class)
    - UserService (interface)    
```

```java
//@Service Annotation 사용
@Service
public class UserServiceImpl implements UserService {
    @Override
    public DefaultRes<List<User>> findAll(){}
}

//value값 명시
@Service(value = "userserviceImp1")
public class USerServiceImp1 implements UserService {}
```

- Interface 및 구현체를 사용하지 않을 경우
```java
@Service
public class UserServiceImpl {
    public DefaultRes<List<User>> findAll(){}
}
```

- HttpStatus int 값으로 보내기 : value() 메소드 사용하기.
```java
    HttpStatus.NOT_FOUND.value()
```

> findUserIdx 주의점 : 나는 userList.get(userIdx)로 했었는데, 그렇게 할 경우, List의 userIdx와 User의 userIdx와 다를 경우 에러가 난다.  
> 즉, DB상에서 Delete를 했을 때 UserIdx가 1씩 증가하게 되는 특징 때문에, 어긋 나는 경우가 생긴다.

#### 코드 흐름
> UserController에서 URL을 받아 해당하는 URL 요청에 맞는 메소드를 실행한다.  
> 이때 UserController의 메소드가 끝나고 나면 response를 받는데, 이는 ResponseEntity 값을 return해주기 때문에 가능하다.  
> 이 메소드 안의 내용이 예를들어 사용자 회원가입이라고 하면  
> 이때 사용자와 관련한 Service들을 모아둔 것이 Service > UserService인 것이고  
> 이 UserService 클래스의 여러 메소드중 URL 요청에 해당하는 내용의 UserService 메소드를 사용한다.  
> UserService 클래스는 DefaultRes라는 일정 형식의 response 하기 때문에,  
> 이 DefaultRes를 ResponseEntitiy에 담아서 response를 받을 수 있도록 한다.  

다음 코드 흐름에 맞게 UserController를 수정하였다.

## @Autowired
- @Autowired 사용은 권장되지 않음
- Spring IoC 컨테이너에 들어있는 Bean 객체 중에서 타입이 일치하는 Bean 객체를 자동으로 주입(삽입) 해준다 (DI)
- 생성자, 필드, 메소드, 세 곳에 적용이 가능하다

> 원래는 @Service로 Spring IoC 컨테이너에 Bean 객체를 삽입하고  
> @Autowired로 사용을 하는데 이를 권장하지 않는다는 것!

> 단순히 키워드를 쓰지 말라기보단 자동 의존성 주입에 이존하게 되면, 의존성이 많이 걸린 만능 클래스를 만드는 습관이 든다  
> 생성자에서만 명확하게 파라미터를 주입받게 로직을 구성하는 습관을 들이자  
> 생성자 호출이 어려울수록 이 클래스가 무겁다는 눈치를 챌 수 있다.  
> 근데 @Autowired에 지나치에 의존하게 되면, 유지보수를 어렵게 만든다.


#### 생성자 의존성 주입
- @Qutowired 사용 대신에 생성자로 의존성을 주입하자
- 아래 방법을 제일 권장합니다.
- 아래의 경우 constructor가 한개일 경우에만!!! : 이경우 @Autowired 생략이 가능하기 때문이다.
```java
public class UserController{
    private final UserService userService;
    
    public UserController(final UserService userService){
        this.userService = userService;
    }
    
    public ResponseEntity getAllusers(){
        log.info("get All Users");
        return new ResponseEntity<>(userService.findAll(). HttpStatus.OK);
    }
}
```
```
Construcotr Injection을 권장하는 이유
1. 단일 책임의 원책
생성자 인자가 많을 ㅅ경우 코드량도 많아지고, 의존 관계도 많아져 단일 책임의 원칙에 위배된다.
따라서 Constructor Injection을 사용해서 의존관계, 복잡성을 쉽게 알 수 있어 리팩토링읟 단조를 제공한다.

2. 테스트 용이성
DI 컨테이너에서 관리되는 클래스는 특정 DI 컨테이너에 의존하지 않고 POJO여야 한다.
DI 컨테이너를 사용하지 않고도 인스턴스화 할 수 있고, 단위 테스트도 가능하며, 다른 DI 프레임 워크로 전환 할 수도 있게 된다.

3. Immutability
Constructor Injection에서는 필드는 final로 선언할 수 있다.
불변 객체가 가능한데 비해 Field injection은 final는 선언할 수 없기 때문에 객체가 변경 가능한 상태가 된다.

4. 순환 의존성
Constructor Injection에서는 멤버 객체가 순환 의존성을 가질 경우 BeanCurrentlyInCreationException이 발생해서 순환 의존성을 알 수 있게 된다.

5. 의존성 명시
의존 객체 중 필수는 Constructor Injection을 옵션인 경우는 Setter Injection을 활용할 수 있다.
```
- Lombok의 @RequiredArgsConstructor과 @NonNull 사용해서 할 수 있지만 사용하지 말기
> Lombok 자체에 오류가 있는 경우가 있기 때문에 직접 생성해 주는 것이 좀더 유리

#### @Qulifier
- 동일한 타입의 Bean 객체들 중에서 특정 Bean을 사용하도록 설정한다.
- 같은 이름의 서비스 인터페이스가 여러개일 경우
    + 교수님 학생 둘다 UserService이지만, Student Professor다른 서비스일 경우? 이런 경우때문에, Quelifer를 사용한다.
    
```java
@Service(value = "UserServiceImpl")
public class UserServiceImpll implements UserService{}

@Service(value = "UserServiceImpl2")
public class UserServiceImpl2 implements UserService{}
```

> 이 컨트롤러는 서비스 3개에 의존한다.  
> 하나의 컨트롤러가 3개의 서비스를 이용!, 1개를 바꾸려면 3개 모두 바꿔야 해서 어렵다.
```java
private final UserService userService;
private final UserService userService2;
private fianl LogiService loginService;

public TempController(@Qualifier("UserserviceImpl") final UserService userService,
                      @Qualifier("UserServiceImpl2") final UserService userService2,
                      final LoginService loginService){
    this.userService = userService;
    this.userService2 = userService2;
    this.loginService = loginService;
}
```
#### IoC를 이용한 클래스 호출 방식
![image](https://user-images.githubusercontent.com/26458200/48881049-99384500-ee56-11e8-91ef-78218c19ee04.png)

> Q. @Authrize를 사용하지 않는데 @Service를 사용하는 이유?? 왜냐하면 @Service annotation 없이 그냥 Class 생성해서 Controller에서 해당 Class를 접근해서 사용해도 되지 않는가에 대한 의문  
> A. @Service를 사용하지 않으면, 객체의 life cycle을 개발자가 관리해야하지만, @Service를 사용하면, 객체의 life cycle을 Spring IoC가 관리해주기 때문이다.