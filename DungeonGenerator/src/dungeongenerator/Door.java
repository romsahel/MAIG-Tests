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
public class Door {

	public Cell pos;
	public int dirX, dirY;

	public Door(Cell pos, int dirX, int dirY)
	{
		this.pos = pos;
		this.dirX = dirX;
		this.dirY = dirY;
	}

}
