import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherFrame extends JFrame
{
  String data = "";
  BufferedImage radar;
  final float marginRight = 0.25f;
  final float marginBottom = 0.25f;
  int width, height;
  boolean viewInit = false;
  public WeatherFrame()
  {
    super("Home Station");
    setLayout(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    setExtendedState(Frame.MAXIMIZED_BOTH);
    validate();
    repaint();

    displayRadar();
  }

  public void makeBottomBar()
  {
    JPanel panel = new JPanel();
    panel.setBounds(getInsets().left, (int)(height * (1-marginBottom)), (int)(width * (1-marginRight)), (int)(height * marginBottom));
    panel.setBackground(Color.GREEN);
    add(panel);
    validate();
    repaint();
  }

  public void makeSidebar()
  {
    JPanel panel = new JPanel();
    panel.setBounds(getInsets().left + (int)(width * (1-marginRight)), 0, (int)(width * marginRight), height);
    panel.setBackground(Color.RED);
    add(panel);
    validate();
    repaint();
  }

  public void displayRadar()
  {
    try
    {
      BufferedImage img = ImageIO.read(new URL(Constants.URL_BASE + Constants.RADAR_LOCAL_URL));
      radar = img;
    } catch (IOException e)
    {
      e.printStackTrace();
    } finally
    {
      repaint();
    }
  }

  public void getConditions()
  {
    try
    {
      URL oracle = new URL("http://api.wunderground.com/api/a2c9b857874a2be5/conditions/q/MO/Benton_City.json");
      URLConnection yc = oracle.openConnection();
      BufferedReader in;
      in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null)
      {
        System.out.println(inputLine);
        data += (inputLine + '\n');
      }
      in.close();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    JTextArea text = new JTextArea(data);
    JScrollPane pane = new JScrollPane(text);
    pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    pane.setBounds(23, 40, 394, 191);
    add(pane);
    validate();
  }

  public void paint(Graphics g)
  {
    super.paint(g);
    width = getWidth() - getInsets().left - getInsets().right;
    height = getHeight() - getInsets().top - getInsets().bottom;
    if(radar != null)
    {
      g.drawImage(radar, getInsets().left, getInsets().top, (int) (width * (1 - marginRight)), (int)(height * (1 - marginBottom)), this);
      if(!viewInit)
      {
        viewInit = true;
        makeSidebar();
        makeBottomBar();
      }
    }
  }
}
