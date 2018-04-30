/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plkrhone.sisrh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Tiago
 */
public class FTPUpload extends RandomicoUtil{
    
    String server = "ftp.prolinkcontabil.com.br";
    int port = 21;
    String user = "prolinkcontabil";
    String pass = "plk*link815";
    
    String dirFTP= "gestorrh";

    public String gerarRandomico(File arquivo, String nomeInicial){
        String nomeArquivo = gerarSerial(nomeInicial+"_");
        nomeArquivo+=arquivo.getName().substring(arquivo.getName()
                        .lastIndexOf("."), arquivo.getName().length());//inserindo extensao no nome do arquivo
        return nomeArquivo;
    }
    public File carregarArquivo(Stage stage, Set<FileChooser.ExtensionFilter> filters){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.setTitle("Selecione um arquivo!");
        chooser.getExtensionFilters().addAll(filters);
        return chooser.showOpenDialog(stage);
        //        JFileChooser chooser = new JFileChooser();
//        chooser.setAcceptAllFileFilterUsed(true);
//        chooser.setDialogTitle("Selecione um arquivo");
//        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Documento PDF", "pdf"));
//        chooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Office","doc","docx","xls", "xlsx", "pptx"));
//        //chooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
//        int retorno = chooser.showOpenDialog(null);
//        if(retorno==JFileChooser.OPEN_DIALOG){
//            return chooser.getSelectedFile();
//        }
//        return null;
    }
    public boolean uploadFile(File arquivo,String novoNome){
        FTPClient ftp = new FTPClient();
        try{
            ftp.connect(server,port);
            ftp.login(user, pass);
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            boolean done;
                try (InputStream stream = new FileInputStream(arquivo)) {
                    String remoteFile = novoNome;
                    System.out.println("Start uploading first file");
                    done = ftp.storeFile(dirFTP+"/"+remoteFile, stream);
                    stream.close();
                }
            if (done)
                return true;

            return false;
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,"Tentativa de enviar o arquivo para o servidor resultou em erro:\n "+e.getMessage(),
                    "Erro de Envio", JOptionPane.ERROR_MESSAGE);
            return false;
        }finally{
            try {
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
