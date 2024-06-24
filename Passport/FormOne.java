package Passport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FormOne {
    Connect ob = new Connect();
    Connection conn = ob.connectDb();

    public void FormOneInsert(String Nic, String Surname, String GivenName, String email, String Contactnumber,
            String Nationality, String Birthofdate, String BirthPlace, String Address) {

        String sql = "INSERT INTO user_information (nic, username, given_name, email, contact_number, " +
                "nationality, birth_date, birth_place, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, Nic);
            preparedStatement.setString(2, Surname);
            preparedStatement.setString(3, GivenName);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, Contactnumber);
            preparedStatement.setString(6, Nationality);
            preparedStatement.setString(7, Birthofdate);
            preparedStatement.setString(8, BirthPlace);
            preparedStatement.setString(9, Address);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data inserted successfully into user_information table.");
                
                openFormTwoInterface();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to insert data into user_information table.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error inserting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void openFormTwoInterface() {
    	FormTwoUi formTwoUi = new FormTwoUi();
    	formTwoUi.setVisible(true);
    }
}