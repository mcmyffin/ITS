package ShellManager.Util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

public class Time {

	private final String timeServerURI = "http://localhost/time";
	private long timeInMills;
	
	public Time() throws JSONException, UnirestException {
		this.timeInMills = getCurrentServerTime();
	}

	private long getCurrentServerTime() throws UnirestException, JSONException {
		HttpResponse<JsonNode> response = Unirest.get(timeServerURI).asJson();
		return response.getBody().getObject().getLong("time");
	}

	public long getTimeInSecPlus30Min() {

		return timeInMills+(30*60);
	}
	
	public long getTimeInSec() {

		return timeInMills;
	}
}
