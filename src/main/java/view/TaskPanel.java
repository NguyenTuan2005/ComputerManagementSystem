package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TaskPanel extends JPanel {
	JLabel role, name;
	JButton productBt, supplierBt, customerBt, stockImportBt, stockExportBt, accountManagementBt, notificationBt, logout , changeInform;

	JPanel componentButton , componentTop,componentBottom;
	GridBagConstraints gbc;
	Dimension buttonSize = new Dimension(230, 20);


	public TaskPanel() {
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		setBackground(Style.BACKGROUND_COLOR);

		// avatar
		componentTop = new JPanel();
		componentTop.setLayout(new BorderLayout());
		componentTop.setBackground(Style.BACKGROUND_COLOR_DIV2);

		role = new JLabel("Manager");
		role.setFont(new Font("Arial",1,25));
		componentTop.add(role, BorderLayout.NORTH);

		role.setHorizontalAlignment(JLabel.CENTER);
		role.setForeground(Style.BACKGROUND_COLOR_DIV2);

		Circle circle = new Circle(22, 22, 70, new Color(211, 211, 211));
		componentTop.add(circle , BorderLayout.CENTER);

		name = new JLabel("Huy Hoang");
		name.setHorizontalAlignment(JLabel.CENTER);
		name.setForeground(Color.GREEN);
		name.setFont(new Font("Arial",1,25));
		componentTop.add(name , BorderLayout.SOUTH);

		gbc.gridx =1;
		gbc.gridy =1;
		gbc.insets = new Insets(-5,10,5,10);
		componentTop.setBorder(BorderFactory.createEmptyBorder());
		add(componentTop,gbc);

		// component button
		componentButton = new JPanel();

		componentButton.setLayout(new GridLayout(0, 1, 0, 30));
		componentButton.setBackground(Style.BACKGROUND_COLOR_DIV2);

		productBt = new JButton("Product");
		productBt.setFocusable(false);
		productBt.setBackground(Color.WHITE);
		productBt.setPreferredSize(buttonSize);
		productBt.setFont(Style.FONT_SIZE);
		productBt.setHorizontalAlignment(SwingConstants.LEFT);
		productBt.setBorderPainted(false);
		componentButton.add(productBt);

		supplierBt = new JButton("Supplier");
		supplierBt.setFocusable(false);
		supplierBt.setBackground(Color.WHITE);
		supplierBt.setPreferredSize(buttonSize);
		supplierBt.setFont(Style.FONT_SIZE);
		supplierBt.setHorizontalAlignment(SwingConstants.LEFT);
		supplierBt.setBorderPainted(false);
		componentButton.add(supplierBt);

		customerBt = new JButton("Customer");
		customerBt.setFocusable(false);
		customerBt.setBackground(Color.WHITE);
		customerBt.setPreferredSize(buttonSize);
		customerBt.setFont(Style.FONT_SIZE);
		customerBt.setHorizontalAlignment(SwingConstants.LEFT);
		customerBt.setBorderPainted(false);
		componentButton.add(customerBt);

		stockImportBt = new JButton("Import");
		stockImportBt.setFocusable(false);
		stockImportBt.setBackground(Color.WHITE);
		stockImportBt.setPreferredSize(buttonSize);
		stockImportBt.setFont(Style.FONT_SIZE);
		stockImportBt.setHorizontalAlignment(SwingConstants.LEFT);
		stockImportBt.setBorderPainted(false);
		componentButton.add(stockImportBt);

		stockExportBt = new JButton("Export");
		stockExportBt.setFocusable(false);
		stockExportBt.setBackground(Color.WHITE);
		stockExportBt.setPreferredSize(buttonSize);
		stockExportBt.setFont(Style.FONT_SIZE);
		stockExportBt.setHorizontalAlignment(SwingConstants.LEFT);
		stockExportBt.setBorderPainted(false);
		componentButton.add(stockExportBt);

		accountManagementBt = new JButton("Account Management");
		accountManagementBt.setFocusable(false);
		accountManagementBt.setBackground(Color.WHITE);
		accountManagementBt.setFont(Style.FONT_SIZE);
		accountManagementBt.setHorizontalAlignment(SwingConstants.LEFT);
		accountManagementBt.setBorderPainted(false);
		componentButton.add(accountManagementBt);

		notificationBt = new JButton("Notification");
		notificationBt.setFocusable(false);
		notificationBt.setBackground(Color.WHITE);
		notificationBt.setPreferredSize(buttonSize);
		notificationBt.setFont(Style.FONT_SIZE);
		notificationBt.setHorizontalAlignment(SwingConstants.LEFT);
		notificationBt.setBorderPainted(false);
		componentButton.add(notificationBt);
		gbc.gridx =1;
		gbc.gridy =2;
		gbc.insets = new Insets(5,0,5,0);
		add(componentButton,gbc);

		// componentBottom
		componentBottom = new JPanel();
		componentBottom.setLayout(new GridLayout(2,1,0,10));
		componentBottom.setBackground(Style.BACKGROUND_COLOR_DIV2);
//		componentBottom.setAlignmentY(BOTTOM_ALIGNMENT);

		logout = new JButton("Log Out");
		logout.setPreferredSize(buttonSize);
		logout.setFocusable(true);
		logout.setBorderPainted(false);
		logout.setBackground(Color.WHITE);
		logout.setFont(Style.FONT_SIZE);
		logout.setHorizontalAlignment(SwingConstants.LEFT);

		changeInform = new JButton("Change Information");
		changeInform.setFocusable(true);
		changeInform.setPreferredSize(buttonSize);
		changeInform.setBorderPainted(false);
		changeInform.setBackground(Color.WHITE);
		changeInform.setFont(Style.FONT_SIZE);
		changeInform.setHorizontalAlignment(SwingConstants.LEFT);
		componentBottom.add(logout);
		componentBottom.add(changeInform);

		gbc.gridx =1;
		gbc.gridy =5;
		gbc.insets = new Insets(80,10,0,10);
		add(componentBottom,gbc);
	}

	// thiết lập ActionListener cho nút "Sản phẩm"
	public void setProductButtonListener(ActionListener listener) {
		productBt.addActionListener(listener);
	}

	// thiết lập ActionListener cho nút "Nhà cung cấp"
	public void setSupplierButtonListener(ActionListener listener) {
		supplierBt.addActionListener(listener);
	}

}
