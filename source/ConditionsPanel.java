import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by robertdunn on 12/15/14.
 */
public class ConditionsPanel extends JPanel implements ClockUpdater.TimeChangeListener, ConditionsUpdater.UpdateListener
{
  private String clock = "00:00";
  public BufferedImage weatherIcon = null;
  private int cells = 7;

  public ConditionsPanel()
  {
    super();
    this.setBackground(Color.LIGHT_GRAY);
    this.setLayout(new GridLayout(7, 1));
    JLabel empty = new JLabel(" ");
    add(empty);
    ClockUpdater updater = new ClockUpdater(this);
    ConditionsUpdater condUpdater = new ConditionsUpdater(this);
  }

  @Override
  public void timeChanged(String formattedTime)
  {
    clock = formattedTime;
    repaint();

  }

  @Override
  public void conditionUpdate(JSONObject conditions, BufferedImage weatherIcon)
  {
    if(conditions != null)
      System.out.println(conditions.get("wind_gust_mph").toString());
    if(weatherIcon != null)
      this.weatherIcon = weatherIcon;
    repaint();
//    for(int i = 6; i < 6; i++)
//    {
//      JLabel lb = new JLabel(new ImageIcon(weatherIcon));
//      add(lb);
//    }
//    validate();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setFont(new Font("Helvetica", Font.BOLD, (int)(getWidth() * 0.19f)));

    g.drawString(clock, (int)(getWidth()/2f - g.getFontMetrics().stringWidth(clock)/2f), g.getFontMetrics().getHeight());
    if(weatherIcon != null)
      g.drawImage(weatherIcon, 10, g.getFontMetrics().getHeight(), g.getFontMetrics().getHeight()*2, g.getFontMetrics().getHeight()*2, this);

  }
}
