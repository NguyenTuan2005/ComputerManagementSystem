package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class YearlyChartDemo extends JFrame {
    private JComboBox<Integer> yearComboBox;
    private JPanel chartPanel;
    private ArrayList<Integer> years;

    public YearlyChartDemo() {
        years = new ArrayList<>();
        // Giả sử bạn có các năm ban đầu
        years.add(2020);
        years.add(2021);
        years.add(2022);

        // Khởi tạo JFrame
        setTitle("Biểu đồ theo năm");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tạo JComboBox và thêm các năm vào ComboBox
        yearComboBox = new JComboBox<>();
        updateYearComboBox();

        // Tạo panel chứa biểu đồ
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(createChartPanel(2020), BorderLayout.CENTER);

        // Tạo nút thêm năm mới (Giả sử thêm năm mới vào ComboBox và biểu đồ)
        JButton addButton = new JButton("Thêm năm mới");
        addButton.addActionListener(e -> addNewYearData());

        // Thêm ComboBox và Button vào giao diện
        JPanel controlPanel = new JPanel();
        controlPanel.add(yearComboBox);
        controlPanel.add(addButton);

        // Thêm các panel vào JFrame
        add(controlPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        // Xử lý sự kiện chọn năm từ ComboBox
        yearComboBox.addActionListener(e -> {
            Integer selectedYear = (Integer) yearComboBox.getSelectedItem();
            if (selectedYear != null) {
                chartPanel.removeAll();
                chartPanel.add(createChartPanel(selectedYear), BorderLayout.CENTER);
                chartPanel.revalidate();
                chartPanel.repaint();
            }
        });
    }

    // Cập nhật ComboBox với danh sách các năm
    private void updateYearComboBox() {
        yearComboBox.removeAllItems();
        for (Integer year : years) {
            yearComboBox.addItem(year);
        }
    }

    // Tạo biểu đồ theo năm
    private ChartPanel createChartPanel(int year) {
        DefaultCategoryDataset dataset = createDataset(year);
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ năm " + year,
                "Tháng", "Số lượng",
                dataset
        );
        return new ChartPanel(chart);
    }

    // Tạo dataset giả lập cho biểu đồ
    private DefaultCategoryDataset createDataset(int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i <= 12; i++) {
            dataset.addValue(i * 10, "Sản phẩm", "Tháng " + i);
        }
        return dataset;
    }

    // Thêm dữ liệu cho năm mới
    private void addNewYearData() {
        int newYear = years.get(years.size() - 1) + 1; // Tăng thêm một năm mới
        years.add(newYear);
        updateYearComboBox();
        yearComboBox.setSelectedItem(newYear);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            YearlyChartDemo demo = new YearlyChartDemo();
            demo.setVisible(true);
        });
    }
}
