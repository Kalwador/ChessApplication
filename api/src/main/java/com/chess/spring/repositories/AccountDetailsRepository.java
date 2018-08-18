package com.chess.spring.repositories;

import com.chess.spring.entities.account.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDetailsRepository  extends JpaRepository<AccountDetails, Long> {
    @Query("SELECT ud FROM AccountDetails ud WHERE LOWER(ud.username) = LOWER(:username)")
    Optional<AccountDetails> findByUsernameCaseInsensitive(@Param("username") String username);

    Optional<AccountDetails> findByEmail(String email);

    Optional<AccountDetails> findById(Long id);
}
