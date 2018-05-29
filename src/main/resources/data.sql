INSERT INTO user (id, username, password, firstname, lastname, email, enabled) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 'admin@admin.com', 1);
INSERT INTO user (id, username, password, firstname, lastname, email, enabled) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'enabled@user.com', 1);
INSERT INTO user (id, username, password, firstname, lastname, email, enabled) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'disabled@user.com', 0);
INSERT INTO user (id, username, password, firstname, lastname, email, enabled) VALUES (4, 'vishal', '$2a$10$LlviLnti41JZGlN0o6BaB.iUKDRAZyQssriqM3C0Xz1Q0WgcXC0ca', 'Vishal', 'Prajapati', 'vishal@gmail.com', 1);

INSERT INTO authority (id, authority_name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, authority_name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (4, 2);
