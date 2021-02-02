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
		
		String query = "SELECT * FROM couponmanager.customers WHERE (EMAIL = '" + email + "' AND PASSWORD = '" + password + "') ;\r\n";
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
		String query = "INSERT INTO couponmanager.customers (`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) "
				+ "VALUES " + "('"+ customer.getFirstName()  +"', '"+ customer.getLastName() +"', '"+ customer.getEmail() + "', '" + customer.getPassword() + "');";
		
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
		String query = "UPDATE couponmanager.customers SET FIRST_NAME = '"+ customer.getFirstName() +"', LAST_NAME = '"+ customer.getLastName() +"', EMAIL = '"+ customer.getEmail() +"', PASSWORD = '"+ customer.getPassword() +"' WHERE (ID = '" + customer.getId() + "');";
		
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
		String query = "DELETE FROM couponmanager.customers WHERE (ID = '" + customerID + "');";
		
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
		String query = "SELECT * FROM couponmanager.customers;";
		ResultSet costumersSet = null;
		ArrayList<Customer> costumers = new ArrayList<Customer>();
		
		try {
			connection = m_connectionPool.getConnection();
			costumersSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		
		while(costumersSet.next())
		{
				Customer customer = new Customer(costumersSet.getInt(1), costumersSet.getString(2), costumersSet.getString(3), costumersSet.getString(4), costumersSet.getString(5));
				this.getCustomerVsCouponTableByCutomerID(customer.getId());
				customer.setCoupons(getCustomerCoupons(customer.getId()));
				costumers.add(customer);
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
	public ArrayList<Coupon> getCustomerCoupons(int customerID){
		ResultSet customerVScoupon = this.getCustomerVsCouponTableByCutomerID(customerID);
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try {
			while(customerVScoupon.next())
			{
				coupons.add(this.getOneCoupon(customerVScoupon.getInt(2)));
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return coupons;
	}
	private Coupon getOneCoupon(int couponID) {
		Connection connection=null;
		Coupon coupon = null;
		String query = "SELECT * FROM couponmanager.coupons WHERE (ID = '" + couponID + "');";
		
		try {
			connection = m_connectionPool.getConnection();
			ResultSet couponsTable = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(couponsTable.next() == true)
			{
				coupon = new Coupon(couponsTable.getInt(1),couponsTable.getInt(2), Category.FromInt(couponsTable.getInt(3)) ,couponsTable.getString(4),couponsTable.getString(5),couponsTable.getDate(6),couponsTable.getDate(7),couponsTable.getInt(8),couponsTable.getDouble(9),couponsTable.getString(10));
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

		return coupon;
	}
	public Customer getOneCustomer(int customerID) {
		Connection connection=null;
		String query = "SELECT * FROM couponmanager.customers WHERE (ID = '" + customerID + "');";
		ResultSet costumersSet = null;
		Customer customer = null;
		

		try {
			connection = m_connectionPool.getConnection();
			costumersSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(costumersSet.first()) {
				customer = new Customer(costumersSet.getInt(1), costumersSet.getString(2), costumersSet.getString(3), costumersSet.getString(4), costumersSet.getString(5));
				customer.setCoupons(getCustomerCoupons(customer.getId()));
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
		
		return customer;
	}
	public boolean isCustomerEmailExists(String email)
	{
		Connection connection =null;
		String query = "SELECT * FROM couponmanager.customers WHERE (EMAIL = '" + email + "');";
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
	
	public ResultSet getCustomerVsCouponTableByCutomerID(int customerID)
	{
		Connection connection=null;
		ResultSet customerVScoupon = null;
		String query = "SELECT * FROM couponmanager.customers_vs_coupons WHERE (CUSTOMER_ID = '" + customerID + "');";
		
		try {
			connection = m_connectionPool.getConnection();
			customerVScoupon = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return customerVScoupon;
	}

	@Override
	public int getCustomerIdByEmailAndPassword(String email, String password) {
	// doesn't check if the email + password are valid, so the user of this function must make sure before using it.
		Connection connection=null;
		String query = "SELECT * FROM couponmanager.customers WHERE (EMAIL = '" + email + "' AND PASSWORD = '" + password + "');";
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
