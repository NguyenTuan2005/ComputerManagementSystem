package Config;

import Model.Product;
import dao.ProductDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// create undo
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductConfig {

    private  Stack<ArrayList<Product>> undoAction = new Stack<>();

    private int action ;

    public ArrayList<Product> getProductsOnStream(){
        // len -1
        ArrayList<Product> products = undoAction.pop();
        return (ArrayList<Product>) products.stream().filter(
                product -> product.getDeleteRow() == 1
        ).collect(Collectors.toList());
    }

    public static final int DELETE_ROW_ON_TABLE = 2;

    public static final int DELETE_ROW_ON_TABLE_SEARCH  = 1;

    public ProductConfig(Stack<ArrayList<Product>> undoAction) {
        this.undoAction = undoAction;
    }

    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void exportProductsToExcel(ArrayList<Product> products, String filePath) {
        // Create a new workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        // Create header row
        String[] headers = {"ID", "Supplier ID", "Name", "Quality", "Price", "Genre", "Brand", "OS",
                "CPU", "Memory", "RAM", "Made In", "Status", "Disk", "Monitor", "Weight", "Card"};

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderStyle(workbook));
        }

        // Populate the rows with product data
        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getSuppliersId());
            row.createCell(2).setCellValue(product.getName());
            row.createCell(3).setCellValue(product.getQuality());
            row.createCell(4).setCellValue(product.getPrice());
            row.createCell(5).setCellValue(product.getGenre());
            row.createCell(6).setCellValue(product.getBrand());
            row.createCell(7).setCellValue(product.getOperatingSystem());
            row.createCell(8).setCellValue(product.getCpu());
            row.createCell(9).setCellValue(product.getMemory());
            row.createCell(10).setCellValue(product.getRam());
            row.createCell(11).setCellValue(product.getMadeIn());
            row.createCell(12).setCellValue(product.getStatus());
            row.createCell(13).setCellValue(product.getDisk());
            row.createCell(14).setCellValue(product.getMonitor());
            row.createCell(15).setCellValue(product.getWeight());
            row.createCell(16).setCellValue(product.getCard());
        }

        // Auto-size all columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook to a file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
            System.out.println("Excel file created successfully!");
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

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    // import
    public static ArrayList<Product> readProductsFromExcel(String filePath) {
        ArrayList<Product> products = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet contains the data

            // Skip header row (index 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // Read each cell in the row and set the corresponding field in a Product object
                Product product = new Product();
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

                // Add the product to the list
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        for (Product p : readProductsFromExcel("demo.xlsx"))
            System.out.println(p);
    }
}
