package com.plkrhone.sisrh.repository.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.plkrhone.sisrh.model.Candidato;
import com.plkrhone.sisrh.model.Vaga;

public interface CandidatoDAO {
	Candidato save(Candidato candidato);
	void save(Set<Candidato> candidatosList);
	void remove(Candidato candidato);
	List<Candidato> getAll();
	Candidato findById(Long id);
	Candidato findByEmail(String email);
	List<Candidato> filtrar(Vaga objetivo, Vaga experiencia, String sexo, int idadeMin, int idadeMax, String indicacao,
			Candidato.Escolaridade escolaridadeMin, Candidato.Escolaridade escolaridadeMax, LocalDate dataCurriculoMin, LocalDate dataCurriculoMax, String buscarPor, String valorPesquisa,boolean indisponivel);
}
