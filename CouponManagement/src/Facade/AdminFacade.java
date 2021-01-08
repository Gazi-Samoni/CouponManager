package Facade;
import java.util.ArrayList;

import JavaBeans.*;
import ConnectionUtils.*;
import java.sql.Connection;

public class AdminFacade extends ClientFacade {
	ConnectionPool m_connetionPool;
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
		if(this.m_companies.isCompanyExists(company.get_email(), company.get_password()))
		{
			System.out.println("The Company already exists");
		}
		else {
			this.m_companies.addCompany(company);
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
