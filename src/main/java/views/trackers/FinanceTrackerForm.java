package views.trackers;

import services.finance.TransactionManager;
import models.finance.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class FinanceTrackerForm {

    private JPanel mainPanel;
    private JTextField amountField;
    private JTextField descriptionField;
    private JComboBox<String> typeCombo;
    private JButton addButton;
    private JTable transactionTable;
    private JLabel incomeLabel;
    private JLabel expensesLabel;
    private JLabel balanceLabel;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton exportButton;
    private JComboBox categoryCombo;

    private TransactionManager manager;

    public FinanceTrackerForm() {

        manager = new TransactionManager();

        categoryCombo.addItem("Plata");
        categoryCombo.addItem("Hrana");
        categoryCombo.addItem("Racuni");
        categoryCombo.addItem("Zabava");
        categoryCombo.addItem("Prijevoz");
        categoryCombo.addItem("Ostalo");

        loadDataIntoTable();
        updateSummary();

        transactionTable.getSelectionModel().addListSelectionListener(e -> {
            int row = transactionTable.getSelectedRow();
            if (row != -1) {
                typeCombo.setSelectedItem(transactionTable.getValueAt(row, 0).toString());
                amountField.setText(transactionTable.getValueAt(row, 1).toString());
                descriptionField.setText(transactionTable.getValueAt(row, 2).toString());
                categoryCombo.setSelectedItem(transactionTable.getValueAt(row, 4).toString());
            }
        });

        addButton.addActionListener(e -> {
            try {
                String type = (String) typeCombo.getSelectedItem();
                String category = (String) categoryCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());
                String description = descriptionField.getText();

                if (description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Description cannot be empty");
                    return;
                }

                Transaction t =
                        new Transaction(type, amount, description, null, category);

                manager.addTransaction(t);

                loadDataIntoTable();
                updateSummary();

                amountField.setText("");
                descriptionField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Amount must be a number!");
            }
        });

        updateButton.addActionListener(e -> {
            int row = transactionTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a row first!");
                return;
            }

            String id = transactionTable.getValueAt(row, 3).toString();
            String type = (String) typeCombo.getSelectedItem();
            String category = (String) categoryCombo.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();

            manager.updateTransaction(id, type, amount, description, category);

            loadDataIntoTable();
            updateSummary();
        });

        deleteButton.addActionListener(e -> {
            int row = transactionTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a row!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Jeste li sigurni da želite izbrisati ovu transakciju?",
                    "Potvrda brisanja",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                String id = transactionTable.getValueAt(row, 3).toString();
                manager.deleteTransaction(id);
                loadDataIntoTable();
                updateSummary();
            }
        });

        exportButton.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

                    java.io.PrintWriter writer =
                            new java.io.PrintWriter(fileChooser.getSelectedFile());

                    writer.println("Ukupni prihod: " + manager.getTotalIncome());
                    writer.println("Ukupni rashod: " + manager.getTotalExpense());
                    writer.println("Stanje: " + (manager.getTotalIncome() - manager.getTotalExpense()));

                    writer.close();
                    JOptionPane.showMessageDialog(null, "Exportovan uspješno!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Greška pri exportu!");
            }
        });
    }

    private void loadDataIntoTable() {

        ArrayList<Transaction> list = manager.getAllTransactions();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Type");
        model.addColumn("Amount");
        model.addColumn("Description");
        model.addColumn("ID");
        model.addColumn("Category");

        for (Transaction t : list) {
            model.addRow(new Object[]{
                    t.getType(),
                    t.getAmount(),
                    t.getDescription(),
                    t.getId(),
                    t.getCategory()
            });
        }

        transactionTable.setModel(model);
    }

    private void updateSummary() {
        double income = manager.getTotalIncome();
        double expense = manager.getTotalExpense();
        double balance = income - expense;

        incomeLabel.setText("Income: " + income);
        expensesLabel.setText("Expenses: " + expense);
        balanceLabel.setText("Balance: " + balance);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
