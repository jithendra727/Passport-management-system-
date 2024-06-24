package Passport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class OfficerRegistration {

    Connect ob = new Connect();
    Connection conn = ob.connectDb();

    public void insertOfficerData(String nic, String username, String role, String email, String password) {
        String sql = "INSERT INTO emp_register (nic, username, role, email, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, nic);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Your registration is successful.");
                openFormOneInterface();
            } else {
                JOptionPane.showMessageDialog(null, "Your registration is Failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openFormOneInterface() {
        OfficerLoginUi officerLoginUi = new OfficerLoginUi();
        officerLoginUi.setVisible(true);
    }
}
