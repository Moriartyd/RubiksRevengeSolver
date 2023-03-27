package ru.mirea.heruistic.cube;

public enum Twist {
	U,  R,  F,  D,  L,  B,  u,  r,  f,  d,  l,  b,
	U2, R2, F2, D2, L2, B2, u2, r2, f2, d2, l2, b2,
	U_, R_, F_, D_, L_, B_, u_, r_, f_, d_, l_, b_;
	
	public static final int NUM_UNIQUE_TWIST = 12;
	
	public static final Integer[][] SWAP_MAP = {
		{1,    2,    null},
		{2,    null, 0},
		{null, 0,    1}
	};
	
	private static final int[] UP = {
		67,19,35,51, 66,18,34,50, 65,17,33,49, 64,16,32,48,
		0,12,15,3, 1,8,14,7, 2,4,13,11, 5,9,10,6
	};
	
	private static final int[] RIGHT = {
		15,47,95,64, 11,43,91,68, 7,39,87,72, 3,35,83,76,
		48,60,63,51, 49,56,62,55, 50,52,61,59, 53,57,58,54
	};
	
	private static final int[] FRONT = {
		12,31,83,48, 13,27,82,52, 14,23,81,56, 15,19,80,60,
		32,44,47,35, 33,40,46,39, 34,36,45,43, 37,41,42,38
	};
	
	private static final int[] DOWN = {
		44,28,76,60, 45,29,77,61, 46,30,78,62, 47,31,79,63,
		80,92,95,83, 81,88,94,87, 82,84,93,91, 85,89,90,86
	};
	
	private static final int[] LEFT = {
		0,79,80,32, 4,75,84,36, 8,71,88,40, 12,67,92,44,
		16,28,31,19, 17,24,30,23, 18,20,29,27, 21,25,26,22
	};
	
	private static final int[] BACK = {
		3,63,92,16, 2,59,93,20, 1,55,94,24, 0,51,95,28,
		64,76,79,67, 65,72,78,71, 66,68,77,75, 69,73,74,70
	};
	
	private static final int[] up = {
		71,23,39,55, 70,22,38,54, 69,21,37,53, 68,20,36,52
	};
	
	private static final int[] right = {
		14,46,94,65, 10,42,90,69, 6,38,86,73, 2,34,82,77
	};
	
	private static final int[] front = {
		8,30,87,49, 9,26,86,53, 10,22,85,57, 11,18,84,61
	};
	
	private static final int[] down = {
		40,24,72,56, 41,25,73,57, 42,26,74,58, 43,27,75,59
	};
	
	private static final int[] left = {
		1,78,81,33, 5,74,85,37, 9,70,89,41, 13,66,93,45
	};
	
	private static final int[] back = {
		7,62,88,17, 6,58,89,21, 5,54,90,25, 4,50,91,29
	};
	
	private static final int[][] INDICES = {
		UP, RIGHT, FRONT, DOWN, LEFT, BACK,
		up, right, front, down, left, back
	};
	
	public int numTwists() {
		return ordinal() / NUM_UNIQUE_TWIST;
	}
	
	public int[] indices() {
		return INDICES[ordinal() % NUM_UNIQUE_TWIST];
	}
	
	public boolean shouldAvoid(Twist twist) {
		if (twist == null) return false;
		
		int value = twist.ordinal() % NUM_UNIQUE_TWIST;
		return ordinal() % NUM_UNIQUE_TWIST == value;
	}
	
	public int axis() {
		return ordinal() % 3;
	}
	
	public int rotation() {
		return (ordinal() / 3) % 2 == 0 ? -90 : 90;
	}
	
	@Override
	public String toString() {
		return super.toString().replace('_', '\'');
	}
	
	public static Twist swap(Twist last, Twist next) {
		int l = last.ordinal();
		int n = next.ordinal();
		int lDiv = l / NUM_UNIQUE_TWIST;
		int nDiv = n / NUM_UNIQUE_TWIST;
		Integer change = SWAP_MAP[lDiv][nDiv];
		
		if (change == null) return null;
		
		return values()[change * NUM_UNIQUE_TWIST + l % NUM_UNIQUE_TWIST];
	}
}