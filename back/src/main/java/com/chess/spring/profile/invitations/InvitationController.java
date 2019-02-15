package com.chess.spring.profile.invitations;

import com.chess.spring.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/invitations")
public class InvitationController {
    private InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping
    public Page<InvitationDTO> getInvitations(Pageable pageable) throws ResourceNotFoundException {
        return invitationService.getInvitations(pageable);
    }

    @GetMapping(path = "/check/{nick}")
    public boolean checkNick(@PathVariable String nick) throws ResourceNotFoundException {
        return invitationService.checkNick(nick);
    }

    @PostMapping(value = "/send/{gameId}/{playerNick}")
    @ResponseStatus(HttpStatus.CREATED)
    public void send(@PathVariable Long gameId, @PathVariable String playerNick) throws ResourceNotFoundException {
        invitationService.sendInvitation(gameId, playerNick);
    }

    @PostMapping(value = "/accept/{gameId}")
    public void acceptInvitation(@PathVariable Long gameId) throws ResourceNotFoundException {
        invitationService.accept(gameId);
    }

    @PostMapping(value = "/decline/{gameId}")
    public void declineInvitation(@PathVariable Long gameId) throws ResourceNotFoundException {
        invitationService.decline(gameId);
    }
}
