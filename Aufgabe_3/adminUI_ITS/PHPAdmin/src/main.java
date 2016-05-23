import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Created by sasa on 10.05.16.
 */
public class main {

	private static Koordinator koordinator = new Koordinator();
	private static Scanner s = new Scanner(System.in);
	private static final String DATEINAME = "nutzerDB.json";
	private static String userlistString = DateiManager.readFromFile(DATEINAME);
	private static ArrayList<User> userlist = new Gson().fromJson(userlistString, new TypeToken<ArrayList<User>>() {
	}.getType());
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) {
		menu();
		// DateiManager.writeToFile(new Gson().toJson(userlist), DATEINAME);

	}

	private static void menu() {
		System.out.println("Waehlen Sie Bitte ein Option Aus: ");
        System.out.println("1. Nutzer anlegen");
        System.out.println("2. Nutzerdaten Aendernloeschen");
        System.out.println("3. Uebersichtsliste");
        System.out.println("Bitte Zahleingeben: ");

		int option = Integer.parseInt(s.nextLine());

		switch (option) {
		case 1:
			nutzerAnlegen();
			break;
		case 2:
			nutzerAendernAuswahl();
			break;
		case 3:
			showUserList();
			break;
		}

	}

	private static void nutzerAendernAuswahl() {
		System.out.println("Geben Sie 1 um Passwort zu aendern ");
        System.out.println("Geben Sie 2 um email zu aendern ");
        System.out.println("Geben Sie 3 um User zu löschen ");
        System.out.println("Bitte Zahleingeben: ");
		int choice = Integer.parseInt(s.nextLine());
		
		System.out.println("welchen Nutzer?");
		String nutzer = s.nextLine();
		
		System.out.println("Geben sie Bitte Neuer Wert an? ");
		String neuerWert = s.nextLine();

		switch (choice) {
		case 1:
			nutzerAendern(nutzer, neuerWert, (byte) 0x1);
			break;
		case 2:
			nutzerAendern(nutzer, neuerWert, (byte) 0x2);
			break;
		case 3:
			nutzerAendern(nutzer, "", (byte) 0x3);
		}
		menu();

	}

	private static void showUserList() {
		System.out.println(userlistString);
	}

	/**
	 * Aendern von PW oder Mail-Adresse oder loescht
	 */
	private static void nutzerAendern(String email, String neuerWert, byte auswahl) {

		User user = findeNutzer(email);
		if (auswahl == 1) {
			user.setPw(textToHash(neuerWert));
		} else if (auswahl == 2) {
			user.setEmail(neuerWert);
		} else if (auswahl == 3) {
			userlist.remove(user);
		}
		aktuallisiereDB();
		menuWiederholung();

	}

	private static void changePass(String username, String neuerWert) {
		User user = findeNutzer(username);

	}


	private static void nutzerAnlegen() {
		// daten abfragen & pw hashen
//		System.out.println("Geben Sie Account-Name ein: ");
//		String name = s.nextLine();

		System.out.println("Geben Sie E-Mail ein: ");
		String mail = s.nextLine();

		System.out.println("Geben Sie Passwort ein: ");
		String pw = s.nextLine();


		koordinator.createUserInUserTable(mail, pw);

		String id = koordinator.getID(mail);
		String pwHash = textToHash(id)+ textToHash(pw);

        koordinator.setNewHashPassInUserTable(mail, pwHash);
		//send token an user
		String token = TokenService.createToken();
		String tokenLink = "https://sadim.informatik.haw-hamburg.de/index.php?token="+token;
		System.out.println(tokenLink);

		//herrausfinden ob token benutzt würde oder nicht
		//if(token accept)
		//koordinator.setIsValid(mail, true);

		menuWiederholung();

//		String salt = makeSalt();
//		pw = textToHash(pw);
//
//		// Nutzer in DB schreiben
//		User derNeue = new User(pw, mail);
//		// String jsonVomNeuen = new Gson().toJson(derNeue)
//
//		System.out.println("erstelltes Json: " + gson.toJson(derNeue));
//		System.out.println("alter json aus File: " + gson.toJson(userlist));
//
//		userlist.add(derNeue);
//
//		aktuallisiereDB();
//		System.out.println(gson.toJson(userlist));
	}
	

	/**
	 * Macht pseudo-random-String der laenge 32
	 * 
	 * @return
	 */
	private static String makeSalt() {
		Random rn = new Random();
		String characters = "abcdefghijklmnopqrstuvwxyzABCDERFGHIKLMNOPQRSTUVWXYZ1234567890!�$%&/()?+#-.,><";
		char[] text = new char[16];
		for (int i = 0; i < text.length; i++) {
			text[i] = characters.charAt(rn.nextInt(characters.length()));
		}
		return new String(text);
	}

	private static String textToHash(String text) {

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bytes = digest.digest((text).getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes)
				sb.append(String.format("%02x", b));
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static User findeNutzer(String email) {
		for (User u : userlist) {
			if (u.getEmail().equals(email))
				return u;
		}
		return null;
	}

	/**
	 * Schreibt nur nochmal die aktuelle userlist-Variable in die Datei
	 */
	private static void aktuallisiereDB() {
		DateiManager.writeToFile(gson.toJson(userlist), DATEINAME);
	}
	
	public static void menuWiederholung() {
		
		System.out.println("Wollen sie wieder zurueck zu Haupt-menue (j/n)");
		String wiederholung = s.nextLine();
		
		if(wiederholung.equals("j")) { 
			clearScreen();
			menu();
		}
	}
	
	public static void clearScreen() {
		     System.out.flush();
	}
}
