package Passport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormTwo {
	
	Connect ob = new Connect(); 
	 Connection conn = ob.connectDb();
	
		public void Upload(String Nic, String Username, String NicImagePath,
	            String BirthCertificateImagePath, String PassportPhotoImagePath)
	            		
		
	    throws SQLException {
			String sql = "INSERT INTO user_image (nic,username, nic_image, birth_certificate_image, passport_photo_image) " +
					"VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
				
				preparedStatement.setString(1, Nic);
				preparedStatement.setString(2, Username);
				preparedStatement.setString(3, NicImagePath);
				preparedStatement.setString(4, BirthCertificateImagePath);
				preparedStatement.setString(5, PassportPhotoImagePath);
				preparedStatement.executeUpdate();
				
			}catch(SQLException e){
				System.out.println("error :" + e.getMessage()); 
			}
		}
	}


