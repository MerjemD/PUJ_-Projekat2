package views;

import services.userService;
import utils.ThemeManager;

import javax.swing.*;

public class CreateUserForm extends JFrame {

    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> themeComboBox;
    private JComboBox<String> roleComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel themeLabel;
    private JLabel roleLabel;

    public CreateUserForm(String theme) {
        setTitle("Create New User");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(mainPanel);

        ThemeManager.applyTheme(mainPanel, theme);


        roleComboBox.addItem("USER");
        roleComboBox.addItem("ADMIN");

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String selectedTheme = (String) themeComboBox.getSelectedItem();
            String role = (String) roleComboBox.getSelectedItem();

            if(username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Username and password cannot be empty!");
                return;
            }

            userService.createUser(username, password, selectedTheme, role);
            JOptionPane.showMessageDialog(mainPanel, "User created successfully!");
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public CreateUserForm() {
        this("Default");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreateUserForm("Default"));
    }
}


