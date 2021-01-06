package ConnectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLQueries {


	//DB Driver Name and DB URL
	public static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
	public static final String DB_URL= "jdbc:mysql://localhost:3306/project.1?useSSL=false&serverTimezone=UTC";

	//DB Server User +password
	public static final String user ="root";
	public static final String password="gazigazi789";


	public static Connection GetConnection() {
		Connection con =null;
		
		try {
			//register the JDBC driver 
			Class.forName(JDBC_DRIVER);

			//Create connection per the driver (JDBC_DRIVER)
			System.out.println("Connection to the mysql DB ....");
			con = DriverManager.getConnection(DB_URL , user ,password);
			
		}
		catch(SQLException sqlex){
			sqlex.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 return con;
	}

}