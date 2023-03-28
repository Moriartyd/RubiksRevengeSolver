package ru.mirea.heruistic.databases;

import ru.mirea.heruistic.cube.*;

import java.util.HashSet;
import java.util.LinkedList;

/**
Runtime: 265.97695 minutes.
0	4900
1	9800
2	161700
3	1342600
4	7056000
5	23755200
6	29008000
7	1724800
 */
public class UDFBCenterDatabase extends Database {
	private static final String PATH = "data/udfbcenter.ddb";
	private static final int SIZE = 63063000;
	private static final int NUM_GOALS = 4900;
	
	private static final Center[] UDFB_CENTERS = new Center[] {
		Center.U0, Center.U1, Center.U2, Center.U3,
		Center.D0, Center.D1, Center.D2, Center.D3,
		Center.F0, Center.F1, Center.F2, Center.F3,
		Center.B0, Center.B1, Center.B2, Center.B3
	};
	
	private static UDFBCenterDatabase Instance;
	
	public static UDFBCenterDatabase getInstance() {
		if (Instance == null) {
			Instance = new UDFBCenterDatabase();
		}
		
		return Instance;
	}
	
	public UDFBCenterDatabase() {
		super(PATH, SIZE);
	}
	
	@Override
	public void run() {
		Twist[] generators = new Twist[] {
			Twist.U,  Twist.R,  Twist.F,  Twist.D,  Twist.L,  Twist.B,
			Twist.U2, Twist.R2, Twist.F2, Twist.D2, Twist.L2, Twist.B2, Twist.u2, Twist.r2, Twist.f2, Twist.d2, Twist.l2, Twist.b2,
			Twist.U_, Twist.R_, Twist.F_, Twist.D_, Twist.L_, Twist.B_
		};
		
		Cube startCube = new Cube();
		
		LinkedList<Cube> cubes = new LinkedList<Cube>();	
		HashSet<Integer> helper = new HashSet<Integer>();
		
		LinkedList<Cube> stack = new LinkedList<Cube>();
		HashSet<Integer> visitedNode = new HashSet<Integer>();
		
		boolean exit = false;
		int count = 0;
		
		for (int depth = 0; !exit; depth++) {
			stack.addLast(startCube);
			visitedNode.clear();
			
			while (!stack.isEmpty()) {
				Cube cube = stack.removeLast();
				
				if (cube.numTwists() == depth) {
					int index = index(cube);
					
					if (!helper.contains(index)) {
						helper.add(index);
						cube.clear();
						cubes.addLast(cube);
						count++;
						
						if (count == NUM_GOALS) {
							exit = true;
							break;
						}
					}
					
					continue;
				}
				
				for (Twist twist : generators) {
					if (twist.shouldAvoid(cube.lastTwist())) continue;
					
					Cube clone = cube.clone();
					clone.twist(twist);
					int index = index(clone);
					
					if (visitedNode.contains(index)) continue;
					
					visitedNode.add(index);
					stack.addLast(clone);
				}
			}
		}
		
		generators = new Twist[] {
			Twist.U,  Twist.R,  Twist.F,  Twist.D,  Twist.L,  Twist.B,            Twist.r,                      Twist.l,
			Twist.U2, Twist.R2, Twist.F2, Twist.D2, Twist.L2, Twist.B2, Twist.u2, Twist.r2, Twist.f2, Twist.d2, Twist.l2, Twist.b2,
			Twist.U_, Twist.R_, Twist.F_, Twist.D_, Twist.L_, Twist.B_,           Twist.r_,                     Twist.l_
		};
		
		generate(generators, cubes);
	}
	
	@Override
	protected int index(Cube cube) {
		byte[] colors = cube.getColors();
		int length = UDFB_CENTERS.length;
		int[] permutation = new int[length];
		
		for (int i = 0; i < length; i++) {
			permutation[i] = colors[UDFB_CENTERS[i].x];
		}
		
		return index(permutation);
	}
}