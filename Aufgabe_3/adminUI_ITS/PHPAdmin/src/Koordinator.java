/**
 * Created by Sasa on 19.05.2016.
 */
public class Koordinator {
    String token;
    public Koordinator() {
    }

    public void createUserInUserTable(String mail, String pw) {
    }

//    public String getID() {
//
//    }

    public void setNewHashPassInUserTable(String mail, String pwHash) {
        //TODO

        String  email  = "",
                userID = "", 
                validTime = "", 
                isCreate = "", 
                isUsed  = "";


        creatTokenInTokenTable(token, email, userID, validTime, isCreate, isUsed);
    }


    public void creatTokenInTokenTable(String token, String email, String userID, String validTime, String isCreate, String isUsed) {
    }

    public String getToken() {
        return token;
    }

    public String getID(String mail) {
    }
}
