
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class QLearning
{

	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int RIGHT = 2;
	private static final int LEFT = 3;

	private static final int[] shifting = new int[]
	{
		-3, 3, 1, -1
	};

	private static final float learningRate = 1f;
	private static final float gamma = 0.5f;

	private final HashMap<String, float[]> states;
	private final HashMap<String, Integer> visits;
	private char[] currentMap;
	private final static Random random = new Random();

	public QLearning(String map)
	{
		this.currentMap = map.toCharArray();
		this.states = new HashMap<>();
		this.visits = new HashMap<>();
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

	private char[] move(int currentPos, int move, char[] map)
	{
		map = map.clone();
		map[currentPos] = ' ';
		map[currentPos + shifting[move]] = '@';
		return map;
	}

	private int getReward(int currentPos, int move, char[] map)
	{
		return (map[currentPos + shifting[move]] == 'G') ? 100 : 0;
	}

	private void getNextPosition(char[] map)
	{
		int current = getCurrentPos(map);
		final ArrayList<Integer> possibilities = getPossibilities(current, map);

		int move;
		if (random.nextInt(100) > 90)
			move = possibilities.get(random.nextInt(possibilities.size()));
		else
		{
			float[] qValues = getQValues(map);
			move = maxQValue(qValues);
			if (qValues[move] == 0)
				move = possibilities.get(random.nextInt(possibilities.size()));
		}
		final char[] newMap = move(current, move, map);

		float[] qValues = getQValues(newMap);

		final float[] qEntry = getQValues(map);
		final float alpha = 1f / visits.get(getMapString(currentMap));
		final int reward = getReward(current, move, map);
		qEntry[move] += alpha * (reward + gamma * (qValues[maxQValue(qValues)] - qEntry[move]));

		currentMap = newMap;
		final String mapString = getMapString(currentMap);
		visits.put(mapString, visits.get(mapString) + 1);
	}

	private float[] getQValues(char[] map)
	{
		final String mapString = getMapString(map);
		float[] qValues = states.get(mapString);
		if (qValues == null)
		{
			states.put(mapString, new float[]
			   {
				   0, 0, 0, 0
			});
			visits.put(mapString, 1);
			qValues = states.get(mapString);
		}
		return qValues;
	}

	String getMapString(char[] map)
	{
		String result = "";
		for (int x = 0; x < map.length; x++)
			result += "" + map[x];
		return result;
	}

	private int maxQValue(final float[] qValues)
	{
		float max = qValues[0];
		int index = 0;
		for (int i = 1; i < qValues.length; i++)
			if (qValues[i] > max)
			{
				index = i;
				max = qValues[i];
			}
		return index;
	}

	private ArrayList<Integer> getPossibilities(int current, char[] map)
	{
		ArrayList<Integer> possibilities = new ArrayList<>();
		if (isValid(current - 3, map))
			possibilities.add(UP);
		if (isValid(current + 3, map))
			possibilities.add(DOWN);
		if (isValid(current + 1, map) && (current % 3) + 1 < 3)
			possibilities.add(RIGHT);
		if (isValid(current - 1, map) && (current % 3) - 1 >= 0)
			possibilities.add(LEFT);
		return possibilities;
	}

	public void solve() throws Exception
	{
		int step = 1;
		while (!isFinished())
		{
//            Thread.sleep(1000);
//			print();
			getNextPosition(currentMap);

			step++;
		}
		allSteps += step;
		System.out.println("Finished in " + step + " steps.");
	}

	public boolean isFinished()
	{
		boolean finished = true;
		for (int i = 0; i < currentMap.length; i++)
			if (currentMap[i] == 'G')
			{
				finished = false;
				break;
			}
		return finished;
	}

	private static int allSteps = 0;

	private void print()
	{
		for (int i = 0; i < currentMap.length; i++)
		{
			if (i % 3 == 0)
				System.out.print("\n|");
			System.out.print(currentMap[i] + "|");
		}
		System.out.println();
		for (int i = 0; i < 3; i++)
			System.out.print("--");
	}

	public static void main(String[] args) throws Exception
	{
		final String initMap = "@ #  G #  #     # ";
		final QLearning qLearning = new QLearning(initMap);

		int i = 0;
		for (; i < 100; i++)
		{
			qLearning.setCurrentMap(initMap.toCharArray());
			qLearning.solve();
		}
		System.out.println((float) allSteps / (float) i);
	}

	/**
	 * @param currentMap the currentMap to set
	 */
	public void setCurrentMap(char[] currentMap)
	{
		this.currentMap = currentMap;
	}
}
