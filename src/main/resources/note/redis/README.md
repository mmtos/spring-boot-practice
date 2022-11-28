# Redis

## 설치 
1. docker-compose.yml 다운로드
```
curl -sSL https://raw.githubusercontent.com/bitnami/containers/main/bitnami/redis/docker-compose.yml > docker-compose.yml
```

2. 실행
```
docker-compose up -d
```

## Spring 설정
1. spring-redis-data 의존성 추가
   - Spring Boot 2.0 부터 Jedis 가 기본 클라이언트에서 deprecated 되고 Lettuce를 기본으로 사용함
2. connectionFactory 빈 설정

## 사용방법 
1. RedisHash를 이용해서 객체단위로 CRUD
  - 객체 정의(serialize 구현)
  - repository 빈 정의
1. redis template을 이용해서 직접 접근 및 사용
  - Redis Template 빈 설정 필요함

## 참고링크
- https://hub.docker.com/r/bitnami/redis
- https://sabarada.tistory.com/105
- https://sabarada.tistory.com/106
- https://bcp0109.tistory.com/328
