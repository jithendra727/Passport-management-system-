package Passport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class UserLogin {
	
	Connect ob = new Connect(); 
	 Connection conn = ob.connectDb();
	 
	 
        public void Login(String Password,String Username){
        	String sql = "SELECT * FROM user_registration WHERE username = ? AND password = ?";
            	try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            		preparedStatement.setString(1, Username);
            		preparedStatement.setString(2, Password);
            			try (ResultSet resultSet = preparedStatement.executeQuery()) {
            				if (resultSet.next()) {
            					JOptionPane.showMessageDialog(null, "Login Successful!");
            					 openFormOneInterface();  // Open FormOneUi interface
            				} else {
            					JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            				}
            			}catch(SQLException e){
            				 JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            	        }
            	}catch(SQLException e){
                    JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
        }
        public void openFormOneInterface() {
	        Home Home = new Home();
	        Home.setVisible(true);
	    }
}
