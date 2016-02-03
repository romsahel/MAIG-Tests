/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author Romsahel
 */
public class MCTS
{

	private static final int WIDTH = 8;

	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int RIGHT = 2;
	private static final int LEFT = 3;

	private static final int[] shifting = new int[]
	{
		-WIDTH, WIDTH, 1, -1
	};

	private char[] currentMap;
	private int goalPosition;
	protected static final char[] startingmap =
	{
		'@', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
		' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
		' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
		' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', 'G', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '
	};

	public static String getMapString(char[] map)
	{
		String result = "";
		for (int x = 0; x < map.length; x++)
			result += "" + map[x];
		return result;
	}
	private static final Random random = new Random();

	public MCTS(char[] map)
	{
		currentMap = map;
		for (int i = 0; i < currentMap.length; i++)
			if (currentMap[i] == 'G')
			{
				goalPosition = i;
				break;
			}
	}

	private boolean isFinished(char[] map)
	{
		return map[goalPosition] != 'G';
	}

	private int getCurrentPos(char[] map)
	{
		for (int i = 0; i < map.length; i++)
			if (map[i] == '@')
				return i;
		throw new Error("No current position could be found.");
	}

	private boolean isValid(int index, char[] map)
	{
		return index >= 0 && index < map.length && map[index] != '#';
	}

	private ArrayList<Integer> getPossibilities(char[] map)
	{
		int current = getCurrentPos(map);
		ArrayList<Integer> possibilities = new ArrayList<>();
		if (isValid(current - WIDTH, map))
			possibilities.add(UP);
		if (isValid(current + WIDTH, map))
			possibilities.add(DOWN);
		if (isValid(current + 1, map) && (current % WIDTH) + 1 < WIDTH)
			possibilities.add(RIGHT);
		if (isValid(current - 1, map) && (current % WIDTH) - 1 >= 0)
			possibilities.add(LEFT);
		return possibilities;
	}

	private char[] move(int move, char[] map)
	{
		int current = getCurrentPos(map);
		map = map.clone();
		map[current] = ' ';
		map[current + shifting[move]] = '@';
		return map;
	}

	public void play() throws Exception
	{
		while (!isFinished(currentMap))
		{
			printMap(currentMap);
			currentMap = getNextState(currentMap);
//			Thread.sleep(1000);
		}
	}

	private char[] getNextState(char[] map)
	{
		Node root = new Node(map.clone());
		int iterations = 0;
		while (iterations++ < 100000)
		{
			Node newNode = treePolicy(root);
			int reward = defaultPolicy(newNode);
			backup(newNode, reward);
		}
		return root.getBestChild().map;
	}

	private Node treePolicy(Node node)
	{
		while (!isFinished(node.map))
			if (!isFullyExpanded(node))
				return expand(node);
			else
				node = node.getBestUCTChild();
		return node;
	}

	private boolean isFullyExpanded(Node node)
	{
		return getPossibilities(node.map).size() == node.children.size();
	}

	private Node expand(Node node)
	{
		final ArrayList<Integer> possibilities = getPossibilities(node.map);
		for (Iterator<Integer> it = possibilities.iterator(); it.hasNext();)
		{
			Integer next = it.next();
			for (int i : node.triedActions)
				if (next == i)
				{
					it.remove();
					break;
				}
		}

		Node newNode = new Node(node.map);
		int move = possibilities.get(random.nextInt(possibilities.size()));
		newNode.map = move(move, node.map);

		newNode.parent = node;
		newNode.depth = node.depth + 1;
		node.children.add(newNode);
		for (int i = 0; i < node.triedActions.length; i++)
		{
			if (node.triedActions[i] != -1)
				continue;
			node.triedActions[i] = move;
			break;
		}
		return newNode;
	}

	private int defaultPolicy(Node node)
	{
		char[] map = node.map.clone();
		while (!isFinished(map))
		{
			final ArrayList<Integer> possibilities = getPossibilities(map);
			int move = possibilities.get(random.nextInt(possibilities.size()));
			map = move(move, map);
		}
		return (isFinished(map)) ? 100 : 0;
	}

	private void backup(Node node, int reward)
	{
		while (node != null)
		{
			node.visits++;
			node.reward += reward;
			node = node.parent;
		}
	}

	public static void main(String[] args) throws Exception
	{
		MCTS mcts = new MCTS(startingmap);
		mcts.play();
	}

	public void printMap(char[] map)
	{
		for (int i = 0; i < map.length; i++)
		{
			if (i % 8 == 0)
				System.out.println("+-+-+-+-+-+-+-+-+");
			System.out.print("|" + map[i]);
			if (i % 8 == 7)
				System.out.println("|");
		}
		System.out.println("+-+-+-+-+-+-+-+-+");
	}
}
