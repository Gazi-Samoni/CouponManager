package Facade;
import DataAccessObjects.*;

public abstract class ClientFacade {
	CompaniesDAO m_companies;
	CouponsDAO m_coupons;
	CustomersDAO m_customers;
	
	public ClientFacade()
	{
		m_companies = new CompaniesDBDAO();
		m_companies = new CompaniesDBDAO();
		m_customers = new CustomersDBDAO();
	}
	public abstract boolean login(String email,String password);

}
