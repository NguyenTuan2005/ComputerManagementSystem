package config;


import entity.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelConfig {
    /**
     * Exports a list of objects to an Excel file.
     *
     * @param dataList the list of data to export
     * @param fileName the name of the output Excel file
     * @param headers  the headers for the Excel sheet
     * @param <M>      the type of objects in the dataList
     */
    public static <M> void exportToExcel(List<M> dataList, String fileName, String[] headers) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        // Create headers
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderCellStyle(workbook));
        }

        int rowNum = 1;
        for (M data : dataList) {
            Row row = sheet.createRow(rowNum++);
            mapInstanceToRow(row, data);
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

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

    /**
     * Maps an instance of a class to an Excel row using a pre-defined mapper.
     *
     * @param row  the Excel row to populate
     * @param data the instance to map to the row
     * @param <M>  the type of the data instance
     */
    private static <M> void mapInstanceToRow(Row row, M data) {
        try {
            if (data.getClass().equals(Product.class)) {
                Product product = (Product) data;
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getSupplier().getId());
                row.createCell(2).setCellValue(product.getName());
                row.createCell(3).setCellValue(product.getQuantity());
                row.createCell(4).setCellValue(product.getPrice());
                row.createCell(5).setCellValue(product.getType());
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
            } else if (data.getClass().equals(Supplier.class)) {
                Supplier supplier = (Supplier) data;
                row.createCell(0).setCellValue(supplier.getId());
                row.createCell(1).setCellValue(supplier.getCompanyName());
                row.createCell(2).setCellValue(supplier.getEmail());
                row.createCell(3).setCellValue(supplier.getPhoneNumber());
                row.createCell(4).setCellValue(supplier.getAddress());
                row.createCell(5).setCellValue(supplier.getContractDate().toString());
            } else if (data.getClass().equals(Customer.class)) {
                Customer customer = (Customer) data;
                row.createCell(0).setCellValue(customer.getId());
                row.createCell(1).setCellValue(customer.getFullName());
                row.createCell(2).setCellValue(customer.getEmail());
                row.createCell(3).setCellValue(customer.getAddress());
                row.createCell(4).setCellValue(customer.getPassword());
                row.createCell(5).setCellValue(customer.getAvatarImg());
            } else if (data.getClass().equals(Order.class)) {
                Order order = (Order) data;
                row.createCell(0).setCellValue(order.getOrderId());
                row.createCell(1).setCellValue(order.getCustomer().getFullName());
                row.createCell(2).setCellValue(order.getOrderedAt());
                row.createCell(3).setCellValue(order.getShipAddress());
                row.createCell(4).setCellValue(order.getStatus());
                row.createCell(5).setCellValue(order.totalCost());
                row.createCell(6).setCellValue(order.totalQuantity());
            }
            else {
                throw new IllegalArgumentException("Unsupported class type: " + data.getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a cell style for the Excel header.
     *
     * @param workbook the workbook to which the style will be applied
     * @return a cell style for headers
     */
    private static CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

/// code moi
    public static void writeManagersToExcel(List<Manager> managers, String filePath) {

        filePath += (filePath.contains(".xlsx")?"":".xlsx");
        Workbook workbook = new XSSFWorkbook(); // Tạo file Excel mới
        Sheet sheet = workbook.createSheet("Managers"); // Tạo sheet tên "Managers"

        // Tạo hàng tiêu đề
        String[] headers = {
                "Manager ID", "Full Name", "Address", "Birth Day", "Phone Number",
                 "Username", "Password", "Email", "Create Date",
                "Avatar Image", "Block"
        };
        Row headerRow = sheet.createRow(0);

        // Ghi tiêu đề
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderCellStyle(workbook));
        }

        // Ghi dữ liệu từ danh sách managers
        int rowIndex = 1;
        for (var manager : managers) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(manager.getId());
            row.createCell(1).setCellValue(manager.getFullName());
            row.createCell(2).setCellValue(manager.getAddress());
            row.createCell(3).setCellValue(manager.getDob().toString());
            row.createCell(4).setCellValue(manager.getPhone());
            row.createCell(6).setCellValue(manager.getFullName());
            row.createCell(7).setCellValue(manager.getPassword());
            row.createCell(8).setCellValue(manager.getEmail());
            row.createCell(9).setCellValue(manager.getCreatedAt().toString());
            row.createCell(10).setCellValue(manager.getAvatarImg());
            row.createCell(11).setCellValue(manager.isActive());
        }

        // Ghi dữ liệu ra file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
            System.out.println("File Excel đã được tạo tại: " + filePath);
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

    public static void main(String[] args) {
        // Ví dụ danh sách ManagerInforDTO


        // Ghi danh sách vào file Excel

    }
}
