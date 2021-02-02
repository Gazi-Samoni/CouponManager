package Login;
import Facade.*;

public class LoginManager {
	private static LoginManager instance = null;
	
	private LoginManager() {
		
	}
	
	public static LoginManager getInstance() {
		if(instance == null)
		{
			System.out.println("Requiring LoginManager...");
			instance = new LoginManager();
			System.out.println("LoginManager established.");
		}
		
		return instance;
	}

	
	public ClientFacade login(String email, String password, ClientType clientType)
	{
		ClientFacade client = null;
		
		switch(clientType)
		{
			case Administrator:
					AdminFacade adminAuthority = new AdminFacade();
					if(adminAuthority.login(email, password))
						client = adminAuthority;
				break;
				
			case Company:
					CompanyFacade companyAuthority = new CompanyFacade();

					if(companyAuthority.login(email, password)) {
						int companyID = companyAuthority.getCompanies().getCompanyIdByEmailAndPassword(email,password);
						companyAuthority.setID(companyID);
						client = companyAuthority;
					}
				break;
				
			case Customer:
					CustomerFacade customerAuthority = new CustomerFacade();
					if(customerAuthority.login(email, password)) {
						int customerID = customerAuthority.getCustomers().getCustomerIdByEmailAndPassword(email,password);
						customerAuthority.setID(customerID);
						client = customerAuthority;
					}
			default:
				break;
		}
		
		return client;
	}
	
	
}