# 1. Spring

Spring architectcure

![image](https://user-images.githubusercontent.com/26458200/48550221-803f0980-e915-11e8-812c-e82c048f937b.png)

## Spring IoC
IoC : Inversion of Control : 제어의 역전  

- 프로그램의 제어 흐름 구조가 바뀌는 것
- 사용자가 객체를 생성하고 소멸시키는 것이 컨테이너가 대신 하게 된다. (제어의 역전)
- 이 제어건이 스프링 Container로 넘어가는 것이 Spring IoC이다.
- 제어권이 Container로 넘어 감으로써 DI, AOP가 가능하다.
- 인스턴스의 생성부터 소멸까지의 객체(Bean)생명 주기를 Container가 관리한다.

> 원래는 객체 생성, 소멸을 Java 문법에 맞게 했어야 하는데 Spring IoC를 사용하게 되면, 객체 생성을 스프링이 대신 해준다. (객체를 new로 선언해서 하는 걸 안해도 된다.)

## Spring DI
DI : Dependency Injection : 의존성 주입
- 인스턴스를 자신이 아닌 IoC Container에서 생성 후 주입한다.
- 내부적으로 new 키워드를 사용하지 않고 내부적으로 setter나 constructor를 이용한다.
- 기능이 변경될 때 마다 코드를 변경하는 게 비용이 많이 들어, 코드의 변화가 적어지도록 프로그램 작성을 위해 탄생
- 모듈 간 결합도를 낮춰서 유연한 변경을 가능하도록한다.
- 불 필요한 의존 관계를 없애거나 줄일 수 있다.
- 각 객체를 bean 컨테이너로 관린한다.
- IoC를 구현하는 한가지 방법이 DI이다.
>> 각 객체를 container로 관리한다.new 연산자로 객체를 만드는 걸 Spring DI가 대신해준다. 내가 만든 user 객체 같은거 말고, 웹적인 객체를 얘가 관리한다.

## Spring Bean

#### Spring Container
- 컨테이너는 보통 인스턴스의 생명 주기를 관리하며, 생성된 인스턴스들에게 추가적인 기능을 제공하도록 한다.
- 작성한 코드의 처리과정을 위임 받은 독립적인 존재이다.
- 적절한 설정만 되어있다면 누구의 도움 없이도 프로그래머가 작성한 코드를 스스로 참조한 뒤 알아서 객체의 생성과 소멸을 컨트롤 해준다.
- Spring Container는 IoC를 사용해 어플리케이션을 구성하는 빈 컴포넌트들을 관리한다.
- Spring Container = IoC Container = DI Container
- Spring Container는 두 종류가 있다. : Bean Factory, ApplicationContext

- 생명주기 : 생성 -> 설정 -> 사용 -> 종료

#### 1. BeanFactory
- DI의 기본사항을 제공하는 가장 단순한 컨테이너
- 팩토리 패턴을 구현한 것
- Bean을 생성하고 분배하는 책임을 지는 클래스
- Bean의 정의는 즉시 로딩 하지만, 빈 인스턴스 생성은 Lazy Loading한다.
- 처음으로 getBean()이 호출된 시점에서야 해당 빈을 생성(Lazy Loading)

#### 2. ApplicationContext
- BeanFactory 인터페이스를 상속받은 하위 인터페이스
- BeanFactory와 유사한 기능을 제공하지만 좀 더 많은 기능을 제공한다
- 국제화가 지원되는 텍스트 메시지를 관리
- 이미지같은 파일 자원을 로드 할 수 있는 포괄적인 방법을 제공한다.
- Listener로 등록된 Bean에게 이벤트 발생을 알려준다.
- Context 초기화 시점에서 모든 싱글 톤 Bean을 미리 로드한 후 어플리케이션 기동

#### Spring Bean
- 쉽게말해 자바 객체
- 스프링 컨테이너가 만들어지고, 관리된 것
- 다양한 Annotation으로 스프링 컨테이너에 Bean을 등록한다.
- 사용자가 직접 컨트롤 하지 않고, 컨테이너가 Bean을 주입해준다.

#### POJO
- 우리가 매일 사용하는 자바 객체 (getter, setter가 있는 그것)
- 스프링을 모두 제거해도 POJO는 작동을 해야하는 것이 스프링의 철학

#### Spring Bean Life Cycle

1.Bean 인스턴스화 및 DI  
- XML파일 / 자바 Config / Annotation에서 bean 정의를 스캔한다.
- Bean 인스턴스 생성
- Bean Property에 의존성 주입

2.스프링인지 여부 검사  
- Bean이 BeanNameAware 인터페이스 구현 시 setBeanName() 호출
- Bean이 BeanClassLoaderAware 인터페이스 구현 시 setBeanClassLoader() 호출
- Bean이 Application ContextAware 인터페이스 구현 시 setApplicationContext() 호출

3.Bean 생성 생명주기
- @PostConstructAnnotation적용 메소드 호출
- Bean이 InitializingBean 인터페이스 구현 시 afterPropertiesSet()호출
- Bean이 init-method 정의하면 지정한 메소드 호출

4.Bean 소멸 생명주기 Callback
- @PreDestroyAnnotation적용 메소드 호출
- Bean이 DispoableBean 인터페이스 구현 시 destroy()호출
- Bean이 destroy-method 정의하면 지정한 메소드 호출