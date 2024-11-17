package Verifier;

import controller.AccountController;
import view.OverrideComponent.ToastNotification;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;

public class UserNameAccoutVerifier extends InputVerifier {
    private AccountController accountController;
    @Override
    public boolean verify(JComponent input) {
        this.accountController = new AccountController();
        String  name = ((JTextField) input).getText().trim();
        if(name.isEmpty())
        {
            System.out.println("trim");
            input.setBackground(Color.PINK);
            return false;
        }

        var acc = accountController.findByName(name);
        if(acc == null) {
            input.setBackground(Color.white);
            return true;
        }
        System.out.println(acc);
        if (acc.sameUsername(name)){
            System.out.println("ok nef");
            input.setBackground(Color.PINK);
            ToastNotification.showToast("Username used !!!",2500,400,100);
            return false;
        }else {
            input.setBackground(Color.white);
            return true;

        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Verifier JTextField Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new FlowLayout());

            // Tạo JTextField và gán InputVerifier
            JTextField textField = new JTextField(15);
            textField.setInputVerifier(new BirthDayVerifier());

            // Tạo các nút
            JButton submitButton = new JButton("Submit");
            JButton cancelButton = new JButton("Cancel");

            // Thêm ActionListener cho nút Submit
            submitButton.setActionCommand("SUBMIT");
            submitButton.addActionListener(e -> {
                String command = e.getActionCommand();
                if ("SUBMIT".equals(command)) {
                    if (textField.getInputVerifier().verify(textField)) {
                        JOptionPane.showMessageDialog(frame, "Ngày sinh hợp lệ: " + textField.getText());
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ngày sinh không hợp lệ. Hãy sửa lại!");
                    }
                }
            });

            // Thêm ActionListener cho nút Cancel
            cancelButton.setActionCommand("CANCEL");
            cancelButton.addActionListener(e -> {
                // Reset JTextField khi nhấn Cancel
                textField.setText("");
            });

            // Thêm các thành phần vào frame
            frame.add(new JLabel("Nhập ngày sinh (dd/MM/yyyy): "));
            frame.add(textField);
            frame.add(submitButton);
            frame.add(cancelButton);

            frame.setVisible(true);
        });
    }
}
