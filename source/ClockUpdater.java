import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by robertdunn on 12/15/14.
 */
public class ClockUpdater implements Runnable
{
  ExecutorService executor;
  TimeChangeListener notifier;

  public ClockUpdater(TimeChangeListener listener)
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
      DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
      Date date = new Date();
      notifier.timeChanged(dateFormat.format(date));
      try
      {
        Thread.sleep(10000);
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  public interface TimeChangeListener
  {
    public void timeChanged(String formattedTime);
  }
}
