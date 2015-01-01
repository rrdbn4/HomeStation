import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by robertdunn on 12/28/14.
 */
public class Logger
{
  public static boolean inUse = false;

  public static void log(String action)
  {
    writeToFile("Successful Update: " + action + "  --" + getTimeString() + "\n");
  }

  public static void logError(String action)
  {
    writeToFile("FAILED Update: " + action + "  --" + getTimeString() + "\n");
  }

  private static String getTimeString()
  {
    DateFormat dateFormat = new SimpleDateFormat("MMM dd  hh:mm aa");
    Date date = new Date();
    return dateFormat.format(date);
  }

  private static void writeToFile(String logMessage)
  {
    while(inUse == true);
    inUse = true;
    Writer output;
    try
    {
      output = new BufferedWriter(new FileWriter("update-log.txt", true));  //clears file every time

      output.append(logMessage);
      output.close();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    inUse = false;
  }
}
