INSERT IGNORE INTO roles(id_role,name) VALUES(1,'ROLE_VENDEDOR');
INSERT IGNORE INTO roles(id_role,name) VALUES(2,'ROLE_ENTREGADOR');
INSERT IGNORE INTO roles(id_role,name) VALUES(3,'ROLE_CLIENTE');
INSERT IGNORE INTO roles(id_role,name) VALUES(4,'ROLE_FORNECEDOR');
INSERT IGNORE INTO roles(id_role,name) VALUES(5,'ROLE_ADMIN');
INSERT IGNORE INTO roles(id_role,name) VALUES(6,'ROLE_BASE');



INSERT IGNORE INTO users(id_user,email,nome_fantasia,senha,razao_social,celular, file_type, uuid, cpf_cnpj, latitude, longitude) VALUES(1,'admin@admin.com','admin','$2a$10$b6IF4oHvT7SSpn2VQAJoLusPn/eFvjVmnBqM.cZ0fCn.1SoxlPbmi','admin','222333','jpeg','1', '951.154.530-29', -22.256498, -45.695579);


INSERT IGNORE INTO user_has_roles(id_user_role,users_id,roles_id) VALUES(1,1,5);



INSERT IGNORE INTO users(id_user,email,nome_fantasia,senha,razao_social,celular, file_type, uuid, cpf_cnpj, latitude, longitude) VALUES(2,'vendedor@vendedor.com','vendedor','$2a$10$jhr2k8FxLVvhrZ7vKGQwjuv91r3BLJVFFTluAoRJO1WJdgJPa8btO','vendedor','4444','jpeg','2','566.532.180-07', -22.256380,-45.697000);

INSERT IGNORE INTO user_has_roles(id_user_role,users_id,roles_id) VALUES(2,2,1);



INSERT IGNORE INTO users(id_user,email,nome_fantasia,senha,razao_social,celular, file_type, uuid, cpf_cnpj, latitude, longitude) VALUES(3,'entregador@entregador.com','entregador','$2a$10$Ryzka75IMwSVbWivL0toy.Lfc1Fptf/V3XK1dVW0D4ZUNy/N9V6ZW','entregador','5555','jpeg','3','848.080.400-93', -22.256318,-45.696328);

INSERT IGNORE INTO user_has_roles(id_user_role,users_id,roles_id) VALUES(3,3,2);



INSERT IGNORE INTO users(id_user,email,nome_fantasia,senha,razao_social,celular, file_type, uuid, cpf_cnpj, latitude, longitude, assigned_to_id) VALUES(4,'cliente@cliente.com','cliente','$2a$10$UFFHLx/zwVhknEr2AOPYGueOIHTMV1waOhaFzYV0ph1hZpNxeiDhu','cliente','77777','jpeg','4','471.469.470-76', -22.256317,-45.696338,2);

INSERT IGNORE INTO user_has_roles(id_user_role,users_id,roles_id) VALUES(4,4,3);



INSERT IGNORE INTO users(id_user,email,nome_fantasia,senha,razao_social,celular, file_type, uuid, cpf_cnpj, latitude, longitude) VALUES(5,'fornecedor@fornecedor.com','fornecedor','$2a$10$0Q48kU/Wm291T1HKDP9fSuyJPtp5GvbB7NQJauSuqaYPps1lRhtHS','fornecedor','88888','jpeg','5','742.862.270-06', -22.2537,-45.7073);

INSERT IGNORE INTO user_has_roles(id_user_role,users_id,roles_id) VALUES(5,5,4);

INSERT IGNORE INTO users(id_user,email,nome_fantasia,senha,razao_social,celular, file_type, uuid, cpf_cnpj, latitude, longitude) VALUES(6,'base@base.com','base','$2a$10$TKX0aA6gU2pKTqZ2jBzXkeOqMLO7b8mffqMPskb..peXxV5xwEhty','base','9999','jpeg','5','580.783.560-30', -22.2537,-45.7073);

INSERT IGNORE INTO user_has_roles(id_user_role,users_id,roles_id) VALUES(6,6,6);
