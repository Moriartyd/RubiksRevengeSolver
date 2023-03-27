package ru.mirea.heruistic.cube;

/**
 *              ---------
 *             |  0 |  3 |
 *             -----------
 *             | 12 | 15 |
 *              ---------
 *  ---------   ---------   ---------   ---------
 * | 16 | 19 | | 32 | 35 | | 48 | 51 | | 64 | 67 |
 * ----------- ----------- ----------- -----------
 * | 28 | 31 | | 44 | 47 | | 60 | 63 | | 76 | 79 |
 *  ---------   ---------   ---------   ---------
 *              ---------
 *             | 80 | 83 |
 *             -----------
 *             | 92 | 95 |
 *              ---------
 */
public enum Corner {
	ULB( 0, 16, 67), URF(15, 48, 35), URB( 3, 51, 64), ULF(12, 19, 32),
	DLF(80, 31, 44), DRB(95, 63, 76), DRF(83, 60, 47), DLB(92, 28, 79);
	
	public static final int NUM_CORNERS = 8;
	
	public static final int[] VALUES = {
		17, 13, 25, 5, 30, 50, 38, 42
	};
	
	private static final int[][][] CORNER_LOOKUP = generateCornerLookup();
	private static final int[][][] ORIENTATION_LOOKUP = generateOrientationLookup();
	public final int x;
	public final int y;
	public final int z;
	
	private Corner(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int indexOf(Cube cube) {
		byte[] colors = cube.getColors();
		
		int value = 0;
		value += Math.pow(colors[x], 2);
		value += Math.pow(colors[y], 2);
		value += Math.pow(colors[z], 2);
		
		for (int i = 0; i < NUM_CORNERS; i++) {
			if (value == VALUES[i]) return i;
		}
		
		return -1;
	}
	
	public static int[] getConfiguration(Cube cube) {
		byte[] colors = cube.getColors();
		int[] result = new int[NUM_CORNERS];
		
		for (int i = 0; i < NUM_CORNERS; i++) {
			Corner corner = values()[i];
			
			int color0 = colors[corner.x];
			int color1 = colors[corner.y];
			int color2 = colors[corner.z];
			
			result[i] = CORNER_LOOKUP[color0][color1][color2];
		}
		
		return result;
	}
	
	public static int[] getOrientation(Cube cube) {
		byte[] colors = cube.getColors();
		int[] result = new int[NUM_CORNERS];
		
		for (int i = 0; i < NUM_CORNERS; i++) {
			Corner corner = values()[i];
			
			int color0 = colors[corner.x];
			int color1 = colors[(i / 2 & 1) == 1 ? corner.y : corner.z];
			int color2 = colors[(i / 2 & 1) == 1 ? corner.z : corner.y];
			
			result[i] = ORIENTATION_LOOKUP[color0][color1][color2];
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
	
	private static int[][][] generateCornerLookup() {
		int[][][] result = new int[Cube.NUM_FACES][Cube.NUM_FACES][Cube.NUM_FACES];
		
		for (int i = 0; i < NUM_CORNERS; i++) {
			Corner corner = values()[i];
			
			int x = corner.x / Cube.N2;
			int y = corner.y / Cube.N2;
			int z = corner.z / Cube.N2;
			
			result[x][y][z] = i;
			result[x][z][y] = i;
			result[y][x][z] = i;
			result[y][z][x] = i;
			result[z][x][y] = i;
			result[z][y][x] = i;
		}
		
		return result;
	}
	
	private static int[][][] generateOrientationLookup() {
		int[][][] result = new int[Cube.NUM_FACES][Cube.NUM_FACES][Cube.NUM_FACES];
		
		for (int i = 0; i < NUM_CORNERS; i++) {
			Corner corner = values()[i];
			
			int x = corner.x / Cube.N2;
			int y = ((i / 2 & 1) == 1 ? corner.y : corner.z) / Cube.N2;
			int z = ((i / 2 & 1) == 1 ? corner.z : corner.y) / Cube.N2;
			
			result[x][y][z] = 0;
			result[z][x][y] = 1;
			result[y][z][x] = 2;
		}
		
		return result;
	}
}
