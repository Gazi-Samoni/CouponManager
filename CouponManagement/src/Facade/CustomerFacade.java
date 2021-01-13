package Facade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import JavaBeans.*;

public class CustomerFacade extends ClientFacade  {
	private int m_customerID;
	
	
	public CustomerFacade(int customerID) {
		this.m_customerID = customerID;
	}
	@Override
	public boolean login(String email, String password) {
		
		return m_customers.isCustomerExists(email,password);
	}
	public void purchaseCoupon(Coupon coupon)
	{
		ResultSet customerVScoupon = m_coupons.getCustomerVsCouponTableByCutomerID(this.m_customerID);
		boolean purchased = false;
		try {
			while(customerVScoupon.next())
			{
				if(customerVScoupon.getInt(2) == coupon.getID())
				{
					purchased =true;
					System.out.println("You already purched this coupon");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		//Not purchased
		if(!purchased)
		{
			if(coupon.getAmount()>0)
			{
				if(isVaildCouponDate(coupon.getEndDate()))
				{
					m_coupons.addCopounPurchase(this.m_customerID, coupon.getID());
					coupon.setAmount(coupon.getAmount()-1);
					m_coupons.updateCoupon(coupon);
				}
				else
				{
					System.out.println("Coupon Expired date");
				}
			}
			else
			{
				System.out.println("There is no more left of this coupon");
			}
		}
	}
	

	private boolean isVaildCouponDate(Date endDate) {

		long millis=System.currentTimeMillis(); 
		java.sql.Date NowDate = new java.sql.Date(millis);
				
		return endDate.before(NowDate);
	}
	public ArrayList<Coupon> getCustomerCopons(){
		ResultSet customerVScoupon = m_coupons.getCustomerVsCouponTableByCutomerID(this.m_customerID);
		ArrayList<Coupon> coupons = null;
		try {
			while(customerVScoupon.next())
			{
				coupons.add(m_coupons.getOneCoupon(customerVScoupon.getInt(2)));
				
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return coupons;
	}
	public ArrayList<Coupon> getCustomerCopons(Category category){
		ArrayList<Coupon> coupons = getCustomerCopons();
		for(Coupon var:coupons)
		{
			if(var.getCategory() != category)
			{
				coupons.remove(var);
			}
		}
		return coupons;
		
	}
	public ArrayList<Coupon> getCustomerCopons(double maxPrice){
		ArrayList<Coupon> coupons = getCustomerCopons();
		for(Coupon var:coupons)
		{
			if(var.getPrice() > maxPrice)
			{
				coupons.remove(var);
			}
		}
		return coupons;
	}
	public Customer getCustomerDetails()
	{
		return m_customers.getOneCustomer(this.m_customerID);
	}
}
