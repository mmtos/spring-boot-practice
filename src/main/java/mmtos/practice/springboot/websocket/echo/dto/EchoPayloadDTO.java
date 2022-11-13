package mmtos.practice.springboot.websocket.echo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EchoPayloadDTO {
    //Payload
    private String message;
}
