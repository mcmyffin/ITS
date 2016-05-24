package DatabaseManager;

import Exceptions.*;
import ShellManager.DTO.EmailVerifyTable;
import ShellManager.DTO.UserDTO;
import ShellManager.DTO.UserPassResetTable;

import java.util.ArrayList;

/**
 * Created by dima on 23.05.16.
 */
public class DBMock implements IDB{

    @Override
    public void createTokenValidEmail(String token, String email, long validTime, int userID, boolean isCreate) {
        System.out.println("createTokenValidEmail("+token+","+email+","+validTime+","+userID+","+isCreate+")");
    }

    @Override
    public int findUserIDByEmail(String email) throws EmailNotFoundException, DatabaseConnectionException {
        System.out.println("findUserIDByEmail("+email+")");
        return 0;
    }

    @Override
    public int createUser(String email, String pwHash) throws EmailAlreadyExistsException, DatabaseConnectionException {
        System.out.println("create("+email+","+pwHash+")");
        return 12;
    }

    @Override
    public void changeUserPassword(int userID, String newPwHash) throws UserIdNotFoundException, DatabaseConnectionException {
        System.out.println("changeUserPassword("+userID+","+newPwHash+")");
    }

    @Override
    public UserPassResetTable getUsersPassReset() throws DatabaseConnectionException {
        System.out.println("getUsersPassReset()");
        return new UserPassResetTable(new ArrayList());
    }

    @Override
    public UserPassResetTable getUserPassReset(String newEmail) throws DatabaseConnectionException {
        System.out.println("getUserPassReset("+newEmail+")");
        return new UserPassResetTable(new ArrayList());
    }

    @Override
    public EmailVerifyTable getUsersUnusedEmailVerify() throws DatabaseConnectionException {
        System.out.println("getUsersUnusedEmailVerify()");
        return new EmailVerifyTable(new ArrayList());
    }

    @Override
    public EmailVerifyTable getUserUnusedEmailVerify(String newEmail) throws EmailNotFoundException, DatabaseConnectionException {
        System.out.println("getUserUnusedEmailVerify("+newEmail+")");
        return new EmailVerifyTable(new ArrayList());
    }

    @Override
    public void deleteUser(int userID) throws UserIdNotFoundException, DatabaseConnectionException {
        System.out.println("deleteUser("+userID+")");
    }
}
