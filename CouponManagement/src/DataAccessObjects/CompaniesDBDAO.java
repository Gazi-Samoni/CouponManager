package DataAccessObjects;
import java.sql.SQLException;
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
		
		String query = "SELECT * FROM `project.1`.`companies` WHERE ('EMAIL' = '" + email + "' AND 'PASSWORD' = '" + password + "') ;\r\n";
		ResultSet companySet = null;
		
		try {
			companySet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(companySet.next() == false)
			{
				isExist = false;
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return isExist;
	}

	public void addCompany(Company company) {
		
		String query = "INSERT INTO `project.1`.`companies` (`ID`, `NAME`, `EMAIL`, `PASSWORD`)" + "VALUES" + "('"+ company.get_id()  +"', '"+ company.get_name() +"', '"+ company.get_email() + "', '" + company.get_password() + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void updateCompany(Company company) { // reverse name set
		
		String query = "UPDATE `project.1`.`companies` SET NAME = '" + company.get_name() +"', EMAIL = '" + company.get_email() + "', PASSWORD = '"+ company.get_password() +"' WHERE ('ID' = '" + company.get_id() + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	public void deleteCompany(int companyID) {
		
		String query = "DELETE FROM `project.1`.`companies` WHERE ('ID' = '" + companyID + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public ArrayList<Company> getAllCompanies(){
		
		String query = "SELECT * FROM `project.1`.`companies` ;\r\n";
		ResultSet companiesSet = null;
		ArrayList<Company> companies = new ArrayList<Company>();
		
		try {
			companiesSet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		
		while(companiesSet.next())
		{
				Company toAdd = new Company(companiesSet.getInt(1), companiesSet.getString(2), companiesSet.getString(3), companiesSet.getString(4));
				companies.add(toAdd);
		}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
			
		return companies;
	}
	
	public Company getOneCompany(int companyID) {
		
		String query = "SELECT * FROM `project.1`.`companies` WHERE ID = '" + companyID + "';";
		ResultSet companiesSet = null;
		Company company = null;
		
		try {
			companiesSet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(companiesSet.first()) {
				company = new Company(companiesSet.getInt("ID"), companiesSet.getString(2), companiesSet.getString(3), companiesSet.getString(4));	
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return company;
	}
	public boolean isEmailExists(String email)
	{
		String query = "SELECT EMAIL FROM `project.1`.`companies` WHERE (`EMAIL` = '" + email + "') ;\r\n";
		ResultSet companiesSet = null;
		
		try {
			companiesSet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(companiesSet.next() == false)
			{
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	public boolean isNameExists(String name)
	{
		String query = "SELECT * FROM `project.1`.`companies` WHERE (`NAME` = '" + name + "') ;\r\n";
		ResultSet companiesSet = null;
		
		try {
			companiesSet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(companiesSet.next() == false)
			{
				return false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	public Company getOneCompanyByName(String name)
	{
		String query = "SELECT * FROM `project.1`.`companies` WHERE (`NAME` = '" + name + "');";
		ResultSet companiesSet = null;
		Company company = null;
		
		try {
			companiesSet = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if( companiesSet.first())
				company = new Company(companiesSet.getInt(1), companiesSet.getString(2), companiesSet.getString(3), companiesSet.getString(4));
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return company;
		
	}
}
