package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.ClienteSetor;

public interface ClienteSetoresDAO {
	ClienteSetor save(ClienteSetor clienteSetor);
	void remove(ClienteSetor clienteSetor);
	List<ClienteSetor> getAll();
	ClienteSetor findByNome(String nome);
}
