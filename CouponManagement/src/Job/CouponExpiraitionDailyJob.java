package Job;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import DataAccessObjects.*;
import JavaBeans.Coupon;


public class CouponExpiraitionDailyJob implements Runnable {
	
	private CouponsDAO m_couponsDAO;
	private boolean m_quit;
	
	public CouponExpiraitionDailyJob()
	{
		m_couponsDAO = new CouponsDBDAO();
		m_quit = false;
	}
	
	@Override
	public void run() {
		
		m_quit = false;
		while(!m_quit) {
			if(java.time.LocalTime.now().equals(java.time.LocalTime.of(19, 55, 0, 0))) {
				deleteExpiredCoupons();
			}
		}
		
		
	}
	
	public synchronized void deleteExpiredCoupons() {
		
		ArrayList<Coupon> coupons = m_couponsDAO.getAllCoupons();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   
		Date now = new Date();
		
		for (int i = 0; i < coupons.size(); i++)
		{
			if(coupons.get(i).getEndDate().after(now))
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
	
	// 
	//

}