package ru.mirea.heruistic.solver.phases;

import ru.mirea.heruistic.cube.*;

public class Phase4c extends Phase4b {
	@Override
	protected float calculateDistance(Cube cube) {
		byte[] colors = cube.getColors();
		float result = super.calculateDistance(cube);
		result += colors[Center.U0.x] == Cube.W ? 0 : 2;
		result += Center.U0.indexOf(cube) == Center.U1.indexOf(cube) ? 0 : 2;
		result += Center.U1.indexOf(cube) == Center.U2.indexOf(cube) ? 0 : 2;
		result += Center.U2.indexOf(cube) == Center.U3.indexOf(cube) ? 0 : 2;
		return result;
	}
}