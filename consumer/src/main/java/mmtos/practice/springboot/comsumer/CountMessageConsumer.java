package mmtos.practice.springboot.comsumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CountMessageConsumer {

    @KafkaListener(topics = KafkaConfig.MY_TOPIC, groupId = KafkaConfig.MY_TOPIC_CONSUMER_GROUP)
    public void consumer(ConsumerRecord<?, ?> consumerRecord) {

        log.info("consumer received value: {}", consumerRecord.value().toString());
    }
}
