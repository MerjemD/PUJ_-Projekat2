package views.trackers;

import models.finance.Transaction;
import services.finance.TransactionManager;
import utils.ThemeManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FinanceTrackerForm {

    private JPanel mainPanel;
    private JTextField amountField;
    private JTextField descriptionField;
    private JComboBox<String> typeCombo;
    private JComboBox<String> categoryCombo;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable transactionTable;
    private JLabel incomeLabel;
    private JLabel expensesLabel;
    private JLabel balanceLabel;
    private JButton exportButton;

    private TransactionManager manager;
    private String username;


    public FinanceTrackerForm(String username, String theme) {
        this.username = username;
        manager = new TransactionManager();

        ThemeManager.applyTheme(mainPanel, theme);


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

                Transaction t = new Transaction(username, type, amount, description, null, category);
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
            if (row == -1) return;

            String id = transactionTable.getValueAt(row, 3).toString();
            manager.updateTransaction(
                    id,
                    (String) typeCombo.getSelectedItem(),
                    Double.parseDouble(amountField.getText()),
                    descriptionField.getText(),
                    (String) categoryCombo.getSelectedItem()
            );

            loadDataIntoTable();
            updateSummary();
        });


        deleteButton.addActionListener(e -> {
            int row = transactionTable.getSelectedRow();
            if (row == -1) return;

            manager.deleteTransaction(transactionTable.getValueAt(row, 3).toString());
            loadDataIntoTable();
            updateSummary();
        });
    }


    private void loadDataIntoTable() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Type", "Amount", "Description", "ID", "Category"}, 0
        );

        for (Transaction t : manager.getAllTransactions(username)) {
            model.addRow(new Object[]{
                    t.getType(), t.getAmount(), t.getDescription(), t.getId(), t.getCategory()
            });
        }

        transactionTable.setModel(model);
    }


    private void updateSummary() {
        double income = manager.getTotalIncome(username);
        double expense = manager.getTotalExpense(username);
        incomeLabel.setText("Income: " + income);
        expensesLabel.setText("Expenses: " + expense);
        balanceLabel.setText("Balance: " + (income - expense));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

