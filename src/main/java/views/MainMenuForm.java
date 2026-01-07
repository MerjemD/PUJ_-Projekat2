package views;

import utils.ThemeManager;
import views.trackers.FinanceTrackerForm;
import views.trackers.ScreenTimeTrackerForm;
import views.trackers.SleepTrackerForm;
import views.trackers.WaterTrackerForm;
import database.mongoDBConnection;

import javax.swing.*;

public class MainMenuForm extends JFrame {

    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JButton sleepTrackerButton;
    private JButton waterTrackerButton;
    private JButton screenTimeButton;
    private JButton financeTrackerButton;
    private JButton exportPdfButton;

    private String theme;
    private String username;

    public MainMenuForm(String username, String role, String theme) {

        this.username = username;
        this.theme = theme;

        setTitle("Life Management System");
        setContentPane(mainPanel);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        welcomeLabel.setText("Welcome, " + username);
        ThemeManager.applyTheme(mainPanel, theme);

        sleepTrackerButton.addActionListener(e -> {
            JFrame frame = new JFrame("Sleep Tracker");
            SleepTrackerForm sleepForm = new SleepTrackerForm(theme);
            frame.setContentPane(sleepForm.getMainPanel());
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

        waterTrackerButton.addActionListener(e -> {
            JFrame frame = new JFrame("Water Tracker");
            WaterTrackerForm waterForm = new WaterTrackerForm(username, theme);
            frame.setContentPane(waterForm.getMainPanel());
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

        screenTimeButton.addActionListener(e -> {
            JFrame frame = new JFrame("Screen Time Tracker");
            ScreenTimeTrackerForm screenForm = new ScreenTimeTrackerForm(username, theme);
            frame.setContentPane(screenForm.getMainPanel());
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

        financeTrackerButton.addActionListener(e -> {
            JFrame frame = new JFrame("Finance Tracker");
            FinanceTrackerForm financeForm = new FinanceTrackerForm(username, theme);
            frame.setContentPane(financeForm.getMainPanel());
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        });

        exportPdfButton.addActionListener(e -> exportUserDataToPdf());

        setVisible(true);
    }

    private void exportUserDataToPdf() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save PDF");

            if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".pdf")) path += ".pdf";

            org.apache.pdfbox.pdmodel.PDDocument document =
                    new org.apache.pdfbox.pdmodel.PDDocument();
            org.apache.pdfbox.pdmodel.PDPage page =
                    new org.apache.pdfbox.pdmodel.PDPage();
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

    private int writeCount(
            org.apache.pdfbox.pdmodel.PDPageContentStream content,
            String label,
            String collection,
            int y
    ) throws Exception {

        long count = mongoDBConnection
                .getDatabase()
                .getCollection(collection)
                .countDocuments();

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText(label + ": " + count);
        content.endText();

        return y - 20;
    }
}

