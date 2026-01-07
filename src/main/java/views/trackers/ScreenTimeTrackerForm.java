package views.trackers;

import models.screen.ScreenRecord;
import services.screen.ScreenTimeManager;
import utils.ThemeManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class ScreenTimeTrackerForm {

    private JPanel mainPanel;
    private JTextField hoursField;
    private JTextField appField;
    private JTextField noteField;
    private JTable screenTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JLabel totalLabel;
    private JLabel hoursLabel;
    private JLabel noteLabel;
    private JLabel appLabel;

    private ScreenTimeManager manager;
    private String username;

    public ScreenTimeTrackerForm(String username, String theme) {
        this.username = username;
        manager = new ScreenTimeManager();
        ThemeManager.applyTheme(mainPanel, theme);

        loadDataIntoTable();
        updateSummary();

        screenTable.getSelectionModel().addListSelectionListener(e -> {
            int row = screenTable.getSelectedRow();
            if (row != -1) {
                hoursField.setText(screenTable.getValueAt(row, 1).toString());
                appField.setText(screenTable.getValueAt(row, 2).toString());
                noteField.setText(screenTable.getValueAt(row, 3).toString());
            }
        });

        addButton.addActionListener(e -> {
            try {
                ScreenRecord record = new ScreenRecord(
                        username,
                        LocalDate.now().toString(),
                        Double.parseDouble(hoursField.getText()),
                        appField.getText(),
                        noteField.getText(),
                        null
                );
                manager.addRecord(record);
                loadDataIntoTable();
                updateSummary();
                hoursField.setText("");
                appField.setText("");
                noteField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Sati moraju biti broj!");
            }
        });

        updateButton.addActionListener(e -> {
            int row = screenTable.getSelectedRow();
            if (row == -1) return;

            manager.updateRecord(
                    screenTable.getValueAt(row, 5).toString(),
                    LocalDate.now().toString(),
                    Double.parseDouble(hoursField.getText()),
                    appField.getText(),
                    noteField.getText()
            );
            loadDataIntoTable();
            updateSummary();
        });

        deleteButton.addActionListener(e -> {
            int row = screenTable.getSelectedRow();
            if (row == -1) return;

            manager.deleteRecord(screenTable.getValueAt(row, 5).toString());
            loadDataIntoTable();
            updateSummary();
        });
    }

    private void loadDataIntoTable() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Username", "Datum", "Sati", "Aplikacija", "Napomena", "ID"}, 0
        );

        for (ScreenRecord r : manager.getAllRecords(username)) {
            model.addRow(new Object[]{
                    r.getUsername(), r.getDate(), r.getHours(), r.getApp(), r.getNote(), r.getId()
            });
        }

        screenTable.setModel(model);
    }

    private void updateSummary() {
        totalLabel.setText("Ukupno sati: " + manager.getTotalHours(username));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
