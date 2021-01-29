package ConnectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLconnection {


	//DB Driver Name and DB URL
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
	public static final String DB_URL= "jdbc:mysql://localhost:3306/couponmanager?useSSL=false&serverTimezone=UTC";

	//DB Server User +password
	public static final String user ="root";
	public static final String password="gazigazi789";


	public static Connection GetConnection() {
		Connection con = null;
		
		try {
			//register the JDBC driver 
			Class.forName(JDBC_DRIVER);

			//Create connection per the driver (JDBC_DRIVER)
			con = DriverManager.getConnection(DB_URL , user ,password);
			
		}
		catch(SQLException sqlex){
			sqlex.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		 return con;
	}

}