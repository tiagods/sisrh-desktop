package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.Cliente;
import com.plkrhone.sisrh.model.ClienteSetor;

public interface ClienteDAO {
	Cliente save(Cliente cliente);
	void remove(Cliente cliente);
	List<Cliente> getAll();
	Cliente findById(Long id);
	Cliente findByNome(String nome);
	Cliente findByCnpj(String cnpj);
	long contar(int situacao);
	List<Cliente> filtrar(int situacao, ClienteSetor setor, String texto, String filtrarPor, String ordenacao);
		
}
