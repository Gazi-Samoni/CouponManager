package Facade;
import DataAccessObjects.*;

public abstract class ClientFacade {
	protected CompaniesDAO m_companies;
	protected CouponsDAO m_coupons;
	protected CustomersDAO m_customers;
	
	public ClientFacade()
	{
		m_companies = new CompaniesDBDAO();
		m_coupons = new CouponsDBDAO();
		m_customers = new CustomersDBDAO();
	}
	
	public abstract boolean login(String email,String password);
	
	public CompaniesDAO getCompanies() {
		return m_companies;
	}


	public CouponsDAO getCoupons() {
		return m_coupons;
		
	}


	public CustomersDAO getCustomers() {
		return m_customers;
	}

}
