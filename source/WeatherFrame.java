import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherFrame extends JFrame
{
	String data = "";

	public WeatherFrame()
	{
		super("Home Station");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		setExtendedState(Frame.MAXIMIZED_BOTH);
		validate();


		try
		{
			URL oracle = new URL("http://api.wunderground.com/api/a2c9b857874a2be5/conditions/q/MO/Benton_City.json");
			URLConnection yc = oracle.openConnection();
			BufferedReader in = null;
			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				System.out.println(inputLine);
				data += ( inputLine + '\n');
			}
			in.close();
		} catch (IOException e) {
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

	}
}
