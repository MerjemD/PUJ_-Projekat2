package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm extends JFrame {

    private JPanel mainPanel;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel izaberiteTemuLabel;


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


        themeComboBox.addItem("Green");
        themeComboBox.addItem("Blue");
        themeComboBox.addItem("Pink");
        themeComboBox.addItem("Orange");
        themeComboBox.addItem("Dark");
        themeComboBox.addItem("Cyberpunk");


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String theme = (String) themeComboBox.getSelectedItem();


                JOptionPane.showMessageDialog(mainPanel,
                        "User: " + username + "\nPassword: " + password + "\nTheme: " + theme);
            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterForm();
            }
        });
    }
}
