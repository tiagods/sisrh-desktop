package com.plkrhone.sisrh.util.storage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.plkrhone.sisrh.config.FTPConfig;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.amazonaws.AmazonServiceException;
import com.plkrhone.sisrh.util.RandomicoUtil;

public class FTPStorage extends Storage{
    FTPConfig cf = FTPConfig.getInstance();
    //String dirFTP= "sisrh";
    
	@Override
	public String gerarNome(File arquivo, String nomeInicial) {
		String nomeArquivo = RandomicoUtil.getInstance().gerarSerial(nomeInicial+"_");
        nomeArquivo+=arquivo.getName().substring(arquivo.getName()
                        .lastIndexOf("."), arquivo.getName().length());//inserindo extensao no nome do arquivo
        return nomeArquivo;
	}
	@Override
	public void uploadFile(File arquivo, String novoNome){
		FTPClient ftp = new FTPClient();
        try{
        	configFTP(ftp);
                try (InputStream stream = new FileInputStream(arquivo)) {
                    String remoteFile = novoNome;
                    System.out.println("Start uploading first file");
                    ftp.storeFile(cf.getValue("dirFTP")+"/"+remoteFile, stream);
                    stream.close();
                }
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Tentativa de enviar o arquivo para o servidor resultou em erro:\n "+e.getMessage(),
                    "Erro de Envio", JOptionPane.ERROR_MESSAGE);
        }finally{
        	shutdown(ftp);
        }
	}

	@Override
	public File downloadFile(String arquivo) {
		FTPClient ftp = new FTPClient();
        try{
            configFTP(ftp);
            String remoteFile1 = cf.getValue("dirFTP")+"/"+arquivo;
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyymmss");
            File dirFiles = new File(System.getProperty("java.io.tmpdir")+"/sisrh");
            if(!dirFiles.exists())
                dirFiles.mkdir();
            File novoArquivo = new File(dirFiles.getAbsolutePath()+"/"+sdf.format(new Date())+arquivo);
            try (OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(novoArquivo))) {
                boolean success = ftp.retrieveFile(remoteFile1, outputStream1);
                if (success) {
                    return novoArquivo;
                }
            }
            return null;
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Tentativa de baixar e abrir o arquivo do servidor resultou em erro:\n "+e.getMessage(),
                    "Erro de Download", JOptionPane.ERROR_MESSAGE);
            return null;
        }finally{
            shutdown(ftp);
        }
	}
	private FTPClient configFTP(FTPClient ftp) throws IOException{
        ftp.connect(cf.getValue("host"),Integer.parseInt(cf.getValue("port")));
        ftp.login(cf.getValue("user"),cf.getValue("pass"));
        ftp.enterLocalPassiveMode();
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        return ftp;
	}
	private void shutdown(FTPClient ftp){
		try {
            if (ftp.isConnected()) {
                ftp.logout();
                ftp.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	@Override
	public void transferTo(String file, String file2) {
		// Tira da pasta temporaria e insere na pasta oficial
	}
	@Override
	public void delete(String arquivo) throws AmazonServiceException {
		// TODO Auto-generated method stub
		
	}

}
