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
public class Node
{

	public Node parent;
	public int depth = 0;
	public Node right = null, left = null;

	public int x, y;
	public int width, height;

	public Node(Node parent, int x, int y, int width, int height)
	{
		this.parent = parent;
		this.depth = (parent != null) ? parent.depth : 0;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

}
