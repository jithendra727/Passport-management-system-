package Passport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class ImageUi extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField applicantNicTextField;
    private JTextField regionalOfficerNicTextField;
    private JTextField imageValidityTextField;
    private JTextField imageDescriptionTextField;

    public ImageUi() {
        setTitle("Regional Officer verification");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        // Create table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Username");
        tableModel.addColumn("NIC Image");
        tableModel.addColumn("Birth Certificate Image");
        tableModel.addColumn("Passport Photo Image");

        // Create table
        table = new JTable(tableModel);

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add scroll pane to the frame
        add(scrollPane);

        // Add input fields and buttons
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setPreferredSize(new Dimension(800, 100));

        applicantNicTextField = new JTextField(15);
        regionalOfficerNicTextField = new JTextField(15);
        imageValidityTextField = new JTextField(10);
        imageDescriptionTextField = new JTextField(15);

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
        backButton.addActionListener(e -> goBack());

        inputPanel.add(new JLabel("Applicant NIC:"));
        inputPanel.add(applicantNicTextField);
        inputPanel.add(new JLabel("Regional Officer NIC:"));
        inputPanel.add(regionalOfficerNicTextField);
        inputPanel.add(new JLabel("Image Validity:"));
        inputPanel.add(imageValidityTextField);
        inputPanel.add(new JLabel("Image Description:"));
        inputPanel.add(imageDescriptionTextField);
        inputPanel.add(storeButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(searchButton);
        inputPanel.add(backButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Fetch and display data
        Image.fetchUserData(tableModel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void storeVerificationData() {
        String applicantNic = applicantNicTextField.getText();
        String regionalOfficerNic = regionalOfficerNicTextField.getText();
        String imageValidity = imageValidityTextField.getText();
        String imageDescription = imageDescriptionTextField.getText();

        Image.storeVerificationData(applicantNic, regionalOfficerNic, imageValidity, imageDescription);

        // Optional: Refresh the table after inserting data
        tableModel.setRowCount(0);
        Image.fetchUserData(tableModel);

        // Clear the text fields after storing data
        clearTextFields();
    }

    private void updateVerificationData() {
        String applicantNic = applicantNicTextField.getText();
        String regionalOfficerNic = regionalOfficerNicTextField.getText();
        String imageValidity = imageValidityTextField.getText();
        String imageDescription = imageDescriptionTextField.getText();

        Image.updateVerificationData(applicantNic, regionalOfficerNic, imageValidity, imageDescription);

        // Optional: Refresh the table after updating data
        tableModel.setRowCount(0);
        Image.fetchUserData(tableModel);

        // Clear the text fields after updating data
        clearTextFields();
    }

    private void deleteVerificationData() {
        String applicantNic = applicantNicTextField.getText();

        Image.deleteVerificationData(applicantNic);

        // Optional: Refresh the table after deleting data
        tableModel.setRowCount(0);
        Image.fetchUserData(tableModel);

        // Clear the text fields after deleting data
        clearTextFields();
    }

    private void searchVerificationData() {
        String applicantNic = applicantNicTextField.getText();

        Vector<Object> searchData = Image.searchVerificationData(applicantNic);

        // Display search results in text fields
        if (searchData != null) {
            regionalOfficerNicTextField.setText((String) searchData.get(0));
            imageValidityTextField.setText((String) searchData.get(1));
            imageDescriptionTextField.setText((String) searchData.get(2));
        } else {
            JOptionPane.showMessageDialog(this, "Search failed. Applicant NIC not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBack() {
        // Implement any action you want when the Back button is pressed
        // For example, you can close the current frame or navigate to another window
        // In this example, I'm simply closing the current frame
        dispose();
    }

    private void clearTextFields() {
        applicantNicTextField.setText("");
        regionalOfficerNicTextField.setText("");
        imageValidityTextField.setText("");
        imageDescriptionTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageUi());
    }
}
