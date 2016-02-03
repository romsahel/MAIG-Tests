
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame
{

	private static int[][] map;
	private final JFrame frame = new JFrame();

	public Frame()
	{

	}

	private static JPanel drawPanel()
	{
		return new JPanel()
		{
			@Override
			public void paint(java.awt.Graphics g)
			{
				super.paint(g);
				for (int y = 0; y < map.length; y++)
					for (int x = 0; x < map.length; x++)
					{
						int value = map[x][y];
						if (value > 255)
							value = 255;
						else if (value < 0)
							value = 0;
						java.awt.Color color = new java.awt.Color(value, value, value);
						g.setColor(color);
						g.fillRect(x, y, 1, 1);
					}
			}
		};
	}

	/**
	 * The Main method.
	 * <p>
	 * @param args
	 */
	public static void main(String[] args)
	{
		DiamondSquare ds = new DiamondSquare();
		map = ds.buildMap(9, 128, 128, 0.8);
		JFrame frame = new Frame();
		JPanel panel = drawPanel();
		panel.setPreferredSize(new Dimension(map.length, map.length));
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

		System.out.println("Exporting...");
		exportImage(panel);
		System.out.println("Exported.");

		frame.setVisible(true);
	}

	public static void exportImage(JPanel panel)
	{
		BufferedImage img = new BufferedImage(panel.getWidth(),
											  panel.getHeight(),
											  BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		panel.printAll(g);
		g.dispose();
		try
		{
			ImageIO.write(img, "png", new File("Paint.png"));
		} catch (IOException exp)
		{
			System.err.println(exp);
		}
	}
}
