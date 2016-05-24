import DatabaseManager.DBMock;
import DatabaseManager.DBMySql;
import Exceptions.*;
import ShellManager.DTO.EmailVerifyTable;
import ShellManager.DTO.UserPassResetTable;
import ShellManager.ShellManager;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by dima on 23.05.16.
 */
public class Main {

    private static final Scanner userInput = new Scanner(System.in);

    private static final String titelDefaultSTART = "== Folgende Aktionen stehen zur Auswahl ==";
    private static final String titelDefaultEND   = "==========================================";
    private static String location = "";
    private static final String[] menu = {"Menu","Benutzer anlegen","Benutzer aendern","Benutzer loeschen","Uebersicht","Beenden"};

    private static final String[] user_create = {"Benutzer anlegen","Bitte geben Sie die E-Mail Adresse ein","Bitte geben Sie das Passwort ein","Passwort wiederholen"};

    private static final String[] menu_user_change = {"Benutzer aendern", "E-Mail aendern","Passwort aendern","Zurueck"};
    private static final String[] user_change_mail = {"Email aendern", "Bitte geben Sie die alte E-Mail Adresse ein","Bitte geben Sie die neue E-Mail Adresse ein"};
    private static final String[] user_change_pw   = {"Passwort aendern","Bitte geben Sie die E-Mail ein","Bitte geben Sie neues Passwort ein"};

    private static final String[] user_delete = {"Benutzer loeschen", "Bitte geben Sie die E-Mail Adresse ein"};

    private static final String[] overview = {"Uebersicht","Alle Anfragen von E-Mail aendern","Benutzer Anfragen von Email aendern","Alle Anfragen von Passwort zuruecksetzen","Benutzer Anfragen von Passwort zurueck setzten","Zurueck"};

    private static final ShellManager manager = new ShellManager(new DBMySql());

    public static void main(String[] args) {



        while (true){
            location = menu[0];
            int i = printMenu();
            switch (i){
                case 1:
                    location = menu[1];
                    userCreate();
                    continue;
                case 2:
                    location = menu[2];
                    int j = printUserChangeMenu();
                    switch (j){
                        case 1:
                            location = menu_user_change[1];
                            userChangeEmail();
                            continue;
                        case 2:
                            location = menu_user_change[2];
                            userChangePassword();
                            continue;
                        default: continue;
                    }

                case 3:
                    location = menu[3];
                    userDelete();
                    continue;
                case 4:
                    location = menu[4];
                    int k = printOverview();
                    switch (k){
                        case 1:
                            printAllEmailValidateUsers();
                            continue;
                        case 2:
                            printUserEmailValidation();
                            continue;
                        case 3:
                            printAllPasswordResetUsers();
                            continue;
                        case 4:
                            printUserPasswordReset();
                            continue;
                        default: continue;
                    }
                default:
                    location = "Beenden";
                    printExit();
            }
        }

    }

    private static void printUserPasswordReset() {
        for (int i = 0; i < 3; i++) {
            System.out.println(">> Geben Sie die E-Mail ein: ");
            String email = userInput.next();

            try {
                UserPassResetTable table = manager.getUserPassResetTable(email);
                System.out.println(table.printTable());
                break;
            } catch (EmailNotFoundException ex) {
                System.err.println(">> Die Email: " + email + " wurde nicht gefunden <<");
            } catch (DatabaseConnectionException ex) {
                printExitNoConnection();
            } catch (UnirestException|JSONException e) {
                System.err.println(">> Der Zeit Servicer ist nicht erreichbar << ");
                break;
            }
        }
    }

    private static void printAllPasswordResetUsers() {
        try {
            UserPassResetTable table = manager.getUserPassResetTable();
            System.out.println(table.printTable());
        } catch (UnirestException|JSONException ex){
            System.err.println(">> Der Zeit Servicer ist nicht erreichbar << ");
        } catch (DatabaseConnectionException ex) {
            printExitNoConnection();
        }

    }

    private static void printUserEmailValidation() {
        for(int i = 0; i < 3; i++){
            System.out.println(">> Geben Sie die E-Mail ein: ");
            String email = userInput.next();

            try {
                EmailVerifyTable table = manager.getEmailValidateTable(email);
                System.out.println(table.printTable());
                break;
            }catch (JSONException|UnirestException ex){
                System.err.println(">> Der Zeit Servicer ist nicht erreichbar << ");
                break;
            }catch (EmailNotFoundException ex){
                System.err.println(">> Die Email: "+email+" wurde nicht gefunden <<");
            }catch (DatabaseConnectionException ex){
                printExitNoConnection();
            }
        }
    }

    private static void printAllEmailValidateUsers() {
        try{
            EmailVerifyTable table = manager.getEmailValidateTable();
            System.out.println(table.printTable());
        }catch (UnirestException|JSONException ex){
            System.err.println(">> Der Zeit Server ist nicht erreichbar <<");
        }catch (DatabaseConnectionException ex){
            printExitNoConnection();
        }
    }

