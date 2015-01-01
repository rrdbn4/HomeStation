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
  UpdateListener notifier;

  public RadarUpdater(UpdateListener notifier)
  {
    this.notifier = notifier;
    executor = Executors.newFixedThreadPool(1);
    executor.execute(this);
  }

  @Override
  public void run()
  {
    while(true)
    {
      BufferedImage img = null;
      boolean retryConnection = false;
      try
      {
        img = ImageIO.read(new URL(Constants.URL_BASE + Constants.RADAR_LOCAL_URL));
      } catch (IOException e)
      {
        retryConnection = true;
        ErrorThrower.inst().setConnectionError(true);
        Logger.logError("radar image");
        e.printStackTrace();
      }
      if(img != null)
      {
        ErrorThrower.inst().setConnectionError(false);
        Logger.log("radar image");
        notifier.radarUpdated(img);
      }

      try
      {
        if(retryConnection)
          Thread.sleep(120000); //2 minutes
        else
          Thread.sleep(600000); //10 minutes
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  public interface UpdateListener
  {
    public void radarUpdated(BufferedImage updatedImg);
  }
}
