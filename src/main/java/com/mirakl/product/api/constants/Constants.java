package com.mirakl.product.api.constants;

import java.io.File;

public final class Constants {
	
	public Constants() {}
	
	public static final String USER_DIR = System.getProperty("user.dir");
	
	public static final String IMPORTED_DIR = USER_DIR + File.separator + "imported" + File.separator;
	
	public static final String IMPORTED_DIR_SUCCESS = IMPORTED_DIR + "success";
	
	public static final String IMPORTED_DIR_ERROR = IMPORTED_DIR + "error";
	
	public static final String CHARSET_NAME_CSV_FILE_MIRAKL = "ISO-8859-1";
	
	public static final String XLSX = ".xlsx";
	
	public static final int MAX_PER_THREAD = 100;
	
	public static final String IMPORT_ID = ":IMPORT_ID";
}