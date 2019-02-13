# 4. Spring AOP
## AOP
- Spring AOP (Aspect Oriented Programming, 관점 지향 프로그래밍)
- 애플리케이션 전체에 걸쳐 사용되는 기능들을 재사용하도록 지원하는 것이다.
- 가로(횡단) 영역의 공통된 부분을 잘라냈다고 해서 크로스 커팅(Cross-Cutting)이라고도 불린다.
- 로깅, 트랜잭션, 보안 등 사용
- 로직 주입
- 프록시 패턴과 유사하다.

> 클라이언트 - AOP - 서버  
> 클라이언트가 서버에서 param을 보내기 전 AOP에서 어떤 내용을 처리하고, 서버에 param을 보낸다.  
> 이 때 이 param이 ProceedingJoinPoint (pjp) 객체로 AOP에서는 처리된다.  
> 즉 AOP는 parameter로 pjp를 받고, return도 pjp를 해서 클라이언트가 보낸 내용을 서버에 그대로 전달을 해준다.  
> 그리고 param을 받고 return하는 그 사이에 controller에서 해줄 method 전에 실행할 method를 여기서 처리한다.  
> service와 비슷한 느낌!!  
> 하지만 service는 의존성 처리를 해주어야 하지만, AOP는 그것도 필요없다!!

- @Auth : Auth Annotation이 정의한 메소드 실행 (@Around로 Auth annotation 메소드를 정의 할 수 있다.)
- 이상이 없다면(정상적으로 AOP가 끝났다면) 진짜 메소드 실행

#### 추가 총 정리 : Spring에서 중요한 3가지
- IoC : Service로 Spring IoC에서 생명주기 관리 (Mapper도 마찬가지)
- DI : IoC에 있는 것, 즉 Service들을 의존성 주입 등의 방법을 이용해서 실제로 사용할 수 있게 한다. (Mapper도 마찬가지)
- AOP : 클라이언트가 서버에게 정보를 주기 전, 실행할 메소드에 대한 정의
