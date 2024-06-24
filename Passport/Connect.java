package Passport;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	Connection conn = null;
	public Connection connectDb(){
try{
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passport","root","1234");
			System.out.println("Connection success ");
				
			return conn ;
}
        
    		catch(SQLException e) {
    			System.out.println("fail");
    			System.out.println(e);
    			
    			return conn;
    		}
	}	

}
