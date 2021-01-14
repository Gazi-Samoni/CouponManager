package Job;
import DataAccessObjects.*;


public class CouponExpiraitionDailyJob implements Runnable {
	
	private CouponsDAO couponsDAO;
	private boolean quit;
	
	public CouponExpiraitionDailyJob()
	{
		
	}
	
	@Override
	public void run() {
		
		if(java.time.LocalTime.now().equals(java.time.LocalTime.of(11, 55, 0, 0))) {
			deleteExpiredCoupons();
		}
	}
	
	public synchronized void deleteExpiredCoupons() {
		
	}
	
	public void stop() {
		
	}

}
