import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by Sasa on 21.05.2016.
 */
public class TokenService {
    public static String  createToken(){
        Random rnd = new Random();
        String txt = "";
        txt += System.currentTimeMillis();
        String randomTxt = Integer.toString(rnd.nextInt(9));
        return createMD5(txt) + createMD5(randomTxt);
    }

    private static String createMD5(String txt){
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
