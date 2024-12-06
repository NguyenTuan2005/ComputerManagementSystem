package Verifier;

import view.OverrideComponent.ToastNotification;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.awt.SystemColor.window;

public class BirthDayVerifier extends InputVerifier {
    private static final String[] DATE_FORMATS = {"yyyy-MM-dd", "dd/MM/yyyy"};
    @Override
    public boolean verify(JComponent input) {
        String birthday = ((JTextField) input).getText().trim();

        for (String format : DATE_FORMATS) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setLenient(false);
            try {
                // Kiểm tra xem ngày có hợp lệ không
                dateFormat.parse(birthday);

                // Bạn có thể kiểm tra thêm logic như không cho phép ngày tương lai:
                if (isFutureDate(birthday, dateFormat)) {
                    input.setBackground(java.awt.Color.PINK);
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    JWindow window = new JWindow();
                    ToastNotification.showToast("SAi roi má", 2500, 50, screenSize.width - window.getWidth() - 10, screenSize.height - window.getHeight() - 50);
                    return false;
                }

                input.setBackground(java.awt.Color.WHITE); // Trả lại màu trắng nếu hợp lệ
                return true;
            } catch (ParseException e) {
                // Lỗi định dạng ngày
                input.setBackground(java.awt.Color.PINK);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                JWindow window = new JWindow();
                ToastNotification.showToast("SAi roi má", 2500, 50, screenSize.width - window.getWidth() - 10, screenSize.height - window.getHeight() - 50);
                return false;
            }
        }
        return false;
    }

    // Hàm kiểm tra nếu ngày nằm trong tương lai
    private boolean isFutureDate(String birthday, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(birthday).after(new java.util.Date());
        } catch (ParseException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BirthDay Verifier Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 150);
            frame.setLayout(new FlowLayout());

            JLabel label = new JLabel("Nhập ngày sinh (dd/MM/yyyy): ");
            JTextField birthdayField = new JTextField(15);
            birthdayField.setInputVerifier(new BirthDayVerifier());

            JButton submitButton = new JButton("Submit");
            submitButton.addActionListener(e -> {
                if (birthdayField.getInputVerifier().verify(birthdayField)) {
                    JOptionPane.showMessageDialog(frame, "Ngày sinh hợp lệ: " + birthdayField.getText());
                } else {
                    JOptionPane.showMessageDialog(frame, "Ngày sinh không hợp lệ. Hãy sửa lại!");
                }
            });

            frame.add(label);
            frame.add(birthdayField);
            frame.add(submitButton);

            frame.setVisible(true);
        });
    }
}
