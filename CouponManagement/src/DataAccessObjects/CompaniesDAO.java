package DataAccessObjects;
import java.util.*;
import JavaBeans.*;

public interface CompaniesDAO {
	public boolean isCompanyExists(String email, String password);
	public void addCompany(Company company);
	public void updateCompany(Company company);
	public void deleteCompany(int companyID);
	public ArrayList<Company> getAllCompanies();
	public Company getOneCompany(int companyID);
	public ArrayList<Coupon> getAllCouponsByCompanyID(int companyID);
	public boolean isEmailExists(String email);
	public boolean isNameExists(String name);
	public Company getOneCompanyByName(String name);
	public int getCompanyIdByEmailAndPassword(String email, String password);
}
