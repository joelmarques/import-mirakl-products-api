package com.mirakl.product.api.model;

public interface Seller {
	
	Integer getId();
	String getName();
	Integer getImportId();
	
	default String getFileName() {
		
		return getId() + "-" + getImportId() + "-" + getName().replaceAll(" ", "") + ".csv";
	}
}