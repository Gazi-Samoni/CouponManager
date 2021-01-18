package Test;

import Facade.*;
import JavaBeans.*;
import Job.CouponExpiraitionDailyJob;
import Login.LoginManager;
import Table.ClearDB;

import java.text.SimpleDateFormat;
import java.util.*;
import DataAccessObjects.*;

public class mainClass {
	public static void main(String[] args)
	{
		(new CouponExpiraitionDailyJob()).run();
		
		
		LoginManager loginManager  = LoginManager.getInstance();
		
		ClearDB.ClearDBTables();
		
		loginManager.login(email, password, clientType)
		

		long millis=System.currentTimeMillis(); 
		java.sql.Date date = new java.sql.Date(millis);
	
		//create coupons
		Coupon coupon = new Coupon(51,21,Category.Electricity,"AC","test1",date,date,5,3.6,"temp");
		Coupon coupon2 = new Coupon(52,23,Category.Food,"AC1","test31",date,date,5,3.6,"temp");
		Coupon coupon3 = new Coupon(51,21,Category.Restaurant,"AC","test1",date,date,5,3.6,"temp");
		

				
	}
	void administratorUserTest(AdminFacade adminFacade)
	{
		System.out.println("------------------Administrator Test------------------");
		
		//Login
		System.out.println("Administrator Login: " + adminFacade.login("admin@admin.com","admin"));
		
		//Add new Company
		Company company = new Company(21,"Amdocs","Amdocs@Amdocs.com","1234");
		Company company2 = new Company(22,"Intel","Intel@Intel.com","1234");
		Company company3 = new Company(23,"Microsoft","Microsoft@Microsoft.com","1234");
		adminFacade.addCompany(company);
		adminFacade.addCompany(company2);
		adminFacade.addCompany(company3);		
		//Failure test 
			//exists name
			Company company4 = new Company(24,"Amdocs","a@a.com","1234");	
			adminFacade.addCompany(company4);
			//exists email
			company4.setEmail("Intel@Intel.com");
			company4.setName("test2");
			adminFacade.addCompany(company4);
		
		//Update Company
			//Edit Password 
			company3.setPassword("8888");
			adminFacade.updateCompany(company3);
			//Edit ID -> fail
			company.setID(25);
			adminFacade.updateCompany(company);
					
			//Edit Company Name -> fail
			company2.setName("zerto");
			adminFacade.updateCompany(company2);
					
			//restore original info
			company.setID(21);
			company2.setName("Intel");
		
		//Delete Company
		adminFacade.deleteCompany(company.getID());
		
		//Get all Companies
		ArrayList<Company> companies = adminFacade.getAllCompanies();
		for (int i = 0; i < companies.size(); i++) {
			System.out.println(companies.get(i).toString());
		}
		//Get one Company
		Company company5 = adminFacade.getOneCompany(company2.getID());
		System.out.println(company5.toString());
		
		
		//Add new customers
		Customer customer = new Customer(31,"Abed","shalgam","a@b.com","1234");
		Customer customer2 = new Customer(32,"Gazi","Samoni","b@c.com","1234");
		adminFacade.addCustomer(customer);
		adminFacade.addCustomer(customer2);
		
		//add customer with exists email	
		Customer customer3 = new Customer(32,"john","kyle","b@c.com","1234");
		adminFacade.addCustomer(customer3);
		
		//update customer
		customer.setLastName("magregor");
		adminFacade.updateCustomer(customer);
			//Edit customer id -> failed
			customer.setId(35);
			adminFacade.updateCustomer(customer);
			
			//reset data
			customer.setId(31);
		
		//Delete customer
		adminFacade.deleteCustomer(customer.getId());
		
		//Get all customers
		ArrayList<Customer> customers = adminFacade.getAllCustomer();
		for (int i = 0; i < customers.size(); i++) {
			System.out.println(customers.get(i).toString());
		};
		
		//Get one customer
		Customer customer4 = adminFacade.getOneCustomer(customer2.getId());
		System.out.println(customer4.toString());
		
	}
	void companyUserTest(CompanyFacade companyFacade )
	{	
		System.out.println("------------------Company Test------------------");
		//Login
		if(companyFacade.login("Amdocs@Amdocs.com","1234")) {
			System.out.println("Company Login");
		}	
		
		//Add Coupon
		long millis=System.currentTimeMillis(); 
		java.sql.Date date = new java.sql.Date(millis);
		Coupon coupon = new Coupon(54,21,Category.Vacation,"AC","amdocs from companyUserTest ",date,date,5,3.6,"temp");
		companyFacade.addCoupon(coupon);
			//Same Title -> failed
			Coupon coupon2 = new Coupon(55,23,Category.Food,"AC","amdocs from companyUserTest",date,date,3,3.6,"temp");
			companyFacade.addCoupon(coupon2);	
		
		//Update coupon
		coupon.setAmount(10);
		coupon.setDescription("amdocs after edit");
		companyFacade.updateCoupon(coupon);
		
		//Delete coupon
		companyFacade.deleteCoupon(coupon.getID());
		
		//Get All Coupons
		ArrayList<Coupon> coupons = companyFacade.getCompanyCoupons();	
		for(Coupon var:coupons)
		{
			System.out.println(var.toString());
		}
		//Get All Coupons by category
		ArrayList<Coupon> coupons2 = companyFacade.getCompanyCoupons(Category.Restaurant);	
		for(Coupon var:coupons2)
		{
			System.out.println(var.toString());
		}
		
		//Get All Coupons by category
		ArrayList<Coupon> coupons3 = companyFacade.getCompanyCoupons(70);	
		for(Coupon var:coupons3)
		{
			System.out.println(var.toString());
		}
		
		//Get Company details
		Company company = companyFacade.getCompanyDetails();
		System.out.println(company.toString());
		
	}
	void CustomerUserTest(CustomerFacade customerFacade)
	{
		long millis=System.currentTimeMillis(); 
		java.sql.Date date = new java.sql.Date(millis);
		@SuppressWarnings("deprecation")
		java.sql.Date dateExpierd = new java.sql.Date(1,1,2020);
		
		Coupon coupon = new Coupon(54,21,Category.Vacation,"AC","amdocs from CustomerUserTest ",date,date,5,3.6,"temp");
		Coupon coupon2 = new Coupon(54,21,Category.Food,"AB","amdocs from CustomerUserTest->zero amount ",date,date,0,3.6,"temp");
		Coupon coupon3 = new Coupon(54,21,Category.Food,"AB","amdocs from CustomerUserTest->expierd date ",date,dateExpierd,0,3.6,"temp");
		//Purchase Coupon
		customerFacade.purchaseCoupon(coupon);
			//repurchase same coupon ->fails
			customerFacade.purchaseCoupon(coupon);
			//Purchase Coupon with amount 0 -> fails
			customerFacade.purchaseCoupon(coupon2);
			//Purchase Coupon with expired date -> fails
			customerFacade.purchaseCoupon(coupon3);
		
		
		//Get customer coupons
		ArrayList<Coupon> custumerCoupons = customerFacade.getCustomerCoupons();
		for(Coupon var:custumerCoupons)
		{
			System.out.println(var.toString());
		}
		//Get customer coupons by Category
		ArrayList<Coupon> custumerCoupons2 = customerFacade.getCustomerCoupons(Category.Food);
		for(Coupon var:custumerCoupons2)
		{
			System.out.println(var.toString());
		}
		//Get customer coupons by max price
		ArrayList<Coupon> custumerCoupons3 = customerFacade.getCustomerCoupons(20);
		for(Coupon var:custumerCoupons3)
		{
			System.out.println(var.toString());
		}
		//Get customer details
		Customer customer = customerFacade.getCustomerDetails();
		System.out.println(customer.toString());
		
	}
	
}
