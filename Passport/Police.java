package Passport;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Police {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/passport";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static void storeVerificationData(String policeNic, String applicantNic, String policeValidity, String policeDescription) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO police_verification (police_nic, user_nic, police_validity, police_description) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, policeNic);
                statement.setString(2, applicantNic);
                statement.setString(3, policeValidity);
                statement.setString(4, policeDescription);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Verification data stored successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void updateVerificationData(String userNic, String policeValidity, String policeDescription) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "UPDATE police_verification SET police_validity = ?, police_description = ? WHERE user_nic = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, policeValidity);
                statement.setString(2, policeDescription);
                statement.setString(3, userNic);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Verification data updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deleteVerificationData(String userNic) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "DELETE FROM police_verification WHERE user_nic = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userNic);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Verification data deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void searchVerificationData(String userNic, JTextField validityTextField, JTextField descriptionTextField) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM police_verification WHERE user_nic = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userNic);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String validity = resultSet.getString("police_validity");
                        String description = resultSet.getString("police_description");
                        String message = "Validity: " + validity + "\nDescription: " + description;
                        JOptionPane.showMessageDialog(null, message, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Search failed. User NIC not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
