package com.mirakl.product.api.download;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mirakl.product.api.constants.Constants;

@Component
public class ProductDownloadClient {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${marketplace.product.import.api}")
	private String marketplaceProductImportApi;
	
	@Value("${authorization.key}")
	private String authorizationKey;
	
	@Value("${authorization.value}")
	private String authorizationValue;
	
	public String importByImportId(final Integer importId) {
		
		HttpEntity requestEntity = getRequestEntity();
        
    	ResponseEntity<String> response = restTemplate.exchange(getUrl(importId), HttpMethod.GET, requestEntity, String.class);
    	
		return response.getBody();		
	}
	
	private String getUrl(final Integer importId) {
		return marketplaceProductImportApi.replace(Constants.IMPORT_ID, importId.toString());
	}
	
	private HttpEntity getRequestEntity() {
		
		List<MediaType> accept = new ArrayList<MediaType>();
		accept.add(MediaType.ALL);
        HttpHeaders headers = new HttpHeaders();
        headers.add(authorizationKey, authorizationValue);
        headers.setAccept(accept);
        HttpEntity<String[]> requestEntity = new HttpEntity<String[]>(headers);
        
        return requestEntity;
	}

}