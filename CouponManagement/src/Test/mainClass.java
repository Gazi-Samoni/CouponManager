package Test;

import Facade.*;
import JavaBeans.*;
import Job.CouponExpiraitionDailyJob;
import Table.DeleteDB;

import java.text.SimpleDateFormat;
import java.util.*;
import DataAccessObjects.*;

public class mainClass {
	public static void main(String[] args)
	{
		(new CouponExpiraitionDailyJob()).run();
		
		DeleteDB.deleteDBTables();
		
		AdminFacade adminFacade = new AdminFacade();
		System.out.println("Login" + adminFacade.login("admin@admin.com","admin"));
		
		
		//add Company test
		Company company = new Company(21,"Amdocs","Amdocs@Amdocs.com","1234");
		Company company2 = new Company(22,"Intel","Intel@Intel.com","1234");
		Company company3 = new Company(23,"Microsoft","Microsoft@Microsoft.com","1234");
		adminFacade.addCompany(company);
		adminFacade.addCompany(company2);
		adminFacade.addCompany(company3);
		//should fail
		adminFacade.addCompany(company);
		
				
		//update Company test
			//should fail
			company.setID(23);
			adminFacade.updateCompany(company);
			
			//restore original data
			company.setID(21);
			
			//should fail
			company2.setName("zerto");
			adminFacade.updateCompany(company2);
			
			////should succeed
			company3.setPassword("8888");
			adminFacade.updateCompany(company3);
			
			//restore original data
			company.setID(21);
			company2.setName("Intel");
		

		long millis=System.currentTimeMillis(); 
		java.sql.Date date = new java.sql.Date(millis);
	
		//create coupons
		Coupon coupon = new Coupon(51,company.getID(),Category.Electricity,"AC","test1",date,date,5,3.6,"temp");
		Coupon coupon2 = new Coupon(52,company.getID(),Category.Electricity,"AC1","test31",date,date,5,3.6,"temp");
		Coupon coupon3 = new Coupon(51,company.getID(),Category.Electricity,"AC","test1",date,date,5,3.6,"temp");
		CompanyFacade companyFacade = new CompanyFacade(company.getID());
		companyFacade.addCoupon(coupon);
		companyFacade.addCoupon(coupon2);
		
		
		//create customers
		Customer customer = new Customer(31,"Abed","shalgam","a@b.com","1234");
		Customer customer2 = new Customer(32,"soso","mmo","b@c.com","1234");
		//add customers
		adminFacade.addCustomer(customer);
		adminFacade.addCustomer(customer2);
		
		//add customer	
		adminFacade.addCustomer(customer);
		
		CustomerFacade customerFacade = new CustomerFacade(customer.getId());
		customerFacade.purchaseCoupon(coupon);
		customerFacade.purchaseCoupon(coupon2);
		
		System.out.println(customerFacade.getCustomerDetails().getCoupons().get(0).getID());
		System.out.println(customerFacade.getCustomerDetails().getCoupons().get(1).getID());
		
		//delete 
		ArrayList<Company> companies = adminFacade.getAllCompanies();
		System.out.println(companies.get(0).toString());
		
		
		Company company4 = adminFacade.getOneCompany(company2.getID());
		System.out.println(company4.toString());
		
		
		//update customer
		customer.setLastName("magregor");
		adminFacade.updateCustomer(customer);
		
		//delete customer
		
		
		//get all customers
		ArrayList<Customer> customers = adminFacade.getAllCustomer();
		System.out.println(customers.get(0).toString());
		
		Customer customer3 = adminFacade.getOneCustomer(customer2.getId());
		System.out.println(customer3.toString());
		
		//delete
		//adminFacade.deleteCustomer(customer.getId());
		adminFacade.deleteCompany(company.getID());
		
		
			
	}
}
