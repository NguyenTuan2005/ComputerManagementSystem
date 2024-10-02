package view;

import javax.swing.*;
import java.awt.*;

public class SupplierPanel extends JPanel {
	JLabel supplierLabel =new JLabel("Quản lý nhà cung cấp");
    public SupplierPanel() {
        setLayout(new BorderLayout());
        supplierLabel.setBackground(Color.GRAY);
        
        add(supplierLabel, BorderLayout.CENTER);
    }
}
