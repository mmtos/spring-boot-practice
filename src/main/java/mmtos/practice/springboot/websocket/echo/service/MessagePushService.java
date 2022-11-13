package mmtos.practice.springboot.websocket.echo.service;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmtos.practice.springboot.websocket.echo.dto.EchoPayloadDTO;
import mmtos.practice.springboot.websocket.echo.repository.EchoMemoryRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
@AllArgsConstructor
@Slf4j
public class MessagePushService {

    private final EchoMemoryRepository repository;
    private final SimpMessagingTemplate template;

    @Scheduled(fixedRate = 5000)
    public void sendMessage(){
        List<EchoPayloadDTO> springMessages =  repository.getAllMessage("spring");
        List<EchoPayloadDTO> summerMessages =  repository.getAllMessage("summer");
        List<EchoPayloadDTO> autumnMessages =  repository.getAllMessage("autumn");
        List<EchoPayloadDTO> winterMessages =  repository.getAllMessage("winter");
        log.info("MessagePushService.sendMessage 실행됨.");

        template.convertAndSend("/topic/season/spring",springMessages);
        template.convertAndSend("/topic/season/summer",summerMessages);
        template.convertAndSend("/topic/season/autumn",autumnMessages);
        template.convertAndSend("/topic/season/winter",winterMessages);
    }
}
