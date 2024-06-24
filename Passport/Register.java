package Passport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Register {
    Connect obj = new Connect(); // Assuming Connect is a class for handling database connection
    Connection conn = obj.connectDb();

    public void insertData(String NIC, String Password, String Cpassword, String Username, String Email) {
        if (Password.equals(Cpassword)) {
            String sql = "INSERT INTO user_registration (nic, username, password, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, NIC);
                preparedStatement.setString(2, Username);
                preparedStatement.setString(3, Password);
                preparedStatement.setString(4, Email);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Registration Successful! You can now login.");
                openLoginInterface();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match. Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openLoginInterface() {
        UserLoginUi loginUi = new UserLoginUi();
        loginUi.setVisible(true);
    }
}
