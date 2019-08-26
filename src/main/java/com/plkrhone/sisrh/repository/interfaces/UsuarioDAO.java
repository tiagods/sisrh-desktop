package com.plkrhone.sisrh.repository.interfaces;

import java.util.List;

import com.plkrhone.sisrh.model.Usuario;

public interface UsuarioDAO {
	List<Usuario> getUsuariosByNome(String nome);
	//Usuario findByLoginAndSenha(String login, String senha);
}
