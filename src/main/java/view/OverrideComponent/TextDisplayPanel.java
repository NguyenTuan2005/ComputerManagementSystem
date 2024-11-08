package view.OverrideComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TextDisplayPanel extends JPanel {
    private JTextArea textArea;
    private static final Color DARK_BLUE = new Color(0, 75, 150);
    private static final Color MEDIUM_BLUE = new Color(51, 153, 255);
    private static final Color LIGHT_BLUE = new Color(235, 245, 255);

    public TextDisplayPanel() {
        // Set up the panel
        setLayout(new BorderLayout(10, 10));
        setBackground(LIGHT_BLUE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create and configure the text area
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Create scroll pane for text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(MEDIUM_BLUE, 2),
                        "Data Display",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Arial", Font.BOLD, 14),
                        DARK_BLUE
                ),
                new EmptyBorder(5, 5, 5, 5)
        ));

        // Add components to panel
        add(scrollPane, BorderLayout.CENTER);

        // Set preferred size
        setPreferredSize(new Dimension(400, 300));
    }

    // Method to append text to the text area
    public void appendText(String text) {
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    // Method to clear the text area
    public void clearText() {
        textArea.setText("");
    }

    // Method to get the text content
    public String getText() {
        return textArea.getText();
    }

    // Method to set the text content
    public void setText(String text) {
        textArea.setText(text);
    }

    // Method to set text area editable/non-editable
    public void setTextEditable(boolean editable) {
        textArea.setEditable(editable);
    }

    // Demo usage of the panel
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Text Display Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            TextDisplayPanel panel = new TextDisplayPanel();

            // Demo adding some text
            panel.appendText("This is a sample text line 1");
            panel.appendText("This is a sample text line 2");
            panel.appendText("This is a sample text line 3");

            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}