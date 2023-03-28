package ru.mirea.heruistic.cube;

/**
 *                        -------------------
 *                       |    |  1 |    |    |
 *                       ---------------------
 *                       |    |    |    |    |
 *                       ---------------------
 *                       |  8 |    |    | 11 |
 *                       ---------------------
 *                       |    | 13 |    |    |
 *                        -------------------
 *  -------------------   -------------------   -------------------   -------------------
 * |    |    | 18 |    | |    | 33 |    |    | |    | 49 |    |    | |    |    | 66 |    |
 * --------------------- --------------------- --------------------- ---------------------
 * |    |    |    |    | |    |    |    |    | |    |    |    |    | |    |    |    |    |
 * --------------------- --------------------- --------------------- ---------------------
 * | 24 |    |    | 27 | | 40 |    |    | 43 | | 56 |    |    | 59 | | 72 |    |    | 75 |
 * --------------------- --------------------- --------------------- ---------------------
 * |    | 29 |    |    | |    | 45 |    |    | |    |    | 62 |    | |    |    | 78 |    |
 *  -------------------   -------------------   -------------------   -------------------
 *                        -------------------
 *                       |    | 81 |    |    |
 *                       ---------------------
 *                       |    |    |    |    |
 *                       ---------------------
 *                       | 88 |    |    | 91 |
 *                       ---------------------
 *                       |    | 93 |    |    |
 *                        -------------------
 */
public enum Dedge {
	UB( 1, 66), UF(13, 33), DF(81, 45), DB(93, 78),
	UL( 8, 18), UR(11, 49), DL(88, 29), DR(91, 62),
	FL(27, 40), FR(56, 43), BL(24, 75), BR(59, 72);
	
	public static final int NUM_DEDGES = 12;
	
	private static final int[] VALUES = {
		16,  4, 29, 41,
		 1,  9, 26, 34,
		 5, 13, 17, 25
	};
	
	private static final int[][] DEDGE_LOOKUP = makeDedgeLookup();
	public final int x;
	public final int y;
	
	private Dedge(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int indexOf(Cube cube) {
		byte[] colors = cube.getColors();
		int value = 0;
		
		value += Math.pow(colors[x], 2);
		value += Math.pow(colors[y], 2);
		
		for (int i = 0; i < NUM_DEDGES; i++) {
			if (value == VALUES[i]) return i;
		}
		
		return -1;
	}
	
	public static int[] getConfiguration(Cube cube) {
		byte[] colors = cube.getColors();
		int[] result = new int[NUM_DEDGES];
		
		for (int i = 0; i < NUM_DEDGES; i++) {
			Dedge dedge = values()[i];
			
			int color0 = colors[dedge.x];
			int color1 = colors[dedge.y];
			
			result[i] = DEDGE_LOOKUP[color0][color1];
		}
		
		return result;
	}
	
	public static int[] getOrientation(Cube cube) {
		byte[] colors = cube.getColors();
		int[] result = new int[NUM_DEDGES];
		
		for (int i = 0; i < NUM_DEDGES; i++) {
			Dedge dedge = values()[i];
			
			int color0 = colors[dedge.x];
			int color1 = colors[dedge.y];
			
			result[i] = isGoodEdge(color0, color1, dedge.x, dedge.y) ? 0 : 1;
		}
		
		return result;
	}
	
	public static int getParity(Cube cube) {
		return getParity(getPairConfiguration(cube));
	}
	
	public static int getParity(int[] array) {
		int count = 0;
		
		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				count += (array[j] < array[i]) ? 1 : 0;
			}
		}
		
		return count % 2;
	}
	
	private static int[] getPairConfiguration(Cube cube) {
		int[] edges = Edge.getConfiguration(cube);
		int[] pairs = new int[] { 0, 0, 1, 2, 1, 2, 3, 3, 4, 5, 6, 7, 6, 7, 4, 5, 8, 8, 9, 10, 9, 10, 11, 11 };
		int[] result = new int[NUM_DEDGES];

		for (int index = 0; index < edges.length; index++) {
			result[pairs[index]] = pairs[edges[index]];
		}

		return result;
	}
	
	private static int[][] makeDedgeLookup() {
		int[][] result = new int[Cube.NUM_FACES][Cube.NUM_FACES];
		
		for (int i = 0; i < NUM_DEDGES; i++) {
			Dedge dedge = values()[i];
			
			int x = dedge.x / Cube.N2;
			int y = dedge.y / Cube.N2;
			
			result[x][y] = i;
			result[y][x] = i;
		}
		
		return result;
	}
	
	private static boolean isGoodEdge(int color0, int color1, int index1, int index2) {
		index1 /= Cube.N2;
		index2 /= Cube.N2;
		
		if ((color0 == Cube.W || color0 == Cube.Y) && (index1 == 2 || index1 == 4)) return false;
		
		if ((color1 == Cube.W || color1 == Cube.Y) && (index2 == 2 || index2 == 4)) return false;
		
		if ((color0 == Cube.B || color0 == Cube.G) && (index1 == 0 || index1 == 5)) return false;
		
		if ((color1 == Cube.B || color1 == Cube.G) && (index2 == 0 || index2 == 5)) return false;
		
		if ((index1 == 1 || index1 == 3) && (index2 == 0 || index2 == 5) && (color0 == Cube.W || color0 == Cube.Y)) return false;
		
		if ((index2 == 1 || index2 == 3) && (index1 == 0 || index1 == 5) && (color1 == Cube.W || color1 == Cube.Y)) return false;
		
		if ((index1 == 1 || index1 == 3) && (index2 == 2 || index2 == 4) && (color0 == Cube.B || color0 == Cube.G)) return false;
		
		if ((index2 == 1 || index2 == 3) && (index1 == 2 || index1 == 4) && (color1 == Cube.B || color1 == Cube.G)) return false;
		
		return true;
	}
}