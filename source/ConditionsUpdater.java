

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by robertdunn on 12/16/14.
 */
public class ConditionsUpdater implements Runnable
{
  ExecutorService executor;
  UpdateListener notifier;

  public ConditionsUpdater(UpdateListener listener)
  {
    notifier = listener;
    executor = Executors.newFixedThreadPool(1);
    executor.execute(this);
  }

  @Override
  public void run()
  {
    while(true)
    {
      String data = "";
      try
      {
        URL oracle = new URL(Constants.URL_BASE + Constants.CONDITIONS_URL);
        URLConnection yc = oracle.openConnection();
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
          data += (inputLine + '\n');
        }
        in.close();
      } catch (IOException e)
      {
        e.printStackTrace();
      }

      if(!data.isEmpty())
      {
        JSONObject obj = (JSONObject) JSONValue.parse(data);
        JSONObject condObj = (JSONObject) obj.get("current_observation");

        BufferedImage img = null;
        try
        {
          img = ImageIO.read(new URL(condObj.get("icon_url").toString()));
        } catch (IOException e)
        {
          e.printStackTrace();
        }

        notifier.conditionUpdate(condObj, img);
      }

      try
      {
        Thread.sleep(900000); //15 minutes
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  public interface UpdateListener
  {
    public void conditionUpdate(JSONObject conditions, BufferedImage weatherIcon);
  }
}
