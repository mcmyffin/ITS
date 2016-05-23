import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Created by sasa on 10.05.16.
 */
public class DateiManager
{
    public static String readFromFile(String dateiname)
    {
        String list = "";
        try (BufferedReader br = new BufferedReader(new FileReader(dateiname)))
        {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                list += sCurrentLine;
            }
        }
        catch (IOException e)
        {
        	System.out.println(System.getProperty("user.dir"));
            e.printStackTrace();
        }
        return list;
    }

    
    
    public static void writeToFile(String content, String dateiname)
    {
        try
        {

            File file = new File(dateiname);

            // if file doesnt exists, then create it
            if (!file.exists())
            {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
