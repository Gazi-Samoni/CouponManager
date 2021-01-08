package Facade;
import DataAccessObjects.*;

public abstract class ClientFacade {
	protected CompaniesDAO m_companies;
	protected CouponsDAO m_coupons;
	protected CustomersDAO m_customers;
	
	public ClientFacade()
	{
		m_companies = new CompaniesDBDAO();
		m_companies = new CompaniesDBDAO();
		m_customers = new CustomersDBDAO();
	}
	public abstract boolean login(String email,String password);

}
