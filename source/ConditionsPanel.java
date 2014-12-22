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
  private final float topMargin = 0.3f;
  private HourlyPanel forecastContainer;

  public ConditionsPanel()
  {
    super();
    this.setLayout(null);
    ClockUpdater updater = new ClockUpdater(this);
    ConditionsUpdater condUpdater = new ConditionsUpdater(this);

    forecastContainer = new HourlyPanel();
    add(forecastContainer);
    validate();
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
    {

    }
    if(weatherIcon != null)
      this.weatherIcon = weatherIcon;
    repaint();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    forecastContainer.setBounds(0, (int)(this.getHeight() * topMargin), getWidth(), (getHeight() - (int)(getHeight() * topMargin)));
    g.setFont(new Font("Helvetica", Font.BOLD, (int)(getWidth() * 0.19f)));

    g.drawString(clock, (int)(getWidth()/2f - g.getFontMetrics().stringWidth(clock)/2f), g.getFontMetrics().getHeight());
    if(weatherIcon != null)
      g.drawImage(weatherIcon, 10, g.getFontMetrics().getHeight(), g.getFontMetrics().getHeight()*2, g.getFontMetrics().getHeight()*2, this);

  }
}
