package shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class BaseMethod {
	
	public static Date convertDate(String day) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			return dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
