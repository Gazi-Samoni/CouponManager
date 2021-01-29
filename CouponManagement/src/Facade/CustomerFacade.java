package Facade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import JavaBeans.*;

public class CustomerFacade extends ClientFacade  {
	private int m_customerID;
	
	public CustomerFacade() {}
	public CustomerFacade(int customerID) { this.m_customerID = customerID;}
	
	
	@Override
	public boolean login(String email, String password) {
		
		return m_customers.isCustomerExists(email,password);
	}
	public void purchaseCoupon(Coupon coupon)
	{
		boolean purchased = false;
		ResultSet customerVScoupon = m_customers.getCustomerVsCouponTableByCutomerID(this.m_customerID);
		
		try {
			while(customerVScoupon.next())
			{
				if(customerVScoupon.getInt(2) == coupon.getID())
				{
					purchased =true;
					System.out.println("You already purchased this coupon");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		//if not purchased
		if(!purchased)
		{
			if(coupon.getAmount()>0)
			{
				if(isVaildCouponDate(coupon.getEndDate()))
				{
					m_coupons.addCopounPurchase(this.m_customerID, coupon.getID());
					coupon.setAmount(coupon.getAmount()-1);
					m_coupons.updateCoupon(coupon);
					m_customers.getOneCustomer(this.m_customerID).getCoupons().add(coupon);
				}
				else
				{
					System.out.println("Coupon Expired date");
				}
			}
			else
			{
				System.out.println(coupon.getTitle() + " sold out :(");
			}
		}
	}
	private boolean isVaildCouponDate(Date endDate) {

		long millis=System.currentTimeMillis(); 
		java.sql.Date NowDate = new java.sql.Date(millis);
				
		return endDate.after(NowDate);
	}
	public ArrayList<Coupon> getCustomerCoupons(){
	
		return m_customers.getCustomerCoupons(this.m_customerID);
	}
	public ArrayList<Coupon> getCustomerCoupons(Category category){
		ArrayList<Coupon> coupons = getCustomerCoupons();
		
		for (int i = 0; i < coupons.size(); i++) {
			if(coupons.get(i).getCategory() != category)
			{
				coupons.remove(i);
			}
		}
		
		
		return coupons;
		
	}
	public ArrayList<Coupon> getCustomerCoupons(double maxPrice){
		ArrayList<Coupon> coupons = getCustomerCoupons();
		for(Coupon var:coupons)
		{
			if(var.getPrice() > maxPrice)
			{
				coupons.remove(var);
			}
		}
		return coupons;
	}
	public Customer getCustomerDetails(){
		Customer customer= m_customers.getOneCustomer(this.m_customerID);
		
		customer.setCoupons(this.getCustomerCoupons());
	
		return customer;
			
	}
	public int getCustomerIdByEmailAndPassword(String email, String password){
		return m_customers.getCustomerIdByEmailAndPassword(email, password);
	}
	public Coupon getOneCoupon(int companyID,String couponTitle) {
		return m_coupons.getOneCoupon(companyID, couponTitle);
	}

	public void setID(int customerID) { m_customerID = customerID; }
	public int getID() { return m_customerID; }
}
