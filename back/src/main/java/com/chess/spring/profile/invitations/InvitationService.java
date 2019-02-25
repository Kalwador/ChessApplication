package com.chess.spring.profile.invitations;

import com.chess.spring.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

public interface InvitationService {
    Page<InvitationDTO> getInvitations(Pageable pageable) throws ResourceNotFoundException;

    boolean checkNick(String nick) throws ResourceNotFoundException, IllegalArgumentException;

    void sendInvitation(Long gameId, String playerNick) throws ResourceNotFoundException;

    @Transactional
    void accept(Long gameId) throws ResourceNotFoundException;

    @Transactional
    void decline(Long gameId) throws ResourceNotFoundException;
}
