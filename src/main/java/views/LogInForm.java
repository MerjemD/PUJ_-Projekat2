package views;

        import javax.swing.*;

public class LogInForm extends JFrame {

    private JLabel logInLabel;
    private JPanel mainPanel;
    private JButton logInButton;
    private JButton cancelButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JLabel usernameLabel;

    public LogInForm() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(mainPanel);


        logInButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());


            JOptionPane.showMessageDialog(mainPanel,
                    "Username: " + username + "\nPassword: " + password);
        });


        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LogInForm());
    }
}
