package DataAccessObjects;
import java.util.*;
import java.sql.*;
import JavaBeans.*;
import ConnectionUtils.*;

public class CompaniesDBDAO implements CompaniesDAO{
	
	private ConnectionPool m_connectionPool=null;
	
	//TODO: wait for Daowd responed
	public boolean isCompanyExists(String email, String password) {
		return true;
	}

	public void addCompany(Company company) {
		
		String query = "INSERT INTO `project.1`.`companies` (`ID`, `NAME`, `EMAIL`, `PASSWORD`) VALUES ('115', 'Hazim', 'a@a.com', '82');\r\n";
		m_connectionPool.getConnection().crea
	}
	
	public void updateCompany(Company company) {
		
	}

	public void deleteCompany(int companyID) {
		
	}
	public ArrayList<Company> getAllCompanies(){
		return new ArrayList<Company>();
	}
	public Company getOneCompany(int companyID) {
		return new Company();
	}
	
	
}
