package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginFrame extends JFrame {
	LoginPanel loginPanel = new LoginPanel();
	
	LoginFrame(){
		setTitle("Login");
		setSize(600, 600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		add(loginPanel);
		setVisible(true);
	}
	public class LoginPanel extends JPanel{
		JLabel userRole, userName, userpw;
		JTextField userRoleTF, userNameTF, userpwTF;
		JButton login ;
		
		
		LoginPanel(){
			setLayout(new GridLayout(6,1));
			userRole = new JLabel("User Role");
			userName = new JLabel("User Name");
			userpw = new JLabel("Password");
			
			userRoleTF = new JTextField();
			userNameTF = new JTextField();
			userpwTF = new JTextField();

			login = new JButton("Login");
			login.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});


			
			add(userRole);
			add(userRoleTF);
			add(userName);
			add(userNameTF);
			add(userpw);
			add(userpwTF);
			add(login);
		}
		
		
		
		
	}
	
	public static void main(String[] args) {
		new LoginFrame();
	}
}
