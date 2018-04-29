package com.mirakl.product.api.download;

import java.io.File;
import java.io.FileWriter;
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
	    
	    final File importedDir = new File(Constants.IMPORTED_DIR);
		importedDir.mkdirs();
		
		for (Seller seller : sellers) {
			
			try {
		
				System.out.println("Importando produtos para o seller..." + seller.getName() + " SellerId: " + seller.getId() + " ImportId: " + seller.getImportId());
				
				String csvFile = productDownloadClient.importByImportId(seller.getImportId());
				
				FileWriter fw = new FileWriter(new File(importedDir, seller.getFileName()));
				fw.write(csvFile);
				fw.close();
				
				System.out.println("Importado com Sucesso!");
			} catch (Exception e) {
				System.out.println("Erro na importacao! " + seller.getImportId());
				e.printStackTrace();
			}
		
		}
	}
	
	public void setSellers(List<Seller> sellers) {
		this.sellers = sellers;
	}

}
