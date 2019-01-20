package com.chess.spring.profile.invitations;

import com.chess.spring.profile.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sun.java2d.cmm.Profile;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByAccount(Account account);
}
