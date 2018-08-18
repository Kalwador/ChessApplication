package com.chess.spring.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveDTO {
    private Long userID;
    private Integer moveNumber;
}
