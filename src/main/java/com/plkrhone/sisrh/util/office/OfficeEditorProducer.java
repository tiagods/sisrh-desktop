package com.plkrhone.sisrh.util.office;

public class OfficeEditorProducer{
	public static OfficeEditor newConfig(String file) {
		String s = file.substring(file.lastIndexOf("."));
		switch(s) {
		case ".xls":
			return new XLSEditor();
		case ".doc":
			return new DOCEditor();
		default:
			return null;
		}
		
	}
}
