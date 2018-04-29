package com.mirakl.product.api.download;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.mirakl.product.api.constants.Constants;
import com.mirakl.product.api.model.Seller;
import com.mirakl.product.api.service.SellerService;
import com.mirakl.product.api.splitter.SellerThreadSplitter;

@Component
public class DefaultProductDownload implements ProductDownload {
	
	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private SellerThreadSplitter sellerThreadSplitter;
	
	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
    private TaskExecutor taskExecutor;
	
	@Override
	public void download() {
		
		System.out.println("Importando produtos de sellers...");
		
		Collection<Seller> sellers = sellerService.getSellers();
		
		System.out.println("Quantidade de sellers..." + sellers.size());
		
		Map<Integer, List<Seller>> sellersByThread = sellerThreadSplitter.split(new ArrayList<Seller>(sellers), Constants.MAX_PER_THREAD);
		
		for (int line : sellersByThread.keySet()) {
			
			ProductDownloadWorker productDownloadWorker = applicationContext.getBean(ProductDownloadWorker.class);
			productDownloadWorker.setSellers(sellersByThread.get(line));
			
			taskExecutor.execute(productDownloadWorker);
		}
	}

}