package JavaBeans;
import java.util.*;

public class Company {
	
	public int get_id() {
		return m_id;
	}

	public void set_id(int m_id) {
		this.m_id = m_id;
	}

	public String get_name() {
		return m_name;
	}

	public void set_name(String m_name) {
		this.m_name = m_name;
	}

	public String get_email() {
		return m_email;
	}

	public void set_email(String m_email) {
		this.m_email = m_email;
	}

	public String get_password() {
		return m_password;
	}

	public void set_password(String m_password) {
		this.m_password = m_password;
	}

	public ArrayList<Coupon> get_coupons() {
		return m_coupons;
	}

	public void set_coupons(ArrayList<Coupon> m_coupons) {
		this.m_coupons = m_coupons;
	}

	public Company() {
		
	}
	
	public Company(int id, String name, String email, String password) {
		this.m_id = id;
		this.m_name = name;
		this.m_email = email;
		this.m_password = password;
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
