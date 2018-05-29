INSERT INTO learning_jwt.user (id, email, enabled, first_name, last_name, last_password_reset_date, password, username) VALUES (1, 'enabled@user.com', true, 'user', 'user', null, '$2a$10$He71YAlKZy/lUioocvNFQeuTv/1NjtbMBYjm75GkxY7DfEH.suCo2', 'user');
INSERT INTO learning_jwt.user (id, email, enabled, first_name, last_name, last_password_reset_date, password, username) VALUES (2, 'admin@admin.com', true, 'admin', 'admin', null, '$2a$10$sn/ZY6Le9vOgYKY0gxOs9uLsrX6z0vkFLocRVIiQ.N2bEQXH32ewm', 'admin');
INSERT INTO learning_jwt.user (id, email, enabled, first_name, last_name, last_password_reset_date, password, username) VALUES (3, 'disabled@user.com', false, 'disabled', 'user', null, '$2a$10$WuU2IQtb.T4sjZvr5hrNUuAYMOuLtySBch6lzt9LpvFJJv5PC0gZy', 'disabled');

INSERT INTO learning_jwt.authority (id, authority_name) VALUES (1, 'ROLE_USER');
INSERT INTO learning_jwt.authority (id, authority_name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO learning_jwt.user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO learning_jwt.user_authority (user_id, authority_id) VALUES (2, 2);
INSERT INTO learning_jwt.user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO learning_jwt.user_authority (user_id, authority_id) VALUES (3, 1);
