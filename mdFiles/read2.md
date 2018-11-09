# 2. Spring Presentation Layer

## @RestController
    @RestController
contoller에는 항상 @RestController annotation을 붙여줘야한다. (class 위에 annotation 부여)

- REST를 위한 전용 controller 기능을 부여하는 Annotation
- @RestContoller = @constoller + @ResponseBody
- 반환 값을 JSON으로 변환해 준다.

> RestController 보는법 : ctrl+@RestController click 
RestController interface를 확인 할 수 있다. 

##### RestController 내부
- @Controller: 템플릿을 이용해서 HTML 페이지를 렌더링하고 표시해준다.
- @ResponseBody : 반환값을 JSON으로 변환해 준다 > API 통신 방식이 JSON이기 때문

## @GetMapping

    @GetMapping("uri")
RestController 클래스 안의 각각의 메소드를 URL과 매핑한다. (액션)

- Get 메소드 전용 Controller Annotation
- 리소스를 조회하는 요청에 사용한다.

> 요청 타입을 Get으로 설정하고, GetMapping("")의 ()안에 있는 요청을 클라이언트에 보낸다.  
이때 해당하는 uri를  ""안에 넣는다. (URL Mapping)

##### URL Mapping

    @GetMapping("")             >127.0.0.1:8080(domain)
    @GetMapping("1")            >127.0.0.1:8080(domain)/1
    @GetMapping("/get1")        >127.0.0.1:8080(domain)/get1
    @GetMapping("/get1/name")   >127.0.0.1:8080(domain)/get1/name

/은 처음에는 생략이 가능하다, 그러나 이후의 /는 생략이 불가능하다.

## @RequestMapping
    @RequestMapping(method = RequestMethod.GET, value ="")
Get method이면서 URL Mapping은 ""라는 의미의 Annotation이다.

RequestMapping의 method의 default 값은 GET!!  

    @RequestMapping("post");

이경우 method = RequestMethod.Get, value = "post" 인 것이다!  
따라서, UrlMapping을 /post로 접근 가능

>request의 내용을 하나하나 설정 할 수 있다.  
method = REST ful method 설정  
value = mapping할 uri 설정  

    @RequestMapping(method = RequestMethod.GET, value ="")

    @GetMapping("")
    
두개는 같은 의미이다 > 같은 의미의 Controller Method가 두개 이상일 경우 서버 실해 에러

## @PathVariable
    @GetMapping("/name/{name}")
    public String getName(@PathVariable(value = "name") final String name){
        return name;
    }
    
- URL에서 각 구분자에 들어가는 값을 처리할 때 사용한다.
- /뒤에 {}을 name이라는 parameter로 받아라
- /{name}의 값을 @PathVariable(value="name")으로 받고 이것을 다시 String name으로 변환

required값
    value = "name", require = true  
이런식으로 설정할 수 있다.  
require = true (default) : 무조껀 {name}자리에 path var을 설정해야 method가 mapping된다.
require = false : {name}자리에 path var을 설정하지 않아도 method가 mapping된다.

>URL mapping에 {}를 넣으면 {}안에 있는 값을 parameter로 받을 수 있다.  
@PathVariable(value = "name")은 {name}의 값을 가져온다는의미이며,  
이를 final Strinrg name으로 저장한다.  
따라서 getName()메소드의 인자로 URL의 name 자리에 잇는 문자열이 들어가고, 그 문자열이 parameter로 들어가 return되는 메소드이다.    

## @RequestParam
    @GetMapping("/part")
    public String getPart(@RequestParam(value = "part", defaultValue = "") final String part){
        return part;
    }
    
    127.0.0.1:8080/part?part=서버
    web > 서버
- QueryString을 처리할 때 사용
- value : queryString의 key값 (queryString의 value값은 param으로 받아온다.)
- defaultValue = queryString의 값이 없을 경우의 기본 값

##### 여러개의 QueryString
    @GetMapping("/info")
    public String getPart2(
            @RequestParam(value = "name")final String name,
            @RequestParam(value = "type", defaultValue = "yb") final String type){
        return name + "이고" + type + "입니다.";
    }
    
    127.0.0.1:8080/info?name="김민정"&type="YB"
    web > 김민정이고 YB 입니다.

    127.0.0.1:8080/info?name="김민정"
    web > 김민정이고 yb 입니다.

- 여러 개의 QueryString을 받을 수 있다.
- 요청 시 QueryString key 값 사이를 &로 이어주면 된다.
    
##### QueryString 배열
    @GetMapping
    public int number(@RequestParam(value = "num") final int[] num){
        int sum = 0;
        for(int i:num){
            sum += i;
        }
        return sum;
    }
    
    127.0.0.1:8080/num?num=1&num=2&num=3&num=4
    web > 10


- QueryString의 key값을 같게 하면 배열로 받을 수 있다.

## @PostMapping

    @RestController
    @RequestMapping("post")
    public class PostController{
        @PostMapping("")
        public String postUser(@RequestBody final User user){
            return user.getName();
        }
    }

    URL : POST | 127.0.0.1:8080/post
    Body : {
            name : "민정".
            part : "서버"
            }
    
    Web > 민정

- Post 메소드 전용 Controller Annotation
- 리소스를 생성하는 요청에 사용한다(서버에 정보 저장: Create)

##### RequestBody
- parameter로 객체를 받는 Annotation
- HTTP Message body를 자바 객체로 변환 (Mapping) > setMember와 같은 역할인듯!
- Spring MVC 내의 HttpMessageConverter가 변환을 처리해 준다.
- 전송한 객체와 전송 받을 Controller의 객체 타입이 어느정도 같아야 한다.
- 같지 않으면 값이 자동으로 채워지지 않는다.
- basic constructor가 저장하고자 하는 객체의 class에 선언이 되어있어야 한다.

> @RequestBody는 body에 있는 json값에 맞는 member에 값을 set하는 것과 같다.
기본 생성자만 있어도 생성가능 
(name, type)을 parameter로 갖는 생성자가 없어도 member를 setting 해준다.
setName(name), setType(type)과 같은 private 멤버의 값을 세팅하는 메소드가 없어도 객체의 멤버에 저장한다.  
필드에 설정이 되있지 않은 멤버라도 json으로 설정할 수는 있다 > 하지만 getmethod가 없어서 받아올 방법이 없을듯!  

## @PutMapping

    @RestController
    @RequestMapping("put")
    public class PutController{
        @PutMapping("")
        public String PutUser(@RequestBody final User user){
            return user.getPart();
        }
    }
    
- put 메소드 전용 Controller Annotation
- 리소스를 수정하는 요청에 사용 (update)

> PostMapping에서와 마찬가지로 RequestBody의 내용대로 수정을 한다.

## @DeleteMapping

    @RestController
    @RequestMapping("delete")
    public class DeleteController{
        @DeleteMapping("")
        public String deleteUser(@RequestBody final User user){
            return "delete sucees";
        }
    }
    
- delete 메소드 전용 Controller Annotation
- 리소스를 삭제하는 요청에 사용 (delete)