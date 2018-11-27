-- -- GLOBAL
INSERT INTO authority (authority) VALUES ('ROLE_ADMIN');
INSERT INTO authority (authority) VALUES ('ROLE_USER');

-- -- ADMIN
INSERT INTO account_details (email,username,password,enabled,expired,locked,credentials_expired) VALUES ('admin@mail.me','admin','$2a$10$BGy6eqJgaYhUCL8nJmRlj.TtIEwOVXTZZEU7f3udQhk6S30DMKXKe',true,true,true,true);
INSERT INTO accounts (account_details,age, nick) VALUES (1,25,'ADMIN');
INSERT INTO account_authority (id,authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO account_authority (id,authority) VALUES (1, 'ROLE_USER');

-- -- TEST USER
INSERT INTO account_details (email,username,password,enabled,expired,locked,credentials_expired) VALUES ('user@mail.me','user','$2a$10$nn3oUY6E3msZbrMxjjQ6R.rij4hn3Oyn/Sip.7HpmQpN.V/QG3B6O',true,true,true,true);
INSERT INTO account_authority (id,authority) VALUES (2, 'ROLE_USER');
INSERT INTO statistics (games_pvp,win_games_pvp,week_games_pvp,week_win_games_pvp,month_games_pvp,month_win_games_pvp,games_pve,win_games_pve,week_games_pve,week_win_games_pve,month_games_pve,month_win_games_pve)
  VALUES (343,184,13,7,83,49,721,584,92,76,349,270);
INSERT INTO accounts (account_details,age,nick,statistics_id) VALUES (2,22,'TestFirstPlayer',1);

-- -- TEST USER 2
INSERT INTO account_details (email,username,password,enabled,expired,locked,credentials_expired) VALUES ('user2@mail.me','user2','$2a$10$nn3oUY6E3msZbrMxjjQ6R.rij4hn3Oyn/Sip.7HpmQpN.V/QG3B6O',true,true,true,true);
INSERT INTO account_authority (id,authority) VALUES (3, 'ROLE_USER');
INSERT INTO statistics (games_pvp,win_games_pvp,week_games_pvp,week_win_games_pvp,month_games_pvp,month_win_games_pvp,games_pve,win_games_pve,week_games_pve,week_win_games_pve,month_games_pve,month_win_games_pve)
  VALUES (343,184,13,7,83,49,721,584,92,76,349,270);
INSERT INTO accounts (account_details,age,nick,statistics_id) VALUES (3,22,'TestSecondPlayer',2);

-- -- TEST GAMES PVE
INSERT INTO game_pve (account_id,board,color,game_started,level,moves,permalink,status,time_per_move)
  VALUES (2,'r1bqkbnr/ppp2ppp/2n5/1B1pp3/8/4PN2/PPPP1PPP/RNBQK2R w KQkq - 0 1','WHITE','2018-11-25',1,null,null,'PLAYER_MOVE',null);

INSERT INTO game_pve (account_id,board,color,game_started,level,moves,permalink,status,time_per_move)
  VALUES (2,'1k6/5R1B/6RN/p7/4P2p/P3P2P/2K3P1/8 w - - 0 1','WHITE','2018-11-25',1,null,null,'PLAYER_MOVE',null);

INSERT INTO game_pve (account_id,board,color,game_started,level,moves,permalink,status,time_per_move)
  VALUES (2,'4k3/1Qp5/8/2BP1N2/p7/P3P2P/1PP4P/RN2KB1R w KQ - 0 1','WHITE','2018-11-25',1,null,null,'PLAYER_MOVE',null);

-- -- TEST GAMES PVP
INSERT INTO game_pvp (white_id,black_id,board,game_started,moves,permalink,status,time_per_move)
  VALUES (2,3,'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1','2018-11-25',null,null,'WHITE_MOVE',null);

INSERT INTO game_pvp (white_id,black_id,board,game_started,moves,permalink,status,time_per_move)
  VALUES (2,null,'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1','2018-11-25',null,null,'ROOM',null);