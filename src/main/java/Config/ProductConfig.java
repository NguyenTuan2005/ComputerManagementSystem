package Config;

import Model.Product;
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

    public static void exportToExcel(List<Product> products, String name) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Products");

        String[] headers = {"ID", "Suppliers ID", "Name", "Quality", "Price", "Genre", "Brand", "Operating System",
                "CPU", "Memory", "RAM", "Made In", "Status", "Delete Row"};

        // Tạo hàng đầu tiên làm tiêu đề
        Row headerRow = ((XSSFSheet) sheet).createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            ((Cell) cell).setCellValue(headers[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        // Điền dữ liệu sản phẩm
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
            row.createCell(13).setCellValue(product.getDeleteRow());
        }

        // Tự động điều chỉnh kích thước cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi file Excel
        try (FileOutputStream fileOut = new FileOutputStream(name)) {
            workbook.write(fileOut);
            System.out.println("Ghi thành công dữ liệu vào file Products.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CellStyle getHeaderCellStyle(XSSFWorkbook workbook) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerCellStyle.setFont(font);
        return headerCellStyle;
    }


    public static List<Product> readExcelFile(String filePath) {
        List<Product> products = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Bỏ qua dòng tiêu đề
            if (rowIterator.hasNext()) rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                int id = (int) row.getCell(0).getNumericCellValue();
                int suppliersId = (int) row.getCell(1).getNumericCellValue();
                String name = row.getCell(2).getStringCellValue();
                int quality = (int) row.getCell(3).getNumericCellValue();
                int price = (int) row.getCell(4).getNumericCellValue();
                String genre = row.getCell(5).getStringCellValue();
                String brand = row.getCell(6).getStringCellValue();
                String operatingSystem = row.getCell(7).getStringCellValue();
                String cpu = row.getCell(8).getStringCellValue();
                String memory = row.getCell(9).getStringCellValue();
                String ram = row.getCell(10).getStringCellValue();
                String madeIn = row.getCell(11).getStringCellValue();
                String status = row.getCell(12).getStringCellValue();
                int deleteRow = (int) row.getCell(13).getNumericCellValue();

                Product product = new Product(id, suppliersId, name, quality, price, genre, brand,
                        operatingSystem, cpu, memory, ram, madeIn, status, deleteRow);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }
}
