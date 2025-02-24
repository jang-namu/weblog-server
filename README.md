# 🔖 Scrabler Server

Scrabler, 웹 브라우저에서 메모를 작성하고 지식을 체계화할 수 있는 지식 공유 플랫폼의 백엔드 서버입니다.

## 📚 프로젝트 소개

- **개발 기간**: 2024.01 ~ 2024.05
- **배포 URL**: https://scrabler.com (폐쇄)

## 🛠 시스템 아키텍처
![image](https://github.com/user-attachments/assets/2d9e3519-4f10-4bd0-87f8-43747e9ec6f0)

## 🔧 기술 스택

### Backend
- **Framework**: Spring Boot
- **Security**: Spring Security + JWT
- **Database**: MySQL(RDS)
- **Cache**: Redis(ElasticCache)
- **Documentation**: Swagger

### DevOps
- **Cloud**: AWS (EC2, RDS, S3, ElasticCache, ...)
- **CI/CD**: Jenkins -> Github Actions
- **Container**: Docker, Jib

## 🚀 주요 기능

### 1. 인증/인가
- JWT 기반 토큰 인증
- OAuth 2.0 소셜 로그인 (Google)
- Spring Security 기반 리소스 접근 제어

### 2. 실시간 알림
- Redis pub/sub 구조를 활용한 실시간 알림
- SSE(Server-Sent Events)를 통한 클라이언트 전송
- 댓글, 좋아요 등 사용자 상호작용 알림

### 3. 파일 관리
- S3를 활용한 프로필 이미지 및 첨부파일 관리
- CloudFront CDN을 통한 정적 리소스 제공

### 4. API 기능
- 포스트 CRUD
- 댓글 및 답글
- 사용자 프로필 관리
- 팔로우/팔로잉
- 지식 나무(Knowledge Tree) 관리

