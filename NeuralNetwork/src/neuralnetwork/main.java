/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.ArrayList;

/**
 *
 * @author Romsahel
 */
public class main
{

	public static void main(String[] args)
	{
		NeuralNetwork nn = new NeuralNetwork(2, 4, 2);
		ArrayList<Pair<double[], double[]>> trainingSet = new ArrayList<>();
		trainingSet.add(new Pair(
				new double[]
				{
					0, 1
				},
				new double[]
				{
					0, 0
				})
		);
		trainingSet.add(new Pair(
				new double[]
				{
					1, 0
				},
				new double[]
				{
					0, 1
				})
		);
		trainingSet.add(new Pair(
				new double[]
				{
					1, 0
				},
				new double[]
				{
					1, 0
				})
		);
		trainingSet.add(new Pair(
				new double[]
				{
					0, 1
				},
				new double[]
				{
					1, 1
				})
		);
		nn.train(trainingSet);

		for (Pair<double[], double[]> set : trainingSet)
		{
			final double[] result = nn.get(set.getSecond());

			System.out.println("========================");
			System.out.println("Input: [" + doubleToBool(result[0], 0) + " != " + doubleToBool(result[1], 0) + "]");
			System.out.println("Result: " + doubleToBool(result[0], result[1]));
			System.out.println("========================");
		}
	}

	private static String doubleToBool(double d1, double d2)
	{
		return (d1 > d2) ? "True" : "False";
	}
}
