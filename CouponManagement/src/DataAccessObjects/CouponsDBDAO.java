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
		Connection connection=null;
		String query = "INSERT INTO couponmanager.coupons (`COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) "
				+ "VALUES " + "('"+ coupon.getCompanyID()  + "', '" + Category.ToInt(coupon.getCategory()) + "', '" + coupon.getTitle() + "', '" + coupon.getDescription() + "', '" + 
				coupon.getStartDate() + "', '" + coupon.getEndDate() + "', '" + coupon.getAmount() + "', '" + coupon.getPrice() + "', '" + 
				coupon.getImage() + "');";
		
		try {
			connection = m_connectionPool.getConnection();
			connection.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
	}
	public void updateCoupon(Coupon coupon) {
		Connection connection=null;
		String query = "UPDATE couponmanager.coupons SET COMPANY_ID = '"+ coupon.getCompanyID() +"', CATEGORY_ID = '"+ Category.ToInt(coupon.getCategory()) +
				"', TITLE = '"+ coupon.getTitle() +"', DESCRIPTION = '"+ coupon.getDescription() + "', START_DATE = '"+ coupon.getStartDate() +
				"', END_DATE = '"+ coupon.getEndDate() +"', AMOUNT = '"+ coupon.getAmount() +"', PRICE = '"+ coupon.getPrice() +"', IMAGE = '"+ coupon.getImage() +
				"' WHERE (ID = '" + coupon.getID() + "');";
		
		try {
			connection = m_connectionPool.getConnection();
			connection.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
	}	
	public void deleteCoupon(int couponID) {
		Connection connection=null;
		String query = "DELETE FROM couponmanager.coupons WHERE (ID = '" + couponID + "');";
		try {
			connection = m_connectionPool.getConnection();
			connection.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
	}
	public ArrayList<Coupon> getAllCoupons(){
		Connection connection=null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		String query = "SELECT * FROM couponmanager.coupons;";
		
		try {
			connection = m_connectionPool.getConnection();
			ResultSet couponsTable = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			while(couponsTable.next())
			{
				Coupon coupon = new Coupon(couponsTable.getInt(1),couponsTable.getInt(2), Category.FromInt(couponsTable.getInt(3)) ,couponsTable.getString(4),couponsTable.getString(5),couponsTable.getDate(6),couponsTable.getDate(7),couponsTable.getInt(8),couponsTable.getDouble(9),couponsTable.getString(10));
				coupons.add(coupon);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return coupons;
	}
	public Coupon getOneCoupon(int couponID) {
		Connection connection=null;
		Coupon coupon = null;
		String query = "SELECT * FROM couponmanager.coupons WHERE (ID = '" + couponID + "');";
		
		try {
			connection = m_connectionPool.getConnection();
			ResultSet couponsTable = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(couponsTable.next() == true)
			{
				coupon = new Coupon(couponsTable.getInt(1),couponsTable.getInt(2), Category.FromInt(couponsTable.getInt(3)) ,couponsTable.getString(4),couponsTable.getString(5),couponsTable.getDate(6),couponsTable.getDate(7),couponsTable.getInt(8),couponsTable.getDouble(9),couponsTable.getString(10));
			}
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}

		return coupon;
	}
	public Coupon getOneCoupon(int companyID,String couponTitle) {
		Connection connection=null;
		Coupon coupon = null;
		String query = "SELECT * FROM couponmanager.coupons WHERE (COMPANY_ID = '" + companyID + "' AND TITLE = '" + couponTitle + "');";
		
		try {
			connection = m_connectionPool.getConnection();
			ResultSet couponsTable = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(couponsTable.next() == true)
			{
				coupon = new Coupon(couponsTable.getInt(1),couponsTable.getInt(2), Category.FromInt(couponsTable.getInt(3)) ,couponsTable.getString(4),couponsTable.getString(5),couponsTable.getDate(6),couponsTable.getDate(7),couponsTable.getInt(8),couponsTable.getDouble(9),couponsTable.getString(10));
			}
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		return coupon;
	}
	public void addCopounPurchase(int customerID, int couponID) {
		Connection connection=null;
		// add to customers_vs_coupons
		String query = "INSERT INTO couponmanager.customers_vs_coupons (`COUPON_ID`, `CUSTOMER_ID`) VALUES ('" + couponID + "', '" + customerID + "');";
		try {
			connection = m_connectionPool.getConnection();
			connection.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
	}
	public void deleteCopounPurchase(int customerID, int couponID) {
		Connection connection=null;
		String query = "DELETE FROM couponmanager.customers_vs_coupons WHERE (COUPON_ID = '" + couponID + "' AND CUSTOMER_ID = '" + customerID + "');";
		try {
			connection = m_connectionPool.getConnection();
			connection.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
	}
	public ResultSet getCustomerVsCouponTableByCouponID(int couponID) {
		Connection connection=null;
		ResultSet customerVScoupon = null;
		String query = "SELECT * FROM couponmanager.customers_vs_coupons WHERE (COUPON_ID = '" + couponID + "');";
		
		try {
			connection = m_connectionPool.getConnection();
			customerVScoupon = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return customerVScoupon;
	}
	public void deleteCouponFromCustomerVsCouponTableByID(int couponID)
	{
		Connection connection=null;
		String query = "DELETE FROM couponmanager.customers_vs_coupons WHERE (COUPON_ID = '" + couponID + "');";
		try {
			connection = m_connectionPool.getConnection();
			connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
	}
	@Override
	public boolean isCouponExist(int couponID) {
		boolean isExist = true;
		Connection connection = null;
		String query = "SELECT * FROM couponmanager.coupons WHERE (ID = '" + couponID + "');";
		ResultSet couponSet = null;
		
		try {
			connection = m_connectionPool.getConnection();
			couponSet = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			if(couponSet.next() == false)
			{
				isExist = false;
			}
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(connection != null)
			{
				m_connectionPool.restoreConnection(connection);
			}
		}
		
		return isExist;
	}

}
