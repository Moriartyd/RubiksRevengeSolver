package ru.mirea.heruistic.databases;

import java.util.LinkedList;
import ru.mirea.heruistic.cube.*;

/**
Runtime: 0.14906666666666665 minutes.
0	1
1	7
2	54
3	294
4	1948
5	7104
6	7648
7	368
 */
public class BackEdgeDatabase extends Database {
	private static final String PATH = "data/backedge.ddb";
	private static final int SIZE = 17424;
	private static final int MULTIPLIER = 132; //12!/10!
	private static final int NUM_EDGES = 12;
	
	private static final Edge[] EDGES0 = new Edge[] {
		Edge.UB1, Edge.UR1, Edge.UL1, Edge.UF1,
		Edge.BR0, Edge.FL0, Edge.FR0, Edge.BL0,
		Edge.DF1, Edge.DR1, Edge.DL1, Edge.DB1
	};
	
	private static final Edge[] EDGES1 = new Edge[] {
		Edge.UB0, Edge.UL0, Edge.UR0, Edge.UF0,
		Edge.BL1, Edge.FR1, Edge.FL1, Edge.BR1,
		Edge.DF0, Edge.DL0, Edge.DR0, Edge.DB0
	};
	
	private static BackEdgeDatabase Instance;
	
	public static BackEdgeDatabase getInstance() {
		if (Instance == null) {
			Instance = new BackEdgeDatabase();
		}
		
		return Instance;
	}
	
	public BackEdgeDatabase() {
		super(PATH, SIZE);
	}
	
	@Override
	public void run() {
		Twist[] generators = new Twist[] {
			Twist.U,            Twist.F,  Twist.D,            Twist.B,
			Twist.U2, Twist.R2, Twist.F2, Twist.D2, Twist.L2, Twist.B2, Twist.u2, Twist.r2, Twist.f2, Twist.d2, Twist.l2, Twist.b2,
			Twist.U_,           Twist.F_, Twist.D_,           Twist.B_
		};
		
		LinkedList<Cube> cubes = new LinkedList<Cube>();		
		cubes.addLast(new Cube());
		
		generate(generators, cubes);
	}
	
	@Override
	protected int index(Cube cube) {
		int[] permutation = new int[NUM_EDGES];
		
		for (int i = 0; i < NUM_EDGES; i++) {
			int idx = EDGES0[i].indexOf(cube);
			
			if (idx != Edge.BR0.ordinal() && idx != Edge.BL1.ordinal()) idx = -1;
			
			permutation[i] = idx;
		}
		
		int index = index(permutation) * MULTIPLIER;
		
		for (int i = 0; i < NUM_EDGES; i++) {
			int idx = EDGES1[i].indexOf(cube);
			
			if (idx != Edge.BR0.ordinal() && idx != Edge.BL1.ordinal()) idx = -1;
			permutation[i] = idx;
		}
		
		index += index(permutation);
		return index;
	}
}