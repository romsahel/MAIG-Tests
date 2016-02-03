
import java.util.Random;

public class DiamondSquare
{

	private static final Random random = new Random();
	private static int[][] map;

	/**
	 * Build heightmap using the Diamond Square
	 * <p>
	 * @param iterations How many iterations to run (influences the size of the height map)
	 * @param seed       The height to seed the four corners of the first square with.
	 * @param variation  The variation to used for the randomized heights.
	 * @param roughness  The roughness factor to use to decrease variation over time.
	 * <p>
	 * @return
	 */
	public int[][] buildMap(int iterations, int seed, float variation, double roughness)
	{
		final int size = (int) Math.pow(2, iterations) + 1;
		map = new int[size][size];

		int length = map.length - 1;
		int sideLength = length;
		map[0][0] = map[length][0] = map[0][length] = map[length][length] = seed;

		while (sideLength > 1)
		{
			int half = sideLength / 2;

			// DiamondStep foreach square
			for (int x = 0; x < length; x += sideLength)
				for (int y = 0; y < length; y += sideLength)
					diamondStep(half, x, y, variation);

			// SquareStep foreach diamond
			for (int x = half; x < length; x += sideLength)
				for (int y = 0; y <= length; y += sideLength)
					squareStep(x, y, half, variation);
			for (int x = 0; x <= length; x += sideLength)
				for (int y = half; y < length; y += sideLength)
					squareStep(x, y, half, variation);

			sideLength /= 2;
			variation = (float) (variation * Math.pow(2, -roughness));
		}
		return map;
	}

	private void diamondStep(int half, int xOffset, int yOffset, float bound)
	{
		final int midX = xOffset + half, midY = yOffset + half;

		if (!isValid(midX, midY))
			return;

		map[midX][midY] = randomizedAverage(bound,
											map[midX - half][midY - half],
											map[midX - half][midY + half],
											map[midX + half][midY + half],
											map[midX + half][midY - half]);
	}

	private void squareStep(int x, int y, int half, float bound)
	{
		if (isValid(x, y))
			map[x][y] = randomizedAverage(bound,
										  getColor(x - half, y),
										  getColor(x + half, y),
										  getColor(x, y - half),
										  getColor(x, y + half));
	}

	private int randomizedAverage(float bound, int... args)
	{
		int result = 0;
		int nb = 0;
		for (int arg : args)
		{
			if (arg == -1)
				continue;
			result += arg;
			nb++;
		}
		result = (result / nb) + getRandom((int) bound);
		return Math.max(result, 0);
	}

	private int getColor(int x, int y)
	{
		if (isValid(x, y))
			return map[x][y];
		return -1;
	}

	private int getRandom(int bound)
	{
		return (bound == 0) ? 0 : random.nextInt(bound * 2) - bound;
	}

	private boolean isValid(int x, int y)
	{
		return (x >= 0 && x < map.length) && (y >= 0 && y < map[x].length);
	}

}
