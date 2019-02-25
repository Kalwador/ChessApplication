package com.chess.spring.profile.invitations;

import com.chess.spring.exceptions.ExceptionMessages;
import com.chess.spring.exceptions.ResourceNotFoundException;
import com.chess.spring.game.pvp.GamePvP;
import com.chess.spring.game.pvp.GamePvPService;
import com.chess.spring.profile.account.Account;
import com.chess.spring.profile.account.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.chess.spring.profile.invitations.InvitationDTO.map;

@Service
public class InvitationServiceImpl implements InvitationService {

    private InvitationRepository invitationRepository;
    private AccountService accountService;
    private GamePvPService gamePvPService;

    public InvitationServiceImpl(InvitationRepository invitationRepository,
                                 AccountService accountService,
                                 GamePvPService gamePvPService) {
        this.invitationRepository = invitationRepository;
        this.accountService = accountService;
        this.gamePvPService = gamePvPService;
    }

    @Override
    public Page<InvitationDTO> getInvitations(Pageable pageable) throws ResourceNotFoundException {
        return map(invitationRepository.findByAccount(pageable, accountService.getCurrent()));
    }

    @Override
    public boolean checkNick(String nick) throws ResourceNotFoundException, IllegalArgumentException {
        if(this.accountService.getCurrent().getNick().equals(nick)){
            throw new IllegalArgumentException(ExceptionMessages.INVITATION_SELF_EXCEPTION.getInfo());
        }
        return accountService.existByNick(nick);
    }

    @Override
    public void sendInvitation(Long gameId, String playerNick) throws ResourceNotFoundException {
        GamePvP game = gamePvPService.getById(gameId);
        Account account = accountService.findPlayerByNickOrName(playerNick);
        Invitation invitation = Invitation.builder().account(account).game(game).build();
        invitationRepository.save(invitation);
        //TODO-sockets send event through sockets about new invitation to current user
    }

    @Override
    @Transactional
    public void accept(Long gameId) throws ResourceNotFoundException {
        GamePvP game = gamePvPService.getById(gameId);
        gamePvPService.startGame(accountService.getCurrent(), game);
        invitationRepository.deleteByGame(game);
        Account accountToInform = game.getWhitePlayer() != null ? game.getWhitePlayer() : game.getBlackPlayer();
        //TODO - emit event through sockets about decline invitation to accountToInform
    }


    @Override
    @Transactional
    public void decline(Long gameId) throws ResourceNotFoundException {
        GamePvP game = gamePvPService.getById(gameId);
        invitationRepository.deleteByGame(game);
        Account accountToInform = game.getWhitePlayer() != null ? game.getWhitePlayer() : game.getBlackPlayer();
        //TODO - emit event through sockets about decline invitation to accountToInform
    }
}
