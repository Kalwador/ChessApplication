package com.chess.spring.dto.game;

import com.chess.spring.dto.MoveDTO;
import com.chess.spring.models.game.SocketMessageType;
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
