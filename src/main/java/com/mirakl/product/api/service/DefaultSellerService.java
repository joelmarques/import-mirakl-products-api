package com.mirakl.product.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mirakl.product.api.constants.Constants;
import com.mirakl.product.api.converter.SellerExcelConverter;
import com.mirakl.product.api.model.Seller;

@Service
public class DefaultSellerService implements SellerService {
	
	@Autowired
	private SellerExcelConverter sellerExcelConverter;

	@Override
	public Collection<Seller> getSellers() {
		
		return sellerExcelConverter.convert(getExcel());
	}
	
	private InputStream getExcel() {
		
		try {
		
			File userDir = new File(Constants.USER_DIR);
			
			for (File file : userDir.listFiles()) {
				
				if (file.getName().endsWith(Constants.XLSX)) {
					
					return new FileInputStream(file);
				}
			}
		
		} catch (Exception e) {
			throw new IllegalArgumentException("Error fetching excel file from directory ..." + Constants.USER_DIR);
		}
		
		return null;
	}
}
