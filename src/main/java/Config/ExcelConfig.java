package Config;

import Model.Product;
import Model.Supplier;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class ExcelConfig {

    // Define ExcelRowMapper Functional Interface to allow custom mappings for any class
    @FunctionalInterface
    public interface ExcelRowMapper<M> {
        void mapRow(Row row, M data);
    }

    public static <M> ArrayList<M> importFromExcel(File file, Class<M> clazz) {
        ArrayList<M> importedItems = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Loop through rows and create instances of the generic type M
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                // Create an instance of M and set its properties based on the row data
                M item = mapRowToInstance(row, clazz);
                if (item != null) {
                    importedItems.add(item);
                }
            }

            JOptionPane.showMessageDialog(null, "Import successful!");

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error importing Excel file", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return importedItems;
    }

    public static <M> void exportToExcel(ArrayList<M> dataList, String fileName, String[] headers, ExcelRowMapper<M> rowMapper) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        // Create headers
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderCellStyle(workbook));
        }

        // Fill data rows
        int rowNum = 1;
        for (M data : dataList) {
            Row row = sheet.createRow(rowNum++);
            rowMapper.mapRow(row, data);
        }

        // Autosize columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Save workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
            System.out.println("Excel file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static <M> M mapRowToInstance(Row row, Class<M> clazz) {
        try {
            Constructor<M> constructor = clazz.getDeclaredConstructor();
            M instance = constructor.newInstance();

            // Here, you'll need to set properties on the instance based on row cell data.
            // For example, if M is Products, set product properties like this:
            if (instance instanceof Supplier supplier) {
                supplier.setId((int) row.getCell(0).getNumericCellValue());
                supplier.setCompanyName(row.getCell(1).getStringCellValue());
                supplier.setEmail(row.getCell(2).getStringCellValue());
                supplier.setPhoneNumber(row.getCell(3).getStringCellValue());
                supplier.setAddress(row.getCell(4).getStringCellValue());
                supplier.setContractDate(row.getCell(5).getDateCellValue());
            }

            if (instance instanceof Product product) {
                product.setId((int) row.getCell(0).getNumericCellValue());
                product.setSuppliersId((int) row.getCell(1).getNumericCellValue());
                product.setName(row.getCell(2).getStringCellValue());
                product.setQuality((int) row.getCell(3).getNumericCellValue());
                product.setPrice((int) row.getCell(4).getNumericCellValue());
                product.setGenre(row.getCell(5).getStringCellValue());
                product.setBrand(row.getCell(6).getStringCellValue());
                product.setOperatingSystem(row.getCell(7).getStringCellValue());
                product.setCpu(row.getCell(8).getStringCellValue());
                product.setMemory(row.getCell(9).getStringCellValue());
                product.setRam(row.getCell(10).getStringCellValue());
                product.setMadeIn(row.getCell(11).getStringCellValue());
                product.setStatus(row.getCell(12).getStringCellValue());
                product.setDisk(row.getCell(13).getStringCellValue());
                product.setMonitor(row.getCell(14).getStringCellValue());
                product.setWeight(row.getCell(15).getStringCellValue());
                product.setCard(row.getCell(16).getStringCellValue());
            }
            // Add more `else if` cases here for other types, if needed

            return instance;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to create a header cell style
    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }
}
