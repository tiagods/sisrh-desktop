package com.plkrhone.sisrh.repository.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import com.plkrhone.sisrh.model.Anuncio;
import com.plkrhone.sisrh.model.Cliente;
import com.plkrhone.sisrh.model.Tarefa;
import com.plkrhone.sisrh.model.Usuario;

public interface TarefaDAO {
	Tarefa save(Tarefa tarefa);
	void remove(Tarefa tarefa);
	List<Tarefa> getAll();
	Tarefa findById(Long id);
	List<Tarefa> filtrar(Anuncio anuncio, Anuncio.Cronograma cronograma, Anuncio.AnuncioStatus anuncioStatus, Cliente cliente, Usuario atendente, LocalDateTime dataInicio, LocalDateTime dataFim,int tarefaStatus);
	long count(int finalizado);
	
}
