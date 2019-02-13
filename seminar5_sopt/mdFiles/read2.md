# 2. File Upload
## File Upload

#### 코드
- application.properties 파일 수정
```
# AWS Key
cloud.aws.credentials.accessKey="ACCESS KEY" //액세스 키ID
cloud.aws.credentials.secretKey="SECRET KEY" //비밀 액세스 키
cloud.aws.stack.auto=false

# AWS S3 Service bucket
cloud.aws.s3.bucket="버킷 이름" //버킷 이름
cloud.aws.region.static=ap-northeast-2 //버킷 지역(서울은 ap-northeast-2)

# AWS S3 Bucket URL
cluod.aws.s3.bucket.url=https://s3.ap-northeast-2.amazonaws.com/버킷이름/
```

- pom.xml 파일 추가
```
<!--AWS-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-aws</artifactId>
            <version>2.0.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-aws-context</artifactId>
            <version>1.2.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-aws-autoconfigure</artifactId>
            <version>1.2.1.RELEASE</version>
        </dependency>
```
> <dependencies>사이에 각종 maven dependency를 붙여넣으면 자동으로 maven 의존성이 추가된다 (단, enable auto import를 선택했을 경우)

- S3Service.java : s3에 업로드한다. 
    + uploadOnS3(filename, file) : file을 filename으로 s3에 업로드한다.
    + TransferManager : AWS S3 전송 객체 (= 나의 IAM)
    + PutObjectRequest : 요청 객체 (= 설정 bucekt)
    + transferManager.upload(request); 
     >나의 IAM(application.properties에서 설정)이 설정한 s3 bucekt에 파일을 업로드한다.
[S3Service.java](../src/main/java/org/sopt/seminar5/service/S3Service.java)

- FileUploadService : file객체를 생성하고, filename을 임의로 정해준다.
    + 확장자 > 파일이름 암호화 (Uuid()메소드이용) > 파일객체 생성 > 파일 변환 > S3파일업로드 (S3 Service이용) > 주소 할당 > 파일 삭제
[FileUploadService.java](../src/main/java/org/sopt/seminar5/service/FileUploadService.java)

#### File Upload 사용해보기
- 클라이언트에게 Multipart-form/data 형식으로 파일을 전송받는다. 이때 파일의 데이터 타입은 MultipartFile이다.
- FileUploadService/S3Service를 통해 파일을 S3에 업로드 하고, 파일이 저장된 URL을 DB에 저장한다.
- 클라이언트가 파일을 요청 시 파일이 아닌 파일이 저장된 경로를 반환한다.
- 따라서 클라이언트로부터 데이터를 받을 때는 MultipartFile 데이터 타입으로 받지만, 반환할 땐 String 타입으로 반환한다.

#### 코드
- User table 수정 : profileUrl/VARCHAR(245) column추가
    + 회원 프로필 사진을 진짜 사진이 아닌 사진이 저장된 경로로 저장 (String)
    + 기폰 프로필 사진을 구현하고 싶다면 Default/Expression에 기본 프로필 사진 경오를 저장

- Model에 SignUpReq 추가
    + 회원 가입, 회원 정보 수정 시 받을 SignUpReq 객체를 추가한다.
    + 파일을 MultipartFile로 받고 실제 S3 업로드 후 파일 경로를 profileUrl에 저장할 예정

- dto-User 객체 수정 : 회원 정보 요청 시 회원 프로필 사진이 저장된 경로를 반환하기 위해 String 타입의 profileUrl 속성 추가

#### Model VS dto
- password
    + 회원 가입 시에는 비밀번호를 같이 받는다. 하지만 회원 정보 요청시 비밀번호를 반환하면 안된다. 따라서 dto에는 password를 뺐다.
    + 비밀번호도 반환하고 싶다면 비밀번호가 있는 필드를 추가해도 되고, 별도로 비밀번호가 있는 객체를 만들어도 된다.
    + 두 객체의 선택은 Mapper의 반환 타입에서 선택
- profile
    + 회원 가입 시엔 실제 프로필 사진(파일)로 받고, S3 저장 후 경로를 profileUrl로 저장한다.
    + 회원 정보 조회시엔 파일은 없고, 사진이 저장된 경로만 반환한다.
> 실제로 사용자에게 받는 file 및 textfield에 관한 것이 model에 들어가고  
> 정보 조회시에 보여줄 내용만이 dto에 들어간다.
- Model : 클라이언트 -> 서버
- dto : 서버 -> 클라이언트
- Mapper의 반환타입을 다양하게 만들어 활용 할 수 있다.

 
## Multipart-form/data


#### 코드
- UserController: signup 수정
```java
 @PostMapping("")
    public ResponseEntity signup(SignUpReq signUpReq, @RequestPart(value = "profile", required = false) final MultipartFile profile) {..}
```
- @RequestParam, @PathVariable, @RequestBody등의 아무런 Annotation을 명시하지 않고 객체로 받으면 from-data로 받게 된다.
- Multipart중에서 profile 키 값의 파일을 MultipartFile타입의 profile객체로 받는다. (파일이 없어도 된다. required = false)

> 사진 객체는 "profile"키로 받아서 MultipartFile 타입의 profile 객체에 저장한다.  
> 나머지 데이터는 SignUpReq 타입의 signUpReq 객체로 받는다.  
> 여기서 중요한 점은 SignUpReq타입 명시 앞에 어떤 annotation도 붙지 않았다.

- Content Type
    + HTTP Request의 데이터 Body/Payload의 Type 정보를 나타낸다.
    + 대표적으로 Multipart/formdata, Application/json, Applicatipn/x-www-formurlencoded가 있다.

#### Multipart-form/data
- 파일업로드시 사용되는 Content타입이다.
- 파일객체는 ```@RequestPart(value-"key"")```나머지 데이터는 객체로 받는다.
- 전송받을 파일을 MultipartFile 타입의 속성으로 받아야한다.

#### Applicaion/x-www-form-urlencoded
- key=value&key=value형식으로 들어온다.
- Servlet Container는 Content-type이 x-www-form-urlencoded이면 request의 body를 읽어 Map형태로 변환한다.
- body를 인코딩해서 사용해야하는데, node.js의 request 라이브러리는 이 작업을 내부적으로 이미 처리하고 있다.

```java
@PostMapping("test1")
public String test1(final SignUpReq signUpReq){
    return signUpReq.toString();
}
```

#### Application/json
- @RequestBody는 Content-Type이 Application/json인 Body만 받는다.
- 프론트엔드는 json타입으로 Body 객체를 보내야한다.
- 아무 Annotation을 붙이지 않을 시엔 x-www-form-urlencoded 전송타입으로 받는다.
- 따라서 사전에 프론트엔드와 정확히 협의하고 가야한다 > 되도록 json을 사용하자.

```java
@PostMapping("test2")
public String test2(@RequestBody final SignUpReq signUpReq){
    return signUpReq.toString();
}
```

> node는 urlencoded와 json을 구분하지 않으나 스프링은 구분한다.