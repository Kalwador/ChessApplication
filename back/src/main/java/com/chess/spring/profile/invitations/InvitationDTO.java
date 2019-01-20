package com.chess.spring.profile.invitations;

import com.chess.spring.game.pvp.GamePvP;
import com.chess.spring.game.pvp.GamePvPDTO;
import com.chess.spring.profile.account.Account;
import com.chess.spring.profile.account.AccountDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class InvitationDTO {
    private GamePvPDTO game;
    private AccountDTO account;

    public static List<InvitationDTO> map(List<Invitation> invitations) {
        return invitations.stream()
                .map(InvitationDTO::map)
                .collect(Collectors.toList());
    }

    public static InvitationDTO map(Invitation invitation) {
        return InvitationDTO.builder()
                .game(GamePvPDTO.map(invitation.getGame()))
                .account(AccountDTO.mapSimple(invitation.getGame().getBlackPlayer() != null ?
                        invitation.getGame().getBlackPlayer() :
                        invitation.getGame().getWhitePlayer()))
                .build();
    }
}

