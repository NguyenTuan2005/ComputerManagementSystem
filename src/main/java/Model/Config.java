package Model;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Config {

    public static  boolean  createFileExcel(ArrayList<Computer> computers ){

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Computer");

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        Row headerRow = sheet.createRow(0);
        String[] columns = {Computer.PRODUCT_CODE,
                            Computer.NAME ,
                            Computer.QUALITY,
                            Computer.STYLE,
                            Computer.BRAND,
                            Computer.OPERATING_SYSTEM,
                            Computer.CPU,
                            Computer.MEMORY,
                            Computer.RAM,
                            Computer.MADE_IN};

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle); // Thiết lập style cho tiêu đề
        }

        int rowNum = 1 , index=0;
        for(Computer computer :computers){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(computer.getProductCode());
            row.createCell(1).setCellValue(computer.getName());
            row.createCell(2).setCellValue(computer.getQuality());
            row.createCell(3).setCellValue(computer.getStyle());
            row.createCell(4).setCellValue(computer.getBrand());
            row.createCell(5).setCellValue(computer.getOperatingSystem());
            row.createCell(6).setCellValue(computer.getCpu());
            row.createCell(7).setCellValue(computer.getMemory());
            row.createCell(7).setCellValue(computer.getRam());
            if( index < columns.length)
                sheet.autoSizeColumn(++index);

        }

        // Ghi file Excel ra ổ đĩa
        try (FileOutputStream fileOut = new FileOutputStream("Computers.xlsx")) {
            workbook.write(fileOut);
            workbook.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
