package Verifier;

import view.OverrideComponent.ToastNotification;

import javax.swing.*;
import java.awt.*;

public class EmailVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String email = ((JTextField) input).getText().trim();
        boolean isEmail =email.contains("@") && email.indexOf("@")!= 0 && email.indexOf("@")!= email.length()-1;

        if( !isEmail){
            input.setBackground(Color.PINK);
            ToastNotification.showToast("Email "+ email+ " unvaliable !!",2500,400,100);
            return false;
        }else {
            input.setBackground(Color.WHITE);
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Demo Verifier JTextField");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new FlowLayout());

            JLabel label = new JLabel("Nhập số email ): ");
            JTextField textField = new JTextField(15);

            // Gắn InputVerifier cho JTextField
//            textField.setInputVerifier(new InputVerifier() {
//                @Override
//                public boolean verify(JComponent input) {
//                    String text = ((JTextField) input).getText();
//                    try {
//                        int value = Integer.parseInt(text);
//                        if (value >= 0 && value <= 100) {
//                            input.setBackground(Color.WHITE); // Màu nền bình thường
//                            return true;
//                        } else {
//                            input.setBackground(Color.PINK); // Màu nền khi lỗi
//                            return false;
//                        }
//                    } catch (NumberFormatException e) {
//                        input.setBackground(Color.PINK);
//                        return false;
//                    }
//                }
//            });
            textField.setInputVerifier(new UserNameAccoutVerifier());

            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(e -> {
//                if (textField.getInputVerifier().verify(textField)) {
//                    JOptionPane.showMessageDialog(frame, "Giá trị hợp lệ: " + textField.getText());
//                } else {
//                    JOptionPane.showMessageDialog(frame, "Giá trị không hợp lệ. Hãy sửa lại!");
//                }
//                textField.getInputVerifier();
            });

            frame.add(label);
            frame.add(textField);
            frame.add(submitButton);

            frame.setVisible(true);
        });
    }
}
