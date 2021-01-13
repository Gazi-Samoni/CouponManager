package DataAccessObjects;
import java.sql.ResultSet;
import java.util.*;
import JavaBeans.*;

public interface CouponsDAO {
	public void addCoupon(Coupon coupon);
	public void updateCoupon(Coupon coupon);
	public void deleteCoupon(int couponID);
	public ArrayList<Coupon> getAllCoupons();
	public ArrayList<Coupon> getAllCouponsByCompanyID(int companyID);
	public Coupon getOneCoupon(int couponID);
	public void addCopounPurchase(int customerID, int couponID);
	public void deleteCopounPurchase(int customerID, int couponID);
	public ResultSet getCustomerVsCouponTableByCouponID(int couponID);
	public ResultSet getCustomerVsCouponTableByCutomerID(int customerID);
	public void executeQueryByID(int couponID);
	
}

