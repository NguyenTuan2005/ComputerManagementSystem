package Config;

import Model.Product;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class ProductWordExporter {
    public static void createAndExportProductListToWord(List<Product> productList, String filePath) {
        // Create a new blank document
        XWPFDocument document = new XWPFDocument();

        // Create a title paragraph for the document
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Product List");
        titleRun.setBold(true);
        titleRun.setFontSize(20);

        // Create a table with headers for the specified product attributes
        XWPFTable table = document.createTable();

        // Define headers without "Operating System"
        String[] headers = {"Name", "Quantity", "Price", "Genre", "CPU", "Memory", "RAM", "Made In"};

        // Add and style header row
        XWPFTableRow headerRow = table.getRow(0);
        for (int i = 0; i < headers.length; i++) {
            XWPFTableCell headerCell;
            if (i == 0) {
                headerCell = headerRow.getCell(0);
            } else {
                headerCell = headerRow.addNewTableCell();
            }
            XWPFParagraph paragraph = headerCell.getParagraphs().get(0);
            XWPFRun run = paragraph.createRun();
            run.setText(headers[i].toUpperCase());  // Set header to uppercase
            run.setBold(true);  // Bold header text
            run.setFontSize(12);  // Set header font size

            // Set column width for better distribution (adjust as necessary)
            headerCell.getCTTc().addNewTcPr().addNewTcW().setW(2000);

            // Disable text wrapping for all headers
            headerCell.getCTTc().addNewTcPr().addNewNoWrap();
        }

        // Decimal format for price (e.g., 2 decimal places)
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        // Add rows for each product (only selected attributes)
        for (Product product : productList) {
            XWPFTableRow row = table.createRow();
            XWPFTableCell nameCell = row.getCell(0);
            nameCell.setText(product.getName());
            nameCell.getCTTc().addNewTcPr().addNewNoWrap();  // Disable wrap for name cell

            XWPFTableCell quantityCell = row.getCell(1);
            quantityCell.setText(String.valueOf(product.getQuantity()));
            quantityCell.getCTTc().addNewTcPr().addNewNoWrap();  // Disable wrap for quantity cell

            XWPFTableCell priceCell = row.getCell(2);
            String formattedPrice = decimalFormat.format(product.getPrice());
            priceCell.setText(formattedPrice);
            priceCell.getCTTc().addNewTcPr().addNewNoWrap();  // Disable wrap for price cell

            XWPFTableCell genreCell = row.getCell(3);
            genreCell.setText(product.getGenre());
            genreCell.getCTTc().addNewTcPr().addNewNoWrap();  // Disable wrap for genre cell

            XWPFTableCell cpuCell = row.getCell(4);
            cpuCell.setText(product.getCpu());
            cpuCell.getCTTc().addNewTcPr().addNewNoWrap();  // Disable wrap for CPU cell

            XWPFTableCell memoryCell = row.getCell(5);
            memoryCell.setText(product.getMemory());
            memoryCell.getCTTc().addNewTcPr().addNewNoWrap();  // Disable wrap for memory cell

            XWPFTableCell ramCell = row.getCell(6);
            ramCell.setText(product.getRam());
            ramCell.getCTTc().addNewTcPr().addNewNoWrap();  // Disable wrap for RAM cell

            XWPFTableCell madeInCell = row.getCell(7);
            madeInCell.setText(product.getMadeIn());
            madeInCell.getCTTc().addNewTcPr().addNewNoWrap();  // Disable wrap for Made In cell
        }

        // Save the document as a new file
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            document.write(out);
            System.out.println("New Word file created successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


