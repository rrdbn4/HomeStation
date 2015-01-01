import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by robertdunn on 12/15/14.
 */
public class ForecastPanel extends JPanel implements ForecastUpdater.UpdateListener
{
  private final int numForecastDays = 5;
  private OneDayForecast[] dayPanels;
  ForecastUpdater updater;

  public ForecastPanel()
  {
    super();
    setBackground(Color.GRAY);
    setLayout(new GridLayout(1, numForecastDays));
    dayPanels = new OneDayForecast[numForecastDays];
    for(int i = 0; i < numForecastDays; i++)
    {
      dayPanels[i] = new OneDayForecast();
      add(dayPanels[i]);
    }
    updater = new ForecastUpdater(this);
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
  }

  @Override
  public void forecastUpdate(JSONArray forecast)
  {
    if(forecast != null)
    {
      for(int i = 0; i < numForecastDays && i < forecast.size(); i++)
      {
        dayPanels[i].update((JSONObject)forecast.get(i));
      }
    }
  }

  private class OneDayForecast extends JPanel implements ImageGetter.ImageReturnListener
  {
    JLabel day, icon, pop, high, low;
    public OneDayForecast()
    {
      super();
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      Font largeFont = new Font("Helvetica", Font.BOLD, 25);
      Font smallFont = new Font("Helvetica", Font.BOLD, 22);
      day = new JLabel("Day", JLabel.CENTER);
      day.setFont(largeFont);
      day.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(day);
      icon = new JLabel("", JLabel.CENTER);
      icon.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(icon);
      pop = new JLabel("0%", JLabel.CENTER);
      pop.setFont(smallFont);
      pop.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(pop);
      high = new JLabel("High: 999", JLabel.CENTER);
      high.setFont(smallFont);
      high.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(high);
      low = new JLabel("Low: 000", JLabel.CENTER);
      low.setFont(smallFont);
      low.setAlignmentX(Component.CENTER_ALIGNMENT);
      add(low);
    }

    public void update(JSONObject data)
    {
      if(data != null)
      {
        JSONObject dateObj = (JSONObject)data.get((String)"date");
        if(dateObj != null)
          day.setText(dateObj.get((String)"weekday").toString());
        pop.setText(data.get((String)"pop").toString() + "%");
        JSONObject highObj = (JSONObject)data.get((String)"high");
        if(dateObj != null)
          high.setText("High: " + highObj.get((String)"fahrenheit").toString() + "° F");
        JSONObject lowObj = (JSONObject)data.get((String)"low");
        if(dateObj != null)
          low.setText("Low: " + lowObj.get((String)"fahrenheit").toString() + "° F");
        new ImageGetter(this, data.get((String)"icon_url").toString());
      }
    }

    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
    }

    @Override
    public void imageRetrieved(BufferedImage img)
    {
      icon.setIcon(new ImageIcon(img));
    }
  }

}
