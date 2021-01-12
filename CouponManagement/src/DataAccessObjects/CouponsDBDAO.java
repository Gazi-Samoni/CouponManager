package DataAccessObjects;
import java.util.*;
import java.sql.*;
import JavaBeans.*;
import ConnectionUtils.*;

public class CouponsDBDAO implements CouponsDAO {
	
	private ConnectionPool m_connectionPool;
	
	public CouponsDBDAO() {
		m_connectionPool = ConnectionPool.getInstance();
	}
	
	public void addCoupon(Coupon coupon) {
		
		String query = "INSERT INTO `project.1`.`coupons` (`ID`, `COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) "
				+ "VALUES " + "('" + coupon.getId() + "', '" + coupon.getCompanyID()  + "', '" + Category.ToInt(coupon.getCategory()) + "', '" + coupon.getTitle() + "', '" + coupon.getDescription() + "', '" + coupon.getStartDate() + "', '" + coupon.getEndDate() + "', '" + coupon.getAmount() + "', '" + coupon.getPrice() + "', '" + coupon.getImage() + "');";
		
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void updateCoupon(Coupon coupon) {
		String query = "UPADTE `project.1`.`coupons` SET COMPANY_ID = '"+ coupon.getCompanyID() +"', CATEGORY_ID = '"+ coupon.getCategory() +"', TITLE = '"+ coupon.getTitle() +"', DESCRIPTION = '"+ coupon.getDescription() +"' WHERE ('ID' = '" + coupon.getId() + "');";
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteCoupon(int couponID) {
		String query = "DELETE FROM `project.1`.`coupons` WHERE (COUPON_ID = '" + couponID + "');";
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public ArrayList<Coupon> getAllCoupons(){
		
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		String query = "SELECT * FROM `project.1`.`coupons`;";
		
		try {
			ResultSet couponsTable = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			while(couponsTable.next())
			{
				Coupon coupon = new Coupon(couponsTable.getInt(1),couponsTable.getInt(2), Category.FromInt(couponsTable.getInt(3)) ,couponsTable.getString(4),couponsTable.getString(5),couponsTable.getDate(6),couponsTable.getDate(7),couponsTable.getInt(8),couponsTable.getDouble(9),couponsTable.getString(10));
				coupons.add(coupon);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return coupons;
	}
	
	public Coupon getOneCoupon(int couponID) {
		Coupon coupon = null;
		String query = "SELECT * FROM `project.1`.`coupons` WHERE (ID = '" + couponID + "');";
		
		try {
			ResultSet couponsTable = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(couponsTable.next() == true)
			{
				coupon = new Coupon(couponsTable.getInt(1),couponsTable.getInt(2), Category.FromInt(couponsTable.getInt(3)) ,couponsTable.getString(4),couponsTable.getString(5),couponsTable.getDate(6),couponsTable.getDate(7),couponsTable.getInt(8),couponsTable.getDouble(9),couponsTable.getString(10));
			}
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}

		return coupon;
	}
	
	public void addCopounPurchase(int customerID, int couponID) {
		// add to customers_vs_coupons
		String query = "INSERT INTO `project.1`.`customers_vs_coupons` (`COUPON_ID`, `CUSTOMER_ID`) VALUES ('" + couponID + "', '" + customerID + "');";
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		
	}
	
	public void deleteCopounPurchase(int customerID, int couponID) {
		String query = "DELETE FROM `project.1`.`customers_vs_coupons` WHERE (COUPON_ID = '" + couponID + "', CUSTOMER_ID = '" + customerID + "');";
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
	}
	
	public ResultSet getTableByID(int couponID) {
		ResultSet customerVScoupon = null;
		String query = "SELECT * FROM `project.1`.`customers_vs_coupons` WHERE (COUPON_ID = '" + couponID + "');";
		
		try {
			customerVScoupon = m_connectionPool.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return customerVScoupon;
	}
	
	public void executeQueryByID(int couponID)
	{
		String query = "DELETE * FROM `project.1`.`customers_vs_coupons` WHERE (COUPON_ID = '" + couponID + "');";
		try {
			m_connectionPool.getConnection().createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
