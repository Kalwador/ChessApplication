package com.chess.spring.dto.game;

import com.chess.spring.dto.MoveDTOPvE;
import com.chess.spring.models.game.GamePvPStatus;
import com.chess.spring.models.game.SocketResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketResponseDTO {
    private SocketResponseType type;
    private GamePvPStatus status;
    private MoveDTOPvE moveDTOPvE;
    private String chatMessage;
}
