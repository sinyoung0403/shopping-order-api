## ⚒ 개발자 소개

| 역할 | 팀장 |
|:-------------:|:-------------:|
|프로필|![image](https://avatars.githubusercontent.com/u/94594402?v=4&size=64)|
|이름|박신영|
|GitHub|sinyoung0403|
|기술블로그|[신영tistory](https://sintory-04.tistory.com/)|

<br>
<br>

## ⚒ 프로젝트

### ✔️ 프로젝트 이름

- **" Shopping Order Api "**

### ✔️ 프로젝트 소개

- 쇼핑 주문 관리 시스템으로, 사용자 인증, 상품 관리, 장바구니, 주문 처리 기능을 제공합니다.

<br>
<br>

## 🌟 주요 기능

- **사용자 관리**: 사용자 등록, 인증, 권한 관리
- **상품 관리**: 상품 CRUD 작업
- **장바구니**: 상품 추가/제거, 장바구니 내용 관리
- **주문 처리**: 주문 생성 및 주문 항목 관리
- **보안**: Spring Security를 통한 JWT 기반 인증

<br>
<br>

## 🛠️ 기술 스택

- **Language & Framework**: <img src="https://img.shields.io/badge/java17-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot 3.5.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">

- **Security**: <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">

- **Database**: <img src="https://img.shields.io/badge/Spring Data  Jpa-2E7D32?style=for-the-badge&logo=Databricks&logoColor=white">  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

- **Caching**: <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">

- **API Docs**: <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black"> <img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">

- **Build & Tools**: <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/Lombok-E9573F?style=for-the-badge">

- **IDE**: <img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white">

- **Version Control**: <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">


<br>
<br>

## 📁 프로젝트 구조

``````
src/main/java/com/shoppingorderapi/
├── application/          # 애플리케이션 계층 (유스케이스, 서비스)
├── domain/              # 도메인 계층 (엔티티, 리포지토리)
│   ├── cart/           # 장바구니 도메인
│   ├── cartItem/       # 장바구니 항목 도메인
│   ├── order/          # 주문 도메인
│   ├── orderItem/      # 주문 항목 도메인
│   ├── product/        # 상품 도메인
│   └── user/           # 사용자 도메인
├── infra/              # 인프라 계층 (외부 서비스)
├── presentation/       # 프레젠테이션 계층 (컨트롤러, DTO)
│   ├── dto/          # DTO 모음
│   ├── auth/          # 인증 컨트롤러
│   ├── cart/          # 장바구니 컨트롤러
│   ├── order/         # 주문 컨트롤러
│   ├── product/       # 상품 컨트롤러
│   ├── config/        # 설정 클래스
│   └── security/      # 보안 설정
└── common/            # 공유 유틸리티 및 설정
``````

<br>
<br>


## 🏷️ ERD

![alt text](image.png)

## API 문서 링크
