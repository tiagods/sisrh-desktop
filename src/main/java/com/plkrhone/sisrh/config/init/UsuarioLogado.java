package com.plkrhone.sisrh.config.init;

import com.plkrhone.sisrh.model.Usuario;

import java.io.IOException;

public class UsuarioLogado {
    private static UsuarioLogado instance;
    private Usuario usuario = null;

    public static UsuarioLogado getInstance() {
        if (instance == null) instance = new UsuarioLogado();
        return instance;
    }
    private UsuarioLogado(){
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        UsuarioCache cache = UsuarioCache.getInstance();
        try {
            cache.save(usuario.getLogin());
        }catch(IOException e) {
        }
    }
    public String lastLogin() {
        UsuarioCache cache = UsuarioCache.getInstance();
        try {
            return cache.load();
        }catch(IOException e) {
            return "";
        }
    }
}
