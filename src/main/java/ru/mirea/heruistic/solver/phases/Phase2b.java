package ru.mirea.heruistic.solver.phases;

import ru.mirea.heruistic.cube.Center;
import ru.mirea.heruistic.cube.Cube;
import ru.mirea.heruistic.cube.Edge;

public class Phase2b extends Phase2a {
	@Override
	protected boolean isGoal(Cube cube) {
		return super.isGoal(cube) && getDistance(cube) == 0;
	}
	
	@Override
	protected float getDistance(Cube cube) {
		return calculateDistance(cube) / 8f;
	}
	
	protected float calculateDistance(Cube cube) {
		float result = 0;
		result += Center.R0.indexOf(cube) == Center.R2.indexOf(cube) ? 0 : 2;
		result += Center.R1.indexOf(cube) == Center.R3.indexOf(cube) ? 0 : 2;
		result += Center.L0.indexOf(cube) == Center.L2.indexOf(cube) ? 0 : 2;
		result += Center.L1.indexOf(cube) == Center.L3.indexOf(cube) ? 0 : 2;
		
		int[] edgeOrientation = Edge.getOrientation(cube);
		int count = 0;
		
		for (int edge : edgeOrientation) {
			count += edge == 1 ? 1 : 0;
		}
		
		if (count / 2 % 2 != 0) result += 2;
		
		for (int parity : Edge.getPairParity(cube)) {
			if (parity != 1) {
				result += 2;
				break;
			}
		}
		
		return result;
	}
}