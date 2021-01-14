package Table;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ConnectionUtils.*;

public class DeleteDB {
	
	public static ConnectionPool m_connectionPool = ConnectionPool.getInstance() ; 
	
	public static void deleteDBTables(){
		
		Connection connection = null;
		String query;
		
		try {
			connection = m_connectionPool.getConnection();
			
			query = "DELETE FROM `project.1`.`customers_vs_coupons`;";
			connection.createStatement().executeUpdate(query);
			
			query = "DELETE FROM `project.1`.`coupons`;";
			connection.createStatement().executeUpdate(query);
			
			query = "DELETE FROM `project.1`.`customers`;";
			connection.createStatement().executeUpdate(query);
			
			query = "DELETE FROM `project.1`.`companies`;";
			connection.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
	}

}
