import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by robertdunn on 12/22/14.
 */
public class ImageGetter implements Runnable
{

  ExecutorService executor;
  ImageReturnListener notifier;
  String imgUrl;

  public ImageGetter(ImageReturnListener notifier, String imgUrl)
  {
    this.notifier = notifier;
    this.imgUrl = imgUrl;
    executor = Executors.newFixedThreadPool(1);
    executor.execute(this);
  }

  @Override
  public void run()
  {
    BufferedImage img = null;
    try
    {
      img = ImageIO.read(new URL(imgUrl));
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    if(img != null)
      notifier.imageRetrieved(img);
    executor.shutdown();
  }

  public interface ImageReturnListener
  {
    public void imageRetrieved(BufferedImage img);
  }
}
