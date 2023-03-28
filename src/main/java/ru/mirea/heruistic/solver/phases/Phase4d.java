package ru.mirea.heruistic.solver.phases;

import ru.mirea.heruistic.cube.*;

public class Phase4d extends Phase4c {
	@Override
	protected float calculateDistance(Cube cube) {
		byte[] colors = cube.getColors();
		float result = super.calculateDistance(cube);
		
		result += colors[Center.R0.x] == Cube.O ? 0 : 2;
		result += Center.R0.indexOf(cube) == Center.R1.indexOf(cube) ? 0 : 2;
		result += Center.R1.indexOf(cube) == Center.R2.indexOf(cube) ? 0 : 2;
		
		result += colors[Center.F0.x] == Cube.B ? 0 : 2;
		result += Center.F0.indexOf(cube) == Center.F1.indexOf(cube) ? 0 : 2;
		result += Center.F1.indexOf(cube) == Center.F2.indexOf(cube) ? 0 : 2;
		
		return result;
	}
}