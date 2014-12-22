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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by robertdunn on 12/22/14.
 */
public class HourlyForecastUpdater implements Runnable
{
  ExecutorService executor;
  UpdateListener notifier;

  public HourlyForecastUpdater(UpdateListener listener)
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
        URL oracle = new URL(Constants.URL_BASE + Constants.HOURLY_URL);
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
        JSONArray hourlyArr = (JSONArray) obj.get("hourly_forecast");

        notifier.forecastUpdate(hourlyArr);
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
    public void forecastUpdate(JSONArray hourlyForecast);
  }
}
