package Config;

public class ExcelConfig {

//    public boolean  createFileExcel(ArrayList<Computer> computers ){
//
//        Workbook workbook = new HSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Computer");
//
//        CellStyle headerStyle = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setBold(true);
//        headerStyle.setFont(font);
//        headerStyle.setAlignment(HorizontalAlignment.CENTER);
//        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        headerStyle.setBorderBottom(BorderStyle.THIN);
//        headerStyle.setBorderTop(BorderStyle.THIN);
//        headerStyle.setBorderLeft(BorderStyle.THIN);
//        headerStyle.setBorderRight(BorderStyle.THIN);
//
//        Row headerRow = sheet.createRow(0);
//        String[] columns = {Computer.PRODUCT_CODE,
//                            Computer.NAME ,
//                            Computer.QUALITY,
//                            Computer.PRICE,
//                            Computer.STYLE,
//                            Computer.BRAND,
//                            Computer.OPERATING_SYSTEM,
//                            Computer.CPU,
//                            Computer.MEMORY,
//                            Computer.RAM,
//                            Computer.MADE_IN};
//
//        for (int i = 0; i < columns.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(columns[i]);
//            cell.setCellStyle(headerStyle);
//        }
//
//        int rowNum = 1 , index=0;
//        for(Computer computer :computers){
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(computer.getProductCode());
//            row.createCell(1).setCellValue(computer.getName());
//            row.createCell(2).setCellValue(computer.getQuality());
//            row.createCell(3).setCellValue(computer.getPrice());
//            row.createCell(4).setCellValue(computer.getStyle());
//            row.createCell(5).setCellValue(computer.getBrand());
//            row.createCell(6).setCellValue(computer.getOperatingSystem());
//            row.createCell(7).setCellValue(computer.getCpu());
//            row.createCell(8).setCellValue(computer.getMemory());
//            row.createCell(9).setCellValue(computer.getRam());
//            row.createCell(10).setCellValue(computer.getMadeIn());
//            if( index < columns.length)
//                sheet.autoSizeColumn(++index);
//
//        }
//
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss");
//        String formattedDateTime = now.format(formatter);
//
//        String fileName = "Computer"+formattedDateTime+ ".xlsx";
//        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
//            workbook.write(fileOut);
//            workbook.close();
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//
//    public void readExcelFile(String filePath) {
//
//        try (FileInputStream fis = new FileInputStream(new File(filePath));
//             Workbook workbook = new HSSFWorkbook(fis)) {
//
//            Sheet sheet = workbook.getSheetAt(0); // Đọc sheet đầu tiên
//            Iterator<Row> rowIterator = sheet.iterator();
//
//            rowIterator.next();
//
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                String productCode = row.getCell(0).getStringCellValue();
//                String name = row.getCell(1).getStringCellValue();
//                int quality = (int) row.getCell(2).getNumericCellValue();
//                Long price = (long) row.getCell(3).getNumericCellValue();
//                String style = row.getCell(4).getStringCellValue();
//                String brand = row.getCell(5).getStringCellValue();
//                String operatingSystem = row.getCell(6).getStringCellValue();
//                String cpu = row.getCell(7).getStringCellValue();
//                int memory = (int) row.getCell(8).getNumericCellValue();
//                String ram = row.getCell(9).getStringCellValue();
//                String madeIn = row.getCell(10).getStringCellValue();
//
//                Computer computer = new Computer(productCode, name, quality, price, style, brand, operatingSystem, cpu, memory, ram, madeIn);
//                ComputerManager.computers.add(computer);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }





}
