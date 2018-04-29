package com.mirakl.product.api.constants;

import java.io.File;

public final class Constants {
	
	public Constants() {}
	
	public static final String USER_DIR = System.getProperty("user.dir");
	
	public static final String IMPORTED_DIR = USER_DIR + File.separator + "imported";
	
	public static final String XLSX = ".xlsx";
	
	public static final int MAX_PER_THREAD = 100;
	
	public static final String IMPORT_ID = ":IMPORT_ID";
}