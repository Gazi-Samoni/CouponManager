package DataAccessObjects;
import java.util.*;
import java.sql.*;
import JavaBeans.*;
import ConnectionUtils.*;

public class CustomersDBDAO implements CustomersDAO {
	
	private ConnectionPool m_connectionPool;
	
	public CustomersDBDAO() {
		m_connectionPool = ConnectionPool.getInstance();
	}
	
	public boolean isCustomerExists(String email, String password) {
		Connection connection=null;
		boolean isExist = true;
		
		String query = "SELECT * FROM `project.1`.costumers WHERE (EMAIL = '" + email + "' AND PASSWORD = '" + password + "') ;\r\n";
		ResultSet costumerSet = null;
		
		try {
			connection = m_connectionPool.getConnection();
			costumerSet = connection.createStatement().executeQuery(query);
			if(costumerSet.next() == false)
			{
				isExist = false;
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return isExist;
	}
	
	public void addCustomer(Customer customer) {
		Connection connection=null;
		String query = "INSERT INTO `project.1`.`customers` (`ID`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) "
				+ "VALUES " + "('" + customer.getId() + "', '"+ customer.getFirstName()  +"', '"+ customer.getLastName() +"', '"+ customer.getEmail() + "', '" + customer.getPassword() + "');\r\n";
		
		try {
			connection = m_connectionPool.getConnection();
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
	
	public void updateCustomer(Customer customer) {
		Connection connection=null;
		String query = "UPDATE `project.1`.`customers` SET FIRST_NAME = '"+ customer.getFirstName() +"', LAST_NAME = '"+ customer.getLastName() +"', EMAIL = '"+ customer.getEmail() +"', PASSWORD = '"+ customer.getPassword() +"' WHERE (ID = '" + customer.getId() + "');\r\n";
		
		try {
			connection = m_connectionPool.getConnection();	
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
	
	public void deleteCustomer(int customerID) {
		Connection connection=null;
		String query = "DELETE FROM `project.1`.`customers` WHERE (ID = '" + customerID + "');\r\n";
		
		try {
			connection = m_connectionPool.getConnection();
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
	
	public ArrayList<Customer> getAllCustomers(){
		Connection connection=null;
		String query = "SELECT * FROM `project.1`.costumers ;\r\n";
		ResultSet costumersSet = null;
		ArrayList<Customer> costumers = new ArrayList<Customer>();
		
		try {
			connection = m_connectionPool.getConnection();
			costumersSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		
		while(costumersSet.next())
		{
				Customer toAdd = new Customer(costumersSet.getInt(1), costumersSet.getString(2), costumersSet.getString(3), costumersSet.getString(4), costumersSet.getString(5));
				costumers.add(toAdd);
		}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
			
		return costumers;
	}
	
	public Customer getOneCustomer(int customerID) {
		Connection connection=null;
		String query = "SELECT * FROM `project.1`.costumers WHERE (ID = '" + customerID + "') ;\r\n";
		ResultSet costumersSet = null;
		Customer result = null;
		

		try {
			connection = m_connectionPool.getConnection();
			costumersSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(costumersSet.first()) {
				result = new Customer(costumersSet.getInt(1), costumersSet.getString(2), costumersSet.getString(3), costumersSet.getString(4), costumersSet.getString(5));
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return result;
	}
	public boolean isCustomerEmailExists(String email)
	{
		Connection connection =null;
		String query = "SELECT * FROM `project.1`.customers WHERE (EMAIL = '" + email + "')";
		ResultSet costumersSet = null;
		boolean isExists = false;
		
		try {
			connection  = m_connectionPool.getConnection();
			costumersSet =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			isExists = costumersSet.first();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return isExists;
	}

	@Override
	public int getCustomerIdByEmailAndPassword(String email, String password) {
	// doesn't check if the email + password are valid, so the user of this function must make sure before using it.
		Connection connection=null;
		String query = "SELECT * FROM `project.1`.`customers` WHERE (EMAIL = '" + email + "' AND PASSWORD = '" + password + "');";
		ResultSet customerSet = null;
		int customerID = -1;
				
		try {
			connection = m_connectionPool.getConnection();
			customerSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			customerSet.first();
			customerID = customerSet.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return customerID;
	}
	
}
