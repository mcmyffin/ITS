package DatabaseManager;

import Exceptions.*;
import ShellManager.DTO.*;
import ShellManager.Util.Time;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/**
 * Created by dima on 24.05.16.
 */
public class DBMySql implements IDB{


    @Override
    public void createTokenValidEmail(String token, String email, long validTime ,int userID, boolean isCreate) throws DatabaseConnectionException {

        try {
            Connection con = buildNewConnection();
            Statement statement = con.createStatement();

            statement.executeUpdate("INSERT INTO `unvalidbenutzer`(`token`, `validtime`, `email`, `userID`, `isCreate`, `isUsed`) " +
                                                    "VALUES ('"+token+"','"+validTime+"','"+email+"','"+userID+"','"+booleanToInt(isCreate)+"','"+0+"')");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    @Override
    public int findUserIDByEmail(String email) throws EmailNotFoundException, DatabaseConnectionException {

        try {
            Connection con = buildNewConnection();
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT `id` FROM `benutzer` WHERE `email` = '"+email+"'");

            if(rs.next()) {
                int id = rs.getInt("id");
                con.close();
                return id;
            }
            con.close();
            throw new EmailNotFoundException();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }


    @Override
    public int createUser(String email, String pwHash) throws EmailAlreadyExistsException, DatabaseConnectionException, EmailNotFoundException {

        try {
            Connection con = buildNewConnection();
            Statement statement = con.createStatement();

            try{
                findUserIDByEmail(email);
                throw new EmailAlreadyExistsException();
            }catch (EmailNotFoundException e) {
                statement.executeUpdate("INSERT INTO `benutzer`(`email`, `password`, `isValid`) " +
                        "VALUES ('"+email+"','"+pwHash+"','"+0+"')");

                return findUserIDByEmail(email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    @Override
    public void changeUserPassword(int userID, String newPwHash) throws UserIdNotFoundException, DatabaseConnectionException {

        try {
            Connection con = buildNewConnection();
            Statement statement = con.createStatement();
            boolean rs = statement.executeUpdate("UPDATE `benutzer` SET `password`='"+newPwHash+"' WHERE `id` = '"+userID+"'") > 0;

            if(!rs) throw new UserIdNotFoundException();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    @Override
    public UserPassResetTable getUsersPassReset() throws DatabaseConnectionException, JSONException, UnirestException {

        try {
            Connection con = buildNewConnection();
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM `passreset` WHERE `isUsed` = '0'");
            List<TokenObjectDTO> list = new ArrayList();

            while(res.next()){

                String token = res.getString("token");
                int userID   = res.getInt("userID");
                boolean isUsed = intToBoolean(res.getInt("isUsed"));
                long validTime = res.getLong("validtime");

                Time t = new Time();

                boolean valid = (t.getTimeInSec()<= validTime);

                TokenObjectDTO dto = new TokenObjectDTO(token,userID,valid,isUsed);
                list.add(dto);
            }

            return new UserPassResetTable(list);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }

    }

    @Override
    public UserPassResetTable getUserPassReset(String newEmail) throws DatabaseConnectionException, JSONException, UnirestException, EmailNotFoundException {
        try {
            int userID = findUserIDByEmail(newEmail);

            Connection con = buildNewConnection();
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM `passreset` WHERE `userID` = '"+userID+"' AND `isUsed` = '0'");
            List<TokenObjectDTO> list = new ArrayList();

            while(res.next()){

                String token = res.getString("token");
                boolean isUsed = intToBoolean(res.getInt("isUsed"));
                long validTime = res.getLong("validtime");

                Time t = new Time();
                boolean valid = (t.getTimeInSec()<= validTime);

                TokenObjectDTO dto = new TokenObjectDTO(token,userID,valid,isUsed);
                list.add(dto);
            }

            return new UserPassResetTable(list);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    @Override
    public EmailVerifyTable getUsersUnusedEmailVerify() throws DatabaseConnectionException, JSONException, UnirestException {

        try {
            Connection con = buildNewConnection();
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM `unvalidbenutzer` WHERE `isUsed` = '0' ORDER BY `validtime` ASC ");

            List<TokenObjectDTO> list = new ArrayList();
            while (res.next()){
                String token = res.getString("token");
                int userID   = res.getInt("userID");
                boolean isUsed = intToBoolean(res.getInt("isUsed"));
                long validTime = res.getLong("validtime");

                Time t = new Time();

                boolean valid = (t.getTimeInSec()<= validTime);

                TokenObjectDTO dto = new TokenObjectDTO(token,userID,valid,isUsed);
                list.add(dto);
            }

            EmailVerifyTable table = new EmailVerifyTable(list);
            return table;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    @Override
    public EmailVerifyTable getUserUnusedEmailVerify(String newEmail) throws EmailNotFoundException, DatabaseConnectionException, JSONException, UnirestException {
        try {
            int userID = findUserIDByEmail(newEmail);

            Connection con = buildNewConnection();
            Statement statement = con.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM `unvalidbenutzer` WHERE `userID` = '"+userID+"' AND `isUsed` = '0' ORDER BY `validtime` ASC ");

            List<TokenObjectDTO> list = new ArrayList();
            while (res.next()){
                String token = res.getString("token");
                boolean isUsed = intToBoolean(res.getInt("isUsed"));
                long validTime = res.getLong("validtime");

                Time t = new Time();

                boolean valid = (t.getTimeInSec()<= validTime);

                TokenObjectDTO dto = new TokenObjectDTO(token,userID,valid,isUsed);
                list.add(dto);
            }

            EmailVerifyTable table = new EmailVerifyTable(list);
            return table;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    @Override
    public void deleteUser(int userID) throws UserIdNotFoundException, DatabaseConnectionException {

        try {
            Connection con = buildNewConnection();
            Statement stmt = con.createStatement();

            int rs = stmt.executeUpdate("DELETE FROM `benutzer` WHERE `id` = '"+userID+"'");
            con.close();
            if(rs <= 0) throw new UserIdNotFoundException();

        } catch (SQLException e) {

            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    private Connection buildNewConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/app","app","");
    }

    private int booleanToInt(boolean b){
        return b ? 1 : 0;
    }
    private boolean intToBoolean(int i){ return i>=1 ? true: false; }
}
