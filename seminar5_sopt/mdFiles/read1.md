# 1. AWS S3
## AWS S3
- AWS S3 : Amazon Simple Storage Service
- EC2 t2.micro의 용량은 8GB이다. 이곳에 서버 애플리케이션과 업로드 된 파일들을 저장하기엔 턱없이 부하다.
- Amazon에서는 객체 스토리지 서비스를 별도로 제공한다.
- 데이터를 bucket이라는 리소스 내에 저장한다.
- Amazon 정리
    + EC2 : 서버 애플리 케이션 [링크](https://github.com/mjung1798/seminar3_sopt/blob/master/mdFiles/read4.md)
    + RDS : DB [링크](https://github.com/mjung1798/seminar4_sopt/blob/master/mdFiles/read4.md)
    + S3 : 멀티미디어 대용량데이터 저장소 

#### 파일업로드 시나리오
- DB에는 진짜 파일이 아닌 파일이 저장된 S3의 주소/경로/URL을 저장한다 (String/TEXT/VARCHAR2)
- File Upload Service & S3 Service를 이용해 파일을 S3에 저장하고, 파일이 저장된 경로를 DB에 저장한다.
- DB에는 파일이 저장된 경로를 저장하고, 이를 클라이언트에게 반환한다.
- Node의 S3 모듈은 반환값으로 자동으로 파일이 저장된 URL을 반환 값으로 줬지만, Spring은 직접 URL을 만들어야한다 (버킷주소 + 파일 이름 형식)

#### AWS S3 주의사항
- 항상 지역이 서울인지 확인
- EC2, RDS와는 다르게 bucket의 수가 1개가 아니어도 상관없다.
- Free-tier는 bucket에 상관 없이 총 5GB, 한달에 2000번의 업로드, 20000번의 다운로드 기능을 제공한다.

## AWS S3 Bucket 생성
- S3 > 버킷만들기
    + 이름 및 리전 = 버킷이름 : 고유값 / 서울
    + 옵션 구성, 권한설정, 검토 = 다음
- 버킷 선택 > 업로드 > 파일추가 > 업로드 > 링크로 접근
    + bucket에 정책 설정을 안해되서 public read가 불가능 하다.
    
## AWS S3 정책
- 버킷선택 > 권한 > 버킷정책 > 정책 생성기
    + Select Policy Type = S3 bucket policy
    + Add Statements = Effect: arrow / principal : * / AWS Service : Amazon S3 / Action : GetObject / ARN : arn:aws:s3:::[버킷이름]/*
    + generate policy 내용 복사
- 버킷 정책 저장시 이 버킷에 퍼블릭 액세스 권한이 있음 뜨면 성공 (아닐 시, 퍼블릭 액세스 설정 편집)
- 링크로 이미지 조회 가능

## AWS IAM 생성
- IAM : Identity and Access Management
- AWS 리소스에 대한 액세스를 안전하게 제어할 수 있는 웹 서비스
- IAM을 사용하여 리소스를 사용하도록 인증(로그인) 및 권한 부여(권한 있음)된 대상을 제어합니다.
- 여기서는 AWS S3만 접근이 가능한 IAM계정을 생성합니다.(2개의 키 발급)
- 이 키들을 절대 유출되서는 안됩니다.

#### IAM 설정
- IAM > 사용자
    + 사용자 이름 설정 / AWS 엑세스 유형선택 : 프로그래밍 방식 액세스
    + 권한 설정 > 기존 정책 직접 연결 > AmazonS3FullAccess연결 
> 액세스 키 ID, 비밀 액세스 키는 절대 잊어버리면 안된다. git에 올리면 안된다. 지금 페이지에서만 볼 수 있고 다음엔 볼 수 없다. 잊어버리면 IAM 다시만든다. 그러면 해당 key를 사용하는 서버 애플리케이션 역시 key를 바꿔야한다.