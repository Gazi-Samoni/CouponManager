package DataAccessObjects;
import java.util.*;
import java.sql.*;
import JavaBeans.*;
import ConnectionUtils.*;

public class CompaniesDBDAO implements CompaniesDAO{
	
	private ConnectionPool m_connectionPool;
	
	public CompaniesDBDAO() {
		m_connectionPool = ConnectionPool.getInstance();
	}
	
	public boolean isCompanyExists(String email, String password) {
		boolean isExist = true;
		Connection connection = null;
		String query = "SELECT * FROM couponmanager.companies WHERE (EMAIL = '" + email + "' AND PASSWORD = '" + password + "') ;\r\n";
		ResultSet companySet = null;
		
		try {
			connection = m_connectionPool.getConnection();
			companySet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(companySet.next() == false)
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
	public void addCompany(Company company) {
		Connection connection = null;
		String query = "INSERT INTO couponmanager.companies (NAME, EMAIL, PASSWORD)" + "VALUES" + "('"+ company.getName() +"', '"+ company.getEmail() + "', '" + company.getPassword() + "');\r\n";
		
		try {
			connection = m_connectionPool.getConnection();
			connection.createStatement().executeUpdate(query);
			System.out.println("company added");
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
	public void updateCompany(Company company) { // reverse name set
		Connection connection = null;
		String query = "UPDATE couponmanager.companies SET NAME = '" + company.getName() +"', EMAIL = '" + company.getEmail() + "', PASSWORD = '"+ company.getPassword() +"' WHERE (ID = '" + company.getID() + "');\r\n";
		
		try {
			connection = m_connectionPool.getConnection();
			connection.createStatement().executeUpdate(query);
			System.out.println("company updated");
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
	public void deleteCompany(int companyID) {
		Connection connection = null;
		String query = "DELETE FROM couponmanager.companies WHERE (ID = '" + companyID + "');";
		
		try {
			connection = m_connectionPool.getConnection();
			connection.createStatement().executeUpdate(query);
			System.out.println("company deleted");
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
	public ArrayList<Company> getAllCompanies(){
		Connection connection = null;
		String query = "SELECT * FROM couponmanager.companies ;\r\n";
		ResultSet companiesSet = null;
		ArrayList<Company> companies = new ArrayList<Company>();
		
		try {
			connection = m_connectionPool.getConnection();
			companiesSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		
		while(companiesSet.next())
		{
				Company company = new Company(companiesSet.getInt(1), companiesSet.getString(2), companiesSet.getString(3), companiesSet.getString(4));
				company.setCoupons(getAllCouponsByCompanyID(company.getID()));
				companies.add(company);
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
			
		return companies;
	}
	public ArrayList<Coupon> getAllCouponsByCompanyID(int companyID){
		Connection connection=null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		String query = "SELECT * FROM couponmanager.coupons WHERE COMPANY_ID = '"+ companyID + "';";
		
		try {
			connection = m_connectionPool.getConnection();
			ResultSet couponsTable = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			while(couponsTable.next()){
				Coupon coupon = new Coupon(couponsTable.getInt(1),couponsTable.getInt(2), Category.FromInt(couponsTable.getInt(3)) ,couponsTable.getString(4),couponsTable.getString(5),couponsTable.getDate(6),couponsTable.getDate(7),couponsTable.getInt(8),couponsTable.getDouble(9),couponsTable.getString(10));
				coupons.add(coupon);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null){
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return coupons;
	}
	public Company getOneCompany(int companyID) {
		Connection connection=null;
		String query = "SELECT * FROM couponmanager.companies WHERE ID = '" + companyID + "';";
		ResultSet companiesSet = null;
		Company company = null;
			
		try {
			connection = m_connectionPool.getConnection();
			companiesSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(companiesSet.first()) {
				company = new Company(companiesSet.getInt("ID"), companiesSet.getString(2), companiesSet.getString(3), companiesSet.getString(4));	
				company.setCoupons(getAllCouponsByCompanyID(company.getID()));
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null){
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return company;
	}
	public boolean isEmailExists(String email) {
		Connection connection=null;
		String query = "SELECT EMAIL FROM couponmanager.companies WHERE (EMAIL = '" + email + "') ;\r\n";
		ResultSet companiesSet = null;
		
		try {
			connection = m_connectionPool.getConnection();
			companiesSet =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(companiesSet.next() == false){
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null){
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return true;
	}
	public boolean isNameExists(String name) {
		Connection connection=null;
		String query = "SELECT * FROM couponmanager.companies WHERE (NAME = '" + name + "') ;\r\n";
		ResultSet companiesSet = null;
		
		try {
			connection = m_connectionPool.getConnection();
			companiesSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(companiesSet.next() == false){
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null){
				m_connectionPool.restoreConnection(connection);
			}
		}
		return true;
	}
	public Company getOneCompanyByName(String name) {
		Connection connection=null;
		String query = "SELECT * FROM couponmanager.companies WHERE (NAME = '" + name + "');";
		ResultSet companiesSet = null;
		Company company = null;
		
		try {
			connection = m_connectionPool.getConnection();
			companiesSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if( companiesSet.first())
				company = new Company(companiesSet.getInt(1), companiesSet.getString(2), companiesSet.getString(3), companiesSet.getString(4));
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null){
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return company;
		
	}
	@Override
	public int getCompanyIdByEmailAndPassword(String email, String password) {
		// doesn't check if the email + password are valid, so the user of this function must make sure before using it.
		Connection connection=null;
		String query = "SELECT * FROM couponmanager.companies WHERE (EMAIL = '" + email + "' AND PASSWORD = '" + password + "');";
		ResultSet companySet = null;
		int companyID = -1;
		
		try {
			connection = m_connectionPool.getConnection();
			companySet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			companySet.first();
			companyID = companySet.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return companyID;
	}
}
