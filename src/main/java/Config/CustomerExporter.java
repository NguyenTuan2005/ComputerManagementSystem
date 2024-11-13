package Config;

import Model.Customer;
import dao.CustomerDAO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CustomerExporter {

    public static void exportCustomerListToExcel(ArrayList<Customer> customers, String filePath) {
        filePath = filePath + ( filePath.contains(".xlsx") ?"":".xlsx");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Customers");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Full Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Address");
        headerRow.createCell(4).setCellValue("Password");
        headerRow.createCell(5).setCellValue("Avatar Image");
        headerRow.createCell(6).setCellValue("Number of Purchased");

        int rowNum = 1;
        for (Customer customer : customers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(customer.getId());
            row.createCell(1).setCellValue(customer.getFullName());
            row.createCell(2).setCellValue(customer.getEmail());
            row.createCell(3).setCellValue(customer.getAddress());
            row.createCell(4).setCellValue(customer.getPassword());
            row.createCell(5).setCellValue(customer.getAvataImg());
            row.createCell(6).setCellValue(customer.getNumberOfPurchased());
        }

        for (int i = 0; i <= 6; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
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

    public static void writeBillToFile(String content, String filePath) {
        filePath = filePath + (filePath.contains(".txt")? "":".txt" );
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();
//        exportCustomerListToExcel(customerDAO.getAll(),"democustomer");
        writeBillToFile("hahaha","text");

    }
}
