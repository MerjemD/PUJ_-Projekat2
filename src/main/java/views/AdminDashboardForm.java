package views;

import org.bson.Document;
import services.userService;
import utils.ThemeManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AdminDashboardForm extends JFrame {

    private JPanel mainPanel;
    private JPanel usersPanel;
    private JPanel adminActionsPanel;
    private JTable usersTable;
    private JButton createUserButton;
    private JButton deleteUserButton;
    private JButton refreshStatsButton;
    private JButton changePasswordButton;
    private JButton exportDataButton;
    private String theme;
    private JTable trackerStatsTable;

    public AdminDashboardForm(String theme) {
        this.theme = theme;

        setTitle("Admin Dashboard");
        setContentPane(mainPanel);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ThemeManager.applyTheme(mainPanel, theme);

        loadUsersTable();

        createUserButton.addActionListener(e -> new CreateUserForm(theme));
        deleteUserButton.addActionListener(e -> deleteSelectedUser());
        refreshStatsButton.addActionListener(e -> loadTrackerStats());
        changePasswordButton.addActionListener(e -> changeAdminPassword());
        exportDataButton.addActionListener(e -> exportData());

        setVisible(true);
    }

    private void loadUsersTable() {
        List<Document> users = userService.getAllUsers();
        String[] columns = {"Username", "Role", "Theme"};
        Object[][] data = new Object[users.size()][3];

        for (int i = 0; i < users.size(); i++) {
            Document u = users.get(i);
            data[i][0] = u.getString("username");
            data[i][1] = u.getString("role");
            data[i][2] = u.getString("theme");
        }

        usersTable.setModel(new DefaultTableModel(data, columns));
    }

    private void deleteSelectedUser() {
        int row = usersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selektuj korisnika prvo!");
            return;
        }
        String username = usersTable.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Jeste li sigurni da želite obrisati korisnika " + username + "?",
                "Potvrda", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userService.deleteUser(username);
            loadUsersTable();
        }
    }

    private int getCount(String collection) {
        return (int) database.mongoDBConnection
                .getDatabase()
                .getCollection(collection)
                .countDocuments();
    }

    private void showChart(int sleep, int water, int screen, int finance) {
        org.jfree.data.category.DefaultCategoryDataset dataset =
                new org.jfree.data.category.DefaultCategoryDataset();

        dataset.addValue(sleep, "Records", "Sleep");
        dataset.addValue(water, "Records", "Water");
        dataset.addValue(screen, "Records", "Screen");
        dataset.addValue(finance, "Records", "Finance");

        org.jfree.chart.JFreeChart chart =
                org.jfree.chart.ChartFactory.createBarChart(
                        "Global Tracker Statistics",
                        "Tracker",
                        "Number of Records",
                        dataset
                );

        org.jfree.chart.ChartPanel panel = new org.jfree.chart.ChartPanel(chart);

        JFrame frame = new JFrame("Global Tracker Statistics");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void loadTrackerStats() {
        int sleep = getCount("sleep");
        int water = getCount("water");
        int screen = getCount("screen");
        int finance = getCount("transactions");

        String[] columns = {"Tracker", "Number of Records"};
        Object[][] data = {
                {"Sleep", sleep},
                {"Water", water},
                {"Screen", screen},
                {"Finance", finance}
        };

        trackerStatsTable.setModel(new DefaultTableModel(data, columns));
        showChart(sleep, water, screen, finance);
    }

    private void changeAdminPassword() {
        String newPassword = JOptionPane.showInputDialog(
                this,
                "Unesite novu lozinku za admina"
        );
        if (newPassword == null || newPassword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lozinka ne može biti prazna!");
            return;
        }
        userService.changeAdminPassword(newPassword);
        JOptionPane.showMessageDialog(this, "Lozinka uspješno promijenjena!");
    }

    private void exportData() {
        try {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".pdf")) path += ".pdf";

            org.apache.pdfbox.pdmodel.PDDocument document = new org.apache.pdfbox.pdmodel.PDDocument();
            org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage();
            document.addPage(page);

            org.apache.pdfbox.pdmodel.PDPageContentStream content =
                    new org.apache.pdfbox.pdmodel.PDPageContentStream(document, page);

            content.beginText();
            content.setFont(org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_BOLD, 16);
            content.newLineAtOffset(50, 750);
            content.showText("Life Management System - Global Export");
            content.endText();

            int y = 720;

            y = writeCount(content, "Sleep records", "sleep", y);
            y = writeCount(content, "Water records", "water", y);
            y = writeCount(content, "Screen records", "screen", y);
            y = writeCount(content, "Finance records", "transactions", y);

            content.close();
            document.save(path);
            document.close();

            JOptionPane.showMessageDialog(this, "PDF uspješno exportovan!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška pri exportu PDF-a!");
        }
    }

    private int writeCount(org.apache.pdfbox.pdmodel.PDPageContentStream content,
                           String label, String collection, int y) throws Exception {

        long count = database.mongoDBConnection
                .getDatabase()
                .getCollection(collection)
                .countDocuments();

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText(label + ": " + count);
        content.endText();

        return y - 20;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboardForm("Blue"));
    }
}

