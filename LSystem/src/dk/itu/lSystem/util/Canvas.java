package dk.itu.lSystem.util;

/**
 * Class Canvas - a class to allow for simple graphical
 * drawing on a canvas.
 * <p>
 * @author: Bruce Quig
 * date: 03-09-1999
 * <p>
 * Small modifications by Anders Hartzen
 * date: 19-09-2012
 */
import dk.itu.lSystem.LSystem;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas implements CanvasInterface
{

	// instance variables - replace the example below with your own
	private JFrame display;
	public JPanel canvas;
	private Graphics2D graphic;
	private Color backgroundColour;

	/**
	 * WaitTime is used to stop the main thread momentarily each time
	 * the DrawLine method is called to avoid a graphics bug.
	 * Without doing this nothing is shown on screen.
	 * <p>
	 */
	public int waitTime = 50; //milliseconds

	/**
	 * Constructor for objects of class DisplayCanvas
	 * <p>
	 * @param title   title to appear in Canvas Frame
	 * @param width   the desired width for the canvas
	 * @param height  the desired height for the canvas
	 * @param bgClour the desired background clour of the canvas
	 */
	public Canvas(String title, int width, int height, Color bgColour)
	{
		display = new JFrame();
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = (JPanel) display.getContentPane();
//        JTextArea t = new JTextArea();
//        t.setSize(new Dimension(50,50));
//        t.setPreferredSize(new Dimension(50,50));
//        display.setLayout(new BorderLayout());
//        display.add(t);
//        canvas = new JPanel();
//        canvas.setBackground(Color.WHITE);
//        display.add(canvas);
		display.setTitle(title);
		display.setSize(width, height);
		canvas.setBackground(bgColour);
		backgroundColour = bgColour;
	}

	/**
	 * Constructor for objects of class DisplayCanvas with default
	 * height, width and background colour (600, 600, white).
	 * <p>
	 * @param title title to appear in Canvas Frame
	 */
	public Canvas(String title)
	{
		this(title, 600, 400, Color.white);
	}

	/**
	 * Constructor for objects of class DisplayCanvas with a default
	 * background colour (white).
	 * <p>
	 * @param title  title to appear in Canvas Frame
	 * @param width  the desired width for the canvas
	 * @param height the desired height for the canvas
	 */
	public Canvas(String title, int width, int height)
	{
		this(title, width, height, Color.white);
	}

	/**
	 * Sets the canvas visibility and brings canvas to the front of screen
	 * when made visible. This method can also be used to bring an already
	 * visible canvas to the front of other windows.
	 * <p>
	 * @param visible boolean value representing the desired visibility of
	 *                the canvas (true or false)
	 */
	public void setVisible(boolean visible)
	{
		display.setVisible(visible);
		if (graphic == null)
			graphic = (Graphics2D) canvas.getGraphics();
	}

	/**
	 * provides information on visibility of the Canvas
	 * <p>
	 * @return boolean value representing the visibility of
	 *         the canvas (true or false)
	 */
	public boolean isVisible()
	{
		return display.isVisible();
	}

	/**
	 * draws a given shape onto the canvas
	 * <p>
	 * @param shape the shape object to be drawn on the canvas
	 */
	public void draw(Shape shape)
	{
		graphic.draw(shape);
	}

	/**
	 * fills the internal dimensions of a given shape
	 * with the current foreground colour of the canvas.
	 * <p>
	 * @param shape the shape object to be filled
	 */
	public void fill(Shape shape)
	{
		graphic.fill(shape);
	}

	/**
	 * erases a given shape on the screen
	 * <p>
	 * @param shape the shape object to be erased
	 */
	public void erase(Shape shape)
	{
		// keep track of the current colour
		Color original = graphic.getColor();
		// set colour to that of the canvas background
		graphic.setColor(backgroundColour);
		// fill shape again using background colour
		graphic.fill(shape);
		// revert graphic object back to original foreground colour
		graphic.setColor(original);
	}

	/**
	 * draws an image onto the canvas
	 * <p>
	 * @param image the Image object to be displayed
	 * @param x     x co-ordinate for Image placement
	 * @param y     y co-ordinate for Image placement
	 * <p>
	 * @return returns boolean value representing whether
	 *         the image was completely loaded
	 */
	public boolean drawImage(Image image, int x, int y)
	{
		return graphic.drawImage(image, x, y, null);
	}

	/**
	 * draws a String on the Canvas
	 * <p>
	 * @param text the String to be displayed
	 * @param x    x co-ordinate for text placement
	 * @param y    y co-ordinate for text placement
	 */
	public void drawString(String text, int x, int y)
	{
		graphic.drawString(text, x, y);
	}

	/**
	 * erases a String on the Canvas by rewriting the same String
	 * using the background colour as the colour of the String.
	 * <p>
	 * @param text the String to be displayed
	 * @param x    x co-ordinate for text placement
	 * @param y    y co-ordinate for text placement
	 */
	public void eraseString(String text, int x, int y)
	{
		Color original = graphic.getColor();
		graphic.setColor(backgroundColour);
		graphic.drawString(text, x, y);
		graphic.setColor(original);
	}

	/**
	 * Draws a straight line on the Canvas
	 * <p>
	 * @param x1 x co-ordinate of start of line
	 * @param y1 y co-ordinate of start of line
	 * @param x2 x co-ordinate of end of line
	 * @param y2 y co-ordinate of end of line
	 */
	public void drawLine(int x1, int y1, int x2, int y2)
	{
		y1 = getInvertedPos(y1);
		y2 = getInvertedPos(y2);
		graphic.drawLine(x1, y1, x2, y2);
		wait(this.waitTime);
	}

	private int getInvertedPos(int y)
	{
		return display.getHeight() - y;
	}

	/**
	 * sets the foreground colour of the Canvas
	 * <p>
	 * @param newColour the new colour for the foreground of the Canvas
	 */
	public void setForegroundColour(Color newColour)
	{
		graphic.setColor(newColour);
	}

	/**
	 * returns the current colour of the foreground
	 * <p>
	 * @return the colour of the foreground of the Canvas
	 */
	public Color getForegroundColour()
	{
		return graphic.getColor();
	}

	/**
	 * sets the background colour of the Canvas
	 * <p>
	 * @param newColour the new colour for the background of the Canvas
	 */
	public void setBackgroundColour(Color newColour)
	{
		backgroundColour = newColour;
		graphic.setBackground(newColour);
	}

	/**
	 * returns the current colour of the background
	 * <p>
	 * @return the colour of the background of the Canvas
	 */
	public Color getBackgroundColour()
	{
		return backgroundColour;
	}

	/**
	 * Changes the current Font used on the Canvas
	 * <p>
	 * @param y a sample parameter for a method
	 * <p>
	 * @return the sum of x and y
	 * */
	public void setFont(Font newFont)
	{
		graphic.setFont(newFont);
	}

	public Font getFont()
	{
		return graphic.getFont();
	}

	/**
	 * sets the size of the canvas display
	 * <p>
	 * @param width  new width
	 * @param height new height
	 */
	public void setSize(int width, int height)
	{
		display.setSize(width, height);
	}

	/**
	 * Waits for a specified number of milliseconds before finishing.
	 * This provides an easy way to specify a small delay which can be
	 * used when producing animations.
	 * <p>
	 * @param milliseconds the number
	 * */
	public void wait(int milliseconds)
	{
		try
		{
			Thread.sleep(milliseconds);
		} catch (Exception e)
		{
			// ignoring exception at the moment
		}
	}

	/**
	 * The main method - use this to run your L-system.
	 * <p>
	 * @param args
	 */
	public static void main(String[] args)
	{
		Canvas c = new Canvas("");
		c.setVisible(true);

		LSystem lSystem = koch();
		lSystem.interpret(c.getInstance());
	}

	private static LSystem basic()
	{
		LSystem lSystem = new LSystem();
		lSystem.axiom = "F";
		lSystem.productionRule.put("F", "F[-F]F[+F][F]");
		lSystem.expand(5);
		return lSystem;
	}


	private static LSystem aaa()
	{
		LSystem lSystem = new LSystem();
		lSystem.axiom = "F++F++F";
		lSystem.productionRule.put("F", "F-F++F-F+++++");
		lSystem.expand(5);
		return lSystem;
	}

	private static LSystem fractal()
	{
		LSystem lSystem = new LSystem();
		lSystem.axiom = "F+F+F+F";
		lSystem.productionRule.put("F", "FF+F+F+F+F+F-F");

		lSystem.expand(4);
		return lSystem;
	}

	private static LSystem koch()
	{
		LSystem lSystem = new LSystem();
		lSystem.axiom = "F";
		lSystem.productionRule.put("F", "F+F-F-F+F");

		lSystem.expand(3);
		return lSystem;
	}

	@Override
	public Canvas getInstance()
	{
		Canvas c = new Canvas("L-System");
		c.setVisible(true);
		return c;
	}

}
