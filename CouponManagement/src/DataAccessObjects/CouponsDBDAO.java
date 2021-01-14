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
		String query = "INSERT INTO `project.1`.`coupons` (`ID`, `COMPANY_ID`, `CATEGORY_ID`, `TITLE`, `DESCRIPTION`, `START_DATE`, `END_DATE`, `AMOUNT`, `PRICE`, `IMAGE`) "
				+ "VALUES " + "('" + coupon.getID() + "', '" + coupon.getCompanyID()  + "', '" + Category.ToInt(coupon.getCategory()) + "', '" + coupon.getTitle() + "', '" + coupon.getDescription() + "', '" + coupon.getStartDate() + "', '" + coupon.getEndDate() + "', '" + coupon.getAmount() + "', '" + coupon.getPrice() + "', '" + coupon.getImage() + "');";
		
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
		String query = "UPADTE `project.1`.`coupons` SET COMPANY_ID = '"+ coupon.getCompanyID() +"', CATEGORY_ID = '"+ coupon.getCategory() +"', TITLE = '"+ coupon.getTitle() +"', DESCRIPTION = '"+ coupon.getDescription() +"' WHERE (ID = '" + coupon.getID() + "');";
		
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
		String query = "DELETE FROM `project.1`.`coupons` WHERE (COUPON_ID = '" + couponID + "');";
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
		String query = "SELECT * FROM `project.1`.`coupons`;";
		
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
	public ArrayList<Coupon> getAllCouponsByCompanyID(int companyID){
		Connection connection=null;
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		String query = "SELECT * FROM `project.1`.`coupons` WHERE ID = '"+ companyID + "';";
		
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
		String query = "SELECT * FROM `project.1`.`coupons` WHERE (ID = '" + couponID + "');";
		
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
		String query = "INSERT INTO `project.1`.`customers_vs_coupons` (`COUPON_ID`, `CUSTOMER_ID`) VALUES ('" + couponID + "', '" + customerID + "');";
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
		String query = "DELETE FROM `project.1`.`customers_vs_coupons` WHERE (COUPON_ID = '" + couponID + "', CUSTOMER_ID = '" + customerID + "');";
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
		String query = "SELECT * FROM `project.1`.`customers_vs_coupons` WHERE (COUPON_ID = '" + couponID + "');";
		
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
	public ResultSet getCustomerVsCouponTableByCutomerID(int customerID)
	{
		Connection connection=null;
		ResultSet customerVScoupon = null;
		String query = "SELECT * FROM `project.1`.`customers_vs_coupons` WHERE (CUSTOMER_ID = '" + customerID + "');";
		
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
	
	public void executeQueryByID(int couponID)
	{
		Connection connection=null;
		String query = "DELETE * FROM `project.1`.`customers_vs_coupons` WHERE (COUPON_ID = '" + couponID + "');";
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

}
