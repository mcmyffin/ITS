package ShellManager;


import DatabaseManager.IDB;
import Exceptions.*;
import ShellManager.DTO.EmailVerifyTable;
import ShellManager.DTO.UserPassResetTable;
import ShellManager.Util.Time;
import ShellManager.Util.TokenService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by dima on 23.05.16.
 */
public class ShellManager {

    private final IDB db;

    public ShellManager(IDB db) {
        this.db = db;
    }

    public void createUser(String email, String password) throws EmailAlreadyExistsException, DatabaseConnectionException, UserIdNotFoundException, EmailNotFoundException, TimeServiceException {
        checkNotNull(email);
        checkNotNull(password);

        try{

            // create ValidTime
            Time t = new Time();
            long validTime = t.getTimeInSecPlus30Min();

            // 1 x Hash
            String pwHash = TokenService.createMD5(password);

            // create User
            int userID = db.createUser(email,pwHash);

            // 2 x Hash
            pwHash = TokenService.createMD5(userID+pwHash);

            // update Password
            db.changeUserPassword(userID,pwHash);

            // create Token
            String token = TokenService.createToken(userID);
            db.createTokenValidEmail(token,email, validTime,userID,true);

        }catch (UnirestException|JSONException ex){
            throw new TimeServiceException();
        }
    }

    public void changeEmail(String emailOld, String emailNew) throws EmailAlreadyExistsException, EmailNotFoundException, DatabaseConnectionException, UserIdNotFoundException, TimeServiceException {
        checkNotNull(emailOld);
        checkNotNull(emailNew);

        try {
            // create ValidTime
            Time t = new Time();
            long validTime = t.getTimeInSecPlus30Min();

            // get UserID
            int userID = db.findUserIDByEmail(emailOld);

            // create Token
            String token = TokenService.createToken(userID);
            db.createTokenValidEmail(token, emailNew, validTime, userID, false);
        }catch (UnirestException|JSONException ex){
            throw new TimeServiceException();
        }
    }

    public void changePassword(String email, String passwordNew) throws EmailNotFoundException, DatabaseConnectionException, UserIdNotFoundException {
        checkNotNull(email);
        checkNotNull(passwordNew);

        // get UserID
        int userID = db.findUserIDByEmail(email);

        // 1 x Hash
        String pwHah = TokenService.createMD5(passwordNew);
        pwHah = TokenService.createMD5(userID+pwHah);

        // change password
        db.changeUserPassword(userID,pwHah);
    }

    public void userDelete(String email) throws EmailNotFoundException, DatabaseConnectionException, UserIdNotFoundException {
        checkNotNull(email);

        // get UserID
        int userID = db.findUserIDByEmail(email);
        db.deleteUser(userID);
    }

    public EmailVerifyTable getEmailValidateTable() throws DatabaseConnectionException, JSONException, UnirestException {
        return db.getUsersUnusedEmailVerify();
    }

    public EmailVerifyTable getEmailValidateTable(String email) throws DatabaseConnectionException, EmailNotFoundException, JSONException, UnirestException {
        checkNotNull(email);
        return db.getUserUnusedEmailVerify(email);
    }

    public UserPassResetTable getUserPassResetTable(String email) throws EmailNotFoundException, DatabaseConnectionException, JSONException, UnirestException {
        checkNotNull(email);
        return db.getUserPassReset(email);
    }

    public UserPassResetTable getUserPassResetTable() throws DatabaseConnectionException, JSONException, UnirestException {
        return db.getUsersPassReset();
    }
}
