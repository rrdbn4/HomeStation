import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by robertdunn on 12/15/14.
 */
public class RadarUpdater implements Runnable
{
  ExecutorService executor;
  WeatherFrame caller;

  public RadarUpdater(WeatherFrame caller)
  {
    this.caller = caller;
    executor = Executors.newFixedThreadPool(1);
    executor.execute(this);
  }

  @Override
  public void run()
  {
    while(true)
    {
      BufferedImage img = null;
      try
      {
        img = ImageIO.read(new URL(Constants.URL_BASE + Constants.RADAR_LOCAL_URL));
      } catch (IOException e)
      {
        e.printStackTrace();
      } finally
      {
        if(img != null)
          caller.setRadarImage(img);
      }
      try
      {
        Thread.sleep(60000);
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }
}
