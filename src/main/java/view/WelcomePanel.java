package view;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
	JLabel welcomeLabel;
	public WelcomePanel() {
		setLayout(new BorderLayout());
		
		welcomeLabel= new JLabel("Welcome Manager :)",SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Arial",1,60));
		welcomeLabel.setForeground(new Color(0, 128, 255, 150));
		
		add(welcomeLabel,BorderLayout.CENTER);
		
	
	}

}
