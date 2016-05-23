import java.util.Calendar;

public class Time {

	private int year;
	private int month;
	private int day;
	private int hour; 
	private int min;
	private int timeout;
	
	private long minInSec;
	private long hourInSec;
	private long dayInSec;
	private long monthInSec;
	private long yearInSec;
	private long sumTime;
	
	public Time() {
		Calendar time = Calendar.getInstance();
	    year = time.get(Calendar.YEAR);
	    month = time.get(Calendar.MONTH) + 1;  //wegen 0 bis 11
	    day = time.get(Calendar.DAY_OF_MONTH);
	    hour= time.get(Calendar.HOUR_OF_DAY);
	    min = time.get(Calendar.MINUTE);
	    
	    timeout = 30 * 60;
	    
	    minInSec = min * 60L;
	    hourInSec = hour * 60L * 60L;
	    dayInSec = day * 24L * 60L * 60L;
	    monthInSec = month * 30L * 24L * 60L * 60L;
	    yearInSec = year * 12L * 30L * 24L * 60L * 60L;
	    
	    sumTime = yearInSec + monthInSec + dayInSec + hourInSec + minInSec;
	}
	
	public long getTimeInSecPlus30Min() {
		return sumTime + timeout;
		
	}
	
	public long getTimeInSec() {
		return sumTime;
	}
}
