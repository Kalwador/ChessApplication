//package com.chess.spring.services;
//
//import com.chess.spring.exceptions.YouJustScrewUpException;
//import com.chess.spring.entities.account.Account;
//import com.chess.spring.entities.Room;
//import com.chess.spring.exceptions.account.PlayerNotExistException;
//import com.chess.spring.dto.RoomDTO;
//import com.chess.spring.exceptions.room.RoomNotExistException;
//import com.chess.spring.models.status.RoomStatus;
//import com.chess.spring.repositories.PlayerRepository;
//import com.chess.spring.repositories.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class RoomService {
//
//    private PlayerRepository playerRepository;
//    private RoomRepository roomRepository;
//    private GameService gameService;
//
//    @Autowired
//    public RoomService(PlayerRepository playerRepository, RoomRepository roomRepository) {
//        this.playerRepository = playerRepository;
//        this.roomRepository = roomRepository;
//    }
//
//    /**
//     * Add account to room.
//     * Look for a room with status "queue" and adds account to it, if there is one,
//     * If not looks for room with status "free", if there isn't any it create new room.
//     *
//     * @param playerId
//     * @return
//     */
//    public RoomDTO joinRoom(Long playerId) {
//        //TODO get playerID from security, not from controller
//        Account account = playerRepository.findById(playerId).orElseThrow(PlayerNotExistException::new);
//
//        Optional<Room> optionalRoom = Optional.ofNullable(account.getRoom());
//        if (optionalRoom.isPresent()) {
//            return RoomDTO.convert(optionalRoom.get());
//        } else {
//            Room room = findRoom();
//
//            room.getPlayers().add(account);
//            account.setRoom(room);
//            playerRepository.save(account);
//            room = roomRepository.save(room);
//
//            tryStartGame(room);
//
//            return RoomDTO.convert(room);
//        }
//    }
//
//    private Room findRoom() {
//        Room room;
//        List<Room> queueRoomList = roomRepository.findByStatus(RoomStatus.QUEUE.ordinal());
//        if (!queueRoomList.isEmpty()) {
//            room = queueRoomList.get(0);
//        } else {
//            List<Room> freeRoomList = roomRepository.findByStatus(RoomStatus.FREE.ordinal());
//            if (!freeRoomList.isEmpty()) {
//                room = freeRoomList.get(0);
//            } else {
//                room = createNewRoom();
//            }
//            room.setStatus(RoomStatus.QUEUE.ordinal());
//        }
//        return room;
//    }
//
//    private void tryStartGame(Room room) {
//        int roomSize = room.getPlayers().size();
//        if (roomSize > 2) {
//            throw new YouJustScrewUpException("Number of players in room is over 2, u know what that mean!!!!");
//        }
//        if (roomSize == 2) {
//            room.setStatus(RoomStatus.RUNNING.ordinal());
//            gameService.startNewGame(room);
//        }
//    }
//
//    public RoomDTO checkRoomStatus(Long roomId) {
//        Optional<Room> optionalRoom = roomRepository.findById(roomId);
//        if (optionalRoom.isPresent()) {
//            return RoomDTO.convert(optionalRoom.get());
//        } else {
//            throw new RoomNotExistException();
//        }
//    }
//
//    private Room createNewRoom() {
//        Room newRoom = new Room();
//        newRoom.setStatus(RoomStatus.FREE.ordinal());
//        newRoom.setPlayers(null);
//        return roomRepository.save(newRoom);
//    }
//}
