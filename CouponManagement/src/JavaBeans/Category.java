package JavaBeans;

public enum Category {
	Food,
	Electricity,
	Restaurant,
	Vacation;
	
	public static Category FromInt(int x)
	{
		switch(x)
		{
			case 0:
				return Food;
			case 1:
				return Electricity;
			case 2:
				return Restaurant;
			case 3:
				return Vacation;	
		}
		
		return null;
	}
}
