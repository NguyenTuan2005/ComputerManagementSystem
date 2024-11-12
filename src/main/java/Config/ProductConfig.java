package Config;

import Model.Product;
import dao.ProductDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
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
