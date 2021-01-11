package DataAccessObjects;
import java.sql.ResultSet;
import java.util.*;
import JavaBeans.*;

public interface CouponsDAO {
	public void addCoupon(Coupon coupon);
	public void updateCoupon(Coupon coupon);
	public void deleteCoupon(int couponID);
	public ArrayList<Coupon> getAllCoupons();
	public Coupon getOneCoupon(int couponID);
	public void addCopounPurchase(int customerID, int couponID);
	public void deleteCopounPurchase(int customerID, int couponID);
	public ResultSet GetTableByID(int couponID);
	public void executeQueryByID(int couponID);
}

