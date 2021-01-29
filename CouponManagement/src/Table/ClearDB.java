package Table;
import java.sql.Connection;
import java.sql.SQLException;

import ConnectionUtils.*;

public class ClearDB {
	
	public static ConnectionPool m_connectionPool = ConnectionPool.getInstance() ; 
	
	public static void ClearDBTables(){
		
		Connection connection = null;
		String query;
		
		try {
			connection = m_connectionPool.getConnection();
			
			query = "DELETE FROM couponmanager.customers_vs_coupons;";
			connection.createStatement().executeUpdate(query);
			
			query = "DELETE FROM couponmanager.coupons;";
			connection.createStatement().executeUpdate(query);
			
			query = "DELETE FROM couponmanager.customers;";
			connection.createStatement().executeUpdate(query);
			
			query = "DELETE FROM couponmanager.companies;";
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
