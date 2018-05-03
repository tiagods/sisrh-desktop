package com.plkrhone.sisrh.util.office;

public enum FileOfficeEnum {
	cronograma_selecao("documents/cronograma_selecao.xls"),
	entrevista("documents/entrevista.doc");
	//ficha_selecao("documents/ficha_selecao_candidato.xls");
	private String descricao;
	FileOfficeEnum(String descricao){
		this.descricao=descricao;
	}
	public String getDescricao() {
		return System.getProperty("user.dir")+"/"+descricao;
	}
}
