//package com.chess.spring.dto;
//
//import com.chess.spring.entities.Room;
//import com.chess.spring.models.status.GameStatus;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.validation.constraints.NotNull;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class RoomDTO {
//    @NotNull
//    private Long id;
//
//    private Integer numberOfPlayers;
//
//    @NotNull
//    private GameStatus status;
//
//    public static RoomDTO convert(Room room) {
//        RoomDTO roomDTO = new RoomDTO();
//        roomDTO.setId(room.getId());
//        roomDTO.setNumberOfPlayers(room.getPlayers().size());
//        roomDTO.setStatus(GameStatus.convert(room.getStatus()));
//        return roomDTO;
//    }
//}
