package Facade;
import java.util.ArrayList;

import JavaBeans.*;
import ConnectionUtils.*;


public class AdminFacade extends ClientFacade {
	ConnectionPool m_connetionPool = ConnectionPool.getInstance();
	
	public static void main(String[] args)
	{
		AdminFacade adminFacade = new AdminFacade();
		System.out.println("Login" + adminFacade.login("admin@admin.com","admin"));
		adminFacade.addCompany(new Company(1,"gazi","g@g.com","11"));
		adminFacade.addCompany(new Company(1,"gazi","g@g.com","11"));
	}
	
	public AdminFacade()
	{
		
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
	public void updateCompany(Company company)
	{
		this.m_companies.updateCompany(company);
	}
	public void deleteCompany(int companyID)
	{
		this.m_companies.deleteCompany(companyID);
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
