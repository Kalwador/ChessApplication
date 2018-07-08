//package com.chess.spring.controllers;
//
//import com.chess.spring.services.RoomService;
//import com.chess.spring.dto.RoomDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController(value = "/room")
//public class RoomController {
//
//    private RoomService roomService;
//
//    @Autowired
//    public RoomController(RoomService roomService) {
//        this.roomService = roomService;
//    }
//
//    @GetMapping(value = "/room/join/{id}")
//    public RoomDTO joinRoom(@PathVariable Long id){
//        return roomService.joinRoom(id);
//    }
//
//    @GetMapping(value = "/room/checkStatus/{id}")
//    public RoomDTO checkRoomStatus(@PathVariable Long id){
//        return this.roomService.checkRoomStatus(id);
//    }
//}
