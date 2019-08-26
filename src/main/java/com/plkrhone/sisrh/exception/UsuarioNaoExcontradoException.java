package com.plkrhone.sisrh.exception;

public class UsuarioNaoExcontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public UsuarioNaoExcontradoException(String mensagem) {
        super(mensagem);
    }
    public UsuarioNaoExcontradoException(String mensagem, Throwable causa) {
        super(mensagem,causa);
    }
}
