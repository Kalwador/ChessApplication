package com.chess.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private List<String> conversation;

    public ChatDTO(Long id) {
        this.id = id;
    }

    public static ChatDTO map(Long gameId, List<String> conversation){
        return ChatDTO.builder()
                .id(gameId)
                .conversation(conversation)
                .build();
    }
}
