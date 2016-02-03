package dk.itu.lSystem;

import dk.itu.lSystem.util.Canvas;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * The LSystem class is for implementing your own L-system that can expand
 * n number of times the axiom of the system. And visualize this expansion
 * utilizing a turtle.
 * <p>
 * @author Anders Hartzen
 *
 */
public class LSystem
{

	/**
	 * The Axiom (i.e. start state) for the L-system.
	 */
	public String axiom;
	public String endState;

	/**
	 * The production rules for the L-system.
	 */
	public Map<String, String> productionRule = new HashMap<>();

	/**
	 * X start position for the turtle. Used for visualization.
	 */
	public double startx = 200;

	/**
	 * Y start position for the turtle. Used for visualization.
	 */
	public double starty = 200;

	/**
	 * Start angle for the turtle. Used for visualization.
	 */
	public double startAngle = 0;

	/**
	 * The turn angle for the turtle to use when a turn symbol is processed. Used for visualization.
	 * PI/6 is 30 degrees in radians.
	 */
	public final double turnAngle = Math.PI / 2;

	/**
	 * Length of each step the turtle takes, when a move forward symbol is processed. Used for visualization.
	 */
	public double length = 10;

	public LSystem(double length)
	{
		this.length = length;
	}
	public LSystem()
	{
	}



	/**
	 * The expand method is used to expand the axiom of the L-system n number of times.
	 * <p>
	 * @param depth The number of times to expand the axiom.
	 */
	public void expand(int depth)
	{
		String newState = "";
		endState = axiom;
		while (depth-- > 0)
		{
			for (char c : endState.toCharArray())
			{
				final String v = String.valueOf(c);
				final String result = productionRule.get(v);
				if (result != null)
					newState += result;
				else
					newState += v;
			}

			endState = newState;
			newState = "";
			System.out.println(endState);
		}
	}

	/**
	 * After expansion we need the turtle to process the expansion and move around on screen to draw our
	 * plant.
	 * <p>
	 * @param C The canvas to draw turtle movement on.
	 */
	public void interpret(Canvas C)
	{
		Stack<State> states = new Stack<>();
		State currentState = new State(startx, starty, startAngle, length, turnAngle);
		for (char c : endState.toCharArray())
			switch (c)
			{
				case 'F':
				case 'f':
					final double x = currentState.getX();
					final double y = currentState.getY();
					final double angle = currentState.getAngle();

					currentState.setCoords(x + length * Math.cos(angle),
										   y + length * Math.sin(angle));

					if (c == 'F')
						C.drawLine((int) x, (int) y, (int) (currentState.getX()), (int) (currentState.getY()));
					break;
				case '[':
					states.push(new State(currentState));
					break;
				case ']':
					currentState = states.pop();
					break;
				case '-':
					currentState.changeAngle(-1);
					break;
				case '+':
					currentState.changeAngle(1);
					break;
			} //
		//			try
		//			{
		//				Thread.sleep(10);
		//			} catch (InterruptedException e)
		//			{
		//				e.printStackTrace();
		//			}
	}
}
