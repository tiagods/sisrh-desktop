package com.plkrhone.sisrh.util.office;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XLSEditor extends OfficeEditor{
	@SuppressWarnings("resource")
	@Override
	public File edit(Map<String,String> change, File arquivo) throws FileNotFoundException, IOException,ClosedChannelException{
		FileInputStream file = new FileInputStream(arquivo);
		HSSFWorkbook workbook= new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iteratorRow = sheet.rowIterator();
		while(iteratorRow.hasNext()) {
			Row row = iteratorRow.next();
			Iterator<Cell>iterator = row.cellIterator();
			while(iterator.hasNext()) {
				Cell cell = iterator.next();
				String as1 = cell.getStringCellValue();
				for(String key : change.keySet()) {
					if (as1.contains(key)) {
						cell.setCellValue(as1.replace(key,change.get(key)==null?"":change.get(key)));
					}
				}
			}
		}
		file.close();
		String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		File fi = new File(System.getProperty("java.io.tmpdir")+"/"+"file"+dateTime+".xls");  
		FileOutputStream fos = new FileOutputStream(fi);
		HSSFWorkbook workbook2 = workbook;
		workbook2.write(fos);
		fos.close();
		return fi;
	}
	@Override
	public void openFile(File f) throws IOException{
		Desktop.getDesktop().open(f);
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String v1="",v2="",v3="",v4="",v5="",v6="",v7="",v8="",v9="",v10="",v11="",v12="",v13="",v14="",v15="",v16="",v17="",v18="";
		Map<String, String> crono = new HashMap<>();
		crono.put("{anuncio.data_divulgacao}", v1);
		crono.put("{anuncio.data_fim_divulgacao}", v2);
		crono.put("{anuncio.divulgacao}",v3);
		crono.put("{anuncio.data_recrutamento}", v4);
		crono.put("{anuncio.data_fim_recrutamento}", v5);
		crono.put("{anuncio.recrutamento}",v6);
		crono.put("{anuncio.data_agendamento}", v7);
		crono.put("{anuncio.data_fim_agendamento}", v8);
		crono.put("{anuncio.agendamento}",v9);
		crono.put("{anuncio.data_entrevista}", v10);
		crono.put("{anuncio.data_fim_entrevista}", v11);
		crono.put("{anuncio.entrevista}",v12);
		crono.put("{anuncio.data_pre-selecionado}", v13);
		crono.put("{anuncio.data_fim_pre-selecionado}", v14);
		crono.put("{anuncio.pre-selecionado}",v15);
		crono.put("{anuncio.data_retorno}", v16);
		crono.put("{anuncio.data_fim_retorno}", v17);
		crono.put("{anuncio.retorno}",v18);
		crono.put("{data.hoje}", sdf.format(Calendar.getInstance().getTime()));
		try {
			OfficeEditor officeJob = OfficeEditorProducer
					.newConfig(FileOfficeEnum.cronograma_selecao.getDescricao());
			File f = officeJob.edit(crono, new File(FileOfficeEnum.cronograma_selecao.getDescricao()));
			officeJob.openFile(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
