package Facade;
import JavaBeans.*;

import java.sql.*;
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
	public CompanyFacade() {}
	
	@Override
	public boolean login(String email, String password) {
		
		return m_companies.isCompanyExists(email, password);
	}
	public void addCoupon(Coupon coupon)
	{
		if(coupon.getCompanyID() == m_companyID)
		{
			if(isCouponTitleExists(coupon)==false)
			{
				m_coupons.addCoupon(coupon);
			}
			else
			{
				System.out.println(coupon.getTitle() + "`s coupon already exists");
			}
		}
		else
		{
			System.out.println(coupon.getTitle() + " This coupon doesn`t belong to our company");
		}
		
	}
	private boolean isCouponTitleExists(Coupon coupon)
	{
		ArrayList<Coupon> coupons = m_companies.getAllCouponsByCompanyID(this.m_companyID);
		boolean isExists= false;
		for(Coupon var:coupons)
		{
			if(var.getTitle().equals(coupon.getTitle()))
			{
				isExists=true;
			}
		}
		return isExists;
		
	}
	public void updateCoupon(Coupon coupon)
	{
		if(coupon.getCompanyID() != this.m_companyID)
		{
			System.out.println("Can't change Company id");
		}
		else if(isCouponTitleExists(coupon))
		{
			System.out.println("The coupon: "+ coupon.getTitle() +" already exists");
		}
		else
		{
			m_coupons.addCoupon(coupon);
		}
	}
	public void deleteCoupon(int couponID)
	{
		ResultSet customerVsCouponTable = m_coupons.getCustomerVsCouponTableByCouponID(couponID);
		
		try {
			while(customerVsCouponTable.next())
			{
				int currCouponID = customerVsCouponTable.getInt(2);
				if(currCouponID == couponID)
				{
					m_coupons.deleteCopounPurchase(currCouponID, customerVsCouponTable.getInt(1));
				}
			}
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
		ArrayList<Coupon> coupons = m_companies.getOneCompany(m_companyID).getCoupons();
		
		for(Coupon var: coupons)
		{
			if(var.getID() == couponID)
			{
				coupons.remove(var);
			}
		}
		
	}
	public ArrayList<Coupon> getCompanyCoupons()
	{
		return m_companies.getAllCouponsByCompanyID(this.m_companyID);
	}
	public ArrayList<Coupon> getCompanyCoupons(Category category)
	{
		ArrayList<Coupon> tempCoupons = m_companies.getAllCouponsByCompanyID(this.m_companyID);
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		for(Coupon var:tempCoupons)
		{
			if(var.getCategory() == category)
			{
				coupons.add(var);
			}
		}
		return coupons;
	}
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice)
	{
		ArrayList<Coupon> tempCoupons = m_companies.getAllCouponsByCompanyID(this.m_companyID);
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		for(Coupon var:tempCoupons)
		{
			if(var.getPrice() <= maxPrice)
			{
				coupons.add(var);
			}
		}
		return coupons;
	}
	public Company getCompanyDetails()
	{
		return m_companies.getOneCompany(this.m_companyID);
	}
	
	public void setID(int companyID) {
		m_companyID = companyID;
	}
	

	
}
