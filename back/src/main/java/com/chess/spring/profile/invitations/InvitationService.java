package com.chess.spring.profile.invitations;

import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.game.Game;
import com.chess.spring.game.pvp.GamePvP;
import com.chess.spring.game.pvp.GamePvPService;
import com.chess.spring.profile.account.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.chess.spring.profile.invitations.InvitationDTO.map;

@Service
public class InvitationService {

    private InvitationRepository invitationRepository;
    private AccountService accountService;
    private GamePvPService gamePvPService;

    public InvitationService(InvitationRepository invitationRepository,
                             AccountService accountService,
                             GamePvPService gamePvPService) {
        this.invitationRepository = invitationRepository;
        this.accountService = accountService;
        this.gamePvPService = gamePvPService;
    }

    public List<InvitationDTO> getInvitations() throws ResourceNotFoundException {
        return map(invitationRepository.findByAccount(accountService.getCurrent()));
    }

    public void accept(Long gameId) throws ResourceNotFoundException {
        GamePvP game = gamePvPService.getById(gameId);
        gamePvPService.startGame(accountService.getCurrent(), game);
    }
}
