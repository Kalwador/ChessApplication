-- -- GLOBAL
INSERT INTO authority (authority) VALUES ('ROLE_ADMIN');
INSERT INTO authority (authority) VALUES ('ROLE_USER');

INSERT INTO backlog (version,note) VALUES ('Alfa-0.85','');
INSERT INTO backlog (version,note) VALUES ('Alfa-0.85','Zaps wiadomości chatu');
INSERT INTO backlog (version,note) VALUES ('Alfa-0.85','Poprawki w obsłudze statusów gry');
INSERT INTO backlog (version,note) VALUES ('Alfa-0.86','Chagelog czesc pierwsza');
INSERT INTO backlog (version,note) VALUES ('Alfa-0.86','');
INSERT INTO backlog (version,note) VALUES (null,'Zaczytywanie listy gier per user');
INSERT INTO backlog (version,note) VALUES (null,'Poprawa scolla w chacie');
INSERT INTO backlog (version,note) VALUES (null,'Poprawa responsywności chatu');
INSERT INTO backlog (version,note) VALUES (null,'Statystyki wykresy i grafy');
INSERT INTO backlog (version,note) VALUES (null,'Profil');
INSERT INTO backlog (version,note) VALUES (null,'Avatary');

-- -- ADMIN
INSERT INTO account_details (email,username,password,enabled,expired,locked,credentials_expired) VALUES ('admin@mail.me','admin','$2a$12$U5NI1R4dEQHRB0syPWPk0emNVi8Hz1h0KVVBLUU3j5HSay2.1gegK',true,true,true,true);
INSERT INTO accounts (account_details,age,nick) VALUES (1,25,'ADMIN');
INSERT INTO account_authority (id,authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO account_authority (id,authority) VALUES (1, 'ROLE_USER');

-- -- TEST USER
INSERT INTO account_details (email,username,password,enabled,expired,locked,credentials_expired) VALUES ('user@mail.me','user1','$2a$12$4V6cV4upNnWD6hc6p8AKIe28FGasftuAeV2jEB86/lWmY545Q6V86',true,true,true,true);
INSERT INTO account_authority (id,authority) VALUES (2, 'ROLE_USER');
INSERT INTO statistics (rank,games_pvp,win_games_pvp,week_games_pvp,week_win_games_pvp,month_games_pvp,month_win_games_pvp,games_pve,win_games_pve,week_games_pve,week_win_games_pve,month_games_pve,month_win_games_pve)
  VALUES (1766,343,184,13,7,83,49,721,584,92,76,349,270);
INSERT INTO accounts (account_details,first_name,last_name,age,nick,gender,statistics_id) VALUES (2,'Jan','Nowak',22,'GraczTestowy1','MALE',1);

-- -- TEST USER 2
INSERT INTO account_details (email,username,password,enabled,expired,locked,credentials_expired) VALUES ('user2@mail.me','user2','$2a$12$WOJzx8RXnQAHBe2guq.igegGSR/zHci967.TOWc.18b7E35W/paHi',true,true,true,true);
INSERT INTO account_authority (id,authority) VALUES (3, 'ROLE_USER');
INSERT INTO statistics (rank,games_pvp,win_games_pvp,week_games_pvp,week_win_games_pvp,month_games_pvp,month_win_games_pvp,games_pve,win_games_pve,week_games_pve,week_win_games_pve,month_games_pve,month_win_games_pve)
  VALUES (1830,343,184,13,7,83,49,721,584,92,76,349,270);
INSERT INTO accounts (account_details,first_name,last_name,age,nick,gender,statistics_id) VALUES (3,'Agnieszka','Kowalska',33,'PrzeciwnikTestowy2','FEMALE',2);

-- -- TEST GAMES PVE
-- roszada
INSERT INTO game_pve (account_id,board,color,game_started,level,moves,permalink,status,time_per_move)
  VALUES (2,'r1bqkbnr/ppp2ppp/2n5/1B1pp3/8/4PN2/PPPP1PPP/RNBQK2R w KQkq - 0 1','WHITE','2018-11-25',1,null,null,'PLAYER_MOVE',null);

-- mat
INSERT INTO game_pve (account_id,board,color,game_started,level,moves,permalink,status,time_per_move)
  VALUES (2,'1k6/5R1B/6RN/p7/4P2p/P3P2P/2K3P1/8 w - - 0 1','WHITE','2018-11-25',1,null,null,'PLAYER_MOVE',null);

-- remis
INSERT INTO game_pve (account_id,board,color,game_started,level,moves,permalink,status,time_per_move)
  VALUES (2,'4k3/1Qp5/8/2BP1N2/p7/P3P2P/1PP4P/RN2KB1R w KQ - 0 1','WHITE','2018-11-25',1,null,null,'PLAYER_MOVE',null);

-- szach
INSERT INTO game_pve (account_id,board,color,game_started,level,moves,permalink,status,time_per_move)
VALUES (2,'N3k2r/p4pRp/2n1bn2/1Q6/P4b2/3P1N2/1P1KBP2/R1B4q w - - 0 1','WHITE','2018-11-25',1,null,null,'PLAYER_MOVE',null);


-- -- TEST GAMES PVP
INSERT INTO game_pvp (white_id,black_id,board,game_started,moves,permalink,status,time_per_move)
  VALUES (2,3,'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1','2018-11-25',null,null,'WHITE_MOVE',null);
INSERT INTO chat (game_id, conversation) VALUES (1, '21:27 Admin: Testowa wiadomosc&zwnj;21:29 Gracz2333: Wszystko działa pozdrawiam :)&zwnj;21:32 NiedzielnyGracz: Witam wszystkich!&zwnj;');

INSERT INTO game_pvp (white_id,black_id,board,game_started,moves,permalink,status,time_per_move)
  VALUES (2,null,'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1','2018-11-25',null,null,'ROOM',null);
INSERT INTO chat (game_id, conversation) VALUES (2, '');
