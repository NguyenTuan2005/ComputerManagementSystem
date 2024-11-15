package view;

import com.toedter.calendar.JCalendar;
import view.OverrideComponent.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        // Tạo một JFrame
        JFrame frame = new JFrame("JFrame with JPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600); // Kích thước của JFrame
        frame.setLocationRelativeTo(null);
        // Tạo một JPanel
        ModifyManager panel = new ModifyManager();

        // Thêm JPanel vào JFrame
        frame.add(panel);

        // Hiển thị JFrame
        frame.setVisible(true);
    }
    private static  JButton createShowPasswdButton(JPasswordField passwordField) {
        JButton toggleButton = new JButton();
        toggleButton.setBackground(Style.LIGHT_BlUE);
        toggleButton.setFocusPainted(false);
        toggleButton.setFocusable(false);
        toggleButton.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
        ImageIcon showPasswd = new ImageIcon("src/main/java/Icon/showPasswd_Icon.png");
        ImageIcon hidePasswd = new ImageIcon("src/main/java/Icon/hidePasswd_Icon.png");

        toggleButton.setIcon(showPasswd);
        toggleButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                toggleButton.setBackground(new Color(130, 180, 230));
            }

            public void mouseExited(MouseEvent evt) {
                toggleButton.setBackground(Style.LIGHT_BlUE);
            }
        });

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField.getEchoChar() != '\u0000') {
                    passwordField.setEchoChar('\u0000');
                    toggleButton.setIcon(hidePasswd);
                } else {
                    // Ẩn mật khẩu
                    passwordField.setEchoChar('*');
                    toggleButton.setIcon(showPasswd);
                }
            }
        });
        return toggleButton;
    }


    static class ModifyManager extends JPanel {
        ChangeInfo changeInfo;
        Avatar avatar;
        ModifyManager() {
            setLayout(new BorderLayout());
            changeInfo = new ChangeInfo();
            avatar = new Avatar();
            add(changeInfo,BorderLayout.CENTER);
            add(avatar,BorderLayout.SOUTH);
        }

        class ChangeInfo extends JPanel {
            LeftPn rightPn;
            RightPn leftPn;
            ChangeInfo() {
                setLayout(new GridLayout(1, 2));
                rightPn = new LeftPn();
                leftPn = new RightPn();
                add(rightPn);
                add(leftPn);
            }

            class LeftPn extends JPanel {
                LeftPn() {
                    setLayout(new GridBagLayout());
                    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,2),"Personal Information"));

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);  // Thiết lập khoảng cách giữa các thành phần

                    // Khởi tạo các thành phần giao diện
                    JLabel lblFullName = new JLabel("Full Name:");
                    JTextField txtFullName = new JTextField(15);

                    JLabel lblAddress = new JLabel("Address:");
                    JTextField txtAddress = new JTextField(15);

                    JLabel lblBirthday = new JLabel("Birthday:");
                    JTextField txtBirthday = new JTextField(15);
                    JButton btnCalendar = new JButton("Select Date");
                    txtBirthday.setEditable(false);

                    // Tạo JDialog chứa JCalendar
                    JDialog calendarDialog = new JDialog((Frame) null, "Select Date", true);
                    calendarDialog.setSize(400, 400);
                    calendarDialog.setLayout(new BorderLayout());
                    calendarDialog.setLocation(250, 200);
                    JCalendar calendar = new JCalendar();
                    calendar.setBackground(Color.WHITE);
                    calendar.setMaxSelectableDate(new java.util.Date());
                    calendarDialog.add(calendar, BorderLayout.CENTER);
                    JButton btnSelect = new JButton("Select");
                    calendarDialog.add(btnSelect, BorderLayout.SOUTH);

                    // Sự kiện khi nhấn nút chọn ngày
                    btnCalendar.addActionListener(e -> calendarDialog.setVisible(true));
                    btnSelect.addActionListener(e -> {
                        Date selectedDate = calendar.getDate();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        txtBirthday.setText(dateFormat.format(selectedDate));
                        calendarDialog.setVisible(false);
                    });

                    JLabel lblPhoneNumber = new JLabel("Phone Number:");
                    JTextField txtPhoneNumber = new JTextField(15);

                    // Cài đặt GridBagConstraints cho các thành phần
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.anchor = GridBagConstraints.WEST;
                    add(lblFullName, gbc);

                    gbc.gridx = 1;
                    add(txtFullName, gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    add(lblAddress, gbc);

                    gbc.gridx = 1;
                    add(txtAddress, gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    add(lblBirthday, gbc);

                    // Sử dụng một JPanel để chứa cả TextField và Button
                    JPanel birthdayPanel = new JPanel(new BorderLayout());
                    birthdayPanel.add(txtBirthday, BorderLayout.CENTER);
                    birthdayPanel.add(btnCalendar, BorderLayout.EAST);

                    gbc.gridx = 1;
                    add(birthdayPanel, gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 3;
                    add(lblPhoneNumber, gbc);

                    gbc.gridx = 1;
                    add(txtPhoneNumber, gbc);
                }

            }
            class RightPn extends JPanel {
                JTextField usernameField,emailField;
                JPasswordField passwordField;
                RightPn() {
                    setLayout(new GridBagLayout());
                    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,2),"Account Information"));
                    GridBagConstraints gbc = new GridBagConstraints();

                    // Cài đặt vị trí của các component trong GridBagLayout
                    gbc.fill = GridBagConstraints.HORIZONTAL;

                    // Cột 1 (JLabel)
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    add(new JLabel("Username:"), gbc);

                    gbc.gridy = 1;
                    add(new JLabel("Password:"), gbc);

                    gbc.gridy = 2;
                    add(new JLabel("Email:"), gbc);

                    // Cột 2 (JTextField, JPasswordField, JTextField)
                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    usernameField = new JTextField(15);
                    add(usernameField, gbc);

                    gbc.gridy = 1;
                    passwordField = new JPasswordField(15);
                    add(passwordField, gbc);

                    gbc.gridy = 2;
                    emailField = new JTextField(15);
                    add(emailField, gbc);

                    // Cột 3 (Button to show/hide password)
                    gbc.gridx = 2;
                    gbc.gridy = 1;
                    JButton togglePasswordButton = createShowPasswdButton(passwordField);

                    add(togglePasswordButton, gbc);
                }
            }


        }


        class Avatar extends JPanel {
            CustomButton importImage, undoBt;
            JLabel label;
            Avatar() {
                setLayout(new BorderLayout());

                label = new JLabel("Drop your image here",SwingConstants.CENTER);
                label.setBackground(Color.WHITE);
                label.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,2));
                label.setPreferredSize(new Dimension(400,300));

                JPanel importImagePn = new JPanel();
                importImage = new CustomButton("Import");
                importImage.setDrawBorder(false);
                importImage.setPreferredSize(new Dimension(100, 40));

                undoBt = new CustomButton("Undo");
                undoBt.setPreferredSize(new Dimension(100, 40));
                undoBt.setDrawBorder(false);
                importImagePn.add(importImage);
                importImagePn.add(undoBt);

                add(label, BorderLayout.CENTER);
                add(importImagePn, BorderLayout.SOUTH);
            }

        }

    }
}