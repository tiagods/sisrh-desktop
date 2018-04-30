package com.plkrhone.sisrh.util.storage;

public enum PathStorageEnum {
	AVALIACAO_APLICADA("avaliacao-aplicada"),
	AVALIACAO("avaliacao"),
	CURRICULO("curriculo"),
	FORMULARIO_REQUISICAO("formulario-requisicao");
	
	private String descricao;
	
	PathStorageEnum(String descricao) {
		this.descricao=descricao;
	}
	public String getDescricao() {
		return descricao;
	}
}
