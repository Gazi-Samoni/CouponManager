package Facade;
import java.sql.SQLException;
import java.util.ArrayList;

import JavaBeans.*;
import ConnectionUtils.*;


public class AdminFacade extends ClientFacade {
	ConnectionPool m_connetionPool;
	
	public static void main(String[] args)
	{
		AdminFacade adminFacade = new AdminFacade();
		System.out.println("Login" + adminFacade.login("admin@admin.com","admin"));
		adminFacade.addCompany(new Company(1,"gazi","g@g.com","11"));
		adminFacade.addCompany(new Company(1,"gazi","g@g.com","11"));
		
		
	}
	
	public AdminFacade()
	{
		m_connetionPool = ConnectionPool.getInstance();
	}
	@Override
	public boolean login(String email, String password) {
		if(email.equals("admin@admin.com") && password.equals("admin"))
			return true;
		else 
			return false;
	}
	public void addCompany(Company company)
	{
		if(this.m_companies.isEmailExists(company.get_email()))
		{
			System.out.println("Company's email already exists");
		}
		else if(this.m_companies.isNameExists(company.get_name()))
		{
			System.out.println("Company's name already exists");
		}
		else 
		{
			this.m_companies.addCompany(company);
			System.out.println("added");
		}
	}
	public void updateCompany(Company company)//need to check if i can block EDIT ID from DAO. // handshake check
	{
		Company tempCompany = this.m_companies.getOneCompany(company.get_id());
		Company tempCompany2 = this.m_companies.getOneCompanyByName(company.get_name());
		
		if(tempCompany.get_name() != tempCompany2.get_name() || tempCompany.get_id() != tempCompany2.get_id() )
		{
			System.out.println("Invaild input: u can't edit company's name/id");
			
		}
		else if(tempCompany != null)
		{	
			if(tempCompany.get_name() != company.get_name())
			{
				System.out.println("Can't change company`s name");
			}
			else
			{
				this.m_companies.updateCompany(company);
			}
			
		}
		else
		{
			System.out.println("Can't find " + company.get_id() + "this company in DB (you can't change company id");
		}
	}
	public void deleteCompany(int companyID)
	{
		Company company = this.m_companies.getOneCompany(companyID);
		ArrayList<Coupon> coupons = company.get_coupons();
		deleteCoupons(coupons);
		

		
		this.m_companies.deleteCompany(companyID);
	}
	private void deleteCoupons(ArrayList<Coupon> coupons) {
		Coupon coupon;
		
		while(!coupons.isEmpty())
		{
			coupon = coupons.get(0);
			deleteCouponsIDFromCustVsCoupon(coupon.getId());
			this.m_coupons.deleteCoupon(coupon.getId());
		}
		
	}

	private void deleteCouponsIDFromCustVsCoupon(int CouponID) {
		String query = "DELETE FROM `project.1`.`customers_vs_coupons` WHERE ('COUPON_ID' = '" + CouponID + "');\r\n";
		try {
			m_connetionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.getMessage();
		}
		
	}

	public ArrayList<Company> getAllCompanies()
	{
		return this.m_companies.getAllCompanies();
	}
	public Company getOneCompany(int companyID)
	{
		return this.m_companies.getOneCompany(companyID);
	}
	public void addCustomer(Customer customer)
	{
		this.m_customers.addCustomer(customer);
	}
	public void updateCustomer(Customer customer)
	{
		this.m_customers.updateCustomer(customer);
	}
	public void deleteCustomer(int customerID)
	{
		this.m_customers.deleteCustomer(customerID);
	}
	public ArrayList<Customer> getAllCustomer()
	{
		return this.m_customers.getAllCustomers();
	}
	public Customer getOneCustomer(int customerID)
	{
		return this.m_customers.getOneCustomer(customerID);
	}
}
