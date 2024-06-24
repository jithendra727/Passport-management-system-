package Passport;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class AppointmentSystem extends JFrame {

    private JDateChooser dateChooser;
    private JTextField nicTextField;
    private JButton submitButton;
    private JButton backButton;

    public AppointmentSystem() {
        setTitle("Appointment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Initialize UI components
        dateChooser = new JDateChooser();
        nicTextField = new JTextField();
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");

        // Set preferred size for components
        dateChooser.setPreferredSize(new Dimension(200, 30));
        nicTextField.setPreferredSize(new Dimension(200, 30));
        submitButton.setPreferredSize(new Dimension(150, 30));
        backButton.setPreferredSize(new Dimension(150, 30));

        // Add action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDateSelection();
            }
        });

        // Add action listener to the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToHome();
            }
        });

        // Layout components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Select Date:"));
        panel.add(dateChooser);
        panel.add(new JLabel("Enter NIC:"));
        panel.add(nicTextField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(submitButton);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(backButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleDateSelection() {
        Date selectedDate = dateChooser.getDate();
        String nic = nicTextField.getText();

        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a date.");
        } else if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter NIC number.");
        } else {
            // Store data in the database
            if (storeData(selectedDate, nic)) {
                JOptionPane.showMessageDialog(this, "Data stored successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error storing data in the database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean storeData(Date selectedDate, String nic) {
        Connect ob = new Connect();
        Connection conn = ob.connectDb();
        try {
            String sql = "INSERT INTO appointments (appointment_date, user_NIC) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setDate(1, new java.sql.Date(selectedDate.getTime()));
                statement.setString(2, nic);

                int rowsAffected = statement.executeUpdate();

                dispose();  // Close the current frame
                // Show the Home interface (replace Home with your actual class)
                new Home().setVisible(true);
                // Return true if at least one row was affected, indicating successful insertion
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void goBackToHome() {
        // Implement the logic to go back to the home interface
        // Close the current frame and show the Home interface frame
        dispose();  // Close the current frame
        // Show the Home interface (replace HomeInterface with your actual class)
        new Home().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppointmentSystem());
    }
}
