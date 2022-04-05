package com.florin.restaurant.menu;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MenuImporter {

    public List<Menu> excelImport(MultipartFile file) {
         List<Menu> menuList = new ArrayList<>();
         int id;
         String name = "";
         String description= "";
         String url= "";
         double price= 0.00;
        String filePath="C:\\Users\\ffilip\\OneDrive - ENDAVA\\Desktop\\restaurant\\restaurant\\web\\src\\main\\resources\\static";
        long start = System.currentTimeMillis();
//FileInputStream
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();
            rowIterator.next();

            while(rowIterator.hasNext()){
                Row nextRow = rowIterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                while(cellIterator.hasNext()){
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex){
                        case 0 :
                            name = nextCell.getStringCellValue();
                            break;
                        case 1:
                            description = nextCell.getStringCellValue();
                            break;
                        case 2:
                            url = nextCell.getStringCellValue();
                            break;
                        case 3:
                            price = nextCell.getNumericCellValue();
                            break;
                            default: break;
                    }
                }
                menuList.add(Menu.builder()
                        .name(name)
                        .description(description)
                        .url(url)
                        .price(price).build());
            }
workbook.close();
            long end = System.currentTimeMillis();
            System.out.printf("Import done in %d ms\n", (end-start));
        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(menuList);
return menuList;
    }
}
