package views.trackers;

import models.sleep.SleepRecord;
import services.sleep.SleepManager;
import utils.ThemeManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class SleepTrackerForm {

    private JPanel mainPanel;
    private JTextField hoursField;
    private JTextField noteField;
    private JComboBox<String> qualityCombo;
    private JTable sleepTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JLabel averageLabel;
    private JLabel hoursLabel;
    private JLabel noteLabel;

    private SleepManager manager;

    public SleepTrackerForm(String theme) {

        manager = new SleepManager();
        ThemeManager.applyTheme(mainPanel, theme);



        loadDataIntoTable();
        updateSummary();

        sleepTable.getSelectionModel().addListSelectionListener(e -> {
            int row = sleepTable.getSelectedRow();
            if (row != -1) {
                hoursField.setText(sleepTable.getValueAt(row, 1).toString());
                qualityCombo.setSelectedItem(sleepTable.getValueAt(row, 2).toString());
                noteField.setText(sleepTable.getValueAt(row, 3).toString());
            }
        });

        addButton.addActionListener(e -> {
            try {
                SleepRecord record = new SleepRecord(
                        LocalDate.now().toString(),
                        Double.parseDouble(hoursField.getText()),
                        qualityCombo.getSelectedItem().toString(),
                        noteField.getText(),
                        null
                );

                manager.addRecord(record);
                loadDataIntoTable();
                updateSummary();

                hoursField.setText("");
                noteField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Sati moraju biti broj!");
            }
        });

        updateButton.addActionListener(e -> {
            int row = sleepTable.getSelectedRow();
            if (row == -1) return;

            manager.updateRecord(
                    sleepTable.getValueAt(row, 4).toString(),
                    LocalDate.now().toString(),
                    Double.parseDouble(hoursField.getText()),
                    qualityCombo.getSelectedItem().toString(),
                    noteField.getText()
            );

            loadDataIntoTable();
            updateSummary();
        });

        deleteButton.addActionListener(e -> {
            int row = sleepTable.getSelectedRow();
            if (row == -1) return;

            manager.deleteRecord(sleepTable.getValueAt(row, 4).toString());
            loadDataIntoTable();
            updateSummary();
        });
    }

    private void loadDataIntoTable() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Datum", "Sati", "Kvalitet", "Napomena", "ID"}, 0
        );

        for (SleepRecord r : manager.getAllRecords()) {
            model.addRow(new Object[]{
                    r.getDate(),
                    r.getHours(),
                    r.getQuality(),
                    r.getNote(),
                    r.getId()
            });
        }

        sleepTable.setModel(model);
    }

    private void updateSummary() {
        averageLabel.setText(
                "Prosjek sati spavanja: " +
                        String.format("%.2f", manager.getAverageHours())
        );
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}


