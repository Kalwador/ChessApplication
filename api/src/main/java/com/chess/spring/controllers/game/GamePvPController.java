//package com.chess.spring.controllers.game;
//
//import com.chess.spring.dto.game.GamePvEDTO;
//import com.chess.spring.dto.game.GamePvPDTO;
//import com.chess.spring.dto.game.SocketResponseDTO;
//import com.chess.spring.exceptions.*;
//import com.chess.spring.services.game.GamePvPServiceImpl;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.awt.*;
//
//import static java.lang.String.format;
//
//@Slf4j
//@RestController
//@RequestMapping(path = "/game/pvp")
//@Api(value = "GamePvP Controller", description = "Manage game with other player transitions and statuses")
//public class GamePvPController {
//
//    private SimpMessageSendingOperations messagingTemplate;
//    private GamePvPServiceImpl gameService;
//
//    public GamePvPController(GamePvPServiceImpl gameService,
//                             SimpMessageSendingOperations messagingTemplate) {
//        this.gameService = gameService;
//        this.messagingTemplate = messagingTemplate;
//    }
//
//
//@ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Succes get list of games")
//})
//@GetMapping
//public List<GamePvP> getAll(){
//        return this.gameService.getAll();
//        }
//
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "success get game"),
//            @ApiResponse(code = 400, message = "Game not found, wrong id")
//    })
//
//    @GetMapping(value = "/{gameId}")
//    public GamePvPDTO getById(@PathVariable Long gameId) throws InvalidDataException {
//        return GamePvPDTO.convert(gameService.getById(gameId));
//    }
//
//    /**
//     * Send message, to other users
//     */
//    @MessageMapping("/chat/{roomId}/sendMessage")
//    public void sendMessage(@DestinationVariable String roomId, @Payload SocketResponseDTO chatMessage) {
//        messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
//    }
//
//    /**
//     * User enter the chat, send info about joinning to other users
//     * here u cna count nouber of observers of game
//     */
//    @MessageMapping("/chat/{roomId}/addUser")
//    public void addUser(@DestinationVariable String roomId, @Payload SocketResponseDTO chatMessage,
//                        SimpMessageHeaderAccessor headerAccessor) {
//        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
//        if (currentRoomId != null) {
//            SocketResponseDTO leaveMessage = new SocketResponseDTO();
//            leaveMessage.setType(TrayIcon.MessageType.LEAVE);
//            leaveMessage.setSender(chatMessage.getSender());
//            messagingTemplate.convertAndSend(format("/channel/%s", currentRoomId), leaveMessage);
//        }
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
//    }
//
//    /**
//     * Enter to chat, step one
//     * Levate the chat, last but one step
//     * @param event
//     */
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        log.info("Received a new web socket connection.");
//    }
//
//    /**
//     * Last step, decrease number of watcching the game players
//     */
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        String roomId = (String) headerAccessor.getSessionAttributes().get("room_id");
//        if (username != null) {
//            log.info("User Disconnected: " + username);
//
//            SocketResponseDTO chatMessage = new SocketResponseDTO();
//            chatMessage.setType(MessageType.LEAVE);
//            chatMessage.setSender(username);
//
//            messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
//        }
//    }
//}