    private static int printMenu(){
        int userinput = -1;
        do{
            System.out.println(titelDefaultSTART);
            System.out.println(">> "+location);

            for(int i = 1; i < menu.length; i++){
                System.out.println("["+i+"] "+menu[i]);
            }

            System.out.println(titelDefaultEND);

            if(userInput.hasNextInt()){
                userinput = userInput.nextInt();
            }else {
                System.err.println(">> Bitte nur Zahlen eingeben <<");
                return -1;
            }


        } while (!(0 < userinput && userinput < menu.length));

        return userinput;
    }

    private static int printUserChangeMenu(){
        int userinput = -1;
        do{
            System.out.println(titelDefaultSTART);
            System.out.println(">> "+location);

            for(int i = 1; i < menu_user_change.length; i++){
                System.out.println("["+i+"] "+menu_user_change[i]);
            }

            System.out.println(titelDefaultEND);
            if(userInput.hasNextInt()){
                userinput = userInput.nextInt();
            }else {
                System.err.println(">> Bitte nur Zahlen eingeben <<");
                return -1;
            }

        } while (!(0 < userinput && userinput < menu_user_change.length));

        return userinput;
    }

    private static void userCreate(){

        for(int i = 0; i < 3; i++){
            System.out.println(">> "+location);
            System.out.println(">> "+user_create[1]);
            String email = userInput.next();
            System.out.println(">> "+user_create[2]);
            String password = userInput.next();

            try{
                manager.createUser(email,password);
                System.out.println(">> Benutzer wurde erfolgreich angelegt");
                break;

            }catch (TimeServiceException ex){
                System.err.println(">> Der Zeit Server ist nicht erreichbar <<");
                break;
            }catch (EmailAlreadyExistsException|UserIdNotFoundException|EmailNotFoundException ex){
                System.err.println(">> Die Email: "+email+" existiert bereits <<");
            }catch (DatabaseConnectionException ex){
                printExitNoConnection();
            }
        }
    }

    private static void userChangeEmail(){
        for(int i = 0; i < 3; i++) {
            System.out.println(">> " + location);
            System.out.println(">> "+user_change_mail[1]);
            String emailOld = userInput.next();

            System.out.println(">> "+user_change_mail[2]);
            String emailNew = userInput.next();

            try {
                manager.changeEmail(emailOld, emailNew);
                System.out.println(">> Email wurde erfolgreich geandert");
                break;

            }catch (TimeServiceException ex){
                System.err.println(">> Der Zeit Servicer ist nicht erreichbar <<");
                break;
            }catch(EmailNotFoundException| UserIdNotFoundException ex){
                System.err.println(">> Die Email: "+emailOld+" wurde nicht gefunden <<");
            }catch (EmailAlreadyExistsException ex){
                System.err.println(">> Die Email: "+emailNew+" existiert bereits <<");
            }catch (DatabaseConnectionException ex){
                printExitNoConnection();
            }
        }
    }

    private static void userChangePassword(){
        for(int i = 0; i < 3; i++) {
            System.out.println(">> " + location);
            System.out.println(">> "+user_change_pw[1]);
            String email = userInput.next();

            System.out.println(">> "+user_change_pw[2]);
            String passwordNew = userInput.next();

            try {
                manager.changePassword(email, passwordNew);
                System.out.println(">> Passwort wurde erfolgreich geandert");
                break;
            }catch (EmailNotFoundException|UserIdNotFoundException ex){
                System.err.println(">> Die Email: "+email+" wurde nicht gefunden <<");
            }catch (DatabaseConnectionException ex){
                printExitNoConnection();
            }
        }
    }

    private static void userDelete(){
        for(int i = 0; i < 3; i++) {
            System.out.println(">> " + location);
            System.out.println(">> "+user_delete[1]);
            String email = userInput.next();

            try {
                manager.userDelete(email);
                System.out.println(">> Benutzer mit der Email: "+email+" wurde erfolgreich geloescht");
                break;
            }catch (EmailNotFoundException|UserIdNotFoundException ex){
                System.err.println(">> Die Email: "+email+" wurde nicht gefunden <<");
            }catch (DatabaseConnectionException ex){
                printExitNoConnection();
            }
        }
    }

    private static int printOverview(){
        int userinput = -1;
        do{
            System.out.println(titelDefaultSTART);
            System.out.println(">> "+location);
            for(int i = 1; i < overview.length; i++){
                System.out.println("["+i+"] "+overview[i]);
            }
            System.out.println(titelDefaultEND);
            userinput = userInput.nextInt();

        } while (!(0 < userinput && userinput < overview.length));

        return userinput;
    }

    private static void printExit(){
        System.out.println(">> "+location);
        System.out.println(">> Sind sie Sicher? (y/n)");
        String input = userInput.next();
        if (input.equals("y")) System.exit(0);
    }

    private static void printExitNoConnection(){
        System.err.println(">> Datenbankverbindung kann nicht hergestellt werden <<");
        System.err.println(">> Bitte versuchen Sie es zu einem spaeterem Zeitpunkt nochmal ... <<");
        System.exit(0);
    }

}