alter table curso_superior rename to curso;
alter table curso add column nivel varchar;
alter table anu_formulario_requisicao rename curso_superior_id to curso_id;

alter table vaga rename to cargo;
alter table anu_formulario_requisicao rename vaga_id to cargo_id;
alter table anu_formulario_requisicao add nivel_id int,
alter table anu_formulario_requisicao add cargo_ads varchar,
