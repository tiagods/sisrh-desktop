--alter table curso_superior rename to curso;
alter table curso_superior add column nivel varchar;
-- alter table vaga rename to cargo;
-- alter table anu_formulario_requisicao rename column curso_superior_id to curso_id;
-- alter table anu_formulario_requisicao rename column vaga_id to cargo_id;
alter table anu_formulario_requisicao add nivel_id integer;
alter table anu_formulario_requisicao add cargo_ads varchar;

create table cargo_nivel (
    id serial,
    nome varchar,
    primary key(id)
);

create table versao_app (
	id serial,
	versao varchar,
	detalhes text,
	historico timestamp,
	primary key (id)
);
