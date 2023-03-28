package ru.mirea.heruistic.solver.phases;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import ru.mirea.heruistic.cube.Cube;
import ru.mirea.heruistic.cube.Dedge;
import ru.mirea.heruistic.cube.Twist;

public class Phase5 implements IPhase {
	protected Twist[] generators;
	protected long goalID;
	
	public Phase5() {
		generators = new Twist[] {
			Twist.U,  Twist.R,  Twist.F,  Twist.D,  Twist.L,  Twist.B,
			Twist.U2, Twist.R2, Twist.F2, Twist.D2, Twist.L2, Twist.B2,
			Twist.U_, Twist.R_, Twist.F_, Twist.D_, Twist.L_, Twist.B_
		};
		
		goalID = index(new Cube());
	}
	
	public ArrayList<Twist> search(Cube startCube, int depth) {
		LinkedList<Cube> cubes = new LinkedList<Cube>();
		System.out.println(this.getClass().getSimpleName() + " start searching");
		HashSet<Long> visitedNodes = new HashSet<Long>();
		
		cubes.addLast(startCube);
		
		while (!cubes.isEmpty()) {
			Cube cube = cubes.removeFirst();
			
			if (isDone(cube)) {
				System.out.println(this.getClass().getSimpleName() + " end searching");
				return cube.getTwists();
			}
			
			Twist lastTwist = cube.lastTwist();
			
			for (Twist twist : generators) {
				if (twist.shouldAvoid(lastTwist)) continue;
				
				Cube clone = cube.clone();
				clone.twist(twist);
				
				long index = index(clone);
				
				if (index == goalID) {
					System.out.println(this.getClass().getSimpleName() + " end searching");
					return clone.getTwists();
				}
				
				if (visitedNodes.contains(index)) {
					continue;
				}
				
				visitedNodes.add(index);
				cubes.addLast(clone);
			}
		}
		System.out.println(this.getClass().getSimpleName() + " end searching");
		return null;
	}
	
	protected boolean isDone(Cube cube) {
		return index(cube) == goalID;
	}
	
	protected long index(Cube cube) {
		long result = 0;
		int[] dedgeOrientation = Dedge.getOrientation(cube);
		
		for (int index = 0; index < dedgeOrientation.length; index++) {
			int edge = dedgeOrientation[index];
			result += (edge << index);
		}
		
		return result;
	}
}