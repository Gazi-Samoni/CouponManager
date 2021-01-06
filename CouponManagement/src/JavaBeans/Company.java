package JavaBeans;
import java.util.*;

public class Company {
	
	public int getM_id() {
		return m_id;
	}

	public void setM_id(int m_id) {
		this.m_id = m_id;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_email() {
		return m_email;
	}

	public void setM_email(String m_email) {
		this.m_email = m_email;
	}

	public String getM_password() {
		return m_password;
	}

	public void setM_password(String m_password) {
		this.m_password = m_password;
	}

	public ArrayList<Coupon> getM_coupons() {
		return m_coupons;
	}

	public void setM_coupons(ArrayList<Coupon> m_coupons) {
		this.m_coupons = m_coupons;
	}

	public Company() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String toString() {
		return "Company [id=" + m_id + ", name=" + m_name + ", email=" + m_email  + "]";
	}
	
	int m_id;
	String m_name;
	String m_email;
	String m_password;
	ArrayList<Coupon> m_coupons;
}
