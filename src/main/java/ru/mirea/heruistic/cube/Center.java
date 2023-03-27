package ru.mirea.heruistic.cube;

/**
 *              ---------
 *             |  5 |  6 |
 *             -----------
 *             |  9 | 10 |
 *              ---------
 *  ---------   ---------   ---------   ---------
 * | 21 | 22 | | 37 | 38 | | 53 | 54 | | 69 | 70 |
 * ----------- ----------- ----------- -----------
 * | 25 | 26 | | 41 | 42 | | 57 | 58 | | 73 | 74 |
 *  ---------   ---------   ---------   ---------
 *              ---------
 *             | 85 | 86 |
 *             -----------
 *             | 89 | 90 |
 *              ---------
 */
public enum Center {
	U0( 5), U1( 6), U2( 9), U3(10),
	R0(53), R1(54), R2(57), R3(58),
	F0(37), F1(38), F2(41), F3(42),
	D0(85), D1(86), D2(89), D3(90),
	L0(21), L1(22), L2(25), L3(26),
	B0(69), B1(70), B2(73), B3(74);
	
	public static final int NUM_CENTERS = 24;
	
	public static final int[] VALUES = {
		0, 0, 0, 0,
		3, 3, 3, 3,
		2, 2, 2, 2,
		5, 5, 5, 5,
		1, 1, 1, 1,
		4, 4, 4, 4
	};
	
	public final int x;
	
	private Center(int x) {
		this.x = x;
	}
	
	public int indexOf(Cube cube) {
		byte[] colors = cube.getColors();
		
		for (int i = 0; i < NUM_CENTERS; i++) {
			if (colors[x] == VALUES[i]) return i;
		}
		
		return -1;
	}
}