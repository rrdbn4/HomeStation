import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by robertdunn on 12/15/14.
 */
public class ConditionsPanel extends JPanel implements ClockUpdater.TimeChangeListener
{
  private String clock = "00:00";

  public ConditionsPanel()
  {
    super();
    this.setBackground(Color.LIGHT_GRAY);
    ClockUpdater updater = new ClockUpdater(this);
  }

  @Override
  public void timeChanged(String formattedTime)
  {
    clock = formattedTime;
    repaint();
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setFont(new Font("Helvetica", Font.BOLD, (int)(getWidth() * 0.19f)));

    g.drawString(clock, (int)(getWidth()/2f - g.getFontMetrics().stringWidth(clock)/2f), g.getFontMetrics().getHeight());
  }
}
