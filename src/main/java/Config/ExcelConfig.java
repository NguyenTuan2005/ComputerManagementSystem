package Config;

import Model.Customer;
import Model.Product;
import Model.Supplier;
import dto.CustomerOrderDTO;
import dto.ManagerInforDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelConfig {
  public static <M> ArrayList<M> importFromExcel(File file, Class<M> clazz) {
    ArrayList<M> importedItems = new ArrayList<>();

    try (FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream)) {

      Sheet sheet = workbook.getSheetAt(0);

      for (Row row : sheet) {
        if (row.getRowNum() == 0) continue;

        M item = mapRowToInstance(row, clazz);
        if (item != null) {
          importedItems.add(item);
        }
      }

      JOptionPane.showMessageDialog(null, "Import successful!");

    } catch (IOException | IllegalStateException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(
          null, "Error importing Excel file", "Error", JOptionPane.ERROR_MESSAGE);
    }

    return importedItems;
  }

  /**
   * Exports a list of objects to an Excel file.
   *
   * @param dataList the list of data to export
   * @param fileName the name of the output Excel file
   * @param headers the headers for the Excel sheet
   * @param <M> the type of objects in the dataList
   */
  public static <M> void exportToExcel(ArrayList<M> dataList, String fileName, String[] headers) {
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

  private static <M> M mapRowToInstance(Row row, Class<M> clazz) {
    try {
      Constructor<M> constructor = clazz.getDeclaredConstructor();
      M instance = constructor.newInstance();

      if (instance instanceof Supplier supplier) {
        supplier.setId((int) row.getCell(0).getNumericCellValue());
        supplier.setCompanyName(row.getCell(1).getStringCellValue());
        supplier.setEmail(row.getCell(2).getStringCellValue());
        supplier.setPhoneNumber(row.getCell(3).getStringCellValue());
        supplier.setAddress(row.getCell(4).getStringCellValue());
        supplier.setContractDate(new Date(row.getCell(5).getDateCellValue().getTime()));
      }

      if (instance instanceof Product product) {
        product.setId((int) row.getCell(0).getNumericCellValue());
        product.setSuppliersId((int) row.getCell(1).getNumericCellValue());
        product.setName(row.getCell(2).getStringCellValue());
        product.setQuantity((int) row.getCell(3).getNumericCellValue());
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

      if (instance instanceof Customer customer) {
        customer.setId((int) row.getCell(0).getNumericCellValue());
        customer.setFullName(row.getCell(1).getStringCellValue());
        customer.setEmail(row.getCell(2).getStringCellValue());
        customer.setAddress(row.getCell(3).getStringCellValue());
        customer.setPassword(row.getCell(4).getStringCellValue());
        customer.setAvataImg(row.getCell(5).getStringCellValue());
        customer.setNumberOfPurchased((int) row.getCell(6).getNumericCellValue());
      }
      // Add more `else if` cases here for other types, if needed

      return instance;

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Maps an instance of a class to an Excel row using a pre-defined mapper.
   *
   * @param row the Excel row to populate
   * @param data the instance to map to the row
   * @param <M> the type of the data instance
   */
  @SneakyThrows
  private static <M> void mapInstanceToRow(Row row, M data) {
    if (data.getClass().equals(Product.class)) {
      Product product = (Product) data;
      row.createCell(0).setCellValue(product.getId());
      row.createCell(1).setCellValue(product.getSuppliersId());
      row.createCell(2).setCellValue(product.getName());
      row.createCell(3).setCellValue(product.getQuantity());
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
      row.createCell(5).setCellValue(customer.getAvataImg());
      row.createCell(6).setCellValue(customer.getNumberOfPurchased());
    } else if (data.getClass().equals(CustomerOrderDTO.class)) {
      CustomerOrderDTO orderDTO = (CustomerOrderDTO) data;
      row.createCell(0).setCellValue(orderDTO.getOrderId());
      row.createCell(1).setCellValue(orderDTO.getCustomerId());
      row.createCell(2).setCellValue(orderDTO.getOrderDate());
      row.createCell(3).setCellValue(orderDTO.getShipAddress());
      row.createCell(4).setCellValue(orderDTO.getStatusItem());
      row.createCell(5).setCellValue(orderDTO.getSaler());
      row.createCell(6).setCellValue(orderDTO.getSalerId());
      row.createCell(7).setCellValue(orderDTO.totalCost());
      row.createCell(8).setCellValue(orderDTO.getQuantity());
    } else {
      throw new IllegalArgumentException("Unsupported class type: " + data.getClass().getName());
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
  public static void writeManagersToExcel(List<ManagerInforDTO> managers, String filePath) {

    filePath += (filePath.contains(".xlsx") ? "" : ".xlsx");
    Workbook workbook = new XSSFWorkbook(); // Tạo file Excel mới
    Sheet sheet = workbook.createSheet("Managers"); // Tạo sheet tên "Managers"

    // Tạo hàng tiêu đề
    String[] headers = {
      "Manager ID", "Full Name", "Address", "Birth Day", "Phone Number",
      "Account ID", "Username", "Password", "Email", "Create Date",
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
    for (ManagerInforDTO manager : managers) {
      Row row = sheet.createRow(rowIndex++);
      row.createCell(0).setCellValue(manager.getManagerId());
      row.createCell(1).setCellValue(manager.getFullName());
      row.createCell(2).setCellValue(manager.getAddress());
      row.createCell(3).setCellValue(manager.getBirthDay().toString());
      row.createCell(4).setCellValue(manager.getPhoneNumber());
      row.createCell(5).setCellValue(manager.getAccountId());
      row.createCell(6).setCellValue(manager.getUsername());
      row.createCell(7).setCellValue(manager.getPassword());
      row.createCell(8).setCellValue(manager.getEmail());
      row.createCell(9).setCellValue(manager.getCreateDate().toString());
      row.createCell(10).setCellValue(manager.getAvataImg());
      row.createCell(11).setCellValue(manager.getBlock());
    }

    // Ghi dữ liệu ra file
    try (FileOutputStream fos = new FileOutputStream(filePath)) {
      workbook.write(fos);
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
}
