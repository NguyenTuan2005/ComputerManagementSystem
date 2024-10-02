package view;

import java.awt.CardLayout;
import javax.swing.*;

public class MainPanel extends JPanel {

	CardLayout cardLayout = new CardLayout();
	WelcomePanel welcomePanel = new WelcomePanel();
	ProductPanel productPanel = new ProductPanel();
	SupplierPanel supplierPanel = new SupplierPanel();
	CustomerPanel customerPanel = new CustomerPanel();
	ImportPanel importPanel = new ImportPanel();
	ExportPanel exportPanel = new ExportPanel();
	AccManagePanel accManagePanel = new AccManagePanel();
	NotificationPanel notificationPanel = new NotificationPanel();

	public MainPanel() {
		setLayout(cardLayout);
		add(welcomePanel, "welcome");
		add(productPanel, "product");
		add(supplierPanel, "supplier");
		add(customerPanel, "customer");
		add(importPanel, "import");
		add(exportPanel, "export");
		add(accManagePanel, "accManage");
		add(notificationPanel, "notification");
		cardLayout.show(this, "welcome");
	}

	public void showPanel(String panelName) {
		cardLayout.show(this, panelName);
	}
}
