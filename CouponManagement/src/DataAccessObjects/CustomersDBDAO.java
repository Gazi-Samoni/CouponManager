package DataAccessObjects;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import JavaBeans.*;
import ConnectionUtils.*;

public class CustomersDBDAO implements CustomersDAO {
	
	private ConnectionPool m_connectionPool=null;
	
	public boolean isCustomerExists(String email, String password) {
		return true;
	}
	
	public void addCustomer(Customer customer) {
		
		String query = "INSERT INTO `project.1`.`customers` (`ID`, `FIRST_NAME`, `LAST_NAME`, `EMAIL` ,`PASSWORD`) "
				+ "VALUES " + "('" + customer.getId() + "' '"+ customer.getFirstName()  +"', '"+ customer.getLastName() +"', '"+ customer.getEmail() + "', '" + customer.getPassword() + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateCustomer(Customer customer) {
		
		String query = "UPDATE `project.1`.`customers` SET FIRST_NAME = '"+ customer.getFirstName() +"', LAST_NAME = '"+ customer.getLastName() +"', EMAIL = '"+ customer.getEmail() +"', PASSWORD = '"+ customer.getPassword() +"' WHERE ('ID' = '" + customer.getId() + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCustomer(int customerID) {
		
		String query = "DELETE FROM `project.1`.`customers` WHERE ('ID' = '" + customerID + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Customer> getAllCustomers(){
		
		String query = "SELECT * FROM `project.1`.`costumers` ;\r\n";
		ResultSet costumersSet = null;
		ArrayList<Customer> costumers = new ArrayList<Customer>();
		
		try {
			costumersSet = m_connectionPool.getConnection().createStatement().executeQuery(query);
		
		while(costumersSet.next())
		{
				Customer toAdd = new Customer(costumersSet.getInt(1), costumersSet.getString(2), costumersSet.getString(3), costumersSet.getString(4), costumersSet.getString(5));
				costumers.add(toAdd);
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return costumers;
	}
	
	public Customer getOneCustomer(int customerID) {
		
		String query = "SELECT * FROM `project.1`.`costumers` WHERE ('ID' = '" + customerID + "') ;\r\n";
		ResultSet costumersSet = null;
		Customer result = null;
		
		try {
			costumersSet = m_connectionPool.getConnection().createStatement().executeQuery(query);
		
		while(costumersSet.next())
		{
				result = new Customer(costumersSet.getInt(1), costumersSet.getString(2), costumersSet.getString(3), costumersSet.getString(4), costumersSet.getString(5));
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
