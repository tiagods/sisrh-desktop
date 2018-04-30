package com.plkrhone.sisrh.util.office;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface OfficeEditorInterface {
	File edit(Map<String,String> change, File arquivo) throws FileNotFoundException, IOException;
	void openFile(File f) throws FileNotFoundException, IOException;
}
