package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductPanel extends JPanel {
	JLabel product = new JLabel("Sản phẩm");
	JButton addBt, modifyBt, deleteBt,sortAscBt,sortDecBt, exportExcelBt, importExcelBt, findBt;
	JTextField findText;
	JTable table;
	DefaultTableModel model;
	JScrollPane scrollBar;

	ToolPanel toolPanel = new ToolPanel();
	TablePanel tablePanel = new TablePanel();

	public ProductPanel() {
		setLayout(new BorderLayout());
		add(toolPanel, BorderLayout.NORTH);
		add(tablePanel, BorderLayout.CENTER);
	}

	public class ToolPanel extends JPanel {

		public ToolPanel() {
			setLayout(new FlowLayout());
			addBt = new JButton("Thêm");

			modifyBt = new JButton("Sửa");
			deleteBt = new JButton("Xóa");
			exportExcelBt = new JButton("Xuất Excel");
			importExcelBt = new JButton("Nhập Excel");
			findBt = new JButton("Tìm");

			findText = new JTextField();

			findText.setPreferredSize(new Dimension(200, 30));





			add(addBt);
			add(modifyBt);
			add(deleteBt);
			add(exportExcelBt);
			add(importExcelBt);
			add(findBt);
			add(findText);

		}
	}

	public class TablePanel extends JPanel {
		public TablePanel() {
			// tao bang du lieu
			String[] header = { "STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", " Đơn Giá", "Loại Máy", "Thương Hiệu",
					"Hệ Điều Hành", "CPU", "Bộ Nhớ", "RAM", "Xuất Xứ" };
			model = new DefaultTableModel(header, 0);
			table = new JTable(model);
			scrollBar = new JScrollPane(table);
			scrollBar.setPreferredSize(new Dimension(1000, 550));
			this.add(scrollBar);

		}
	}
}
