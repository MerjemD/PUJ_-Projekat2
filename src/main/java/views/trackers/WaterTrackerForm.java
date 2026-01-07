package views.trackers;

import models.water.WaterRecord;
import services.water.WaterManager;
import utils.ThemeManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class WaterTrackerForm {

    private JPanel mainPanel;
    private JTextField litersField;
    private JTextField noteField;
    private JTable waterTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JLabel averageLabel;
    private JLabel litersLabel;
    private JLabel noteLabel;

    private WaterManager manager;
    private String username;

    public WaterTrackerForm(String username, String theme) {

        this.username = username;
        manager = new WaterManager();
        ThemeManager.applyTheme(mainPanel, theme);

        loadDataIntoTable();
        updateSummary();

        waterTable.getSelectionModel().addListSelectionListener(e -> {
            int row = waterTable.getSelectedRow();
            if (row != -1) {
                litersField.setText(waterTable.getValueAt(row, 1).toString());
                noteField.setText(waterTable.getValueAt(row, 2).toString());
            }
        });

        addButton.addActionListener(e -> {
            try {
                WaterRecord record = new WaterRecord(
                        username,
                        LocalDate.now().toString(),
                        Double.parseDouble(litersField.getText()),
                        noteField.getText(),
                        null
                );
                manager.addRecord(record);
                loadDataIntoTable();
                updateSummary();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Litri moraju biti broj!");
            }
        });

        updateButton.addActionListener(e -> {
            int row = waterTable.getSelectedRow();
            if (row == -1) return;

            manager.updateRecord(
                    waterTable.getValueAt(row, 3).toString(),
                    LocalDate.now().toString(),
                    Double.parseDouble(litersField.getText()),
                    noteField.getText()
            );
            loadDataIntoTable();
            updateSummary();
        });

        deleteButton.addActionListener(e -> {
            int row = waterTable.getSelectedRow();
            if (row == -1) return;

            manager.deleteRecord(waterTable.getValueAt(row, 3).toString());
            loadDataIntoTable();
            updateSummary();
        });
    }

    private void loadDataIntoTable() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Datum", "Litri", "Napomena", "ID"}, 0
        );

        for (WaterRecord r : manager.getAllRecords(username)) {
            if (r.getUsername().equals(username)) {
                model.addRow(new Object[]{
                        r.getDate(), r.getLiters(), r.getNote(), r.getId()
                });
            }
        }

        waterTable.setModel(model);
    }

    private void updateSummary() {
        averageLabel.setText("Prosjek: " + manager.getAverageLiters(username) + " L");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

