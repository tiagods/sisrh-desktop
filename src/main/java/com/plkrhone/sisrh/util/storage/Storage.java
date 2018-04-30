package com.plkrhone.sisrh.util.storage;

import java.io.File;
import java.util.Set;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public abstract class Storage implements StorageInterface{
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
    
}
