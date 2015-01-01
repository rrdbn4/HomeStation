import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
public class ForecastUpdater implements Runnable
{
  ExecutorService executor;
  UpdateListener notifier;

  public ForecastUpdater(UpdateListener listener)
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
      boolean retryConnection = false;
      try
      {
        URL oracle = new URL(Constants.URL_BASE + Constants.FORECAST_URL);
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
        retryConnection = true;
        ErrorThrower.inst().setConnectionError(true);
        Logger.logError("daily forecast");
        e.printStackTrace();
      }

      if(!data.isEmpty())
      {
        ErrorThrower.inst().setConnectionError(false);
        Logger.log("daily forecast");
        JSONObject obj = (JSONObject) JSONValue.parse(data);
        JSONObject foreObj = (JSONObject) obj.get((String)"forecast");
        JSONObject simpleObj = (JSONObject) foreObj.get((String)"simpleforecast");
        JSONArray forecastArr = (JSONArray) simpleObj.get("forecastday");

        notifier.forecastUpdate(forecastArr);
      }
      else
      {
        ErrorThrower.inst().setConnectionError(true);
        Logger.logError("daily forecast");
      }

      try
      {
        if(retryConnection)
          Thread.sleep(120000); //2 minutes
        else
          Thread.sleep(3600000); //60 minutes
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  public interface UpdateListener
  {
    public void forecastUpdate(JSONArray forecast);
  }
}
