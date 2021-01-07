package JavaBeans;
import java.util.*;

public class Customer {
	
	public int getId() {
		return m_id;
	}
	public void setId(int id) {
		this.m_id = id;
	}
	public String getFirstName() {
		return m_firstName;
	}
	public void setFirstName(String firstName) {
		this.m_firstName = firstName;
	}
	public String getLastName() {
		return m_lastName;
	}
	public void setLastName(String lastName) {
		this.m_lastName = lastName;
	}
	public String getEmail() {
		return m_email;
	}
	public void setEmail(String email) {
		this.m_email = email;
	}
	public String getPassword() {
		return m_password;
	}
	public void setPassword(String password) {
		this.m_password = password;
	}
	public ArrayList<Coupon> getCoupons() {
		return m_coupons;
	}
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.m_coupons = coupons;
	}
	
	public Customer() {

	}
	
	public Customer(int id, String firstName, String lastName, String email, String password) {
		this.m_id = id;
		this.m_firstName = firstName;
		this.m_lastName = lastName;
		this.m_email = email;
		this.m_password = password;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + m_id + ", firstName=" + m_firstName + ", lastName=" + m_lastName + ", email=" + m_email + "]";
	}


	int m_id;
	String m_firstName;
	String m_lastName;
	String m_email;
	String m_password;
	ArrayList<Coupon> m_coupons;
}
