/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeongenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Romsahel
 */
public class Room
{

	public static Random random = new Random();
	public final Cell pos;
	public final ArrayList<Door> doors;
	public final int width;
	public final int height;

	public Room(Cell pos, int width, int height)
	{
		this.pos = pos;
		this.width = Math.min(width, DungeonGenerator.Width - pos.x - 1);
		this.height = Math.min(height, DungeonGenerator.Height - pos.y - 1);
		this.doors = new ArrayList<>();
		System.out.println("Adding room at " + pos.toString() + "of " + width + "x" + height + ".");
	}

//	public Door addDoor()
//	{
//		// space above
//		if (pos.y > 1)
//		{
//
//		}
//		// space below
//		if (pos.y < DungeonGenerator.Height - 1)
//		{
//
//		}
//		// space on the right
//		if (pos.x > 1)
//		{
//
//		}
//		// space on the left
//		if (pos.x < DungeonGenerator.Width - 1)
//		{
//
//		}
//
//		boolean isHorizontal = random.nextBoolean();
//		Cell doorPos;
//		if (isHorizontal)
//			doorPos = new Cell(pos.x + width, pos.y + random.nextInt(height));
//		else
//			doorPos = new Cell(pos.x + random.nextInt(width), pos.y + height);
//
//		Door door = new Door(doorPos, ());
//		doors.add(door);
//		return door;
//	}

	public char[][] stringify(char[][] str)
	{
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				str[pos.y + i][pos.x + j] = ' ';

		for (Door door : doors)
			str[door.pos.y][door.pos.x] = 'O';

		return str;
	}
}
