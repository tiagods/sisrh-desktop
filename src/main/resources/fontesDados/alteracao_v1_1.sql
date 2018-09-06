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

create table anu_can_conclusao(
    id serial,
    candidato_id integer,
    anuncio_id integer,
    treinamento_id integer,
    data_inicio_treinamento date,
    data_fim_treinamento date,
    data_inicio date,
    primary key(id)
);

create table ava_condicao(
    id serial,
    de decimal,
    ate decimal,
    descricao text,
    avaliacao_id integer,
    primary key(id)
);

alter table anu_ent_avaliacao add descricao text;

alter table anuncio drop candidato_aprovado_id;
alter table anuncio drop havera_treinamento;
alter table anuncio drop treinamento_id;
alter table anuncio drop data_inicio_treinamento;
alter table anuncio drop data_fim_treinamento;
