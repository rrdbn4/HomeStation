import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
	public MainFrame()
	{
		super("Home Station");
		setSize(1000, 1000);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawString("stuff", 20, 20);
	}
}
