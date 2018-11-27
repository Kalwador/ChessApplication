package com.chess.spring.controllers.game;

import com.chess.spring.dto.game.GamePvPDTO;
import com.chess.spring.dto.game.SocketMessageDTO;
import com.chess.spring.exceptions.*;
import com.chess.spring.models.game.SocketMessageType;
import com.chess.spring.services.game.GamePvPServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.validation.Valid;

import static java.lang.String.format;


@Slf4j
@RestController
@RequestMapping(path = "/game/pvp")
@Api(value = "GamePvP Controller", description = "Manage game with other player transitions and statuses")
public class GamePvPController {

    private SimpMessageSendingOperations messagingTemplate;
    private GamePvPServiceImpl gameService;

    public GamePvPController(GamePvPServiceImpl gameService,
                             SimpMessageSendingOperations messagingTemplate) {
        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Succes get list of games")
    })
    @GetMapping
    public Page<GamePvPDTO> getAll(Pageable page) {
        return this.gameService.getAll(page);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success get game"),
            @ApiResponse(code = 400, message = "Game not found, wrong id")
    })

    @GetMapping(value = "/{gameId}")
    public GamePvPDTO getById(@PathVariable Long gameId) throws InvalidDataException {
        return GamePvPDTO.map(gameService.getById(gameId));
    }

    @GetMapping(path = "/test/{message}")
    public void test(@PathVariable String message) {
        this.distributeChatMessage("1", new SocketMessageDTO(SocketMessageType.CHAT,null,message,"SEFVER"));
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game found"),
            @ApiResponse(code = 404, message = "User not logged in"),
            @ApiResponse(code = 409, message = "Level out of scale")
    })
    @PostMapping(value = "/find")
    public Long findGame(@RequestBody @Valid GamePvPDTO gamePvEDTO) throws ResourceNotFoundException {
        return gameService.findGame(gamePvEDTO);
    }

    /**
     * Enter to chat, step one
     * Levate the chat, last but one step
     *
     * @param event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection.");
    }

    /**
     * Send message, to other users
     */
    @MessageMapping("/channel/game/{roomId}/chat")
    public void distributeChatMessage(@DestinationVariable String roomId, @Payload SocketMessageDTO chatMessage) {
        messagingTemplate.convertAndSend(format("/channel/game/%s", roomId), chatMessage);
    }

    /**
     * Send message, to other users
     */
    @MessageMapping("/channel/game/{roomId}/move")
    public void makeMove(@DestinationVariable String roomId, @Payload SocketMessageDTO chatMessage) {
        System.out.println(chatMessage);
    }

    /**
     * User enter the chat, send info about joinning to other users
     * here u cna count nouber of observers of game
     */
    @MessageMapping("/channel/game/{roomId}/join")
    public void addUser(@DestinationVariable String roomId, @Payload SocketMessageDTO chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {
        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            SocketMessageDTO leaveMessage = new SocketMessageDTO();

            chatMessage.setType(SocketMessageType.JOIN);
            messagingTemplate.convertAndSend(format("/channel/%s", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
    }

    /**
     * Last step, decrease number of watcching the game players
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String roomId = (String) headerAccessor.getSessionAttributes().get("room_id");
        if (username != null) {
            log.info("User Disconnected: " + username);

            SocketMessageDTO chatMessage = new SocketMessageDTO();
            chatMessage.setType(SocketMessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend(format("/channel/%s", roomId), chatMessage);
        }
    }
}
