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
		boolean isExist = true;
		
		String query = "SELECT * FROM `project.1`.costumers WHERE (EMAIL = '" + email + "' AND PASSWORD = '" + password + "') ;\r\n";
		ResultSet costumerSet = null;
		
		try {
			costumerSet = m_connectionPool.getConnection().createStatement().executeQuery(query);
			if(costumerSet.next() == false)
			{
				isExist = false;
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return isExist;
	}
	
	public void addCustomer(Customer customer) {
		
		String query = "INSERT INTO `project.1`.`customers` (`ID`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) "
				+ "VALUES " + "('" + customer.getId() + "', '"+ customer.getFirstName()  +"', '"+ customer.getLastName() +"', '"+ customer.getEmail() + "', '" + customer.getPassword() + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void updateCustomer(Customer customer) {
		
		String query = "UPDATE `project.1`.`customers` SET FIRST_NAME = '"+ customer.getFirstName() +"', LAST_NAME = '"+ customer.getLastName() +"', EMAIL = '"+ customer.getEmail() +"', PASSWORD = '"+ customer.getPassword() +"' WHERE (ID = '" + customer.getId() + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteCustomer(int customerID) {
		
		String query = "DELETE FROM `project.1`.`customers` WHERE (ID = '" + customerID + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public ArrayList<Customer> getAllCustomers(){
		
		String query = "SELECT * FROM `project.1`.costumers ;\r\n";
		ResultSet costumersSet = null;
		ArrayList<Customer> costumers = new ArrayList<Customer>();
		
		try {
			costumersSet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		
		while(costumersSet.next())
		{
				Customer toAdd = new Customer(costumersSet.getInt(1), costumersSet.getString(2), costumersSet.getString(3), costumersSet.getString(4), costumersSet.getString(5));
				costumers.add(toAdd);
		}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
			
		return costumers;
	}
	
	public Customer getOneCustomer(int customerID) {
		
		String query = "SELECT * FROM `project.1`.costumers WHERE (ID = '" + customerID + "') ;\r\n";
		ResultSet costumersSet = null;
		Customer result = null;
		
		try {
			costumersSet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(costumersSet.first()) {
				result = new Customer(costumersSet.getInt(1), costumersSet.getString(2), costumersSet.getString(3), costumersSet.getString(4), costumersSet.getString(5));
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	public boolean isCustomerEmailExists(String email)
	{
		String query = "SELECT * FROM `project.1`.customers WHERE (EMAIL = '" + email + "')";
		
		ResultSet costumersSet = null;
		boolean isExists = false;
		try {
			costumersSet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			isExists = costumersSet.first();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return isExists;
	}

	@Override
	public int getCustomerIdByEmailAndPassword(String email, String password) {
	// doesn't check if the email + password are valid, so the user of this function must make sure before using it.
		
		String query = "SELECT * FROM `project.1`.`customers` WHERE (EMAIL = '" + email + "' AND PASSWORD = '" + password + "');";
		ResultSet customerSet = null;
		int customerID = -1;
		
		try {
			customerSet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			customerSet.first();
			customerID = customerSet.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return customerID;
	}
	
}
