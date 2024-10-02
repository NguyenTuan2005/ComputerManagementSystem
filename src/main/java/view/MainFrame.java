package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	MainPanel mainPanel = new MainPanel();
	TaskPanel taskPanel = new TaskPanel();

	public MainFrame() {
		setTitle("Login");
		setSize(1200, 650);
		setResizable(false);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Computer Managerment");
		setLayout(new BorderLayout());
		taskPanel.setProductButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.showPanel("product");
				taskPanel.productBt.setBackground(new Color(144,238,144));
				taskPanel.supplierBt.setBackground(Color.WHITE);
			}
		});
		taskPanel.setSupplierButtonListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.showPanel("supplier");
				taskPanel.supplierBt.setBackground(new Color(144,238,144));
				taskPanel.productBt.setBackground(Color.WHITE);
			}
		});
		
		add(taskPanel, BorderLayout.WEST);
		add(mainPanel, BorderLayout.CENTER);

		setVisible(true);

	}

	public static void main(String[] args) {
		new MainFrame();
	}

}
