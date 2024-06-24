package Passport;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Image {
    public static void fetchUserData(DefaultTableModel tableModel) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM user_image";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object rowData[] = {
                            resultSet.getString("nic"),
                            resultSet.getString("username"),
                            resultSet.getString("nic_image"),
                            resultSet.getString("birth_certificate_image"),
                            resultSet.getString("passport_photo_image")
                    };
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching user data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void storeVerificationData(String applicantNic, String regionalOfficerNic, String imageValidity, String imageDescription) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO regional_verification (regional_nic, user_nic, regional_validity, regional_description) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, regionalOfficerNic);
                statement.setString(2, applicantNic);
                statement.setString(3, imageValidity);
                statement.setString(4, imageDescription);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Verification data stored successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error storing verification data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void updateVerificationData(String applicantNic, String regionalOfficerNic, String imageValidity, String imageDescription) {
        try (Connection connection = getConnection()) {
            if (isNICExists(applicantNic) && isNICExists(regionalOfficerNic)) {
                String sql = "UPDATE regional_verification SET regional_validity = ?, regional_description = ? WHERE user_nic = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, imageValidity);
                    statement.setString(2, imageDescription);
                    statement.setString(3, applicantNic);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Verification data updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating verification data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deleteVerificationData(String applicantNic) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM regional_verification WHERE user_nic = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, applicantNic);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Verification data deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting verification data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isNICExists(String nic) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT COUNT(*) FROM user_information WHERE nic = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nic);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Vector<Object> searchVerificationData(String applicantNic) {
        Vector<Object> searchData = null;
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM regional_verification WHERE user_nic = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, applicantNic);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        searchData = new Vector<>();
                        searchData.add(resultSet.getString("regional_nic"));
                        searchData.add(resultSet.getString("regional_validity"));
                        searchData.add(resultSet.getString("regional_description"));
                    } else {
                        JOptionPane.showMessageDialog(null, "No verification data found for the specified NIC.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching verification data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return searchData;
    }




    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/passport";
        String user = "root";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }
}
