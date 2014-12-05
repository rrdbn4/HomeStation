import javax.swing.*;
import java.awt.*;

public class WeatherFrame extends JFrame
{
	public WeatherFrame()
	{
		super("Home Station");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		setExtendedState(Frame.MAXIMIZED_BOTH);
		validate();
		repaint();
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillOval(100, 100, 100, 100);
	}
}
