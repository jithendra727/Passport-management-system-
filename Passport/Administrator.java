package Passport;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Administrator {
	private Connect obj = new Connect();
    private Connection connection = obj.connectDb();

    public Administrator() {
        try {
            String url = "jdbc:mysql://localhost:3306/passport";
            String user = "root";
            String password = "1234";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Vector<Vector<Object>> fetchUserData() {
        Vector<Vector<Object>> data = new Vector<>();
        try {
            String sql = "SELECT * FROM user_information";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Vector<Object> rowData = new Vector<>();
                    rowData.add(resultSet.getString("nic"));
                    rowData.add(resultSet.getString("username"));
                    rowData.add(resultSet.getString("given_name"));
                    rowData.add(resultSet.getString("email"));
                    rowData.add(resultSet.getString("contact_number"));
                    rowData.add(resultSet.getString("nationality"));
                    rowData.add(resultSet.getDate("birth_date"));
                    rowData.add(resultSet.getString("birth_place"));
                    rowData.add(resultSet.getString("address"));
                    data.add(rowData);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching user data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }

    public void storeVerificationData(String administratorNic, String applicantNic, String validity, String description) {
        try {
            if (isNICExists(applicantNic)) {
                String sql = "INSERT INTO administrator_verification (administrator_nic, user_nic, administrator_validity, administrator_description) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, administratorNic);
                    statement.setString(2, applicantNic);
                    statement.setString(3, validity);
                    statement.setString(4, description);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Verification data stored successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Applicant NIC does not exist in user_information table.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error storing verification data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isNICExists(String nic) {
        try {
            String sql = "SELECT COUNT(*) FROM user_information WHERE nic = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, nic);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next() && resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error checking NIC: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void updateVerificationData(String administratorNic, String validity, String description) {
        try {
            if (isAdministratorNICExists(administratorNic)) {
                String sql = "UPDATE administrator_verification SET administrator_validity = ?, administrator_description = ? WHERE administrator_nic = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, validity);
                    statement.setString(2, description);
                    statement.setString(3, administratorNic);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Verification data updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Administrator NIC does not exist in administrator_verification table.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating verification data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteVerificationData(String administratorNic) {
        try {
            if (isAdministratorNICExists(administratorNic)) {
                String sql = "DELETE FROM administrator_verification WHERE administrator_nic = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, administratorNic);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Verification data deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Administrator NIC does not exist in administrator_verification table.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting verification data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Vector<Object> searchVerificationData(String administratorNic) {
        try {
            if (isAdministratorNICExists(administratorNic)) {
                String sql = "SELECT user_nic, administrator_validity, administrator_description FROM administrator_verification WHERE administrator_nic = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, administratorNic);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            Vector<Object> searchData = new Vector<>();
                            searchData.add(resultSet.getString("user_nic"));
                            searchData.add(resultSet.getString("administrator_validity"));
                            searchData.add(resultSet.getString("administrator_description"));
                            return searchData;
                        } else {
                            JOptionPane.showMessageDialog(null, "No verification data found for the specified administrator NIC.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Administrator NIC does not exist in administrator_verification table.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching verification data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private boolean isAdministratorNICExists(String administratorNic) {
        try {
            String sql = "SELECT COUNT(*) FROM administrator_verification WHERE administrator_nic = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, administratorNic);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next() && resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error checking administrator NIC: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
