package view.otherComponent;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import lombok.Getter;

@Getter
public class DragDropImageApp extends JFrame {
  private JPanel imagePanel;
  private List<String> imagePaths;
  private final String destinationFolder = "src/main/java/img/";

  public DragDropImageApp() {
    setTitle("Drag and Drop Images");
    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Tạo thư mục nếu chưa tồn tại
    File folder = new File(destinationFolder);
    if (!folder.exists()) {
      folder.mkdirs();
    }

    // Khởi tạo danh sách để lưu đường dẫn hình ảnh
    imagePaths = new ArrayList<>();

    // Tạo panel để hiển thị hình ảnh
    imagePanel = new JPanel();
    imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
    imagePanel.setBackground(Color.LIGHT_GRAY);

    // Bọc imagePanel trong JScrollPane
    JScrollPane scrollPane = new JScrollPane(imagePanel);
    add(scrollPane);

    // Kích hoạt kéo thả
    new DropTarget(
        imagePanel,
        new DropTargetListener() {
          @Override
          public void dragEnter(DropTargetDragEvent dtde) {}

          @Override
          public void dragOver(DropTargetDragEvent dtde) {}

          @Override
          public void dropActionChanged(DropTargetDragEvent dtde) {}

          @Override
          public void dragExit(DropTargetEvent dte) {}

          @Override
          public void drop(DropTargetDropEvent dtde) {
            try {
              if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                java.util.List<File> files =
                    (java.util.List<File>)
                        dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                for (File file : files) {
                  saveAndDisplayImage(file);
                }

                dtde.dropComplete(true);
              } else {
                dtde.rejectDrop();
              }
            } catch (Exception ex) {
              ex.printStackTrace();
            }
          }
        });

    setVisible(true);
  }

  // Phương thức lưu tệp và hiển thị hình ảnh
  private void saveAndDisplayImage(File file) {
    try {
      // Xác định tệp đích trong thư mục chỉ định
      File destinationFile = new File(destinationFolder, file.getName());

      // Sao chép tệp vào thư mục đích
      Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

      // Lưu đường dẫn của hình ảnh vào danh sách
      var contextPath = "src/main/java/img/" + file.getName();
      imagePaths.add(contextPath);

      // Tạo ImageIcon từ tệp đích
      ImageIcon imageIcon = new ImageIcon(destinationFile.getAbsolutePath());
      // Resize ảnh nếu quá lớn
      Image scaledImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
      JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

      // Thêm JLabel vào panel
      imagePanel.add(imageLabel);
      imagePanel.revalidate(); // Cập nhật giao diện
      imagePanel.repaint();
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(this, "Error saving file: " + file.getName());
    }
  }

  // Phương thức in danh sách đường dẫn hình ảnh
  public void printImagePaths() {
    System.out.println("List of image paths:");
    for (String path : imagePaths) {
      System.out.println(path);
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          DragDropImageApp app = new DragDropImageApp();

          // Thêm nút để in danh sách đường dẫn hình ảnh
          JButton printButton = new JButton("Print Image Paths");
          printButton.addActionListener(e -> app.printImagePaths());
          app.add(printButton, BorderLayout.SOUTH);
        });
  }
}
