/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plkrhone.sisrh.util;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;

/**
 *
 * @author Tiago
 */
public class FTPDownload {
    String server = "ftp.prolinkcontabil.com.br";
    int port = 21;
    String user = "prolinkcontabil";
    String pass = "plk*link815";
    
    String dirFTP= "gestorrh";
    private File novoArquivo;
    
    public File downloadFile(String arquivo){
        FTPClient ftp = new FTPClient();
        try{
            ftp.connect(server,port);
            ftp.login(user, pass);
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            String remoteFile1 = dirFTP+"/"+arquivo;
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyymmss");
            File dirFiles = new File(System.getProperty("java.io.tmpdir")+"/gestorrh");
            if(!dirFiles.exists())
                dirFiles.mkdir();
            novoArquivo = new File(dirFiles.getAbsolutePath()+"/"+sdf.format(new Date())+arquivo);
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
