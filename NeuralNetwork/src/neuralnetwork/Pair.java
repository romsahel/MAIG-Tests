/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

/**
 *
 * @author Romsahel
 */
public class Pair<K, V>
{

	private final K element0;
	private final V element1;

	public static <K, V> Pair<K, V> createPair(K element0, V element1)
	{
		return new Pair<K, V>(element0, element1);
	}

	public Pair(K element0, V element1)
	{
		this.element0 = element0;
		this.element1 = element1;
	}

	public K getFirst()
	{
		return element0;
	}

	public V getSecond()
	{
		return element1;
	}

}
