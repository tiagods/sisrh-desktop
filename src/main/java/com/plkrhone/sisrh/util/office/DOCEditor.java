package com.plkrhone.sisrh.util.office;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class DOCEditor extends OfficeEditor{
	private HWPFDocument doc;
	@Override
	public File edit(Map<String,String> change, File arquivo) throws FileNotFoundException, IOException{
		FileInputStream fis = new FileInputStream(arquivo);  
		POIFSFileSystem fs = new POIFSFileSystem(fis);  
        doc = new HWPFDocument(fs);
        for(String key : change.keySet()) {
			Range range = doc.getRange();  
        	range.replaceText(key,change.get(key));   
		}
		String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		File fi = new File(System.getProperty("java.io.tmpdir")+"/"+"file"+dateTime+".doc");  
		FileOutputStream fos = new FileOutputStream(fi);
		doc.write(fos);  
		fos.close();
	    fis.close();  
	    return fi;
	}
	@Override
	public void openFile(File f) throws IOException{
		Desktop.getDesktop().open(f);
	}
}
