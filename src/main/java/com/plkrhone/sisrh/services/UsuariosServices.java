package com.plkrhone.sisrh.services;

import com.plkrhone.sisrh.exception.UsuarioNaoExcontradoException;
import com.plkrhone.sisrh.model.Usuario;
import com.plkrhone.sisrh.repository.Usuarios;
import com.plkrhone.sisrh.util.CriptografiaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServices {

    @Autowired
    Usuarios usuarios;
    @Autowired
    CriptografiaUtil criptografia;

    public List<Usuario> filtrar(String nome, int status, String order) {
        return usuarios.filtrar(nome,status,order);
    }

    public Usuario findByLoginAndSenha(String login, String senha) throws UsuarioNaoExcontradoException {
        Usuario usuario = usuarios.findByLoginAndSenha(login,criptografia.criptografar(senha));
        if(usuario == null){
            throw new UsuarioNaoExcontradoException("Não foi encontrado usuario com essas credenciais");
        }
        else return usuario;
    }

    public List<Usuario> listar(String order) {
        return usuarios.findAll(Sort.by(order));
    }

    public Usuario buscar(long id) throws UsuarioNaoExcontradoException{
        Optional<Usuario> usuario = usuarios.findById(id);
        if(usuario.isPresent()) {
            return usuario.get();
        }
        else
            throw new UsuarioNaoExcontradoException("Não foi encontrado esse usuario");
    }

    public Usuario findByLogin(String login) {
        return usuarios.findByLogin(login);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarios.save(usuario);
    }
}
