package cm.sherli.api.mycow.bovin;

import java.time.LocalDate;


public class CheckDate {
	
	public boolean compareTwoDates(LocalDate startDate, LocalDate endDate,LocalDate birthday) {

	    LocalDate tomorrow = LocalDate.now().plusDays(1);
	    LocalDate today = LocalDate.now();
	    if(tomorrow.isAfter(birthday) || tomorrow.isEqual(birthday))  {
	    	return true;
	    }
	    
	    return true;
	}

	
}
