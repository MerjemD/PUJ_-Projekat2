package views;

import services.userService;

import javax.swing.*;

public class AdminLoginForm extends JFrame {

    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton logInButton;
    private JButton cancelButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    public AdminLoginForm() {
        setTitle("Admin Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(mainPanel);


        logInButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                if (userService.loginAdmin(username, password)) {
                    JOptionPane.showMessageDialog(mainPanel, "Admin login successful!");


                    String theme = userService.getUserTheme(username);


                    new AdminDashboardForm(theme);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Invalid admin credentials!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage());
            }
        });


        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminLoginForm::new);
    }
}
