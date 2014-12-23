import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by robertdunn on 12/21/14.
 */
public class HourlyPanel extends JPanel implements HourlyForecastUpdater.UpdateListener
{
  public static final int numForecastHours = 14;
  private OneHourForecastPanel[] hours;
  private JSONArray hourlyForecastData = null;

  public HourlyPanel()
  {
    super();
    this.setLayout(new GridLayout(numForecastHours, 1));
    HourlyForecastUpdater updater = new HourlyForecastUpdater(this);

    hours = new OneHourForecastPanel[numForecastHours];
    for(int i = 0; i < numForecastHours; i++)
    {
      hours[i] = new OneHourForecastPanel();
      hours[i].setBackground(Color.LIGHT_GRAY);
      add(hours[i]);
    }
    validate();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
  }

  @Override
  public void forecastUpdate(JSONArray hourlyForecast)
  {
    hourlyForecastData = hourlyForecast;
    if(hourlyForecastData != null)
    {
      for(int i = 0; i < numForecastHours && i < hourlyForecastData.size(); i++)
      {
        hours[i].update((JSONObject) hourlyForecastData.get(i));
      }
    }
    else
      System.out.println("Error getting hourly forecast update");
  }

  /**
   * subclass view for each hour in the hourly forecast
   */
  private class OneHourForecastPanel extends JPanel implements ImageGetter.ImageReturnListener
  {
    JLabel icon, time, pop, temp;
    public OneHourForecastPanel()
    {
      super();
      setVisible(true);
      setLayout(new GridLayout(1, 4));
      Font font = new Font("Helvetica", Font.BOLD, 16);
      icon = new JLabel("", JLabel.LEFT);
      add(icon);
      time = new JLabel("00AM", JLabel.LEFT);
      time.setFont(font);
      add(time);
      pop = new JLabel("00%", JLabel.CENTER);
      pop.setFont(font);
      add(pop);
      temp = new JLabel("00 F", JLabel.CENTER);
      temp.setFont(font);
      add(temp);
    }

    public void update(JSONObject hourData)
    {
      if(hourData != null)
      {
        JSONObject tempObj = (JSONObject)hourData.get((String)"temp");
        if(tempObj != null)
          temp.setText(tempObj.get((String)"english").toString() + "° F");
        pop.setText("Precip: " + hourData.get("pop").toString() + "%");
        JSONObject timeObj = (JSONObject)hourData.get((String)"FCTTIME");
        if(timeObj != null)
          time.setText(timeObj.get((String)"civil").toString());
        new ImageGetter(this, hourData.get((String)"icon_url").toString());
      }
    }

    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      g.setColor(Color.BLACK);
      g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
    }

    @Override
    public void imageRetrieved(BufferedImage img)
    {
      if(img != null)
        icon.setIcon(new ImageIcon(img));
    }
  }
}
