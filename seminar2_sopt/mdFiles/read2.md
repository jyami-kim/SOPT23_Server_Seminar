# 2. Lombok

## Lombok
- IDEA와 Build도구에 자동으로 연결되는 Java 라이브러리
- Model을 만들고 각 멤버 변수와 관련된 **Method를 자동으로 만들어 준다**.
- 다양한 Annotation을 사용해 코드가 간결해진다.
- Annotation Processor를 이용해 Compile시점에 코드를 생성해준다.
- 라이브러리를 도입하기 전엔 항상 모든 팀원 간의 합의가 있어야한다.

## @Data
- 모든 멤버 변수의 setter 및 getter, ToString, equals, hashCode등 모든 메소드 자동 생성
- 만능 Annotation이지만 사용하지 않는 메소드까지 만들어주므로 사용시 고민할 필요가 있따.
- Builder 클래스까지는 자동으로 만들어주지 않는다.

```java
import lombok.Data;

@Data
public class TestData{  
    private int userIdx;
    private String name;
    privateString email;
}
//TestData() 
//getUserIdx():int getName():String getEmail():String 
//setUserIdx(String):void setName(String):void setEmail(String):void 
//equals(Object):boolean|Object hashCode():int|Object 
//canEqual(Object):boolean
//toString():String|Object
```
> 이 Annotation으로 생성된 메소드 확인 방법 : structure 메뉴 확인하기
> class의 구조를 볼 수 있다.

## @Setter & Getter
- @Setter : 필드에 대해 자동으로 Setter 메소드를 만들어 준다.
- @Getter : 필드에 대해 자동으로 Getter 메소드를 만들어 준다.
> @Data를 쓸경우, 필요없는 Setter, Getter도 생성되므로 안쓰고 이 annotation만 사용가능

```java
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class TestGetterSetter{  
    private int userIdx;
    private String name;
    privateString email;
}
//getUserIdx():int getName():String getEmail():String 
//setUserIdx(String):void setName(String):void setEmail(String):void 
```

## @NoArgsConstructor
- Default 생성자 자동 생성
- 같은기능의 생성자가 이미 존재할 경우 Compile Error (이미 기본 생성자 만들어 놨을 때)
```java
//@Setter @Getter @ToString @EqualsAndHashCode @NoArgsConstructor
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TestNoArgsConstructor{  
    private int userIdx;
    private String name;
    privateString email;
}

//TestNoArgsConstructor()
```

- 아무 생성자도 생성해 주지 않으면 **compile시** default 생성자 생성
- 하지만 생성자를 만들어 줄 경우 default 생성자는 생성되지 않는다.

## @AllArgsConstructor
- 모든 필드에 대한 생성자 자동 생성
> NoArgsConstructor와 AllArgsConstrucot 조합해서 사용해도 된다.
```java
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestAllArgsConstructor{  
    private int userIdx;
    private String name;
    privateString email;
}

//TestAllArgsConstructor(int, String, String)
```

## @NonNull
- 클래스가 아닌 필드에 붙는 Annotation
- **Runtime**시에 Null 체크를 통해 NullPointerException 발생
- Compile시에는 Null 체크하지 않는다.

```java
import lombok.NonNull;

public class TestNonNull {
    private int userIdx;
    @NonNull
    private String name;
    private String email;
    
    public TestNonNull(){
        this.name = null;
    }
}

//NullPointer Exception발생 runtime에 새로운 객체 생성, runtime시 null체크
// TestNonNull testNonNull = new TestNonNull(0,null,null);

//NullPointer Exception발생X compileitme에 null을 체크한다 (애초에 생성자부터!)
// TestNonNull test Nonnull2 = new TestNonNull();

```
> runtime시 null체크를 하면 nullpointerexception 예외처리가 발생한다.

## @RequiredArgsConstructor
- @NonNull과 같이 사용한다.
- @NonNull필드나 Fianl키워드가 붙은 필드의 생성자를 자동으로 생성한다.

```java
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestRequiredArgsConstructor {
    private int userIdx;
    @NonNull
    private String name;
    @NonNull
    private String email;

    public TestNonNull(){
        this.name = null;
    }
}

//TestRequiredArgsConstructor(String, String)
```

## @Builder
- 자동으로 Builder 클래스 및 메소드 생성
```java
import lombok.Builder;

@Builder
public class Testbuilder {
    private int userIdx;
    private String name;
    private String email;
}

//TestBuilderBuilder
/*
TestBuilderBuilder()
userIdx(int):TestBuilderBuilder
name(String):TestBuilderBuilder
email(String):TestBuilderBuilder
build():TestBuilder
toString():String|Object
*/
//TestBuilder(int, String, String)
//builder():TestBuilderBuilder
```

## @Value

- immutable class로 만드는 Annotation
- 모든 필드 값 private final
- Getter 메소드는 생성하지만 Setter 메소드는 생성하지 않는다.
- 해당 클래스는 더 이상 상속이 불가능하다. (Fianl class)
> import org.springframework.breans.factory.annotation.Value와 다르다!!

```java
import lombok.Value;

@Value
public class TestValue {
    private int userIdx;
    private String name;
    private String email;
}

//TestValue(int, String, String)
//getUserIdx():int getName():String getEmail():String
//equals(Object):boolean|Object
//hashCode():int|Object
//toString(): String|Object
```

> 모든 필드 값 fianl / getter 메소드만 자동 생성, AllArgsConstructor 자동 생성

## @Wither
- 해당 필드를 값을 변경한 새로운 객체를 만들어 반환한다.
```java
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.Wither;

@Value
public class TestValue {
    @Wither(AccessLevel.PROTECTED)
    private int userIdx;
    private String name;
    private String email;
}
```

```java
public class Main{
    public static void main(String... args){
        TestValue testValue = new TestValue(0, "김민정", "mor2222@naver.com");
        TestValue testValue1 = testValue.withUserIdx(1);
    }
    
}
```
> withUserIdx(int):TestValue  = 새로운 객체를 만들어 반환한다.

## @NonFinal
- 필드의 final을 선언하지 않을 때 사용
- 해당 annotation이 위치한 필드만 final이 해제된다.

```java
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.Wither;

@Value
public class TestValue {
    @Wither(AccessLevel.PROTECTED)
    private int userIdx;
    private String name;
    @NonFinal
    private String email;
}
```

## @Slf4j
- 콘솔창에 로그 출력
- 로그 출력으로 System.out.println을 사용하지 말자!!
```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserController{
    public void method(){
        Log.info("get All Users");
    }
}
```

- TRACE -> DEBUG -> INFO -> WARN -> ERROR
- 로그 Level
   + 1.TRACE : DEBUG보다 좀 더 상세한 정보를 나타낸다. 로그 파일에만 기록
   + 2.DEBUG : 시스템 흐름에 관한 자세한 정보, 로그 파일에만 기록된다.
   + 3.INFO : 콘솔에 출력
   + 4.WARN : API사용 미숙, 오류에 가까운 것, 콘솔에 권장 표시
   + 5.ERROR : 기타 RunTime Error, 예기치 않은 상황, 콘솔에 표시


