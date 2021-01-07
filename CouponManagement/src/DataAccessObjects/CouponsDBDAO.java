package DataAccessObjects;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import JavaBeans.*;
import ConnectionUtils.*;

public class CouponsDBDAO implements CouponsDAO {
	
	private ConnectionPool m_connectionPool=null;
	
	public void addCoupon(Coupon coupon) {
		
	}
	public void updateCoupon(Coupon coupon) {
		
	}
	public void deleteCoupon(int couponID) {
		
	}
	public ArrayList<Coupon> getAllCoupons(){
		return new ArrayList<Coupon>();
	}
	public Coupon getOneCoupon(int couponID) {
		return new Coupon();
	}
	public void addCopounPurchase(int customerID, int couponID) {
		
	}
	public void deleteCopounPurchase(int customerID, int couponID) {
		
	}
}
