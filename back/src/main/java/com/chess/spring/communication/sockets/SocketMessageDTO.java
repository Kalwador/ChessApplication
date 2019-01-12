package com.chess.spring.communication.sockets;

import com.chess.spring.game.MoveDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketMessageDTO {
    private SocketMessageType type;
    private MoveDTO moveDTO;
    private String chatMessage;
    private String sender;
    private String date;
}
