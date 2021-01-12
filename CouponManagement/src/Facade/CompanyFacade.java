package Facade;
import JavaBeans.*;
import java.util.*;

public class CompanyFacade extends ClientFacade {
	private int m_companyID;
	

	public int getCompanyID() {
		return m_companyID;
	}
	public CompanyFacade(int companyID)
	{
		m_companyID = companyID;
	}
	@Override
	public boolean login(String email, String password) {
		
		return m_companies.isCompanyExists(email, password);
	}
	public void addCoupon(Coupon coupon)
	{
		ArrayList<Coupon> coupons = m_coupons.getAllCouponsByCompanyID(this.m_companyID);
		boolean isExists= false;
		for(Coupon var:coupons)
		{
			if(var.getTitle().equals(coupon.getTitle()))
			{
				isExists=true;
				System.out.println(var.getTitle() + "`s coupon already exists");
			}
		}
		if(!isExists)
		{
			m_coupons.addCoupon(coupon);
		}
		
	}
	public void updateCoupon(Coupon coupon)
	{
		
	}
	public void deleteCoupon(int couponID)
	{
		
	}
	public ArrayList<Coupon> getCompanyCoupons()
	{
		return null;
	}
	public ArrayList<Coupon> getCompanyCoupons(Category category)
	{
		return null;
	}
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice)
	{
		return null;
	}
	public Company getCompanyDetails()
	{
		return null;
	}
	

	
}
