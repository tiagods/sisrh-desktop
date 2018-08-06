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

import com.plkrhone.sisrh.config.FTPConfig;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;

/**
 *
 * @author Tiago
 */
public class FTPDownload {
    private File novoArquivo;

    public File returnFile(){
        return this.novoArquivo;
    }
    public boolean downloadFile(String arquivo){
        FTPConfig cf = FTPConfig.getInstance();
        FTPClient ftp = new FTPClient();
        try{
            ftp.connect(cf.getValue("host"),Integer.parseInt(cf.getValue("port")));
            ftp.login(cf.getValue("user"),cf.getValue("pass"));
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            String remoteFile1 = cf.getValue("dirFTP")+"/"+arquivo;
            novoArquivo= new File(System.getProperty("java.io.tmpdir")+"/"+arquivo);
            try (OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(novoArquivo))) {
                boolean success = ftp.retrieveFile(remoteFile1, outputStream1);
                if (success) {
                    return true;
                }
            }
            return false;
        }catch(IOException e){
            System.out.println("Erro = "+e.getMessage());
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
