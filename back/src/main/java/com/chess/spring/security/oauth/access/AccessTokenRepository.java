//package com.chess.spring.security.oauth.access;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
////TODO przeniesc do mongo
////@N1qlPrimaryIndexed
////@ViewIndexed(designDoc = "couchbaseAccessToken")
//public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {
//    List<AccessToken> findByClientId(String clientId);
//
//    List<AccessToken> findByClientIdAndUsername(String clientId, String username);
//
//    Optional<AccessToken> findByTokenId(String tokenId);
//
//    Optional<AccessToken> findByRefreshToken(String refreshToken);
//
//    Optional<AccessToken> findByAuthenticationId(String authenticationId);
//
//}
