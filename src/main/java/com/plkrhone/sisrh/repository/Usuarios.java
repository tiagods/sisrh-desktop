package com.plkrhone.sisrh.repository;

import com.plkrhone.sisrh.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Usuarios extends JpaRepository<Usuario,Long> {

    List<Usuario> filtrar(String nome, int inativo, String ordem);

    Usuario findByLogin(String login);

    @Query("select u from Usuario u where login ilike=:login and senha ilike = :senha")
    Usuario findByLoginAndSenha(String login, String senha);
}
