/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcts;

import java.util.ArrayList;

/**
 *
 * @author Romsahel
 */
public class Node
{

	private static final double C = 100;
	public final int[] triedActions = new int[]
	{
		-1, -1, -1, -1
	};
	public char[] map;
	public int visits;
	public int reward;
	public Node parent;
	public final ArrayList<Node> children;
	public int depth;

	public Node(char[] state)
	{
		this.children = new ArrayList<>();
		this.reward = 0;
		this.visits = 0;
		this.depth = 0;
		this.map = state;
	}

	public double getUCTScore()
	{
		double exploration = 2 * Math.log(parent.visits) / visits;
		return (reward / visits) + C * Math.sqrt(exploration);
	}

	public Node getBestUCTChild()
	{
		Node bestChild = null;
		double score = Double.NEGATIVE_INFINITY;
		for (Node node : children)
		{
			double uctScore = node.getUCTScore();
			if (uctScore > score)
			{
				bestChild = node;
				score = uctScore;
			}
		}
		return bestChild;
	}


	public Node getBestChild()
	{
		Node bestChild = null;
		double score = Double.NEGATIVE_INFINITY;
		for (Node node : children)
		{
			double uctScore = node.reward;
			if (uctScore > score)
			{
				bestChild = node;
				score = uctScore;
			}
		}
		return bestChild;
	}
}
