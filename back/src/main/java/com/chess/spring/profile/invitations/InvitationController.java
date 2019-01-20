package com.chess.spring.profile.invitations;

import com.chess.spring.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class InvitationController {
    private InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    public List<InvitationDTO> getInvitations() throws ResourceNotFoundException {
        return invitationService.getInvitations();
    }

    public void acceptInvitation(Long gameId) throws ResourceNotFoundException {
        invitationService.accept(gameId);
    }
}
