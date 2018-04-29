package com.mirakl.product.api.converter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.mirakl.product.api.model.Seller;
import com.mirakl.product.api.model.SellerModel;

@Component
public class SellerExcelConverter implements Converter<InputStream, Collection<Seller>> {

	@Override
	public Collection<Seller> convert(InputStream excel) {
		
		if (Objects.isNull(excel)) {
			return Collections.emptyList();
		}
		
		try {

			Collection<Seller> sellers = new ArrayList<Seller>();
			
            XSSFWorkbook workbook = new XSSFWorkbook(excel);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            
            XSSFRow header = (XSSFRow) rows.next();
            
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                
                SellerModel seller = new SellerModel();
                
                while (cells.hasNext()) {
                    XSSFCell cell = (XSSFCell) cells.next();
                    int z = cell.getColumnIndex();

                    switch (z) {
                        case 0:
                            seller.setId(Double.valueOf(cell.getNumericCellValue()).intValue());
                            break;
                        case 1:
                        	seller.setName(cell.getStringCellValue());
                        	break;
                        case 2:
                        	seller.setImportId(Double.valueOf(cell.getNumericCellValue()).intValue());
                        	break;
                    }
                }
                
                sellers.add(seller);
            }
            
            workbook.close();
            return sellers;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
	}

}
