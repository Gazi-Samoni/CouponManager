package DataAccessObjects;
import java.util.*;
import JavaBeans.*;

public interface CustomersDAO {
	public boolean isCustomerExists(String email, String password);
	public void addCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public void deleteCustomer(int customerID);
	public ArrayList<Customer> getAllCustomers();
	public Customer getOneCustomer(int customerID);
	public boolean isCustomerEmailExists(String email);
	public int getCustomerIdByEmailAndPassword(String email, String password);
}
