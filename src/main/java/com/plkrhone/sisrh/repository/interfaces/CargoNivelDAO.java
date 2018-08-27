package com.plkrhone.sisrh.repository.interfaces;

import com.plkrhone.sisrh.model.CargoNivel;
import com.plkrhone.sisrh.model.ClienteSetor;

import java.util.List;

public interface CargoNivelDAO {
	CargoNivel save(CargoNivel nivel);
	void remove(CargoNivel nivel);
	List<CargoNivel> getAll();
	CargoNivel findByNome(String nome);
}
