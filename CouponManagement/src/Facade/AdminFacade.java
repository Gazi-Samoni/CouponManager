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
		if(this.m_companies.isEmailExists(company.getEmail()))
		{
			System.out.println("Company's email already exists");
		}
		else if(this.m_companies.isNameExists(company.getName()))
		{
			System.out.println("Company's name already exists");
		}
		else 
		{
			this.m_companies.addCompany(company);
			System.out.println("company added");
		}
	}
	public void updateCompany(Company company)//need to check if i can block EDIT ID from DAO. // handshake check
	{
		Company tempCompany = this.m_companies.getOneCompany(company.getID()); 
		Company tempCompany2 = this.m_companies.getOneCompanyByName(company.getName()); 
		
		if(tempCompany== null || tempCompany2 == null)
		{
			System.out.println("Invaild input");
		}
		else 
		{
			if(!tempCompany.getName().equals(tempCompany2.getName()) || tempCompany.getID() != tempCompany2.getID())
			{
				System.out.println("Invaild input: u can't edit company's name/id");
			}
			else
			{
				this.m_companies.updateCompany(company);
				System.out.println("company updated");
			}
		}
	}
	public void deleteCompany(int companyID)
	{
		Company company = this.m_companies.getOneCompany(companyID);
		ArrayList<Coupon> coupons = company.getCoupons();
		deleteCouponsHistory(coupons);
		this.m_companies.deleteCompany(companyID);
		System.out.println("company deleted");
	}
	private void deleteCouponsHistory(ArrayList<Coupon> coupons) {
		Coupon coupon;
		
		while(!coupons.isEmpty())
		{
			coupon = coupons.get(0);
			deleteCouponFromCustomerHistory(coupon.getID());
			this.m_coupons.deleteCoupon(coupon.getID());
			coupons.remove(0);
		}	
	}
	private void deleteCouponFromCustomerHistory(int couponID) {
		ResultSet customerVsCouponTable=null;
		
		try {		
			customerVsCouponTable = m_coupons.getCustomerVsCouponTableByCouponID(couponID);
			
			while(customerVsCouponTable.next())
			{
				int customerID = customerVsCouponTable.getInt(1);
				ArrayList<Coupon> customerCoupons =  m_customers.getOneCustomer(customerID).getCoupons();
				
				for (int i = 0; i < customerCoupons.size(); i++) {
					if(customerCoupons.get(i).getID() == couponID ) 
					{
						customerCoupons.remove(i);
					}
				}
			}
			//Delete from customers_vs_coupons table			
			m_coupons.deleteCouponFromCustomerVsCouponTableByID(couponID);
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
		if(coupons == null)
		{
			System.out.println("no coupons purchase for : " + customerID);
		}
		else {
			for(Coupon var:coupons)
			{
				this.m_coupons.deleteCopounPurchase(customerID, var.getID());
			}
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
