package views;

import services.userService;
import javax.swing.*;

public class RegisterForm extends JFrame {

    private JPanel mainPanel;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel themeLabel;
    private JPasswordField passwordField;
    private JComboBox<String> themeComboBox;
    private JButton saveButton;
    private JButton cancelButton;

    public RegisterForm() {
        setTitle("Register New User");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(mainPanel);

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String theme = (String) themeComboBox.getSelectedItem();

            try {
                userService.createUser(username, password, theme, "USER");
                JOptionPane.showMessageDialog(mainPanel, "User registered successfully!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterForm::new);
    }
}
