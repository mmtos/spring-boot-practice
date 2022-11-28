# Kafka 연습

## 프로젝트 컴포넌트 설계
### consumer
- 메시지를 소비하는 컴포넌트

### producer
- 메시지를 생성하는 컴포넌트
- 메시지 생성 속도 : 1000ms
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

## 참고링크
- https://www.baeldung.com/spring-kafka
- https://github.com/bitnami/containers/blob/main/bitnami/kafka/README.md
- http://jmlim.github.io/spring/2018/11/27/spring-boot-schedule/
- https://erjuer.tistory.com/89
