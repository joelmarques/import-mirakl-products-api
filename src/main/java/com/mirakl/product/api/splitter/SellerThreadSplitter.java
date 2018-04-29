package com.mirakl.product.api.splitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mirakl.product.api.model.Seller;

@Component
public class SellerThreadSplitter {

	public Map<Integer, List<Seller>> split(List<Seller> allSellers, int maxWorkers) {

		Map<Integer, List<Seller>> result = new HashMap();
		
		int totalItensPerWorker = defineTotalItensByWorker(allSellers, maxWorkers);

		if (totalItensPerWorker == allSellers.size()) {
			result.put(0, allSellers);
		} else {
			result = buildMapPerWorker(allSellers, maxWorkers, totalItensPerWorker);
		}
		
		return result;
	}

	private Map<Integer, List<Seller>> buildMapPerWorker(List<Seller> allSellers, int maxThreads, int totalItensByWorker) {

		Map<Integer, List<Seller>> result = new HashMap<>();
		
		List<Seller> offersForThread = new ArrayList<>();
		
		int startIndex = 0;
		for (int i = 0;i < maxThreads; i++) {
			
			offersForThread.addAll(allSellers.subList(startIndex, startIndex + totalItensByWorker));
			result.put(i, offersForThread);

			startIndex += offersForThread.size();

			if (startIndex < allSellers.size() && i + 1 == maxThreads) {
				offersForThread.addAll(allSellers.subList(startIndex, allSellers.size()));
				result.put(i, offersForThread);
			}
			
			offersForThread = new ArrayList<>();
		}
	
		return result;
	}

	private int defineTotalItensByWorker(List<Seller> offers, int maxWorkers) {
		int result = offers.size() / maxWorkers;
		if (result == 0) {
			result = offers.size();
		}
		return result;
	}
}
