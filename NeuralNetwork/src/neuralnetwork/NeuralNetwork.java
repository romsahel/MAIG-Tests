/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Romsahel
 */
public class NeuralNetwork
{

	private final Random random = new Random();

//	private final int nbHiddenLayers;
	private final double[] inputN, hiddenN, outputN;
	private final double[] biasHiddenN, biasOutputN;
	private final double[] wInputHidden, wHiddenOutput;

	public NeuralNetwork(int nbInput, int nbHidden, int nbOutput)
	{
//		this.nbHiddenLayers = nbHiddenLayers;

		this.inputN = new double[nbInput];
		this.hiddenN = new double[nbHidden];
		this.outputN = new double[nbOutput];



		this.biasHiddenN = new double[nbHidden];
		for (int i = 0; i < biasHiddenN.length; i++)
			biasHiddenN[i] = (random.nextDouble() - 0.5d) / 2d;

		this.biasOutputN = new double[nbOutput];
		for (int i = 0; i < biasOutputN.length; i++)
			biasOutputN[i] = (random.nextDouble() - 0.5d) / 2d;

		this.wInputHidden = new double[nbInput * nbHidden];
		for (int i = 0; i < wInputHidden.length; i++)
			wInputHidden[i] = random.nextDouble() - 0.5d;

		this.wHiddenOutput = new double[nbHidden * nbOutput];
		for (int i = 0; i < wHiddenOutput.length; i++)
			wHiddenOutput[i] = random.nextDouble() - 0.5d;
	}

	public int train(ArrayList<Pair<double[], double[]>> trainingSet)
	{
		int iterations = 0;
		double averageDelta = 0;
		int numberOfDeltas = 0;
		do
		{
			averageDelta = 0;
			numberOfDeltas = 0;
			for (Pair<double[], double[]> set : trainingSet)
			{
				for (int i = 0; i < inputN.length; i++)
					inputN[i] = set.getSecond()[i];

				propagateForward();

				// compute delta for output layer
				double[] outputDelta = new double[outputN.length];
				for (int i = 0; i < outputN.length; i++)
				{
					outputDelta[i] = outputN[i] * (1 - outputN[i]) * (set.getFirst()[i] - outputN[i]);
					averageDelta += Math.abs(outputDelta[i]);
				}
				numberOfDeltas += outputN.length;

				// compute delta for hidden layer
				double[] hiddenDelta = new double[hiddenN.length];
				for (int i = 0; i < hiddenN.length; i++)
				{
					hiddenDelta[i] = 0;
					for (int j = 0; j < outputN.length; j++)
						hiddenDelta[i] += outputDelta[j] * wHiddenOutput[j * hiddenN.length + i];
					hiddenDelta[i] *= hiddenN[i] * (1 - hiddenN[i]);
					averageDelta += Math.abs(hiddenDelta[i]);
				}
				numberOfDeltas += hiddenN.length;

				// backpropagate hidden to input
				for (int i = 0; i < hiddenN.length; i++)
					for (int j = 0; j < inputN.length; j++)
						wInputHidden[i * inputN.length + j] += 0.5 * hiddenDelta[i] * inputN[j];

				// propagate output to hidden
				for (int i = 0; i < outputN.length; i++)
					for (int j = 0; j < hiddenN.length; j++)
						wHiddenOutput[i * hiddenN.length + j] += 0.5 * outputDelta[i] * hiddenN[j];

				// update biases
				for (int i = 0; i < hiddenN.length; i++)
					biasHiddenN[i] += 0.5 * hiddenDelta[i];

				for (int i = 0; i < outputN.length; i++)
					biasOutputN[i] += 0.5 * outputDelta[i];
			}
			iterations++;
		} while (averageDelta / numberOfDeltas > 0.001d);
		System.out.println("Training done in: " + iterations + " iterations.");
		return iterations;
	}

	private void propagateForward()
	{
		// propagate input to hidden
		for (int i = 0; i < hiddenN.length; i++)
		{
			hiddenN[i] = 0;
			for (int j = 0; j < inputN.length; j++)
				hiddenN[i] += wInputHidden[i * inputN.length + j] * inputN[j];
			hiddenN[i] = sigmoid(biasHiddenN[i] + hiddenN[i]);
		}
		// propagate hidden to output
		for (int i = 0; i < outputN.length; i++)
		{
			outputN[i] = 0;
			for (int j = 0; j < hiddenN.length; j++)
				outputN[i] += wHiddenOutput[i * hiddenN.length + j] * hiddenN[j];
			outputN[i] = sigmoid(biasOutputN[i] + outputN[i]);
		}
	}

	public double[] get(double[] input)
	{
		for (int i = 0; i < inputN.length; i++)
			inputN[i] = input[i];

		propagateForward();

		return outputN.clone();
	}


	private static double sigmoid(double x)
	{
		return 1 / (1 + Math.exp(-x));
	}

}
