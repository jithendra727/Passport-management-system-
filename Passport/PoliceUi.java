package Passport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PoliceUi extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField userNicTextField;
    private JTextField policeNicTextField;
    private JTextField policeValidityTextField;
    private JTextField policeDescriptionTextField;

    public PoliceUi() {
        setTitle("Police Verification");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(2000, 600);

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
        inputPanel.setPreferredSize(new Dimension(800, 100));
        
        userNicTextField = new JTextField(20);
        policeNicTextField = new JTextField(20);
        policeValidityTextField = new JTextField(10);
        policeDescriptionTextField = new JTextField(20);

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

        inputPanel.add(new JLabel("User NIC:"));
        inputPanel.add(userNicTextField);
        inputPanel.add(new JLabel("Police NIC:"));
        inputPanel.add(policeNicTextField);
        inputPanel.add(new JLabel("Police Validity:"));
        inputPanel.add(policeValidityTextField);
        inputPanel.add(new JLabel("Police Description:"));
        inputPanel.add(policeDescriptionTextField);
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
        Connection connection = null;

        try {
            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/passport";
            String user = "root";
            String password = "1234";

            // Establish the database connection
            connection = DriverManager.getConnection(url, user, password);

            // Create SQL statement to retrieve data from user_information table
            String sql = "SELECT * FROM user_information";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Process the results and add to the table model
                    while (resultSet.next()) {
                        Object rowData[] = {
                                resultSet.getString("nic"),
                                resultSet.getString("username"),
                                resultSet.getString("given_name"),
                                resultSet.getString("email"),
                                resultSet.getString("contact_number"),
                                resultSet.getString("nationality"),
                                resultSet.getDate("birth_date"),
                                resultSet.getString("birth_place"),
                                resultSet.getString("address")
                        };
                        tableModel.addRow(rowData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection in the finally block
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void storeVerificationData() {
        String userNic = userNicTextField.getText();
        String policeNic = policeNicTextField.getText();
        String policeValidity = policeValidityTextField.getText();
        String policeDescription = policeDescriptionTextField.getText();

        Police.storeVerificationData(policeNic, userNic, policeValidity, policeDescription);

        // Optional: Refresh the table after inserting data
        tableModel.setRowCount(0);
        fetchUserData();
    }

    private void updateVerificationData() {
        String userNic = userNicTextField.getText();
        String policeValidity = policeValidityTextField.getText();
        String policeDescription = policeDescriptionTextField.getText();

        Police.updateVerificationData(userNic, policeValidity, policeDescription);

        // Optional: Refresh the table after updating data
        tableModel.setRowCount(0);
        fetchUserData();
    }

    private void deleteVerificationData() {
        String userNic = userNicTextField.getText();

        Police.deleteVerificationData(userNic);

        // Optional: Refresh the table after deleting data
        tableModel.setRowCount(0);
        fetchUserData();
    }

    private void searchVerificationData() {
        String userNic = userNicTextField.getText();

        Police.searchVerificationData(userNic, policeValidityTextField, policeDescriptionTextField);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PoliceUi::new);
    }
}
