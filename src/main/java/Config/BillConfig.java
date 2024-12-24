package Config;

import controller.CustomerController;
import dto.CustomerOrderDTO;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BillConfig {

  private ArrayList<CustomerOrderDTO> bills;

  private Map<Integer, List<CustomerOrderDTO>> metadataMap;

  public BillConfig(ArrayList<CustomerOrderDTO> bills) {
    this.bills = bills;
    this.metadataMap = this.getMapByOrderId();
  }

  public String getNewBill() {
    for (Map.Entry<Integer, List<CustomerOrderDTO>> data : this.metadataMap.entrySet()) {

      System.out.println(generateBill((ArrayList<CustomerOrderDTO>) data.getValue()));
      System.out.println("\n");
      System.out.println("\n");
      System.out.println("\n");
      System.out.println("\n");
    }
    return "co cai nit";
  }

  public String getBillCurrent() {
    for (Map.Entry<Integer, List<CustomerOrderDTO>> bill : this.metadataMap.entrySet()) {
      return generateBill((ArrayList<CustomerOrderDTO>) bill.getValue());
    }
    return "Not found";
  }

  public static String generateBill(ArrayList<CustomerOrderDTO> orderList) {
    if (orderList == null || orderList.isEmpty()) {
      return "No orders to generate bill.";
    }

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    StringBuilder bill = new StringBuilder();

    int columnWidth = 76;

    String headerLine = createDynamicLine(columnWidth, '=');
    bill.append(headerLine).append("\n");
    bill.append(String.format("%" + (columnWidth / 2) + "s\n", "HÓA ĐƠN BÁN HÀNG"));
    bill.append(headerLine).append("\n");
    bill.append(String.format("Ngày xuất hóa đơn: %-" + columnWidth + "s\n\n", new Date()));

    CustomerOrderDTO firstOrder = orderList.get(0);
    bill.append(
        String.format("Mã khách hàng      : %-" + columnWidth + "d\n", firstOrder.getCustomerId()));
    bill.append(
        String.format(
            "Địa chỉ giao hàng  : %-" + columnWidth + "s\n\n",
            truncateText(firstOrder.getShipAddress(), columnWidth)));

    String productHeader =
        String.format(
            "%-48s %-37s %-28s %-28s", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền");
    bill.append(productHeader).append("\n");
    bill.append(createDynamicLine(columnWidth, '='));
    bill.append("\n");

    double totalAmount = 0;
    int sapce = 50;
    for (CustomerOrderDTO order : orderList) {
      double productTotal = order.getUnitPrice() * order.getQuantity();

      bill.append(
          String.format(
              "%-50s %-" + sapce + "d %-20s %-20s\n",
              truncateText(order.getProductName(), 53),
              order.getQuantity(),
              currencyFormatter.format(order.getUnitPrice()),
              currencyFormatter.format(productTotal)));

      totalAmount += productTotal;
    }

    bill.append("\n").append(createDynamicLine(columnWidth, '='));
    bill.append("\n");
    bill.append(
        String.format(
            "%-" + (columnWidth - String.valueOf(totalAmount).length() * 2) + "s %s\n",
            "TỔNG CỘNG:",
            currencyFormatter.format(totalAmount)));

    bill.append("\nCHI TIẾT SẢN PHẨM:\n");
    bill.append(createDynamicLine(columnWidth, '='));
    bill.append("\n");

    for (CustomerOrderDTO order : orderList) {

      bill.append(
          String.format(
              "Sản phẩm     : %-" + columnWidth + "s\n",
              truncateText(order.getProductName(), columnWidth)));
      bill.append(
          String.format(
              "Hãng         : %-" + columnWidth + "s\n",
              truncateText(order.getProductBrand(), columnWidth)));
      bill.append(
          String.format(
              "Xuất xứ      : %-" + columnWidth + "s\n",
              truncateText(order.getMadeIn(), columnWidth)));
      bill.append(
          String.format(
              "Cấu hình     : CPU %s, RAM %s, Ổ cứng %s\n",
              truncateText(order.getCpu(), 40),
              truncateText(order.getRam(), 40),
              truncateText(order.getDisk(), 40)));
      bill.append(createDynamicLine(columnWidth, '-')).append("\n");
    }

    bill.append("\nTHÔNG TIN NHÂN VIÊN\n");

    bill.append("\n");
    bill.append(
        String.format(
            "Nhân viên bán hàng: %-" + columnWidth + "s\n",
            truncateText(firstOrder.getSaler(), columnWidth)));
    bill.append(
        String.format("Mã nhân viên      : %-" + columnWidth + "d\n", firstOrder.getSalerId()));
    bill.append("\n").append(createDynamicLine(columnWidth, '='));
    bill.append("\n");
    bill.append(String.format("%" + (columnWidth / 2) + "s\n", "CẢM ƠN QUÝ KHÁCH VÀ HẸN GẶP LẠI"));
    bill.append(createDynamicLine(columnWidth, '='));

    return bill.toString();
  }

  private static String createDynamicLine(int length, char lineChar) {
    StringBuilder line = new StringBuilder();
    for (int i = 0; i < length; i++) {
      line.append(lineChar);
    }
    return line.toString();
  }

  private static String truncateText(String text, int maxLength) {
    if (text == null) return "";
    return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
  }

  private ArrayList<CustomerOrderDTO> addToArrayList(CustomerOrderDTO that) {
    ArrayList<CustomerOrderDTO> unitBills = new ArrayList<>();
    unitBills.add(that);
    return unitBills;
  }

  public Map<Integer, List<CustomerOrderDTO>> getMapByOrderId() {
    Comparator<Integer> comparator =
        new Comparator<>() {
          @Override
          public int compare(Integer o1, Integer o2) {
            return o2 - o1;
          }
        };
    TreeMap<Integer, List<CustomerOrderDTO>> map = new TreeMap<>(comparator);
    var m =
        this.bills.stream()
            .collect(Collectors.groupingBy(p -> p.getOrderId(), Collectors.toList()));
    map.putAll(m);
    return map;
  }

  public static void main(String[] args) {
    CustomerController c = new CustomerController();
    BillConfig b = new BillConfig(c.findCustomerOrderById(10));
    b.getMapByOrderId().entrySet().forEach(System.out::println);

    //        System.out.println(b.getNewBill());

  }
}
