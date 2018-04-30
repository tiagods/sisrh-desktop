ALTER TABLE "candidato"	ADD "objetivo1_id" INTEGER;
ALTER TABLE "candidato"	ADD "objetivo2_id" INTEGER;
ALTER TABLE "candidato"	ADD "objetivo3_id" INTEGER;
ALTER TABLE "candidato"	ADD "empresa1" VARCHAR(255);
ALTER TABLE "candidato"	ADD "empresa2" VARCHAR(255);
ALTER TABLE "candidato"	ADD "empresa3" VARCHAR(255);
ALTER TABLE "candidato"	ADD "cargo1_id" INTEGER;
ALTER TABLE "candidato"	ADD "cargo2_id" INTEGER;
ALTER TABLE "candidato"	ADD "cargo3_id" INTEGER;
ALTER TABLE "candidato"	ADD "descricao_cargo1" TEXT;
ALTER TABLE "candidato"	ADD "descricao_cargo2" TEXT;
ALTER TABLE "candidato"	ADD "descricao_cargo3" TEXT;
ALTER TABLE "candidato"	ADD CONSTRAINT fk_can_cargo1_id FOREIGN KEY (cargo1_id)
        REFERENCES public.vaga (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE SET NULL,
ALTER TABLE "candidato"	ADD CONSTRAINT fk_can_cargo2_id FOREIGN KEY (cargo2_id)
        REFERENCES public.vaga (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE "candidato"	ADD CONSTRAINT fk_can_cargo3_id FOREIGN KEY (cargo3_id)
        REFERENCES public.vaga (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE "candidato"	ADD CONSTRAINT fk_can_criado_por_id FOREIGN KEY (criado_por_id)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE "candidato"	ADD CONSTRAINT fk_can_obetivo2_id FOREIGN KEY (objetivo2_id)
        REFERENCES public.vaga (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE "candidato"	ADD CONSTRAINT fk_can_objetivo1_id FOREIGN KEY (objetivo1_id)
        REFERENCES public.vaga (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE "candidato"	ADD CONSTRAINT fk_can_objetivo3_id FOREIGN KEY (objetivo3_id)
        REFERENCES public.vaga (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE "candidato" add "data_modificacao" timestamp;