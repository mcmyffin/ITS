package ShellManager.Util;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.Calendar;
import org.json.JSONException;

public class Time {

    private final String timeServerURI = "http://localhost/time/index.php";
    private long timeInMills;

    public Time() {
            this.timeInMills = getLocalTime();
    }

    public long getCurrentServerTime() throws UnirestException, JSONException {
            TimeDTO t = getCurrentServerTimeAsJSON();
            return t.getTime();
    }
    
    private TimeDTO getCurrentServerTimeAsJSON() throws UnirestException{
        HttpResponse<String> response = Unirest.get(timeServerURI).asString();
        
        Gson g = new Gson();
        TimeDTO t = g.fromJson(response.getBody(), TimeDTO.class);
        
        return t;
    }

    public long getTimeInSecPlus30Min() {

            return timeInMills+(30*60);
    }

    public long getTimeInSec() {

            return timeInMills;
    }
    
    private long getLocalTime(){
        Calendar c = Calendar.getInstance();
      
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day  = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min  = c.get(Calendar.MINUTE);
        long sec = 60L;
        long result = 0L;
        
        result += min * sec;
        result += hour * (sec * sec);
        result += day  * (24 * sec * sec);
        result += month* (30 * 24 * sec * sec);
        result += year * (12 * 30 * 24 * sec * sec);
        
        return result;
    }
    
}

class TimeDTO {
    
    private long time;
    private long year;
    private long month;
    private long day;
    private long hour;
    private long min;

    public TimeDTO(long time, long year, long month, long day, long hour, long min) {
        this.time = time;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

       
    
    
}