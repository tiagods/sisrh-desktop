package com.plkrhone.sisrh.util.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.plkrhone.sisrh.config.AWSConfig;


public class AWSStorage extends Storage{
	private static String bucketName="";
	private AmazonS3 builder() {
		AWSConfig config = AWSConfig.getInstance();
		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard().withCredentials(new AWSCredentialsProvider() {
			@Override
			public void refresh() {
			}
			@Override
			public AWSCredentials getCredentials() {
				AWSCredentials c =  new BasicAWSCredentials(
						config.getValue("aws_access_key_id"), 
						config.getValue("aws_secret_access_key"));
				return c;
			}
		});
		builder.withRegion("us-east-1");		
		bucketName=config.getValue("bucket-teste");
		AmazonS3 s3 = builder.build();
		return s3;
	}
	
	@Override
	public String gerarNome(File arquivo, String nomeInicial) {
		LocalDateTime now = LocalDateTime.now();
		String extensao = arquivo.getName().substring(arquivo.getName().lastIndexOf("."));
		String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String novoNome = nomeInicial+"_"+time+extensao;//+"_"+arquivo.getName();
		return novoNome;
	}

	@Override
	public File downloadFile(String arquivo) throws AmazonServiceException,FileNotFoundException,IOException{
		S3Object o = builder().getObject(bucketName, arquivo);
		S3ObjectInputStream  s3Input = o.getObjectContent();
		File file = new File(System.getProperty("java.io.tmpdir")+"/"+arquivo);
		if(!file.getParentFile().exists())
			file.getParentFile().mkdir();
		FileOutputStream output = new FileOutputStream(file);
		byte[] readbuf = new byte[1024];
		int read_len = 0;
		while ((read_len = s3Input.read(readbuf)) > 0) {
			output.write(readbuf, 0, read_len);
		}
		s3Input.close();
		output.close();
		return file;		    
	}

	@Override
	public void transferTo(String file, String file2) throws AmazonServiceException{
		AmazonS3 s3 = builder();
		s3.copyObject(bucketName, file, bucketName, file2);
		s3.deleteObject(bucketName, file);
	}

	@Override
	public void uploadFile(File arquivo, String novoNome) throws AmazonServiceException{
		builder().putObject(bucketName, novoNome, arquivo).isRequesterCharged();
	}

	@Override
	public void delete(String arquivo) throws AmazonServiceException {
		builder().deleteObject(bucketName, arquivo);
	}	
}
