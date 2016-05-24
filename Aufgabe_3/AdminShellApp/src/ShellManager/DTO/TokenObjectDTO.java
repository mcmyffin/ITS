package ShellManager.DTO;

/**
 * Created by dima on 23.05.16.
 */
public class TokenObjectDTO {

    private String token;
    private int userID;
    private boolean validTime;
    private boolean used;

    public TokenObjectDTO(String token, int userID, boolean validTime, boolean used) {
        this.token = token;
        this.userID = userID;
        this.validTime = validTime;
        this.used = used;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isValid() {
        return validTime;
    }

    public void setValidTime(boolean validTime) {
        this.validTime = validTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
