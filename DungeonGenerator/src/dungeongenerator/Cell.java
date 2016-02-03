/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeongenerator;

/**
 *
 * @author Romsahel
 */
public class Cell
{

	public int x, y;

	public Cell(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}

}
