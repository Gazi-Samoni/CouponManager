package Job;

import java.util.ArrayList;
import DataAccessObjects.*;
import JavaBeans.Coupon;
import java.time.*;

public class CouponExpiraitionDailyJob implements Runnable {
	
	private CouponsDAO m_couponsDAO;
	private boolean m_quit;
	
	public CouponExpiraitionDailyJob(){
		m_couponsDAO = new CouponsDBDAO();
		m_quit = false;
	}
	
	@Override
	public void run() {
		boolean singalDelete = true;
		m_quit = false;
		
		while(!m_quit) {
			LocalTime now = java.time.LocalTime.now();
			LocalTime jobStartTime = java.time.LocalTime.of(15, 32, 0, 0);
		
			if(now.getHour() == jobStartTime.getHour() && now.getMinute() == jobStartTime.getMinute() && singalDelete) {
				deleteExpiredCoupons();
				singalDelete = false;
			}
		}	
	}
	
	public synchronized void deleteExpiredCoupons() {
		
		ArrayList<Coupon> coupons = m_couponsDAO.getAllCoupons();
	
		long millis=System.currentTimeMillis(); 
		java.sql.Date NowDate = new java.sql.Date(millis);

		for (int i = 0; i < coupons.size(); i++)
		{
			if(coupons.get(i).getEndDate().before(NowDate))
			{
				Coupon currCoupon = coupons.get(i);
				// delete from purchase
				m_couponsDAO.deleteCouponFromCustomerVsCouponTableByID(currCoupon.getID());
				// delete coupon
				m_couponsDAO.deleteCoupon(currCoupon.getID());
			}
		}
		stop();
	}
	
	public void stop() {
		m_quit = true;
	}
}