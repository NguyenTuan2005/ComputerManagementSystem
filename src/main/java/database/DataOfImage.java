package database;

import Model.Image;
import Model.Product;
import dao.ImageDAO;
import dao.ProductDAO;

public class DataOfImage {
  public static void main(String[] args) {
    String[] imageUrls = {
      "src/main/java/img/Acer_Aspire_7.jpg",
      "src/main/java/img/Acer_Nitro_5.jpg",
      "src/main/java/img/Acer_predator_helios_300.jpg",
      "src/main/java/img/Acer_Swift_3.jpg",
      "src/main/java/img/Acer_Swift_X.jpg",
      "src/main/java/img/Apple_MacBook_Pro_14_M1.jpg",
      "src/main/java/img/Asus_ROG_Strix_Scar_15.jpg",
      "src/main/java/img/Asus_ROG_Zephyrus_G15.jpg",
      "src/main/java/img/Asus_VivoBook_S15.jpg",
      "src/main/java/img/Asus_TUF_Gaming_A15.jpg",
      "src/main/java/img/Asus_ZenBook_14.jpg",
      "src/main/java/img/Dell_G15_Gaming.jpg",
      "src/main/java/img/Dell_Alienware_M15_R6.jpg",
      "src/main/java/img/Dell_Inspiron_15_7000.jpg",
      "src/main/java/img/Dell_Vostro_5402.jpg",
      "src/main/java/img/Dell_XPS_13.jpg",
      "src/main/java/img/Dell_XPS_15.jpg",
      "src/main/java/img/HP_Elite_Dragonfly.jpg",
      "src/main/java/img/HP_Envy_13.jpg",
      "src/main/java/img/HP_Omen_15.jpg",
      "src/main/java/img/HP_Pavilion_x360.jpg",
      "src/main/java/img/HP_Spectre_X360.jpg",
      "src/main/java/img/HP_ZBook_Firefly_14.jpg",
      "src/main/java/img/Lenovo_Ideapad_5.jpg",
      "src/main/java/img/Lenovo_Legion_5.jpg",
      "src/main/java/img/Surface_Laptop_5.jpg",
      "src/main/java/img/Razer_Blade_15.jpg",
      "src/main/java/img/MSI_Prestige_14.jpg",
      "src/main/java/img/MSI_GF65_Thin.jpg",
      "src/main/java/img/MSI_GE76_Raider.jpg",
      "src/main/java/img/Microsoft_Surface_Pro_9.jpg",
      "src/main/java/img/Microsoft_Surface_Go_3.jpg",
      "src/main/java/img/MacBook_Pro_16_M2_Pro.jpg",
      "src/main/java/img/MacBook_Air_M2_2023.jpg",
      "src/main/java/img/MacBook_Air_M1_2020.jpg",
      "src/main/java/img/LG_Gram_17.jpg",
      "src/main/java/img/Lenovo_Yoga_Slim_7.jpg",
      "src/main/java/img/Lenovo_Yoga_9i.jpg",
      "src/main/java/img/Lenovo_ThinkPad_X1_Carbon_Gen_7.jpg",
      "src/main/java/img/Lenovo_ThinkBook_14s.jpg"
    };

    // Mảng chứa tên máy tính
    String[] laptopNames = new String[imageUrls.length];

    // Mảng chứa alt text dài hơn
    String[] altTexts = new String[imageUrls.length];
    // Tách tên máy tính từ URL
    for (int i = 0; i < imageUrls.length; i++) {
      // Lấy tên máy tính từ URL bằng cách loại bỏ đường dẫn và phần mở rộng
      String fileName =
          imageUrls[i].substring(imageUrls[i].lastIndexOf('/') + 1, imageUrls[i].lastIndexOf('.'));

      // Thay thế dấu gạch dưới bằng khoảng trắng
      laptopNames[i] = fileName.replace('_', ' ');
      // Tạo alt text dài hơn
      altTexts[i] =
          "This is an image of the "
              + laptopNames[i]
              + ". It is a high-performance laptop designed for users who need a powerful and reliable device. "
              + "The "
              + laptopNames[i]
              + " features cutting-edge technology, including fast processors, ample memory, and a sleek design. "
              + "This laptop is perfect for gaming, productivity, and multimedia use, providing an excellent balance of power and portability.";
    }

    Image[] images = new Image[40];
    ProductDAO productDAO = new ProductDAO();
    ImageDAO imageDAO = new ImageDAO();
    int c = 0;
    for (int i = 0; i < images.length; i++) {
      Product p = productDAO.findOneByName(laptopNames[i]);
      if (p != null) {
        int productId = p.getId();
        String laptopName = p.getName();
        images[i] = new Image(productId, imageUrls[i], altTexts[i]);
        imageDAO.save(images[i]);
      }
    }
  }
}
