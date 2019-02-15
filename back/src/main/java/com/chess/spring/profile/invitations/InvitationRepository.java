package com.chess.spring.profile.invitations;

import com.chess.spring.game.pvp.GamePvP;
import com.chess.spring.profile.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Page<Invitation> findByAccount(Pageable pageable, Account account);

    void deleteByGame(GamePvP gamePvP);
}