package view.otherComponent;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import model.Product;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartManager {

  public static JFreeChart createBarChart(Map<Product, Long> data) {
    DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
    for (Map.Entry<Product, Long> value : data.entrySet()) {
      barDataset.addValue(value.getValue(), value.getKey().getName(), value.getKey().getName());
    }

    String currentMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    JFreeChart barChart =
        ChartFactory.createBarChart(
            "Chart of the business rating column of " + currentMonth,
            "Products",
            "Sold",
            barDataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false);

    CategoryPlot plot = barChart.getCategoryPlot();

    NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
    yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    return barChart;
  }
}
