package com.mirakl.product.api.download;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mirakl.product.api.constants.Constants;
import com.mirakl.product.api.model.Seller;

@Component
@Scope("prototype")
public class ProductDownloadWorker implements Runnable {
	
	@Autowired
	private ProductDownloadClient productDownloadClient;
	
	private List<Seller> sellers;
	
	@Override
    public void run() {
		
		String threadName = Thread.currentThread().getName();
	    System.out.println("ThreadName: " + threadName);
	    
	    final File successDir = new File(Constants.IMPORTED_DIR_SUCCESS);
		successDir.mkdirs();
		
		final File errorDir = new File(Constants.IMPORTED_DIR_ERROR);
		errorDir.mkdirs();
		
		for (Seller seller : sellers) {
			
			try {
		
				System.out.println("Importando produtos para o seller..." + seller.getName() + " SellerId: " + seller.getId() + " ImportId: " + seller.getImportId());
				
				String csvFile = productDownloadClient.importByImportId(seller.getImportId());
				
				saveSuccess(seller, successDir, csvFile);
				
				System.out.println("Importado com Sucesso!");
			} catch (Exception e) {
				saveError(seller, errorDir, e);
			}		
		}
	}
	
	private void saveSuccess(Seller seller, File successDir, String csvFile) throws Exception {
		FileWriter fw = new FileWriter(new File(successDir, seller.getFileName()));
		fw.write(new String(csvFile.getBytes(Constants.CHARSET_NAME_CSV_FILE_MIRAKL)));
		fw.close();
	}

	private void saveError(Seller seller, File errorDir, Exception e) {
		
		try {
			FileWriter fw = new FileWriter(new File(errorDir, seller.getFileName()));
			fw.write(getStackTrace(e));
			fw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private String getStackTrace(Throwable thrown) {
	   StringWriter sw = new StringWriter();
	   thrown.printStackTrace(new PrintWriter(sw));
	   return sw.toString();
	}
	
	public void setSellers(List<Seller> sellers) {
		this.sellers = sellers;
	}

}
