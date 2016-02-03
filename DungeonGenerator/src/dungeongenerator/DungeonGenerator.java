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
public class DungeonGenerator
{

	private static Random random = new Random();
	public static int Width = 10, Height = 6;
	private static ArrayList<Room> rooms = new ArrayList<>();

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Node root = new Node(null, 0, 0, Width, Height);

		partition(root);

		partitionToRoom(root);

//		for (int i = 0; i < 50; i++)
//		{
//			Cell pos = new Cell(random.nextInt(Width), random.nextInt(Height));
//			Room room = addRoom(pos);
//		}
////		Door door = room.addDoor();
//
////		room = addRoom(door.pos);
		print();
	}

	private static void partition(Node node)
	{
		if ((random.nextBoolean() || node.height <= 5) && node.width > 5)
		{
			int newWidth = 5 + random.nextInt(node.width - 5);
			node.left = new Node(node, node.x, node.y, newWidth, node.height);
			node.right = new Node(node, newWidth + node.x, node.y, newWidth, node.height);
		}
		else if (node.height > 5)
		{
			int newHeight = 5 + random.nextInt(node.height - 5);
			node.left = new Node(node, node.x, node.y, node.width, newHeight);
			node.right = new Node(node, node.x, node.y + newHeight, node.width, newHeight);
		}
		else
			return;
		partition(node.left);
		partition(node.right);
	}

	private static void partitionToRoom(Node node)
	{
		if (node.left == null && node.right == null)
			rooms.add(new Room(new Cell(node.x, node.y), node.width, node.height));
		else
		{
			if (node.left != null)
				partitionToRoom(node.left);
			if (node.right != null)
				partitionToRoom(node.right);
		}
	}

	private static Room addRoom(Cell pos)
	{
		return addRoom(pos, 8);
	}

	private static Room addRoom(Cell pos, int factor)
	{
		Room room = new Room(pos, 1 + random.nextInt(Width / factor - 1), 1 + random.nextInt(Height / factor - 1));
		rooms.add(room);
		return room;
	}

	private static void print()
	{
		char[][] map = new char[Height][Width];
		for (char[] map1 : map)
			for (int j = 0; j < map1.length; j++)
				map1[j] = 'x';

		for (Room r : rooms)
			map = r.stringify(map);

		for (char[] str : map)
			System.out.println(str);
	}

}
