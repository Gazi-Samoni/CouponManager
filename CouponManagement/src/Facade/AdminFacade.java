package Facade;
import java.sql.*;
import java.util.ArrayList;

import JavaBeans.*;



public class AdminFacade extends ClientFacade {
	
	public AdminFacade(){}
	
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
		// its okay i understand it now..
		// we should use ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY everywhere when we TAKE! info from DATA BASE
		if(tempCompany== null || tempCompany2 == null)
		{
			System.out.println("Invaild input");
		}
		else 
		{
			if(!tempCompany.get_name().equals(tempCompany2.get_name()) || tempCompany.get_id() != tempCompany2.get_id())
			{
				System.out.println("Invaild input: u can't edit company's name/id");
			}
			else
			{
				this.m_companies.updateCompany(company);
			}
		}
	}
	public void deleteCompany(int companyID)
	{
		Company company = this.m_companies.getOneCompany(companyID);
		ArrayList<Coupon> coupons = company.get_coupons();
		deleteCouponsHistory(coupons);
		this.m_companies.deleteCompany(companyID);
	}
	private void deleteCouponsHistory(ArrayList<Coupon> coupons) {
		Coupon coupon;
		
		while(!coupons.isEmpty())
		{
			coupon = coupons.get(0);
			deleteCouponFromCustomerHistory(coupon.getId());
			this.m_coupons.deleteCoupon(coupon.getId());
			coupons.remove(0);
		}	
	}
	private void deleteCouponFromCustomerHistory(int couponID) {
		ResultSet customerVsCouponTable=null;
		
		try {	
			
			customerVsCouponTable = m_coupons.getTableByID(couponID);
			
			while(customerVsCouponTable.next())
			{
				int customerID = customerVsCouponTable.getInt(1);
				ArrayList<Coupon> customerCoupons =  m_customers.getOneCustomer(customerID).getCoupons();
				for(Coupon var :customerCoupons)
				{
					if(var.getId() == couponID ) 
					{
						customerCoupons.remove(var);
					}
				}
			}

			//Delete from customers_vs_coupons table
			
			m_coupons.executeQueryByID(couponID);
			

		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
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
		if(m_customers.isCustomerEmailExists(customer.getEmail()))
		{
			System.out.println(customer.getEmail() + " already Exists");
		}
		else
		{
			this.m_customers.addCustomer(customer);
		}
	}
	public void updateCustomer(Customer customer)
	{
		this.m_customers.updateCustomer(customer);
	}
	public void deleteCustomer(int customerID)
	{
		ArrayList<Coupon> coupons = this.m_customers.getOneCustomer(customerID).getCoupons();
		//delete from customer_vs_coupon table
		for(Coupon var:coupons)
		{
			this.m_coupons.deleteCopounPurchase(customerID, var.getId());
		}
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
