package Passport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.SwingUtilities;

public class appoinmenttest {
	
	
	Connect ob = new Connect();
    Connection conn = ob.connectDb();
    

	    public static void main(String[] args) {
	    	
	        SwingUtilities.invokeLater(() -> new AppointmentSystem());
	    }

}
