# Kafka 연습

## 프로젝트 컴포넌트 설계
### consumer
- 메시지를 소비하는 컴포넌트

### producer
- 메시지를 생성하는 컴포넌트
- 메시지 생성 속도 : 1000ms
---

## Spring 설정
### 공통
- 브로커 리스너를 찾기위해 부트스트랩 서버 ip를 지정해 준다.

### consumer
- consumerFactory 빈을 정의하여 kafka consumer를 생성할 준비를 한다.(그룹 ID 지정 필수)
- KafkaListenerContainerFactory 빈을 정의해서 실제로 메시지를 소비할 클래스에서 사용할 수 있다.

### producer
- producerFactory 빈과 kafkaTemplate 빈을 정의한다.
- kafkaTemplate의 의존성 주입 받으면 간단한 API를 사용해서 kafka에 메시지를 생성할 수 있다.

---
## kafka 설치(Docker-compose)
1. docker-compose.yml 다운로드 
```
curl -sSL https://raw.githubusercontent.com/bitnami/containers/main/bitnami/kafka/docker-compose.yml > docker-compose.yml
```

2. 클라이언트에게 노출할 리스너 IP 설정(docker-compose.yml 일부 수정)
```
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://192.168.219.1:9092

```

3. 실행
```
docker-compose up -d
```

### 토픽 생성 및 메시지 생성 / 소비 테스트
```

docker-compose exec kafka /opt/bitnami/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --topic testtopic --partitions 1 --replication-factor 1
docker-compose exec kafka /opt/bitnami/kafka/bin/kafka-console-producer.sh --topic testtopic --broker-list localhost:9092 
> a
> b
> c
docker-compose exec kafka /opt/bitnami/kafka/bin/kafka-console-consumer.sh --topic testtopic --bootstrap-server localhost:9092 --from-beginning
```

---
## kafka 관련 메모
### consumer 그룹을 지정하는 이유
  - 토픽의 파티션이 10개로 나누어져 있고, consumer group에 속한 consumer가 2개인 경우, 각각 5개 파티션씩 구독한다.
  - https://www.stackchief.com/questions/What%20is%20Kafka%20Consumer%20Group%20ID%3F

---

## 참고링크
- https://www.baeldung.com/spring-kafka
- https://github.com/bitnami/containers/blob/main/bitnami/kafka/README.md
- http://jmlim.github.io/spring/2018/11/27/spring-boot-schedule/
- https://erjuer.tistory.com/89
