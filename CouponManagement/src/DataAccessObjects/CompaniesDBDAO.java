package DataAccessObjects;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import JavaBeans.*;
import ConnectionUtils.*;

public class CompaniesDBDAO implements CompaniesDAO{
	
	private ConnectionPool m_connectionPool=null;
	
	public boolean isCompanyExists(String email, String password) {
		return true;
	}

	public void addCompany(Company company) {
		
		String query = "INSERT INTO `project.1`.`companies` (`ID`, `NAME`, `EMAIL`, `PASSWORD`) "
				+ "VALUES " + "('"+ company.get_id()  +"', '"+ company.get_name() +"', '"+ company.get_email() + "', '" + company.get_password() + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateCompany(Company company) {
		
		String query = "UPDATE `project.1`.`companies` SET NAME = '" + company.get_name() + "', EMAIL = '" + company.get_email() + "', PASSWORD = '"+ company.get_password() +"' WHERE ('ID' = '" + company.get_id() + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCompany(int companyID) {
		
		String query = "DELETE FROM `project.1`.`companies` WHERE ('ID' = '" + companyID + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Company> getAllCompanies(){
		
		String query = "SELECT * FROM `project.1`.`companies` ;\r\n";
		ResultSet companiesSet = null;
		ArrayList<Company> companies = new ArrayList<Company>();
		
		try {
			companiesSet = m_connectionPool.getConnection().createStatement().executeQuery(query);
		
		while(companiesSet.next())
		{
				Company toAdd = new Company(companiesSet.getInt(1), companiesSet.getString(2), companiesSet.getString(3), companiesSet.getString(4));
				companies.add(toAdd);
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return companies;
	}
	
	public Company getOneCompany(int companyID) {
		
		String query = "SELECT * FROM `project.1`.`companies` WHERE ('ID' = '" + companyID + "') ;\r\n";
		ResultSet companiesSet = null;
		Company result = null;
		
		try {
			companiesSet = m_connectionPool.getConnection().createStatement().executeQuery(query);
		
		while(companiesSet.next())
		{
				result = new Company(companiesSet.getInt(1), companiesSet.getString(2), companiesSet.getString(3), companiesSet.getString(4));
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	public boolean isEmailExists(String email)
	{
		return true;
	}
}
