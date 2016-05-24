package DatabaseManager;


import ShellManager.DTO.EmailVerifyTable;
import ShellManager.DTO.UserDTO;
import ShellManager.DTO.UserPassResetTable;
import Exceptions.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

/**
 * Created by Dima on 23.05.2016.
 */

public interface IDB {


    /**
     * Erstellt ein Token Eintrag in der Datenbank bei Anderung oder Erstellung einer Email
     * @param token, Unique Token
     * @param email, Eine gueltige Email
     * @param userID, Eine gueltige UserID
     * @param isCreate, true wenn neuer Benutzer, false wenn update Email
     */
    public void createTokenValidEmail(String token,String email, long validTime, int userID, boolean isCreate) throws DatabaseConnectionException;

    /**
     * Sucht in der Datenbank nach der email und gibt, falls gefunden, die userID zurueck.
     * @param email
     * @return userID
     * @throws EmailNotFoundException, wenn Email nicht existiert
     * @throws DatabaseConnectionException, wenn Datenbankverbindung nicht aufgebaut werden kann
     */
    public int findUserIDByEmail(String email) throws EmailNotFoundException, DatabaseConnectionException;


    /**
     * Erstellt einen neuen Benutzer anhand der Email und Passwort
     * @param email
     * @param pwHash
     * @return userID, Wenn erfolgreich, dann die Eindeutige ID des Benutzers
     * @throws EmailAlreadyExistsException, wenn die angegebene Email bereits existiert
     * @throws DatabaseConnectionException, wenn Datenbankverbindung nicht aufgebaut werden kann
     */
    public int createUser(String email, String pwHash) throws EmailAlreadyExistsException, DatabaseConnectionException, EmailNotFoundException;


    /**
     * Aendert das Passwort des Benutzers
     * @param userID
     * @param newPwHash
     * @throws UserIdNotFoundException, wenn die userID nicht gefunden wurde
     * @throws DatabaseConnectionException, wenn Datenbankverbindung nicht aufgebaut werden kann
     */
    public void changeUserPassword(int userID, String newPwHash) throws UserIdNotFoundException ,DatabaseConnectionException;

    /**
     * Sucht nach Bentuzerm, die den Wunsch Passwort zuruecksetzen gesetzt und diesen nicht benutzt haben.
     *
     * @return UserPassResetTable, mit allen gefundenen Benutzern
     * @throws DatabaseConnectionException, wenn Datenbankverbindung nicht aufgebaut werden kann
     */
    public UserPassResetTable getUsersPassReset() throws DatabaseConnectionException, JSONException, UnirestException;

    /**
     * Sucht nach dem Benutzer, der den Wunsch Passwort zuruecksetzen gesetzt und diesen nicht benutzt hat.
     * @param newEmail
     * @return UserPassResetTable, zu dem Benutzer
     * @throws DatabaseConnectionException
     */
    public UserPassResetTable getUserPassReset(String newEmail) throws DatabaseConnectionException, JSONException, UnirestException, EmailNotFoundException;

    /**
     * Sucht nach Benutzern, die den Wunsch Email aendern gesetzt und nicht gabraucht haben.
     * @return EmailVerifyTable, mit allen gefundenen Benutzern
     * @throws DatabaseConnectionException, wenn Datenbankverbindung nicht aufgebaut werden kann
     */
    public EmailVerifyTable getUsersUnusedEmailVerify() throws DatabaseConnectionException, JSONException, UnirestException;

    /**
     * Sucht nach dem Benutzer, der den Wunsch Email aendern gesetzt und nicht gabraucht hat.
     * @param newEmail
     * @return EmailVerifyTable
     * @throws EmailNotFoundException
     * @throws DatabaseConnectionException, wenn Datenbankverbindung nicht aufgebaut werden kann
     */
    public EmailVerifyTable getUserUnusedEmailVerify(String newEmail) throws EmailNotFoundException, DatabaseConnectionException, JSONException, UnirestException;

    /**
     * Loescht den Benutzer anhand seiner eindeutigen UserID
     * @param userID
     * @throws UserIdNotFoundException, wenn die userID nicht gefunden wurde
     * @throws DatabaseConnectionException, wenn Datenbankverbindung nicht aufgebaut werden kann
     */
    public void deleteUser(int userID) throws UserIdNotFoundException, DatabaseConnectionException;

}