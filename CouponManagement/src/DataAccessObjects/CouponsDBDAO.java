package DataAccessObjects;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;
import JavaBeans.*;
import ConnectionUtils.*;

public class CouponsDBDAO implements CouponsDAO {
	
	private ConnectionPool m_connectionPool=null;
	
	public void addCoupon(Coupon coupon) {
		
		String query = "INSERT INTO `project.1`.`coupons` (`ID`, `COMPANY_ID`, `CATEGORY_ID`, `TITLE` ,`DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) "
				+ "VALUES " + "('" + coupon.getId() + "' '"+ coupon.getCompanyID()  +"', '"+ coupon.getCategory() +"', '"+ coupon.getTitle() + "', '" + coupon.getDescription() + "', '" + coupon.getStartDate() + "', '" + coupon.getEndDate() + "', '" + coupon.getAmount() + "', '" + coupon.getPrice() + "', '" + coupon.getImage() + "');\r\n";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCoupon(Coupon coupon) {
		String query = "UPADTE `project.1`.`coupons` SET COMPANY_ID = '"+ coupon.getCompanyID() +"', CATEGORY_ID = '"+ coupon.getCategory() +"', TITLE = '"+ coupon.getTitle() +"', DESCRIPTION = '"+ coupon.getDescription() +"' WHERE ('ID' = '" + customer.getId() + "');\r\n";
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
