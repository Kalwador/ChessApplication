INSERT INTO account_details (id,email,username,password,enabled,expired,locked,credentials_expired) VALUES (1,'admin@mail.me','admin','$2a$10$BGy6eqJgaYhUCL8nJmRlj.TtIEwOVXTZZEU7f3udQhk6S30DMKXKe',true,true,true,true);
INSERT INTO account_details (id,email,username,password,enabled,expired,locked,credentials_expired) VALUES (2,'user@mail.me','user','$2a$10$nn3oUY6E3msZbrMxjjQ6R.rij4hn3Oyn/Sip.7HpmQpN.V/QG3B6O',true,true,true,true);
-- --
INSERT INTO accounts (id,account_details,age, name) VALUES (1,1,22,'Admin');
INSERT INTO accounts (id,account_details,age, name) VALUES (2,2,24,'UserFirstPlayer');
-- --
INSERT INTO authority (authority) VALUES ('ROLE_USER');
INSERT INTO authority (authority) VALUES ('ROLE_ADMIN');
-- --
INSERT INTO account_authority (id,authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO account_authority (id,authority) VALUES (1, 'ROLE_USER');
INSERT INTO account_authority (id,authority) VALUES (2, 'ROLE_USER');