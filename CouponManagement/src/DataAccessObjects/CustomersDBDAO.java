package DataAccessObjects;
import java.util.*;

import ConnectionUtils.ConnectionPool;
import JavaBeans.*;

public class CustomersDBDAO implements CustomersDAO {
	private ConnectionPool m_connectionPool=null;
	
	public boolean isCustomerExists(String email, String password) {
		return true;
	}
	public void addCustomer(Customer customer) {
		
	}
	public void updateCustomer(Customer customer) {
		
	}
	public void deleteCustomer(int customerID) {
		
	}
	public ArrayList<Customer> getAllCustomers(){
		return new ArrayList<Customer>();
	}
	public Customer getOneCustomer(int customerID) {
		return new Customer();
	}
}
