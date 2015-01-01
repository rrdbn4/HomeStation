import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherFrame extends JFrame implements RadarUpdater.UpdateListener, ErrorStateChangedListener
{
  String data = "";
  BufferedImage radar;
  final float marginRight = 0.25f;
  final float marginBottom = 0.18f;
  int width, height;
  boolean isInErrorState = false;
  ConditionsPanel sideBar;
  ForecastPanel bottomBar;
  RadarUpdater radarService;


  public WeatherFrame()
  {
    super("Home Station");
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    setExtendedState(Frame.MAXIMIZED_BOTH);
    validate();
    repaint();

    ErrorThrower.inst().setErrorChangedListener(this);

    setupSidebars();
    radarService = new RadarUpdater(this);
  }

  public void setupSidebars()
  {
    bottomBar = new ForecastPanel();
    add(bottomBar);

    sideBar = new ConditionsPanel();
    add(sideBar);

    resizeViews();
    validate();
    repaint();
  }

  public void resizeViews()
  {
    if(sideBar == null || bottomBar == null)
      return;
    bottomBar.setBounds(getInsets().left, (int) (height * (1 - marginBottom)), (int) (width * (1 - marginRight)), (int) (height * marginBottom));
    sideBar.setBounds(getInsets().left + (int)(width * (1-marginRight)), 0, (int)(width * marginRight), height);
    validate();
  }

  public void paint(Graphics g)
  {
    super.paint(g);
    width = getWidth() - getInsets().left - getInsets().right;
    height = getHeight() - getInsets().top - getInsets().bottom;
    resizeViews();
    if(radar != null)
    {
      g.drawImage(radar, getInsets().left, getInsets().top, (int) (width * (1 - marginRight)), (int)(height * (1 - marginBottom)), this);
    }
    if(isInErrorState)
    {
      g.setColor(Color.RED);
      g.setFont(new Font("Helvetica", Font.BOLD, 60));
      String s = "NO INTERNET CONNECTION";
      g.drawString(s, getWidth() / 2 - g.getFontMetrics().stringWidth(s) / 2, g.getFont().getSize() + 20);
    }
  }

  @Override
  public void radarUpdated(BufferedImage updatedImg)
  {
    radar = updatedImg;
    repaint();
  }

  @Override
  public void errorStateChanged(boolean isConnectionError)
  {
    isInErrorState = isConnectionError;
    repaint();
  }
}
