package ru.mirea.heruistic.cube;

/**
 *                        -------------------
 *                       |    |  1 |  2 |    |
 *                       ---------------------
 *                       |  4 |    |    |  7 |
 *                       ---------------------
 *                       |  8 |    |    | 11 |
 *                       ---------------------
 *                       |    | 13 | 14 |    |
 *                        -------------------
 *  -------------------   -------------------   -------------------   -------------------
 * |    | 17 | 18 |    | |    | 33 | 34 |    | |    | 49 | 50 |    | |    | 65 | 66 |    |
 * --------------------- --------------------- --------------------- ---------------------
 * | 20 |    |    | 23 | | 36 |    |    | 39 | | 52 |    |    | 55 | | 68 |    |    | 71 |
 * --------------------- --------------------- --------------------- ---------------------
 * | 24 |    |    | 27 | | 40 |    |    | 43 | | 56 |    |    | 59 | | 72 |    |    | 75 |
 * --------------------- --------------------- --------------------- ---------------------
 * |    | 29 | 30 |    | |    | 45 | 46 |    | |    | 61 | 62 |    | |    | 77 | 78 |    |
 *  -------------------   -------------------   -------------------   -------------------
 *                        -------------------
 *                       |    | 81 | 82 |    |
 *                       ---------------------
 *                       | 84 |    |    | 87 |
 *                       ---------------------
 *                       | 88 |    |    | 91 |
 *                       ---------------------
 *                       |    | 93 | 94 |    |
 *                        -------------------
 */
public enum Edge {
	UB1( 1, 66,  1,  1), UB0( 2, 65, -1,  0), UL0( 4, 17, -1,  4), UR1( 7, 50,  1,  5), UL1( 8, 18,  1,  2), UR0(11, 49, -1,  3), UF0(13, 33, -1,  7), UF1(14, 34,  1,  6),
	BL1(20, 71, -1, 14), BR0(55, 68,  1, 15), FL0(23, 36,  1, 12), FR1(52, 39, -1, 13), FL1(27, 40, -1, 10), FR0(56, 43,  1, 11), BL0(24, 75,  1,  8), BR1(59, 72, -1,  9),
	DF1(81, 45,  1, 17), DF0(82, 46, -1, 16), DL0(84, 30, -1, 20), DR1(87, 61,  1, 21), DL1(88, 29,  1, 18), DR0(91, 62, -1, 19), DB0(93, 78, -1, 23), DB1(94, 77,  1, 22);
	
	public static final int NUM_EDGES = 24;
	
	public static final int[] VALUES = {
		16, 16,  1,  9,  1,  9,  4,  4,
		17, 25,  5, 13,  5, 13, 17, 25,
		29, 29, 26, 34, 26, 34, 41, 41
	};
	
	private static final int[][][] EDGE_LOOKUP = generateEdgeLookup();
	
	private static final int[][][] ORIENTATION_LOOKUP = generateOrientationLookup();
	
	public final int x;
	public final int y;
	private final int orientation;
	private final int pair;
	
	private Edge(int x, int y, int orientation, int pair) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.pair = pair;
	}
	
	public int indexOf(Cube cube) {
		byte[] colors = cube.getColors();
		
		int value = 0;
		value += Math.pow(colors[x], 2);
		value += Math.pow(colors[y], 2);
		
		for (int i = 0; i < NUM_EDGES; i++) {
			if (value == VALUES[i]) return i;
		}
		
		return -1;
	}
	
	public static int[] getConfiguration(Cube cube) {
		byte[] colors = cube.getColors();
		int[] result = new int[NUM_EDGES];
		
		for (int i = 0; i < NUM_EDGES; i++) {
			Edge edge = values()[i];
			
			int color0 = colors[edge.x];
			int color1 = colors[edge.y];
			
			result[i] = EDGE_LOOKUP[i][color0][color1];
		}
		
		return result;
	}
	
	public static int[] getOrientation(Cube cube) {
		byte[] colors = cube.getColors();
		int[] result = new int[NUM_EDGES];
		
		for (int i = 0; i < NUM_EDGES; i++) {
			Edge edge = values()[i];
			
			int color0 = colors[edge.x];
			int color1 = colors[edge.y];
			
			result[i] = ORIENTATION_LOOKUP[i][color0][color1];
		}
		
		return result;
	}
	
	public static int getParity(Cube cube) {
		int[] array = getConfiguration(cube);
		int count = 0;
		
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				count += (array[j] < array[i]) ? 1 : 0;
			}
		}
		
		return count % 2;
	}
	
	public static int[] getPairParity(Cube cube) {
		int[] configuration = getConfiguration(cube);
		int[] orientation = getOrientation(cube);
		int[] result = new int[NUM_EDGES];
		int[] helper = new int[NUM_EDGES];

		for (int i = 0; i < NUM_EDGES; i++) {
			helper[configuration[i]] = orientation[i];
		}

		for (int i = 0; i < NUM_EDGES; i++) {
			result[i] = helper[i] * helper[values()[i].pair];
		}
		
		return result;
	}
	
	private static int[][][] generateEdgeLookup() {
		int[][][] result = new int[NUM_EDGES][Cube.NUM_FACES][Cube.NUM_FACES];
		
		for (int i = 0; i < NUM_EDGES; i++) {
			Edge edge1 = values()[i];
			
			for (int j = 0; j < NUM_EDGES; j++) {
				Edge edge2 = values()[j];
				
				int orientation = edge1.orientation * edge2.orientation;
				int x = (orientation == 1 ? edge2.x : edge2.y) / Cube.N2;
				int y = (orientation == 1 ? edge2.y : edge2.x) / Cube.N2;
				
				result[i][x][y] = j;
			}
		}
		
		return result;
	}
	
	private static int[][][] generateOrientationLookup() {
		int[][][] result = new int[NUM_EDGES][Cube.NUM_FACES][Cube.NUM_FACES];
		Cube cube1 = new Cube();
		Cube cube2 = new Cube();
		Cube cube3 = new Cube();
		
		// Brute force orientations.
		for (int index = 0; index < 50; index++) {
			cube1.twist(Twist.F);
			fillArray(result, cube1);
			cube1.twist(Twist.U);
			fillArray(result, cube1);
			cube1.twist(Twist.B);
			fillArray(result, cube1);

			cube2.twist(Twist.U);
			fillArray(result, cube2);
			cube2.twist(Twist.B);
			fillArray(result, cube2);
			cube2.twist(Twist.D);
			fillArray(result, cube2);

			cube3.twist(Twist.D);
			fillArray(result, cube3);
			cube3.twist(Twist.F);
			fillArray(result, cube3);
			cube3.twist(Twist.U);
			fillArray(result, cube3);
		}
		
		return result;
	}
	
	private static void fillArray(int[][][] array, Cube cube) {
		byte[] colors = cube.getColors();
		
		for (int i = 0; i < NUM_EDGES; i++) {
			Edge edge = values()[i];
			
			int color0 = colors[edge.x];
			int color1 = colors[edge.y];
			
			array[i][color0][color1] = 1;
			array[i][color1][color0] = -1;
		}
	}
}