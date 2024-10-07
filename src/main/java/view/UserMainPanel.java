package view;

import javax.swing.*;
import java.awt.*;

public class UserMainPanel extends JPanel {
    LoginFrame loginFrame;
    CardLayout cardLayout ;
    WelcomePanel welcomePanel;
    ProductCatalogPanel productCatalogPanel;
    NotificationPanel notificationPanel;
    PurchasedPanel purchasedPanel;

    JButton cartBt;

    static final String WELCOME_CONSTRAINT = "welcome";
    static final String PRODUCT_CATALOG_CONSTRAINT = "catalog";
    static final String NOTIFICATION_CONSTRAINT = "notification";
    static final String PURCHASED_CONSTRAINT = "purchased";
    //constructor
    public UserMainPanel(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        cardLayout = new CardLayout();
        welcomePanel = new WelcomePanel();
        productCatalogPanel = new ProductCatalogPanel();
        notificationPanel = new NotificationPanel();
        purchasedPanel = new PurchasedPanel();
        setLayout(cardLayout);
        add(welcomePanel, WELCOME_CONSTRAINT);
        add(productCatalogPanel, PRODUCT_CATALOG_CONSTRAINT);
        add(purchasedPanel, PURCHASED_CONSTRAINT);
        add(notificationPanel, NOTIFICATION_CONSTRAINT);

        cardLayout.show(this, WELCOME_CONSTRAINT);
    }
    public void showPanel(String panelName) {
        cardLayout.show(this, panelName);
    }

    class WelcomePanel extends JPanel {
        JLabel welcomeLabel,userNameForWelcome;

        public WelcomePanel() {
            setLayout(new BorderLayout());

            welcomeLabel = new JLabel("<html><div style='text-align: center;'>Welcome User :O<br>"+ loginFrame.userNameField.getText()+"</div></html>" , SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 60));
            welcomeLabel.setForeground(Style.BACKGROUND_COLOR);

            add(welcomeLabel, BorderLayout.CENTER);

        }
    }
    class ProductCatalogPanel extends JPanel{
        ToolPanel toolPanel;
        public ProductCatalogPanel() {
            setLayout(new BorderLayout());
            toolPanel = new ToolPanel();
            add(toolPanel, BorderLayout.NORTH);
            JLabel lb = new JLabel("Coming soon!");
            add(lb,BorderLayout.CENTER);
        }
        class ToolPanel extends JPanel{
            public ToolPanel() {
                setLayout(new FlowLayout(FlowLayout.CENTER));
                cartBt = new JButton("Cart");
                setStyleButton(cartBt,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,new Dimension(80,80));
                add(cartBt);
            }
        }

    }
     class PurchasedPanel extends JPanel{

        public PurchasedPanel() {
            setLayout(new BorderLayout());
            add(new JLabel("PurchasedPanel is Coming soonnnnnnn!"));
        }
    }

    class NotificationPanel extends JPanel{

        public NotificationPanel() {
            setLayout(new BorderLayout());
            add(new JLabel("Cart is Coming soonnnnnnn!"));
        }
    }
    private static void setStyleButton(JButton that, Color color, Dimension size) {
        that.setBackground(color);
        that.setFont(Style.FONT_SIZE);
        that.setHorizontalAlignment(SwingConstants.LEFT);
        that.setBorderPainted(false);
        that.setForeground(Color.white);
        that.setFocusable(false);
        that.setPreferredSize(size);
    }
}
