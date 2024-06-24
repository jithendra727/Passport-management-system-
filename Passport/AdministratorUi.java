package Passport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class AdministratorUi extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField administratorNicTextField;
    private JTextField applicantNicTextField;
    private JTextField validityTextField;
    private JTextField descriptionTextField;
    private Administrator administrator;

    public AdministratorUi() {
        setTitle("Administrator verification");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        administrator = new Administrator();

        // Create table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("NIC Number");
        tableModel.addColumn("Username");
        tableModel.addColumn("Given Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Contact Number");
        tableModel.addColumn("Nationality");
        tableModel.addColumn("Birth Date");
        tableModel.addColumn("Birth Place");
        tableModel.addColumn("Address");

        // Create table
        table = new JTable(tableModel);

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add scroll pane to the frame
        add(scrollPane);

        // Add input fields and buttons
        JPanel inputPanel = new JPanel(new FlowLayout());

        inputPanel.setPreferredSize(new Dimension(800, 100));// button and textfield panel size changer

        administratorNicTextField = new JTextField(20);
        applicantNicTextField = new JTextField(20);
        validityTextField = new JTextField(10);
        descriptionTextField = new JTextField(20);

        JButton storeButton = new JButton("Store Data");
        JButton updateButton = new JButton("Update Data");
        JButton deleteButton = new JButton("Delete Data");
        JButton searchButton = new JButton("Search Data");
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
           
        	dispose();
            new OfficerLoginUi().setVisible(true);
            this.setVisible(false);
        });
        
        inputPanel.add(backButton);

        storeButton.addActionListener(e -> storeVerificationData());
        updateButton.addActionListener(e -> updateVerificationData());
        deleteButton.addActionListener(e -> deleteVerificationData());
        searchButton.addActionListener(e -> searchVerificationData());

        inputPanel.add(new JLabel("Administrator NIC:"));
        inputPanel.add(administratorNicTextField);
        inputPanel.add(new JLabel("Applicant NIC Number:"));
        inputPanel.add(applicantNicTextField);
        inputPanel.add(new JLabel("Validity:"));
        inputPanel.add(validityTextField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionTextField);
        inputPanel.add(storeButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Fetch and display data
        fetchUserData();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchUserData() {
        // Fetch user data
        Vector<Vector<Object>> data = administrator.fetchUserData();

        // Add fetched data to the table model
        for (Vector<Object> rowData : data) {
            tableModel.addRow(rowData);
        }
    }

    private void storeVerificationData() {
        // Get values from text fields
        String administratorNic = administratorNicTextField.getText();
        String applicantNic = applicantNicTextField.getText();
        String validity = validityTextField.getText();
        String description = descriptionTextField.getText();

        // Store data
        administrator.storeVerificationData(administratorNic, applicantNic, validity, description);

        // Clear the text fields after storing data
        clearTextFields();

        // Refresh the table after inserting data
        tableModel.setRowCount(0);
        fetchUserData();
    }

    private void updateVerificationData() {
        // Get values from text fields
        String administratorNic = administratorNicTextField.getText();
        String validity = validityTextField.getText();
        String description = descriptionTextField.getText();

        // Update data
        administrator.updateVerificationData(administratorNic, validity, description);

        // Clear the text fields after updating data
        clearTextFields();

        // Refresh the table after updating data
        tableModel.setRowCount(0);
        fetchUserData();
    }

    private void deleteVerificationData() {
        // Get values from text fields
        String administratorNic = administratorNicTextField.getText();

        // Delete data
        administrator.deleteVerificationData(administratorNic);

        // Clear the text fields after deleting data
        clearTextFields();

        // Refresh the table after deleting data
        tableModel.setRowCount(0);
        fetchUserData();
    }

    private void searchVerificationData() {
        // Get values from text fields
        String administratorNic = administratorNicTextField.getText();

        // Search data
        Vector<Object> searchData = administrator.searchVerificationData(administratorNic);

        // Display search results in text fields
        if (searchData != null) {
            applicantNicTextField.setText((String) searchData.get(0));
            validityTextField.setText((String) searchData.get(1));
            descriptionTextField.setText((String) searchData.get(2));
        } else {
            JOptionPane.showMessageDialog(this, "Search failed. Administrator NIC not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearTextFields() {
        administratorNicTextField.setText("");
        applicantNicTextField.setText("");
        validityTextField.setText("");
        descriptionTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdministratorUi();
        });
    }
}
