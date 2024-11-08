package view;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileChooserExample {
    public static void main(String[] args) {
        String fileName = JOptionPane.showInputDialog(null, "Enter file name excel:", "Input file", JOptionPane.QUESTION_MESSAGE);

        if (fileName != null && !fileName.trim().isEmpty()) {
            // Thêm phần mở rộng .xlsx nếu người dùng không nhập
            if (!fileName.toLowerCase().endsWith(".xlsx")) {
                fileName += ".xlsx";
            }


//            List<Product> products = ReadExcelToArrayList.readExcelFile(fileName);

//            if (products != null && !products.isEmpty()) {
//                for (Product product : products) {
//                    System.out.println(product);
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "Không tìm thấy dữ liệu trong file hoặc file không tồn tại.", "Thông báo", JOptionPane.WARNING_MESSAGE);
//            }
        } else {
            JOptionPane.showMessageDialog(null, "Tên file không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
//    public static void main(String[] args) {
//        JFrame frame = new JFrame("File Chooser Example");
//        JButton button = new JButton("Chọn file");
//
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//
////                int result = fileChooser.showOpenDialog(frame);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File selectedFile = fileChooser.getSelectedFile();
//                    System.out.println("Đã chọn file: " + selectedFile.getAbsolutePath());
//                } else {
//                    System.out.println("Chọn file đã bị hủy.");
//                }
//            }
//        });
//
//        frame.add(button);
//        frame.setSize(300, 200);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
}

