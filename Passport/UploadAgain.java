package Passport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class UploadAgain {

    private Connect ob = new Connect();
    private Connection conn = ob.connectDb();

    public void Upload(String Nic, String Username, String NicImagePath,
                       String BirthCertificateImagePath, String PassportPhotoImagePath) {
        String sql = "UPDATE user_image SET username = ?, nic_image = ?, birth_certificate_image = ?, passport_photo_image = ? " +
                "WHERE nic = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, NicImagePath);
            preparedStatement.setString(3, BirthCertificateImagePath);
            preparedStatement.setString(4, PassportPhotoImagePath);
            preparedStatement.setString(5, Nic);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update data.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
