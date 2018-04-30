ALTER TABLE usuario DROP CONSTRAINT fk_usu_nivel_id;
ALTER TABLE usuario DROP COLUMN usu_nivel_id;

ALTER TABLE usuario RENAME usu_id TO id;
ALTER TABLE usuario RENAME usu_ativo TO ativo;
ALTER TABLE usuario RENAME usu_login TO login;
ALTER TABLE usuario RENAME usu_nome TO nome;
ALTER TABLE usuario RENAME usu_senha TO senha;
ALTER TABLE usuario RENAME usu_email TO email;
ALTER TABLE usuario RENAME usu_celular TO celular;
ALTER TABLE usuario RENAME usu_criadoem TO criado_em;
ALTER TABLE usuario RENAME usu_criadopor_id TO criador_id;
ALTER TABLE usuario RENAME usu_ultimoacesso TO ultimo_acesso;
ALTER TABLE usuario ADD usu_nivel varchar(100);