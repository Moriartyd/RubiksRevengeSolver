package ru.mirea.heruistic.databases;

import java.util.LinkedList;

import ru.mirea.heruistic.cube.*;

public class RLCenterDatabase extends Database {
	private static final String PATH = "data/rlcenter.ddb";
	private static final int SIZE = 51482970;
	private static final Center[] CENTERS = Center.values();
	private static RLCenterDatabase Instance;
	
	public static RLCenterDatabase getInstance() {
		if (Instance == null) {
			Instance = new RLCenterDatabase();
		}
		
		return Instance;
	}
	
	public RLCenterDatabase() {
		super(PATH, SIZE);
	}
	
	@Override
	public void run() {
		Twist[] generators = Twist.values();
		LinkedList<Cube> cubes = new LinkedList<Cube>();		
		cubes.addLast(new Cube());
		generate(generators, cubes);
	}
	
	@Override
	protected int index(Cube cube) {
		byte[] colors = cube.getColors();
		int length = CENTERS.length;
		int[] permutation = new int[length];
		
		for (int i = 0; i < length; i++) {
			byte color = colors[CENTERS[i].x];
			permutation[i] = color == Cube.O || color == Cube.R ? color : 0;
		}
		
		return index(permutation);
	}
}