package mmtos.practice.springboot.produce;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountMessageProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    private long count = 0;

    @Scheduled(fixedDelay = 1000)
    public void counting(){
        kafkaTemplate.send(KafkaConfig.MY_TOPIC,String.valueOf(count++));
    }
}
