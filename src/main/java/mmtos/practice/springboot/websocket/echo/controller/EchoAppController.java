package mmtos.practice.springboot.websocket.echo.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmtos.practice.springboot.websocket.echo.dto.EchoPayloadDTO;
import mmtos.practice.springboot.websocket.echo.repository.EchoMemoryRepository;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * STOMP기반 EchoApp에서 메시지를 전달을 위한 핸들러
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class EchoAppController {

    private final SimpMessagingTemplate template;
    private final EchoMemoryRepository repository;

    @MessageMapping("/{season}/enter")
    @SendTo("/topic/season/{season}")
    public EchoPayloadDTO doSubscribe(
             Message<EchoPayloadDTO> message
            , @Header("simpSessionId") String simpSessionId
            , @DestinationVariable("season") String season
            , @Headers Map headerMap

    ){
        EchoPayloadDTO payload = message.getPayload();
        log.info(headerMap.toString());
        String fullMessage = season + "(by new subscriber," + simpSessionId + ") : " + payload.getMessage();
        repository.push(season,new EchoPayloadDTO(fullMessage));
        return new EchoPayloadDTO(fullMessage);
    }

    @MessageMapping("/{season}/sendMessage")
    @SendTo("/topic/season/{season}")
    public EchoPayloadDTO submitEchoMessage(Message<EchoPayloadDTO> message
            , @Header("simpSessionId") String simpSessionId
            , @DestinationVariable("season") String season
    ){
        // 이미 subscribe를 완료한 client에서 전달하는 추가 메세지
        EchoPayloadDTO payload = message.getPayload();
        String fullMessage = season + "(" + simpSessionId + ") : " + payload.getMessage();
        repository.push(season,new EchoPayloadDTO(fullMessage));
        return new EchoPayloadDTO(fullMessage);
    }
}
