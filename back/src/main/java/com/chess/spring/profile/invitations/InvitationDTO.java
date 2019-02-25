package com.chess.spring.profile.invitations;

import com.chess.spring.game.pvp.GamePvP;
import com.chess.spring.game.pvp.GamePvPDTO;
import com.chess.spring.profile.account.AccountDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class InvitationDTO {
    private GamePvPDTO game;
    private AccountDTO account;


    public static Page<GamePvPDTO> map(Page<GamePvP> all, Pageable page) {
        List<GamePvPDTO> games = all.stream().map(GamePvPDTO::map).collect(Collectors.toList());
        return new PageImpl<>(games, page, games.size());
    }

    public static Page<GamePvPDTO> map(List<GamePvP> all, Pageable page) {
        List<GamePvPDTO> games = all.stream().map(GamePvPDTO::map).collect(Collectors.toList());
        return new PageImpl<>(games, page, games.size());
    }


    public static Page<InvitationDTO> map(Page<Invitation> invitations) {
        List<InvitationDTO> invs = invitations.stream()
                .map(InvitationDTO::map)
                .collect(Collectors.toList());
        return new PageImpl<InvitationDTO>(invs, invitations.getPageable(), invs.size());
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

