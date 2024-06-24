package Passport;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfficerLogin {
    private Connect ob = new Connect();
    private Connection conn = ob.connectDb();

    public boolean emplogin(String username, String password, String role) {
        boolean loginSuccessful = false;

        String sql = "SELECT * FROM emp_register WHERE username = ? AND password = ? AND role = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, role);

            try (ResultSet resultSet = statement.executeQuery()) {
                loginSuccessful = resultSet.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return loginSuccessful;
    }

    public void openPoliceUi() {
        new PoliceUi().setVisible(true);
    }

    public void openAdministratorUi() {
    	new AdministratorUi().setVisible(true);
    }

    public void openImageUi() {
        new ImageUi().setVisible(true);
    }

//    public boolean register(String username, String password, String role) {
//        String sql = "INSERT INTO emp_register(username, password, role) VALUES (?, ?, ?)";
//
//        try (PreparedStatement statement = conn.prepareStatement(sql)) {
//            statement.setString(1, username);
//            statement.setString(2, password);
//            statement.setString(3, role);
//
//            int rowsAffected = statement.executeUpdate();
//
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//    }
}
