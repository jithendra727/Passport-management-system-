package Passport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextArea;

public class Status {
    private Connect obj = new Connect();
    private Connection conn = obj.connectDb();

    public void SearchData(String userNic, JTextArea outputTextArea) {
        outputTextArea.setText("");

        // Search police_verification table
        String policeQuery = "SELECT * FROM police_verification WHERE user_nic = ?";
        try (PreparedStatement policeStmt = conn.prepareStatement(policeQuery)) {
            policeStmt.setString(1, userNic);
            ResultSet policeResult = policeStmt.executeQuery();
            outputTextArea.append("Police Verification:\n");
            while (policeResult.next()) {
                String policeNic = policeResult.getString("police_nic");
                String policeValidity = policeResult.getString("police_validity");
                String policeDescription = policeResult.getString("police_description");
                outputTextArea.append("Police NIC: " + policeNic + "\n");
                outputTextArea.append("Validity: " + policeValidity + "\n");
                outputTextArea.append("Description: " + policeDescription + "\n");
            }
            outputTextArea.append("\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Search administrator_verification table
        String adminQuery = "SELECT * FROM administrator_verification WHERE user_nic = ?";
        try (PreparedStatement adminStmt = conn.prepareStatement(adminQuery)) {
            adminStmt.setString(1, userNic);
            ResultSet adminResult = adminStmt.executeQuery();
            outputTextArea.append("Administrator Verification:\n");
            while (adminResult.next()) {
                String adminNic = adminResult.getString("administrator_nic");
                String adminValidity = adminResult.getString("administrator_validity");
                String adminDescription = adminResult.getString("administrator_description");
                outputTextArea.append("Administrator NIC: " + adminNic + "\n");
                outputTextArea.append("Validity: " + adminValidity + "\n");
                outputTextArea.append("Description: " + adminDescription + "\n");
            }
            outputTextArea.append("\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Search regional_verification table
        String regionalQuery = "SELECT * FROM regional_verification WHERE user_nic = ?";
        try (PreparedStatement regionalStmt = conn.prepareStatement(regionalQuery)) {
            regionalStmt.setString(1, userNic);
            ResultSet regionalResult = regionalStmt.executeQuery();
            outputTextArea.append("Regional Verification:\n");
            while (regionalResult.next()) {
                String regionalNic = regionalResult.getString("regional_nic");
                String regionalValidity = regionalResult.getString("regional_validity");
                String regionalDescription = regionalResult.getString("regional_description");
                outputTextArea.append("Regional NIC: " + regionalNic + "\n");
                outputTextArea.append("Validity: " + regionalValidity + "\n");
                outputTextArea.append("Description: " + regionalDescription + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}