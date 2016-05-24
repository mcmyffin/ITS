package ShellManager.Util;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by Sasa on 21.05.2016.
 */
public class TokenService {
    public static String  createToken(Object o){
        Random rnd = new Random();
        String txt1 = o.toString()+rnd.nextInt(9);
        String txt2 = Long.toString(System.currentTimeMillis());

        return createMD5(txt1) + createMD5(txt2);
    }

    public static String createMD5(String txt){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest((txt).getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes)
                sb.append(String.format("%02x", b));
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
