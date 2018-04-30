package com.plkrhone.sisrh.util.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.amazonaws.AmazonServiceException;

public interface StorageInterface {
	String gerarNome(File arquivo, String nomeInicial);
	File downloadFile(String arquivo) throws AmazonServiceException,FileNotFoundException,IOException;
	void transferTo(String file, String file2) throws AmazonServiceException;
	void uploadFile(File arquivo, String novoNome) throws AmazonServiceException;
	void delete(String arquivo) throws AmazonServiceException;
}
