package views;

import services.userService;
import utils.ThemeManager;
import javax.swing.*;

public class LogInForm extends JFrame {

    private JPanel mainPanel;
    private JButton logInButton;
    private JButton cancelButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel adminLinkLabel;
    private JLabel logInLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    public LogInForm() {

        setContentPane(mainPanel);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        adminLinkLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        adminLinkLabel.setForeground(java.awt.Color.BLUE);
        adminLinkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new AdminLoginForm();
            }
        });


        logInButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            userService userService = new userService();

            try {
                if (userService.loginAdmin(username, password)) {
                    JOptionPane.showMessageDialog(mainPanel, "Admin login successful!");
                    String theme = userService.getUserTheme(username);
                    ThemeManager.applyTheme(mainPanel, theme);
                    new MainMenuForm(username, "ADMIN", theme);
                    dispose();
                } else if (userService.loginUser(username, password)) {
                    JOptionPane.showMessageDialog(mainPanel, "User login successful!");
                    String theme = userService.getUserTheme(username);
                    ThemeManager.applyTheme(mainPanel, theme);

                    MainMenuForm menu = new MainMenuForm(username, "USER", theme);
                    menu.setVisible(true);
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Invalid username or password!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage());
            }
        });


        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogInForm::new);
    }
}

