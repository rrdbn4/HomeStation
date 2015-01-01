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
  private String temperature = "99° F";
  private String humidity = "Humidity: 99%";
  private String feelsLike = "99° F";
  public BufferedImage weatherIcon = null;
  private final float topMargin = 0.3f;
  private HourlyPanel forecastContainer;
  ConditionsUpdater condUpdater;

  public ConditionsPanel()
  {
    super();
    this.setLayout(null);
    ClockUpdater updater = new ClockUpdater(this);
    condUpdater = new ConditionsUpdater(this);

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
      temperature = conditions.get((String)"temp_f").toString() + "° F";
      humidity = "Humidity: " + conditions.get((String)"relative_humidity").toString();
      feelsLike = "Feels like: " + conditions.get((String)"feelslike_f").toString() + "° F";
    }
    if(weatherIcon != null)
      this.weatherIcon = weatherIcon;
    repaint();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    forecastContainer.setBounds(0, (int)(this.getHeight() * topMargin), getWidth(), (getHeight() - (int)(getHeight() * topMargin)));
    g.setFont(new Font("Helvetica", Font.BOLD, (int)(getWidth() * 0.17f)));

    final float divider = 0.4f;
    g.drawLine(0, (int)(getHeight() * topMargin * divider), getWidth(), (int)(getHeight() * topMargin * divider));
    g.drawString(clock, (int)(getWidth()/2f - g.getFontMetrics().stringWidth(clock)/2f), g.getFont().getSize());
    if(weatherIcon != null)
      g.drawImage(weatherIcon, 10, (int)(getHeight() * topMargin * divider), (int)(getHeight() * topMargin * divider), (int)(getHeight() * topMargin * divider), this);
    g.setFont(new Font("Helvetica", Font.BOLD, (int)(getWidth() * 0.14f)));
    final int gap = (int)(getWidth() * 0.1f);
    g.drawString(temperature, (int)(getHeight() * topMargin * divider) + gap, (int)(getHeight() * topMargin * (divider)) + g.getFont().getSize());
    int ystart = (int)(getHeight() * topMargin * (divider)) + g.getFont().getSize();
    g.setFont(new Font("Helvetica", Font.PLAIN, (int)(getWidth() * 0.04f)));
    final int vertGap = (int)(getHeight() * topMargin * 0.02f);
    ystart += (g.getFont().getSize() + vertGap);
    g.drawString(feelsLike, (int)(getHeight() * topMargin * divider) + gap, ystart);
    ystart += (g.getFont().getSize() + vertGap);
    g.drawString(humidity, (int)(getHeight() * topMargin * divider) + gap, ystart);
  }
}
